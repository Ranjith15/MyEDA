package com.edassist.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edassist.constants.ApplicationConstants;
import com.edassist.constants.ContentConstants;
import com.edassist.dao.GenericDao;
import com.edassist.dao.ProgramExpenseTypeDao;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.Application;
import com.edassist.models.domain.ApplicationCourses;
import com.edassist.models.domain.ApplicationCourses.QUESTION_TYPE;
import com.edassist.models.sp.ApplicationNumber;
import com.edassist.models.domain.ApplicationType;
import com.edassist.models.domain.BenefitPeriod;
import com.edassist.models.domain.CourseHistory;
import com.edassist.models.domain.ExpenseType;
import com.edassist.models.sp.PercentagePayOut;
import com.edassist.models.domain.Program;
import com.edassist.models.domain.ProgramExpenseType;
import com.edassist.models.domain.type.ProgramAndApplicationTypeCode;
import com.edassist.models.dto.ProgramDTO;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.ApplicationCoursesService;
import com.edassist.service.ApplicationService;
import com.edassist.service.BenefitPeriodService;
import com.edassist.service.CourseHistoryService;
import com.edassist.service.GenericService;
import com.edassist.service.ProgramExpenseTypeService;
import com.edassist.service.ProgramService;
import com.edassist.service.RulesService;
import com.edassist.service.SessionService;
import com.edassist.utils.CommonUtil;

@Service
public class ProgramExpenseTypeServiceImpl extends GenericServiceImpl<ProgramExpenseType> implements ProgramExpenseTypeService {

	private static Logger log = Logger.getLogger(ProgramExpenseTypeServiceImpl.class);

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private GenericService<ApplicationType> applicationTypeService;

	@Autowired
	private ApplicationCoursesService applicationCoursesService;

	@Autowired
	private CourseHistoryService courseHistoryService;

	@Autowired
	private GenericService<ExpenseType> expenseTypeService;

	@Autowired
	private BenefitPeriodService benefitPeriodService;

	@Autowired
	private RulesService rulesService;

	@Autowired
	private ProgramService programService;

	@Autowired
	private GenericDao<ApplicationCourses> applicationCoursesDao;

	private ProgramExpenseTypeDao programExpenseTypeDao;

	public ProgramExpenseTypeServiceImpl() {
	}

	@Autowired
	public ProgramExpenseTypeServiceImpl(@Qualifier("programExpenseTypeDaoImpl") GenericDao<ProgramExpenseType> genericDao) {
		super(genericDao);
		this.programExpenseTypeDao = (ProgramExpenseTypeDao) genericDao;
	}

	@Override
	public List<ProgramExpenseType> search(Long programId, Long programExpenseTypeId) throws ExceededMaxResultsException {
		return programExpenseTypeDao.search(programId, programExpenseTypeId);
	}

