package com.edassist.service.impl;

import com.edassist.constants.ApplicationConstants;
import com.edassist.dao.GenericDao;
import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.*;
import com.edassist.models.domain.ApplicationCourses.QUESTION_TYPE;
import com.edassist.models.sp.ApplicationCourseCompliancy;
import com.edassist.models.sp.PercentagePayOut;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.*;
import com.edassist.utils.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

@Service
public class ApplicationCoursesServiceImpl extends GenericServiceImpl<ApplicationCourses> implements ApplicationCoursesService {

	private static Logger log = Logger.getLogger(ApplicationCoursesServiceImpl.class);
	protected static final int ONE_TAX_QUESTION = 1;
	protected static final int THREE_TAX_QUESTIONS = 3;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	private GenericService<CourseMethod> courseMethodService;

	@Autowired
	private CourseHistoryService courseHistoryService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private GradesService gradesService;

	@Autowired
	private GenericDao<ApplicationCourses> applicationCoursesDao;

	@Autowired
	private BenefitPeriodService benefitPeriodService;

	public ApplicationCoursesServiceImpl() {
	}

	@Autowired
	public ApplicationCoursesServiceImpl(@Qualifier("applicationCoursesDao") GenericDao<ApplicationCourses> genericDao) {
		super(genericDao);
	}

	@Override
	public void saveOrUpdate(ApplicationCourses entity) {
		super.saveOrUpdate(entity);
	}

	@Override
	public void remove(ApplicationCourses entity) {
		// dont delete the course/expense, just clear out any $ amounts
		if (entity == null) {
			return;
		}

		entity.setAmountPaid(null);
		entity.setAmountReportedPaid(null);
		entity.setDiscountAmount(null);
		entity.setFeesAmount(null);
		entity.setGradeID(null);
		entity.setNumberOfBooks(null);
		entity.setRefundAmount(null);
		entity.setRequestedFees(null);
		entity.setRequestedTuition(null);
		entity.setTaxability(null);
		entity.setTuitionAmount(null);

		/* TAM-2248 */
		entity.setCreditHours(null);

		/* TAM-2480 */
		if (entity.getCourseName() != null) {
			if (entity.getCourseName().length() > 32) {
				entity.setCourseName("DELETED-" + entity.getCourseName().substring(0, 32));
			} else {
				entity.setCourseName("DELETED-" + entity.getCourseName());
			}
		}

		Grades grades = gradesService.findById(new Long(21 /* 21 == "NI" */));
		entity.setGradeID(grades);

		/* TAM-2480 */
		if (entity.getCourseNumber() != null) {
			if (entity.getCourseNumber().length() > 8) {
				entity.setCourseNumber("X-" + entity.getCourseNumber().substring(0, 8));
			} else {
				entity.setCourseNumber("X-" + entity.getCourseNumber());
			}
		}

		saveOrUpdate(entity);
	}

	@Override
	public ApplicationCourses createCourse(Long applicationId, String courseNumber, String courseName, BigDecimal tuitionAmount, BigDecimal refundAmount, Double creditHours, Long courseMethodId,
			String maintainSkillsYN, String meetMinimumQualsYN, String newCareerFieldYN, Long gradeId, String courseDescriptionURL, String courseSchedule) {

		ApplicationCourses newCourse = new ApplicationCourses();

		try {
			Application application = applicationService.findById(applicationId);

			if (application == null) {
				throw new BadRequestException("Application must not be null.  ApplicationId: [" + applicationId + "]");
			}

			newCourse.setApplicationID(application);
			newCourse.setCourseNumber(courseNumber);
			newCourse.setCourseName(courseName);
			newCourse.setRequestedTuition(tuitionAmount);
			newCourse.setTuitionAmount(tuitionAmount);
			newCourse.setCourseDescriptionURL(courseDescriptionURL);
			newCourse.setCourseSchedule(courseSchedule);
			if (refundAmount != null) {
				newCourse.setRefundAmount(refundAmount);
			}

			newCourse.setCreditHours(creditHours);

			CourseMethod courseMethod = courseMethodService.findById(courseMethodId);
			newCourse.setCourseMethod(courseMethod);

			if (gradeId != null) {
				Grades grade = gradesService.findById(gradeId);
				newCourse.setGradeID(grade);
			} else {
				Grades grade = gradesService.findById(-1L);
				newCourse.setGradeID(grade);
			}

			// TODO Modify them to boolean both database/code
			newCourse.setMaintainSkillsYN(ApplicationCourses.convertToQuestionType(maintainSkillsYN));
			newCourse.setMeetMinimumQualsYN(ApplicationCourses.convertToQuestionType(meetMinimumQualsYN));
			newCourse.setNewCareerFieldYN(ApplicationCourses.convertToQuestionType(newCareerFieldYN));
			newCourse.setTaxability(calculateTaxability(application, newCourse.getMaintainSkillsYN(), newCourse.getMeetMinimumQualsYN(), newCourse.getNewCareerFieldYN()));

			if (tuitionAmount == null || tuitionAmount.equals(BigDecimal.ZERO)) {
				newCourse.setDiscountAmount(BigDecimal.ZERO);
			} else {
				newCourse.setDiscountAmount(benefitPeriodService.calculateDiscountAmount(tuitionAmount, benefitPeriodService.fetchDiscountAmount(application)));
			}

			if (application.getApplicationCoursesCollection() == null) {
				application.setApplicationCoursesCollection(new ArrayList<ApplicationCourses>());

			}

			this.persistApplicationCourseOrExpense(newCourse);
			application.getApplicationCoursesCollection().add(newCourse);

			applicationService.saveOrUpdate(application);

			// TAM-3308
			if (application.getApplicationStatusID().getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_REVIEW)
					|| application.getApplicationStatusID().getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_INCOMPLETE_COMPLETION_DOCUMENTS_REQUIRED)) {
				log.debug("Course added at APPLICATION_STATUS_PAYMENT_REVIEW and APPLICATION_STATUS_PAYMENT_INCOMPLETE_COMPLETION_DOCUMENTS_REQUIRED");

				// Create a place holder for approved amount
				Long approvedChangeId = courseHistoryService.findApprovedChangeId(application);
				CourseHistory courseHist = new CourseHistory(application, approvedChangeId, application.getOriginalStatusID(), application.getApplicationStatusID(), newCourse,
						newCourse.getCourseNumber(), newCourse.getCourseName(), newCourse.getTuitionAmount(), newCourse.getFeesAmount(), newCourse.getDiscountAmount(), newCourse.getGradeID(),
						newCourse.getTaxability(), newCourse.getCreditHours(), newCourse.getMaintainSkillsYN(), newCourse.getMeetMinimumQualsYN(), newCourse.getNewCareerFieldYN(), new Date(), null,
						newCourse.getCreatedBy(), null);
				courseHistoryService.saveOrUpdate(courseHist);

				// Apply Payout if needed
				if (application.getBenefitPeriodID().getProgramID().getApplyPercentagePayOut() == null
						|| application.getBenefitPeriodID().getProgramID().getApplyPercentagePayOut().equals(Boolean.FALSE)) {
					log.debug("No Payout set up.");
				} else {
					BigDecimal payout = BigDecimal.valueOf(100L);
					List<PercentagePayOut> payoutList = applicationService.callFindPercentagePayoutForAppCourse(newCourse.getApplicationCoursesID());
					if (payoutList != null && !payoutList.isEmpty()) {
						PercentagePayOut percentagePayOutFromDB = payoutList.get(0);
						if (percentagePayOutFromDB != null && percentagePayOutFromDB.getValue() != null) {
							payout = percentagePayOutFromDB.getValue();
						}
					}
					log.debug("ApplyPercentagePayout for  applicationCourseID :" + newCourse.getApplicationCoursesID() + " from DB is : " + payout);
					BigDecimal tuiAmount = (newCourse.getTuitionAmount()).multiply((payout).divide(BigDecimal.valueOf(100L)), MathContext.DECIMAL32);
					newCourse.setTuitionAmount(tuiAmount);
					newCourse.setPercentagePayOut(payout);
					applicationCoursesDao.saveOrUpdate(newCourse);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return newCourse;
	}

	@Override
	public QUESTION_TYPE calculateTaxability(Application application, QUESTION_TYPE maintainSkillsYN, QUESTION_TYPE meetMinimumQualsYN, QUESTION_TYPE newCareerFieldYN) {
		// TODO Auto-generated method stub
		if (application == null) {
			throw new BadRequestException("Applicaton must not be null.");
		}
		if (application.getBenefitPeriodID() == null || application.getBenefitPeriodID().getProgramID() == null) {
			throw new BadRequestException("Program must not be null.");
		}

		Integer numberOfTaxQuestions = application.getBenefitPeriodID().getProgramID().getTaxQuestionsCount();

		if (numberOfTaxQuestions == null || numberOfTaxQuestions == 0) {
			return QUESTION_TYPE.N;
		} else if (numberOfTaxQuestions == ONE_TAX_QUESTION) {
			return maintainSkillsYN;
		} else if (numberOfTaxQuestions == THREE_TAX_QUESTIONS) {
			if (maintainSkillsYN == null) {
				return QUESTION_TYPE.N;
			} else {
				if (QUESTION_TYPE.Y.equals(maintainSkillsYN) && QUESTION_TYPE.N.equals(meetMinimumQualsYN) && QUESTION_TYPE.N.equals(newCareerFieldYN)) {
					return QUESTION_TYPE.Y;
				} else {
					return QUESTION_TYPE.N;
				}
			}
		} else {
			throw new BadRequestException("Unsupported number of tax questions: " + numberOfTaxQuestions);
		}
	}

	@Override
	public String getPopUpStringForCourse(String clientCode, String programCode, String ContentName) {
		// Course Taxable PopUp Info as course is not tax exempt

		String coursePopUpInfo = null;

		// TODO: content call
		// ExampleContent courseTaxablePopUpInfoExample =
		// ExampleContent.createClientExampleContentTake3(clientCode,
		// programCode, /*Program*/
		// Criterion.VAL_CONTENT_TYPE_PAGECONTENT, /* Content Type */
		// Criterion.VAL_CONTENT_CATEGORY_APPLICATION_STEP_2, /* Content Category */
		// ContentName); /* Content Name */

		String coursePopUpInfoContent = "todo";

		if (coursePopUpInfoContent != null) {
			coursePopUpInfo = "todo";
		}
		return coursePopUpInfo;
	}

	@Override
	public void persistApplicationCourseOrExpense(ApplicationCourses courseOrExpense) {
		try {
			Long userId = sessionService.getClaimAsLong(JWTTokenClaims.USER_ID);
			if (courseOrExpense.getApplicationCoursesID() == null || courseOrExpense.getApplicationCoursesID() == 0L) {
				courseOrExpense.setDateCreated(new Date());
				courseOrExpense.setCreatedBy(userId);
			} else {
				courseOrExpense.setModifiedDate(new Date());
				courseOrExpense.setModifiedBy(userId);
			}

			saveOrUpdate(courseOrExpense);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ApplicationCourses> getApplicationCoursesWithGradeCompliance(Application application) {

		List<ApplicationCourses> applicationCoursesList = null;

		try {
			String[] paramNames = { "applicationID", "expenseType", "tuitionAmount" };
			Object[] paramValues = { application, null, CommonUtil.RESTRICTION_NOT_NULL };
			applicationCoursesList = this.findByParams(paramNames, paramValues, null, null);
			if (CollectionUtils.isNotEmpty(applicationCoursesList)) {
				for (ApplicationCourses currCourse : applicationCoursesList) {
					if (currCourse != null) {
						ApplicationCourseCompliancy currCourseCompliancy = applicationService.getApplicationCourseCompliancyProc(currCourse.getApplicationCoursesID());
						currCourse.setGradeCompliance(currCourseCompliancy.getCompliancy());
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}

		return applicationCoursesList;
	}

	@Override
	public ApplicationCourses deleteCourse(Long courseId) {

		ApplicationCourses course = this.findById(courseId);

		if (course == null) {
			throw new BadRequestException("Course must not be null.  courseId: [" + courseId + "]");
		}

		// find all the expenses related to this course
		List<ApplicationCourses> relatedExpenses = this.findByParam("relatedCourseID.applicationCoursesID", courseId);

		if (relatedExpenses != null && !relatedExpenses.isEmpty()) {
			for (ApplicationCourses currExpense : relatedExpenses) {
				if (currExpense != null) {
					if (currExpense.isBookExpense()) {
						// only prepay apps create a separate app for the book
						Application application = currExpense.getApplicationID();
						if (application == null) {
							throw new BadRequestException("Course application must not be null.");
						}

						if (application.isBookApplication()) {
							application.getApplicationCoursesCollection().remove(currExpense);
							this.remove(currExpense);
							if (application.getApplicationCoursesCollection().isEmpty()) {
								// TODO DBJ: should create a "code" column rather than using the db id
								ApplicationStatus cancelledStatus = applicationStatusService.findById(ApplicationStatus.STATUS_CODE_CANCELLED);
								application.setApplicationStatusID(cancelledStatus, sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
							}

							applicationService.saveOrUpdate(application);
						} else {
							this.remove(currExpense);
						}
					} else {
						this.remove(currExpense);
					}
				}
			}
		}
		this.remove(course);
		return course;
	}

	@Override
	public BigDecimal getApprovedAmount(BigDecimal requestedAmount, Long appStatusId, List<CourseHistory> historyList, boolean isCourse) {
		List<Long> nonApprovedStatuses = new ArrayList<>(Arrays.asList(ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW, ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE,
				ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW, ApplicationConstants.APPLICATION_STATUS_CARRYOVER_PAYMENT_APPROVED));

		if (appStatusId.equals(ApplicationConstants.APPLICATION_STATUS_SAVED_NOT_SUBMITTED) || appStatusId > ApplicationConstants.APPLICATION_STATUS_CLOSED) {
			return BigDecimal.ZERO;
		} else if (nonApprovedStatuses.contains(appStatusId)) {
			return requestedAmount;
		} else {
			List<Long> approvedStatuses = new ArrayList<>(Arrays.asList(ApplicationConstants.APPLICATION_STATUS_APPROVED, ApplicationConstants.APPLICATION_STATUS_LOC));
			CourseHistory lastApprovedEntry = getLastHistoryForStatus(historyList, approvedStatuses);
			if (lastApprovedEntry != null) {
				if (isCourse) {
					return lastApprovedEntry.getTuitionAmount();
				} else {
					return lastApprovedEntry.getFeesAmount();
				}
			} else {
				return requestedAmount;
			}
		}
	}

	@Override
	public BigDecimal getPaidAmount(BigDecimal requestedAmount, Long appStatusId, List<CourseHistory> historyList, boolean isCourse) {
		if (appStatusId < ApplicationConstants.APPLICATION_STATUS_PAYMENT_REVIEW || appStatusId > ApplicationConstants.APPLICATION_STATUS_CLOSED) {
			return BigDecimal.ZERO;
		} else if (appStatusId.equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_REVIEW) || appStatusId
				.equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_PROCESSING_INCOMPLETE_INFORMATION_NEEDED)) {
			return requestedAmount;
		} else {
			List<Long> paidStatuses = new ArrayList<>(Arrays.asList(ApplicationConstants.APPLICATION_STATUS_PAYMENT_REIMBURSEMENT_PROGRESS));
			CourseHistory lastPaidEntry = getLastHistoryForStatus(historyList, paidStatuses);
			if (lastPaidEntry != null) {
				if (isCourse) {
					return lastPaidEntry.getTuitionAmount();
				} else {
					return lastPaidEntry.getFeesAmount();
				}
			} else {
				return BigDecimal.ZERO;
			}
		}
	}

	private CourseHistory getLastHistoryForStatus(List<CourseHistory> historyList, List<Long> statusList) {
		return historyList.stream().filter(h -> statusList.contains(h.getApplicationStatus().getApplicationStatusID())).findFirst().orElse(null);
	}

}