	@Override
	public ApplicationCourses createExpense(Long applicationId, Long expenseTypeId, Long relatedCourseId, BigDecimal feesAmount, Integer numberOfBooks, String maintainSkillsYN,
			String meetMinimumQualsYN, String newCareerFieldYN) throws Exception {

		Map<String, Object> jsonResultList = new HashMap<String, Object>();

		Application application = applicationService.findById(applicationId);
		if (application == null) {
			throw new BadRequestException("Application must not be null.  ApplicationId: [" + applicationId + "]");
		}

		ApplicationCourses newExpense = new ApplicationCourses();
		newExpense.setApplicationID(application);
		if (expenseTypeId != null) {
			newExpense.setExpenseType(expenseTypeService.findById(expenseTypeId));
		}
		newExpense.setFeesAmount(feesAmount);
		newExpense.setNumberOfBooks(numberOfBooks);

		if (relatedCourseId != null) {
			newExpense.setRelatedCourseID(applicationCoursesService.findById(relatedCourseId));
			newExpense.setMaintainSkillsYN(newExpense.getRelatedCourseID().getMaintainSkillsYN());
			newExpense.setMeetMinimumQualsYN(newExpense.getRelatedCourseID().getMeetMinimumQualsYN());
			newExpense.setNewCareerFieldYN(newExpense.getRelatedCourseID().getNewCareerFieldYN());
			newExpense.setTaxability(newExpense.getRelatedCourseID().getTaxability());
		} else {
			newExpense.setMaintainSkillsYN(ApplicationCourses.convertToQuestionType(maintainSkillsYN));
			newExpense.setMeetMinimumQualsYN(ApplicationCourses.convertToQuestionType(meetMinimumQualsYN));
			newExpense.setNewCareerFieldYN(ApplicationCourses.convertToQuestionType(newCareerFieldYN));
			newExpense
					.setTaxability(applicationCoursesService.calculateTaxability(application, newExpense.getMaintainSkillsYN(), newExpense.getMeetMinimumQualsYN(), newExpense.getNewCareerFieldYN()));
		}

		String courseTaxPopUpInfo = null;
		if (QUESTION_TYPE.Y == newExpense.getTaxability()) {
			courseTaxPopUpInfo = applicationCoursesService.getPopUpStringForCourse(newExpense.getApplicationID().getParticipantID().getClient().getClientCode(),
					newExpense.getApplicationID().getBenefitPeriodID().getProgramID().getProgramCode(), ContentConstants.VAL_NAME_EXPENSE_NONTAXABLE_POPUP);
		} else {
			courseTaxPopUpInfo = applicationCoursesService.getPopUpStringForCourse(newExpense.getApplicationID().getParticipantID().getClient().getClientCode(),
					newExpense.getApplicationID().getBenefitPeriodID().getProgramID().getProgramCode(), ContentConstants.VAL_NAME_EXPENSE_TAXABLE_POPUP);
		}
		jsonResultList.put("courseTaxPopUpInfo", CommonUtil.stripHtmlTags(courseTaxPopUpInfo));

		// sync requestedFees with fees for rules
		if (feesAmount != null) {
			if ((application.getApplicationStatusID().getApplicationStatusID() < 120) || (application.getApplicationStatusID().getApplicationStatusID() == 125)) {
				newExpense.setRequestedFees(feesAmount);
			}
		}

		// if the expense is a book expense then we need to add the expense to a Reimburse Book Application
		if (application.isPrePaymentApplication() && newExpense.isBookExpense()) {
			// ExpenseType 3 is a book expense
			ExpenseType bookExpenseType = expenseTypeService.findById(new Long(3));
			List<ProgramExpenseType> listProgramExpenseType = programExpenseTypeDao.findByParams(new String[] { "programID", "expenseTypeID" },
					new Object[] { application.getBenefitPeriodID().getProgramID(), bookExpenseType }, null, null);
			if (listProgramExpenseType != null && listProgramExpenseType.size() > 0) {
				newExpense.setApplicationID(application);
				if (application.getApplicationCoursesCollection() == null) {
					application.setApplicationCoursesCollection(new ArrayList<ApplicationCourses>());
				}
				applicationCoursesService.persistApplicationCourseOrExpense(newExpense);
				application.getApplicationCoursesCollection().add(newExpense);
			} else {
				Application bookApplication = null;
				List<Application> relatedBookApps = applicationService.findRelatedBookApplications(application.getApplicationID());
				if (relatedBookApps != null && relatedBookApps.size() > 0) {
					// if multiple bookapps are found then use the first one one in the list.
					// the applicationService should already filter out cancelled/deleted apps
					bookApplication = relatedBookApps.get(0);
				} else {
					List<ProgramDTO> bookProgramList = rulesService.retrieveEligibleBookPrograms(application.getParticipantID().getClient().getClientId(),
							application.getParticipantID().getParticipantId());

					if (bookProgramList == null || bookProgramList.isEmpty() == true) {
						log.debug("Failed to get an eligible book program and hence can not create an book expense");
						throw new BadRequestException("Failed to get an eligible book program and hence can not create an book expense");
					} else {
						// Assuming only one book program is returned by rules may need revisiting
						ProgramDTO programDTO = bookProgramList.get(0);
						Program program = programService.findById(programDTO.getProgramID());

						// TODO DBJ: MOVE THIS TO A UTIL CLASS, CACHE THE RESULT
						List<ApplicationType> appTypesByCode = applicationTypeService.findByParam("applicationTypeCode", ProgramAndApplicationTypeCode.BFR);
						if (appTypesByCode == null || appTypesByCode.size() != 1) {
							throw new BadRequestException("Expected 1 application type matching ApplicationTypeCode: [" + ProgramAndApplicationTypeCode.BFR + "]");
						}
						ApplicationType bookType = appTypesByCode.get(0);

						// create a new book application
						bookApplication = application.createBookApp(application.getPrepayTuitionApp(), bookType, 0.0, sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
						try {
							benefitPeriodService.adjustBenefitPeriod(bookApplication);
						} catch (Exception ex) {
							BenefitPeriod defaultBenefitPeriod = benefitPeriodService.findById(program.getDefaultBenefitPeriodID());
							bookApplication.setBenefitPeriodID(defaultBenefitPeriod);
							log.debug("Leaving default benefitperiod for Book Application");
							List<ApplicationNumber> nextApplicationNumberList = applicationService.getNextApplicationNumber();
							ApplicationNumber an = nextApplicationNumberList.get(0);
							bookApplication.setApplicationNumber(an.getApplicationNumber());
							applicationService.saveOrUpdate(bookApplication);
						}
					}
				}

				if (bookApplication.getApplicationCoursesCollection() == null) {
					bookApplication.setApplicationCoursesCollection(new ArrayList<ApplicationCourses>());
				}
				newExpense.setApplicationID(bookApplication);
				bookApplication.getApplicationCoursesCollection().add(newExpense);
				applicationCoursesService.persistApplicationCourseOrExpense(newExpense);
				application.setDateModified(new Date());
				application.setModifiedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));

				applicationService.saveOrUpdate(bookApplication);
			}
		} else {

			newExpense.setApplicationID(application);
			if (application.getApplicationCoursesCollection() == null) {
				application.setApplicationCoursesCollection(new ArrayList<ApplicationCourses>());
			}
			applicationCoursesService.persistApplicationCourseOrExpense(newExpense);
			application.getApplicationCoursesCollection().add(newExpense);
		}

		// TAM-3308
		if (application.getApplicationStatusID().getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_REVIEW)
				|| application.getApplicationStatusID().getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_INCOMPLETE_COMPLETION_DOCUMENTS_REQUIRED)) {
			log.error("Expense added at APPLICATION_STATUS_PAYMENT_REVIEW and APPLICATION_STATUS_PAYMENT_INCOMPLETE_COMPLETION_DOCUMENTS_REQUIRED");

			// Create a place holder for approved amount
			log.error("Creating a place holder for approved amount");
			Long approvedChangeId = courseHistoryService.findApprovedChangeId(application);
			CourseHistory courseHist = new CourseHistory(application, approvedChangeId, application.getOriginalStatusID(), application.getApplicationStatusID(), newExpense,
					newExpense.getCourseNumber(), newExpense.getCourseName(), newExpense.getTuitionAmount(), newExpense.getFeesAmount(), newExpense.getDiscountAmount(), newExpense.getGradeID(),
					newExpense.getTaxability(), newExpense.getCreditHours(), newExpense.getMaintainSkillsYN(), newExpense.getMeetMinimumQualsYN(), newExpense.getNewCareerFieldYN(), new Date(), null,
					newExpense.getCreatedBy(), null);
			courseHistoryService.saveOrUpdate(courseHist);

			// Apply Payout if needed
			if (application.getBenefitPeriodID().getProgramID().getApplyPercentagePayOut() != null || application.getBenefitPeriodID().getProgramID().getApplyPercentagePayOut().equals(Boolean.TRUE)) {
				BigDecimal payout = BigDecimal.valueOf(100L);
				List<PercentagePayOut> payoutList = applicationService.callFindPercentagePayoutForAppCourse(newExpense.getApplicationCoursesID());
				if (payoutList != null && !payoutList.isEmpty()) {
					PercentagePayOut percentagePayOutFromDB = payoutList.get(0);
					if (percentagePayOutFromDB != null && percentagePayOutFromDB.getValue() != null) {
						payout = percentagePayOutFromDB.getValue();
					}
				}
				log.debug("applyPercentagePayout for  applicationCourseID :" + newExpense.getApplicationCoursesID() + " from DB is : " + payout);
				BigDecimal feeAmount = (newExpense.getFeesAmount()).multiply((payout).divide(BigDecimal.valueOf(100L)), MathContext.DECIMAL32);
				newExpense.setFeesAmount(feeAmount);
				newExpense.setPercentagePayOut(payout);
				applicationCoursesDao.saveOrUpdate(newExpense);
			}
		}

		return newExpense;
	}

}
