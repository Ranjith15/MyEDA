package com.edassist.service.impl;

import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.IntStream;

import com.edassist.models.sp.ApplicationCourseCompliancy;
import com.edassist.models.sp.ApplicationNumber;
import com.edassist.models.sp.PercentagePayOut;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.edassist.constants.*;
import com.edassist.dao.ApplicationDao;
import com.edassist.dao.GenericDao;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.contracts.Option;
import com.edassist.models.domain.*;
import com.edassist.models.domain.type.ProgramAndApplicationTypeCode;
import com.edassist.models.dto.ApplicationSubmissionDTO;
import com.edassist.models.dto.ExpenseSnapshotDTO;
import com.edassist.models.dto.RulesDTO;
import com.edassist.models.dto.RulesOutputDTO;
import com.edassist.models.mappers.ApplicationStatusMapper;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.*;
import com.edassist.utils.CommonUtil;

@Service
public class ApplicationServiceImpl extends GenericServiceImpl<Application> implements ApplicationService {

	private static Logger log = Logger.getLogger(ApplicationServiceImpl.class);

	@Autowired
	private GenericService<Payments> paymentService;

	@Autowired
	private GenericService<Refunds> refundsService;

	@Autowired
	private RulesService rulesService;

	@Autowired
	private GenericService<ReimburseStudentLoanAppHistory> reimburseStudentLoanAppHistoryService;

	@Autowired
	private GenericService<ParentRelatedApplicationRelationship> parentRelatedApplicationRelationshipService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ApplicationCoursesService applicationCoursesService;

	@Autowired
	private CourseHistoryService courseHistoryService;

	@Autowired
	private ApplicationStatusMapper applicationStatusMapper;

	@Autowired
	private UserTypeService userTypeService;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	private GenericService<EligibilityEventComment> eligibilityEventCommentService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private UserService userService;

	@Autowired
	private BenefitPeriodService benefitPeriodService;

	@Autowired
	private GenericService<State> stateService;

	@Autowired
	private GenericService<ApplicationType> applicationTypeService;

	@Autowired
	private GenericService<FinancialAidSource> financialAidSourceService;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private ApplicationApprovalService applicationApprovalService;

	@Autowired
	private AccessService accessService;

	private ApplicationDao applicationDao;

	public ApplicationServiceImpl() {
	}

	@Autowired
	public ApplicationServiceImpl(@Qualifier("applicationDaoImpl") GenericDao<Application> genericDao) {
		super(genericDao);
		this.applicationDao = (ApplicationDao) genericDao;
	}

	private Long[] relatedBookApplicationsExcludedStatuses = { 530L, 900L, 910L, 930L };

	@Override
	public List<Application> search(Object object) throws ExceededMaxResultsException {
		return applicationDao.search(object);
	}

	@Override
	public List<Application> findRelatedBookApplications(Long applicationId) throws ExceededMaxResultsException {
		return applicationDao.findRelatedBookApplications(applicationId, relatedBookApplicationsExcludedStatuses);
	}

	@Override
	public PaginationResult<ThinApp> findSelfAndSuperviseeApplciations(Participant supervisorParticipant, int index, int recordsPerPage, String teamMemberType, String sortingProperty,
			String benefitPeriods) {
		return applicationDao.findSelfAndSuperviseeApplciations(supervisorParticipant.getParticipantId(), index, recordsPerPage, teamMemberType, sortingProperty, benefitPeriods);
	}

	@Override
	public void saveOrUpdate(Application entity) {
		try {
			if (entity.getReimburseStudentLoanApp() != null) {
				this.captureApplicationHistory(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Long userId = sessionService.getClaimAsLong(JWTTokenClaims.USER_ID);

		// copy all PREPAY/REIMBURSE RELATED FIELDS/METHODS to the appropriate
		// fields
		updatePrepayReimburseAndBookObjects(entity);
		super.saveOrUpdate(entity);

		// if app hasn't been saved yet then sync up book apps
		if (!entity.isBookApplication()
				&& (entity.isSavedNotSubmitted() || entity.isSubmittedIncomplete() || entity.isSubmittedPendingReview() || entity.isApproved() || entity.isDenied() || entity.isCancelled())) {
			// get list of all related apps
			try {
				List<Application> relatedApps = this.findRelatedBookApplications(entity.getApplicationID());
				if (CollectionUtils.isNotEmpty(relatedApps)) {
					for (Application currApp : relatedApps) {
						// if its a book app then set its status to the same as
						// the app and save
						if (currApp != null && currApp.isBookApplication()) {
							// no need to save if the status remains unchanged
							if (!currApp.getApplicationStatusID().equals(entity.getApplicationStatusID())) {
								// TAM-2736 : Updating bookapp status to
								// Approved when related app moves to LOC
								if (entity.getApplicationStatusID().getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_LOC)) {
									currApp.setApplicationStatusID(applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_APPROVED), userId);
								} else {
									currApp.setApplicationStatusID(entity.getApplicationStatusID(), userId);
								}
								currApp.setDegreeObjectiveID(entity.getDegreeObjectiveID());
								currApp.setCourseOfStudyID(entity.getCourseOfStudyID());
								currApp.setBenefitPeriodID(entity.getBenefitPeriodID());
								this.saveOrUpdate(currApp);
							}
						}
					}
				}
			} catch (ExceededMaxResultsException e) {
				throw new BadRequestException("Application has exceeded the maximum number of allowable related applications.");
			}

		}

		// If the Application Status changed take a snap shot of Application
		// Courses into CourseHistory
		if (entity.hasStatusChanged()) {

			// iterate thru list of courses&expenses
			List<ApplicationCourses> courseAndExpenseList;
			courseAndExpenseList = applicationCoursesService.findByParam("applicationID", entity);

			if (CollectionUtils.isNotEmpty(courseAndExpenseList)) {
				Long changeId = generateChangeId();

				for (ApplicationCourses currCourse : courseAndExpenseList) {
					if (currCourse != null) {
						// foreach course/expense write a record to the
						// CourseHistoryTable
						CourseHistory courseHist = new CourseHistory(entity, changeId, entity.getOriginalStatusID(), entity.getApplicationStatusID(), currCourse, currCourse.getCourseNumber(),
								currCourse.getCourseName(), currCourse.getTuitionAmount(), currCourse.getFeesAmount(), currCourse.getDiscountAmount(), currCourse.getGradeID(),
								currCourse.getTaxability(), currCourse.getCreditHours(), currCourse.getMaintainSkillsYN(), currCourse.getMeetMinimumQualsYN(), currCourse.getNewCareerFieldYN(),
								new Date(), null, currCourse.getCreatedBy(), null);
						courseHistoryService.saveOrUpdate(courseHist);
					}
				}
			}

			// TAM-2975: Since shot has been taken and status changed saved at
			// this point making OrigialStatusID ApplicationStatusID
			entity.setOrigialStatusID(entity.getApplicationStatusID());

		}

	}

	@Override
	public void saveApplication(Application application) {
		ApplicationStatus fromStatus = application.getOriginalStatusID();

		application.setDateModified(new Date());
		application.setModifiedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));

		saveOrUpdate(application);

		// TAM-3308: If app transitioning backword from Paymebt to Submitted
		// Incomplete Deny Cancel Void unapply %payout
		// BUGFIX : May need to account for other transitions too here
		if (fromStatus.getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_REVIEW)
				|| fromStatus.getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_REIMBURSEMENT_PROGRESS)
				|| fromStatus.getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_INCOMPLETE_COMPLETION_DOCUMENTS_REQUIRED)
				|| fromStatus.getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_APPROVED_PROGRESS)
				|| fromStatus.getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_COMPLETE)
				|| fromStatus.getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_REPAYMENT_REQUIRED)
				|| fromStatus.getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_SENT_COLLECTIONS)) {
			if (application.getApplicationStatusID().getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_VOID)
					|| application.getApplicationStatusID().getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_CANCELLED)
					|| application.getApplicationStatusID().getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_DENIED)
					|| application.getApplicationStatusID().getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE)) {
				unApplyPercentagePayout(application);
			}
		}

		// TAM-3308: If app transitioning forward to Paymet Review from
		// approve/LOC apply % Payout
		if (application.getApplicationStatusID().getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_REVIEW)) {
			if (fromStatus.getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_APPROVED)
					|| fromStatus.getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_LOC)) {
				applyPercentagePayout(application);
			}
		}
	}

	private void unApplyPercentagePayout(Application application) {

		if (application.getBenefitPeriodID().getProgramID().getApplyPercentagePayOut() == null || application.getBenefitPeriodID().getProgramID().getApplyPercentagePayOut().equals(Boolean.FALSE)) {
			// M: This may need to be changed in future
			log.info("Not unapplying as the unApplyPercentagePayout at Program level is turned off for program code :" + application.getBenefitPeriodID().getProgramID().getProgramCode());
		} else {
			List<ApplicationCourses> courseAndExpenseList = null;
			try {
				courseAndExpenseList = applicationCoursesService.findByParam("applicationID", application);
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExceededMaxResultsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ApplicationCourses applicationCourse = new ApplicationCourses();
			Iterator<ApplicationCourses> iter1 = courseAndExpenseList.iterator();
			while (iter1.hasNext()) {
				BigDecimal tuiAmount = BigDecimal.ZERO;
				applicationCourse = iter1.next();
				if ((applicationCourse.isExpense() == false) && applicationCourse.getTuitionAmount() != null) {
					// M: Need to get back the approved stamped amt from backend
					CourseHistory approvedEntity = courseHistoryService.findApprovedEntity(applicationCourse);
					if (approvedEntity == null) {
						// Go off PaymentAmt Since there is no Approved History
						// Amount
						log.info("No Approved amount in history going off Payment amount.");
						tuiAmount = (applicationCourse.getTuitionAmount()).multiply(BigDecimal.valueOf(100L).divide(applicationCourse.getPercentagePayOut(), 2, RoundingMode.HALF_UP));
					} else {
						tuiAmount = approvedEntity.getTuitionAmount();
					}
					applicationCourse.setPercentagePayOut(BigDecimal.valueOf(100L));
					applicationCourse.setTuitionAmount(tuiAmount);
					applicationCourse.setModifiedDate(new Date());
					applicationCourse.setModifiedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
					applicationCoursesService.saveOrUpdate(applicationCourse);
				}

				if ((applicationCourse.isExpense() == true) && applicationCourse.getFeesAmount() != null) {

					// M: Need to get back the approved stamped amt from backend
					CourseHistory approvedEntity = courseHistoryService.findApprovedEntity(applicationCourse);
					BigDecimal feeAmount = BigDecimal.ZERO;
					if (approvedEntity == null) {
						// Go off PaymentAmt Since there is no Approved History
						// Amount
						log.info("No Approved amount in history going off Payment amount.");
						feeAmount = (applicationCourse.getFeesAmount()).multiply(BigDecimal.valueOf(100L)).divide(applicationCourse.getPercentagePayOut(), 2, RoundingMode.HALF_UP);
					} else {
						feeAmount = approvedEntity.getTuitionAmount();
					}

					approvedEntity.getFeesAmount();
					applicationCourse.setPercentagePayOut(BigDecimal.valueOf(100L));
					applicationCourse.setFeesAmount(feeAmount);
					applicationCourse.setModifiedDate(new Date());
					applicationCourse.setModifiedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
					applicationCoursesService.saveOrUpdate(applicationCourse);
				}
			}
		}
	}

	private void applyPercentagePayout(Application application) {

		if (application.getBenefitPeriodID().getProgramID().getApplyPercentagePayOut() == null || application.getBenefitPeriodID().getProgramID().getApplyPercentagePayOut().equals(Boolean.FALSE)) {
			log.info("Not applying as the applyPercentagePayout at Program level is turned off for program code :" + application.getBenefitPeriodID().getProgramID().getProgramCode());
		} else {

			List<ApplicationCourses> courseAndExpenseList = null;
			try {
				courseAndExpenseList = applicationCoursesService.findByParam("applicationID", application);
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExceededMaxResultsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ApplicationCourses applicationCourse = new ApplicationCourses();
			Iterator<ApplicationCourses> iter1 = courseAndExpenseList.iterator();
			while (iter1.hasNext()) {
				applicationCourse = iter1.next();

				// Get the % Payout from DB
				BigDecimal payout = BigDecimal.valueOf(100L);
				List<PercentagePayOut> payoutList = callFindPercentagePayoutForAppCourse(applicationCourse.getApplicationCoursesID());
				if (payoutList != null && !payoutList.isEmpty()) {
					PercentagePayOut percentagePayOutFromDB = payoutList.get(0);
					if (percentagePayOutFromDB != null && percentagePayOutFromDB.getValue() != null) {
						payout = percentagePayOutFromDB.getValue();
					}
				}
				log.info("applyPercentagePayout for  applicationCourseID :" + applicationCourse.getApplicationCoursesID() + " from DB is : " + payout);

				if ((applicationCourse.isExpense() == false) && applicationCourse.getTuitionAmount() != null) {

					BigDecimal tuiAmount = (applicationCourse.getTuitionAmount()).multiply((payout).divide(BigDecimal.valueOf(100L)), MathContext.DECIMAL32);

					applicationCourse.setPercentagePayOut(payout);

					applicationCourse.setTuitionAmount(tuiAmount);
					applicationCourse.setModifiedDate(new Date());
					applicationCourse.setModifiedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
					applicationCoursesService.saveOrUpdate(applicationCourse);
				}

				if ((applicationCourse.isExpense() == true) && applicationCourse.getFeesAmount() != null) {

					BigDecimal feeAmount = (applicationCourse.getFeesAmount()).multiply((payout).divide(BigDecimal.valueOf(100L)), MathContext.DECIMAL32);
					applicationCourse.setPercentagePayOut(payout);
					applicationCourse.setFeesAmount(feeAmount);
					applicationCourse.setModifiedDate(new Date());
					applicationCourse.setModifiedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
					applicationCoursesService.saveOrUpdate(applicationCourse);
				}
			}
		}
	}

	@Override
	public void saveOrUpdate(com.edassist.models.domain.Application entity, String newStatus) {
		// copy all PREPAY/REIMBURSE RELATED FIELDS/METHODS to the appropriate
		// fields
		updatePrepayReimburseAndBookObjects(entity);
		super.saveOrUpdate(entity);

		// if app hasn't been saved yet then sync up book apps
		if (!entity.isBookApplication()
				&& (entity.isSavedNotSubmitted() || entity.isSubmittedIncomplete() || entity.isSubmittedPendingReview() || entity.isApproved() || entity.isDenied() || entity.isCancelled())) {
			// get list of all related apps
			try {
				List<Application> relatedApps = this.findRelatedBookApplications(entity.getApplicationID());
				if (CollectionUtils.isNotEmpty(relatedApps)) {
					for (Application currApp : relatedApps) {
						// if its a book app then set its status to the same as
						// the app and save
						if (currApp != null && currApp.isBookApplication()) {
							/* NEW-6442 */
							if ("Payment Review".equals(newStatus)) {
								;
							} else {
								// no need to save if the status remains
								// unchanged
								if (!currApp.getApplicationStatusID().equals(entity.getApplicationStatusID())) {
									// TAM-2736 : Updating bookapp status to
									// Approved when related app moves to LOC
									Long userId = sessionService.getClaimAsLong(JWTTokenClaims.USER_ID);
									if (entity.getApplicationStatusID().getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_LOC)) {
										currApp.setApplicationStatusID(applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_APPROVED), userId);
									} else {
										currApp.setApplicationStatusID(entity.getApplicationStatusID(), userId);
									}
									currApp.setDegreeObjectiveID(entity.getDegreeObjectiveID());
									currApp.setCourseOfStudyID(entity.getCourseOfStudyID());
									currApp.setBenefitPeriodID(entity.getBenefitPeriodID());
									this.saveOrUpdate(currApp);
								}
							}
						}
					}
				}
			} catch (ExceededMaxResultsException e) {
				throw new BadRequestException("Application has exceeded the maximum number of allowable related applications.");
			}

		}

		// If the Application Status changed take a snap shot of Application
		// Courses into CourseHistory
		if (entity.hasStatusChanged()) {

			// iterate thru list of courses&expenses
			List<ApplicationCourses> courseAndExpenseList;
			courseAndExpenseList = applicationCoursesService.findByParam("applicationID", entity);

			if (CollectionUtils.isNotEmpty(courseAndExpenseList)) {
				Long changeId = generateChangeId();

				for (ApplicationCourses currCourse : courseAndExpenseList) {
					if (currCourse != null) {
						// foreach course/expense write a record to the
						// CourseHistoryTable
						CourseHistory courseHist = new CourseHistory(entity, changeId, entity.getOriginalStatusID(), entity.getApplicationStatusID(), currCourse, currCourse.getCourseNumber(),
								currCourse.getCourseName(), currCourse.getTuitionAmount(), currCourse.getFeesAmount(), currCourse.getDiscountAmount(), currCourse.getGradeID(),
								currCourse.getTaxability(), currCourse.getCreditHours(), currCourse.getMaintainSkillsYN(), currCourse.getMeetMinimumQualsYN(), currCourse.getNewCareerFieldYN(),
								new Date(), null, currCourse.getCreatedBy(), null);
						courseHistoryService.saveOrUpdate(courseHist);
					}
				}
			}

			// TAM-2975: Since shot has been taken and status changed saved at
			// this point making OrigialStatusID ApplicationStatusID
			entity.setOrigialStatusID(entity.getApplicationStatusID());

		}

	}

	private Long generateChangeId() {
		Date d = new Date();
		return d.getTime();
	}

	private void updatePrepayReimburseAndBookObjects(com.edassist.models.domain.Application entity) {

		// copy provider to participant current educ profile? how will we do
		// that since app doesnt have FK?
		if (entity.isPrePaymentApplication()) {
			entity.getPrepayTuitionApp().setAmtPaid(entity.getAmtPaid());
			entity.getPrepayTuitionApp().setAmtRptdPaid(entity.getAmtRptdPaid());
			entity.getPrepayTuitionApp().setCountry(entity.getCountry());
			entity.getPrepayTuitionApp().setCourseEndDate(entity.getCourseEndDate());
			entity.getPrepayTuitionApp().setCourseOfStudyID(entity.getCourseOfStudyID());
			entity.getPrepayTuitionApp().setCourseStartDate(entity.getCourseStartDate());
			entity.getPrepayTuitionApp().setCreditHours(entity.getCreditHours());
			entity.getPrepayTuitionApp().setDegreeObjectiveID(entity.getDegreeObjectiveID());
			entity.getPrepayTuitionApp().setGradesComplete(entity.getGradesComplete());
			entity.getPrepayTuitionApp().setIssueDate(entity.getIssueDate());
			entity.getPrepayTuitionApp().setMissingInvoiceLetterSent(entity.getMissingInvoiceLetterSent());
			entity.getPrepayTuitionApp().setOtherCourseOfStudy(entity.getOtherCourseOfStudy());
			entity.getPrepayTuitionApp().setOtherObjective(entity.getOtherObjective());
			entity.getPrepayTuitionApp().setPaymentAmount(entity.getPaymentAmount());
			entity.getPrepayTuitionApp().setPaymentDate(entity.getPaymentDate());
			entity.getPrepayTuitionApp().setPaymentInfo(entity.getPaymentInfo());
			entity.getPrepayTuitionApp().setPaymentTotal(entity.getPaymentTotal());
			entity.getPrepayTuitionApp().setProviderAddress1(entity.getProviderAddress1());
			entity.getPrepayTuitionApp().setProviderAddress2(entity.getProviderAddress2());
			entity.getPrepayTuitionApp().setProviderCity(entity.getProviderCity());
			entity.getPrepayTuitionApp().setProviderCode(entity.getProviderCode());
			entity.getPrepayTuitionApp().setProviderName(entity.getProviderName());
			entity.getPrepayTuitionApp().setProviderPhone(entity.getProviderPhone());
			entity.getPrepayTuitionApp().setProviderStateID(entity.getProviderStateID());
			entity.getPrepayTuitionApp().setProviderZip(entity.getProviderZip());
			entity.getPrepayTuitionApp().setRanCourseCompletion(entity.getRanCourseCompletion());
			entity.getPrepayTuitionApp().setRanCourseCompletion2(entity.getRanCourseCompletion2());
			entity.getPrepayTuitionApp().setRanMissingInvoice(entity.isRanMissingInvoice());
			entity.getPrepayTuitionApp().setRefundAmount(entity.getRefundAmount());
			entity.getPrepayTuitionApp().setSectorID(entity.getSectorID());
			entity.getPrepayTuitionApp().setTaxableAmt(entity.getTaxableAmt());
		} else if (entity.isReimbursementApplication()) {
			entity.getReimburseTuitionApp().setAmtPaid(entity.getAmtPaid());
			entity.getReimburseTuitionApp().setAmtRptdPaid(entity.getAmtRptdPaid());
			entity.getReimburseTuitionApp().setCountry(entity.getCountry());
			entity.getReimburseTuitionApp().setCourseEndDate(entity.getCourseEndDate());
			entity.getReimburseTuitionApp().setCourseOfStudyID(entity.getCourseOfStudyID());
			entity.getReimburseTuitionApp().setCourseStartDate(entity.getCourseStartDate());
			entity.getReimburseTuitionApp().setCreditHours(entity.getCreditHours());
			entity.getReimburseTuitionApp().setDegreeObjectiveID(entity.getDegreeObjectiveID());
			entity.getReimburseTuitionApp().setGradesComplete(entity.getGradesComplete());
			entity.getReimburseTuitionApp().setOtherCourseOfStudy(entity.getOtherCourseOfStudy());
			entity.getReimburseTuitionApp().setOtherObjective(entity.getOtherObjective());
			entity.getReimburseTuitionApp().setPaymentAmount(entity.getPaymentAmount());
			entity.getReimburseTuitionApp().setPaymentDate(entity.getPaymentDate());
			entity.getReimburseTuitionApp().setPaymentInfo(entity.getPaymentInfo());
			entity.getReimburseTuitionApp().setPaymentTotal(entity.getPaymentTotal());
			entity.getReimburseTuitionApp().setProviderAddress1(entity.getProviderAddress1());
			entity.getReimburseTuitionApp().setProviderAddress2(entity.getProviderAddress2());
			entity.getReimburseTuitionApp().setProviderCity(entity.getProviderCity());
			entity.getReimburseTuitionApp().setProviderCode(entity.getProviderCode());
			entity.getReimburseTuitionApp().setProviderName(entity.getProviderName());
			entity.getReimburseTuitionApp().setProviderPhone(entity.getProviderPhone());
			entity.getReimburseTuitionApp().setProviderStateID(entity.getProviderStateID());
			entity.getReimburseTuitionApp().setProviderZip(entity.getProviderZip());
			entity.getReimburseTuitionApp().setRanCourseCompletion(entity.getRanCourseCompletion());
			entity.getReimburseTuitionApp().setRanCourseCompletion2(entity.getRanCourseCompletion2());
			entity.getReimburseTuitionApp().setRefundAmount(entity.getRefundAmount());
			entity.getReimburseTuitionApp().setSectorID(entity.getSectorID());
			entity.getReimburseTuitionApp().setTaxableAmt(entity.getTaxableAmt());
		} else if (entity.isBookApplication()) {
			entity.getReimburseBookApp().setAmtPaid(entity.getAmtPaid());
			entity.getReimburseBookApp().setAmtRptdPaid(entity.getAmtRptdPaid());
			entity.getReimburseBookApp().setCourseEndDate(entity.getCourseEndDate());
			entity.getReimburseBookApp().setCourseStartDate(entity.getCourseStartDate());
			entity.getReimburseBookApp().setCreditHours(entity.getCreditHours());
			entity.getReimburseBookApp().setPaymentAmount(entity.getPaymentAmount());
			entity.getReimburseBookApp().setPaymentDate(entity.getPaymentDate());
			entity.getReimburseBookApp().setPaymentInfo(entity.getPaymentInfo());
			entity.getReimburseBookApp().setPaymentTotal(entity.getPaymentTotal());
			entity.getReimburseBookApp().setProviderCode(entity.getProviderCode());
			entity.getReimburseBookApp().setProviderName(entity.getProviderName());
			entity.getReimburseBookApp().setRefundAmount(entity.getRefundAmount());
			entity.getReimburseBookApp().setTaxableAmt(entity.getTaxableAmt());
		} else if (entity.isReimburseStudentLoanApplication()) {
			// System.out.println("gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg"+entity.getApplicationStatusID().getApplicationStatusID());
			// System.out.println("ggg"++"hhh"+entity.getLoanApprovedAmount()+"iii"+entity.getLoanPaidAmount());
			entity.getReimburseStudentLoanApp().setCourseOfStudyID(entity.getCourseOfStudyID());
			entity.getReimburseStudentLoanApp().setDegreeObjectiveID(entity.getDegreeObjectiveID());
			entity.getReimburseStudentLoanApp().setMonthlyPaymentAmount(entity.getMonthlyPaymentAmount());
			// entity.getReimburseStudentLoanApp().setStudentLoanServicerID(entity.getStudentLoanServicerID());
			entity.getReimburseStudentLoanApp().setGradePointAverage(entity.getGradePointAverage());
			entity.getReimburseStudentLoanApp().setGraduationDate(entity.getGraduationDate());
			entity.getReimburseStudentLoanApp().setLoanAccountNumber(entity.getLoanAccountNumber());
			entity.getReimburseStudentLoanApp().setLoanApprovedAmount(entity.getLoanApprovedAmount());
			entity.getReimburseStudentLoanApp().setLoanBalance(entity.getLoanBalance());
			entity.getReimburseStudentLoanApp().setLoanPaidAmount(entity.getLoanPaidAmount());
			entity.getReimburseStudentLoanApp().setLoanPaymentPeriodEndDate(entity.getLoanPaymentPeriodEndDate());
			entity.getReimburseStudentLoanApp().setLoanPaymentPeriodStartDate(entity.getLoanPaymentPeriodStartDate());
			entity.getReimburseStudentLoanApp().setLoanRequestedPaymentAmount(entity.getLoanRequestedPaymentAmount());
		}

	}

	@Override
	public Application merge(Application entity) {
		updatePrepayReimburseAndBookObjects(entity);
		return super.merge(entity);
	}

	@Override
	public List<ApplicationNumber> getNextApplicationNumber() throws Exception {
		return applicationDao.callGetNextApplicationNumberProc();
	}

	@Override
	public ApplicationCourseCompliancy getApplicationCourseCompliancyProc(final long applicationCoursesID) {
		return applicationDao.callGetApplicationCourseCompliancyProc(applicationCoursesID);
	}

	@Override
	public List<Application> search(Application application, List<Client> clientList) throws ExceededMaxResultsException {
		List<Application> applicationList = new LinkedList<Application>();
		if (clientList == null) {
			return null;
		} else {
			Iterator it = clientList.iterator();
			while (it.hasNext()) {
				Client client = (Client) it.next();
				application.getParticipantID().setClient(client);
				List<Application> subList = applicationDao.search(application);
				applicationList.addAll(subList);
			}
			return applicationList;

		}

	}

	/********************************************************************************************/

	@Override
	public ExpenseSnapshotDTO requestedTotals(Long applicationId) throws DataAccessException, ExceededMaxResultsException {
		Application application = applicationDao.findById(applicationId);

		Long applicationStatusCode = application.getApplicationStatusID().getApplicationStatusCode();

		List<ApplicationCourses> courseAndExpenseList = applicationCoursesService.findByParam("applicationID", application);

		Map<String, Object> requestedTotalsAsBigDecimal = getRequestedTotalsAsBigDecimal(courseAndExpenseList, applicationStatusCode, null);
		BigDecimal crsReqTuitionTotal = (BigDecimal) requestedTotalsAsBigDecimal.get("requestedTuition");
		BigDecimal crsReqFeesTotal = (BigDecimal) requestedTotalsAsBigDecimal.get("requestedFee");

		ExpenseSnapshotDTO expenseSnapshotDTO = getExpenseSnapshotDTO(Boolean.FALSE, crsReqTuitionTotal, crsReqFeesTotal, BigDecimal.ZERO);

		return expenseSnapshotDTO;
	}

	@Override
	public Map<String, Object> getRequestedTotalsAsBigDecimal(List<ApplicationCourses> courseAndExpenseList, Long applicationStatusCode, List<ProgramExpenseType> ProgramExpenseTypeNotApplyToCapList) {
		// Back projecting requested values
		Map<String, Object> retVal = new HashMap<String, Object>();
		BigDecimal requestedTuition = BigDecimal.ZERO;
		BigDecimal requestedFee = BigDecimal.ZERO;
		if (applicationStatusCode == 90) {
			if (CollectionUtils.isNotEmpty(courseAndExpenseList)) {
				for (ApplicationCourses currCourseAndExpense : courseAndExpenseList) {
					if (currCourseAndExpense.isExpense() && currCourseAndExpense.getFeesAmount() != null) {

						boolean appliesToCap = true;
						// TAM-3050 : if there are expenses that do not apply to
						// cap
						if (CollectionUtils.isNotEmpty(ProgramExpenseTypeNotApplyToCapList)) {

							for (ProgramExpenseType currProgramExpenseType : ProgramExpenseTypeNotApplyToCapList) {
								if (currProgramExpenseType.getExpenseTypeID().getExpenseTypeID().equals(currCourseAndExpense.getExpenseType().getExpenseTypeID())) {
									appliesToCap = false;
									break;
								}
							}
						}

						if (appliesToCap == true) {
							requestedFee = requestedFee.add(currCourseAndExpense.getFeesAmount());
						}
					} else {
						if (currCourseAndExpense.getTuitionAmount() != null) {
							requestedTuition = requestedTuition.add(currCourseAndExpense.getTuitionAmount());
						}
					}
				}
			}
		} else {
			if (CollectionUtils.isNotEmpty(courseAndExpenseList)) {

				for (ApplicationCourses currCourseAndExpense : courseAndExpenseList) {
					if (currCourseAndExpense != null) {
						// TAM-3050 : if there are expenses that do not apply to
						// cap
						boolean appliesToCap = true;
						if (currCourseAndExpense.isExpense() && currCourseAndExpense.getFeesAmount() != null && CollectionUtils.isNotEmpty(ProgramExpenseTypeNotApplyToCapList)) {
							for (ProgramExpenseType currProgramExpenseType : ProgramExpenseTypeNotApplyToCapList) {
								if (currProgramExpenseType.getExpenseTypeID().getExpenseTypeID().equals(currCourseAndExpense.getExpenseType().getExpenseTypeID())) {
									appliesToCap = false;
									break;
								}
							}
						}

						if ((currCourseAndExpense.isExpense() && currCourseAndExpense.getFeesAmount() != null && appliesToCap == true)
								|| (currCourseAndExpense.isExpense() == false && currCourseAndExpense.getTuitionAmount() != null)) {
							CourseHistory requestedEntity = courseHistoryService.findRequestedEntity(currCourseAndExpense);
							if (requestedEntity == null) {
								if (currCourseAndExpense.isExpense()) {
									if (currCourseAndExpense.getFeesAmount() != null) {
										requestedFee = requestedFee.add(currCourseAndExpense.getFeesAmount());
									}
								} else {
									if (currCourseAndExpense.getTuitionAmount() != null) {
										requestedTuition = requestedTuition.add(currCourseAndExpense.getTuitionAmount());
									}
								}
							} else {
								if (currCourseAndExpense.isExpense()) {
									if (requestedEntity.getFeesAmount() != null) {
										requestedFee = requestedFee.add(requestedEntity.getFeesAmount());
									}
								} else {
									if (requestedEntity.getTuitionAmount() != null) {
										requestedTuition = requestedTuition.add(requestedEntity.getTuitionAmount());
									}
								}
							}
						}
					}

				}
			}
		}
		retVal.put("requestedFee", requestedFee);
		retVal.put("requestedTuition", requestedTuition);
		return retVal;

	}

	@Override
	public ExpenseSnapshotDTO approvedTotals(@RequestParam Long applicationId) throws ExceededMaxResultsException {

		Application application = applicationDao.findById(applicationId);
		ExpenseSnapshotDTO expenseSnapshotDTO = new ExpenseSnapshotDTO();

		Long applicationStatusCode = application.getApplicationStatusID().getApplicationStatusCode();

		if (applicationStatusCode == 90 || applicationStatusCode > 900) {
			expenseSnapshotDTO = getExpenseSnapshotDTO(Boolean.TRUE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
		} else if (applicationStatusCode == 125 || applicationStatusCode == 100 || applicationStatusCode == 110) {

			List<ApplicationCourses> courseAndExpenseList = applicationCoursesService.findByParam("applicationID", application);
			expenseSnapshotDTO = calculateApplicationCoursesTotals(courseAndExpenseList);

		} else {
			List<CourseHistory> courseHistoryList = courseHistoryService.findApprovedEntities(application);
			expenseSnapshotDTO = calculateHistoryTotals(courseHistoryList);
		}

		return expenseSnapshotDTO;
	}

	@Override
	public void changeApplicationStatus(Application application, ApplicationStatus status, String reason, boolean viewableByParticipant, String eventType, boolean appStatusChange) {
		boolean removeWorkItem = false;
		if (status != null) {
			ApplicationStatus oldStatus = application.getApplicationStatusID();

			if (!isStatusChangeValid(oldStatus.getApplicationStatusCode(), status.getApplicationStatusCode(), true)) {
				throw new BadRequestException(
						"Invalid state change, application cannot be changed from [" + application.getApplicationStatusID().getApplicationStatus() + "] to [" + status.getApplicationStatus() + "]");
			}

			/**
			 * TAM-1716 If the application is in a status of "Submitted - Pending Review" then a change to a different status needs to pull the item off the workflow queue.
			 */
			removeWorkItem = (ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW.equals(oldStatus.getApplicationStatusID()));

			application.setApplicationStatusID(status, sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));

			// TAM-2980
			addComment(application, reason, viewableByParticipant, true);
			// addEligibilityEvent(application, reason, status, eventType);
		}
	}

	private void addComment(Application application, String commentText, boolean viewableByParticipant, boolean appStatusChange) {
		EligibilityEventComment comment = new EligibilityEventComment();

		comment.setApplicationID(application.getApplicationID());
		comment.setComment(commentText);

		ThinUser cUser = new ThinUser(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID), sessionService.getClaimAsString(JWTTokenClaims.USER_FIRST_NAME),
				sessionService.getClaimAsString(JWTTokenClaims.USER_LAST_NAME), sessionService.getClaimAsString(JWTTokenClaims.USER_MIDDLE_INITIALS));
		comment.setCreatedBy(cUser);

		comment.setDateCreated(new Timestamp(new Date().getTime()));
		comment.setApplicationStatus(application.getApplicationStatusID());
		comment.setCommentTypeID(0L);
		comment.setViewableByParticipant(viewableByParticipant);
		comment.setAppStatusChange(appStatusChange);
		eligibilityEventCommentService.saveOrUpdate(comment);
	}

	@Override
	public boolean isStatusChangeValid(Long fromStatus, Long toStatus, boolean allowNoChange) {

		if (fromStatus == null || toStatus == null) {
			return false;
		}

		if (allowNoChange && fromStatus.equals(toStatus)) {
			return true;
		}

		if (ApplicationConstants.APPLICATION_STATUS_CANCELLED.equals(fromStatus)) {
			return true;
		}
		if (ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_APPROVED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_LOC.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_DENIED.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_CANCELLED.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus));
		} else if (ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_APPROVED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_LOC.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_DENIED.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_CANCELLED.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW.equals(toStatus));
		} else if (ApplicationConstants.APPLICATION_STATUS_APPROVED.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_DENIED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_CANCELLED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_PAYMENT_REVIEW.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_PAYMENT_PROCESSING_INCOMPLETE_INFORMATION_NEEDED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus));
		} else if (ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_LOC.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_APPROVED.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_DENIED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_CANCELLED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW.equals(toStatus)); // ED-1345
		} else if (ApplicationConstants.APPLICATION_STATUS_PAYMENT_PROCESSING_INCOMPLETE_INFORMATION_NEEDED.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_DENIED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_CANCELLED.equals(toStatus)
			/* TS-934 */
					|| ApplicationConstants.APPLICATION_STATUS_PAYMENT_REVIEW.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_PAYMENT_REIMBURSEMENT_PROGRESS.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus)
					/* TAM-2258 */
					|| ApplicationConstants.APPLICATION_STATUS_PAYMENT_PROCESSING_INCOMPLETE_INFORMATION_NEEDED.equals(toStatus));
		} else if (ApplicationConstants.APPLICATION_STATUS_PAYMENT_REVIEW.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_DENIED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_CANCELLED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_PAYMENT_REIMBURSEMENT_PROGRESS.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_PAYMENT_PROCESSING_INCOMPLETE_INFORMATION_NEEDED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus));
		} else if (ApplicationConstants.APPLICATION_STATUS_PAYMENT_REIMBURSEMENT_PROGRESS.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_DENIED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_CANCELLED.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_PAYMENT_PROCESSING_INCOMPLETE_INFORMATION_NEEDED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_PAYMENT_REVIEW.equals(toStatus));
		} else if (ApplicationConstants.APPLICATION_STATUS_SAVED_NOT_SUBMITTED.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_CANCELLED.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_APPROVED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_DENIED.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_LOC.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW.equals(toStatus));
		} else if (ApplicationConstants.APPLICATION_STATUS_CARRYOVER_PAYMENT_APPROVED.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_PAYMENT_REIMBURSEMENT_PROGRESS.equals(toStatus));
		} else if (ApplicationConstants.APPLICATION_STATUS_LOC.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_DENIED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_CANCELLED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_PAYMENT_REVIEW.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_PAYMENT_PROCESSING_INCOMPLETE_INFORMATION_NEEDED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus));
		} else if (ApplicationConstants.APPLICATION_STATUS_PAYMENT_APPROVED_FUND_PROGRESS.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus));
		} else if (ApplicationConstants.APPLICATION_STATUS_PAYMENT_COMPLETE_COMPLETION_DOCUMENTS_REQUIRED.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_REPAYMENT_REQUIRED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_CLOSED.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus));
		} else if (ApplicationConstants.APPLICATION_STATUS_SENT_COLLECTIONS.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_REPAYMENT_REQUIRED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_CLOSED.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus));
		} else if (ApplicationConstants.APPLICATION_STATUS_REPAYMENT_REQUIRED.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_SENT_COLLECTIONS.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_CLOSED.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus));
		} else if (ApplicationConstants.APPLICATION_STATUS_CLOSED.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_REPAYMENT_REQUIRED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_PAYMENT_COMPLETE_COMPLETION_DOCUMENTS_REQUIRED.equals(toStatus));
		} else if (ApplicationConstants.APPLICATION_STATUS_CANCELLED.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus));
		} else if (ApplicationConstants.APPLICATION_STATUS_DENIED.equals(fromStatus)) {
			return (ApplicationConstants.APPLICATION_STATUS_APPROVED.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_LOC.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW.equals(toStatus) || ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE.equals(toStatus)
					|| ApplicationConstants.APPLICATION_STATUS_VOID.equals(toStatus));
		} else {
			return false;
		}
	}

	@Override
	public ExpenseSnapshotDTO paidTotals(Long applicationId) throws DataAccessException, ExceededMaxResultsException {

		ExpenseSnapshotDTO expenseSnapshotDTO = new ExpenseSnapshotDTO();

		Application application = applicationDao.findById(applicationId);
		Long applicationStatusCode = application.getApplicationStatusID().getApplicationStatusCode();

		if (applicationStatusCode == 90 || applicationStatusCode > 900) {
			expenseSnapshotDTO = getExpenseSnapshotDTO(Boolean.TRUE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
		} else if (applicationStatusCode == 125 || applicationStatusCode == 100 || applicationStatusCode == 110) {
			List<ApplicationCourses> courseAndExpenseList = applicationCoursesService.findByParam("applicationID", application);
			expenseSnapshotDTO = calculateApplicationCoursesTotals(courseAndExpenseList);

		} else {
			List<CourseHistory> courseHistoryList = courseHistoryService.findPaidEntities(application);
			expenseSnapshotDTO = calculateHistoryTotals(courseHistoryList);
		}

		return expenseSnapshotDTO;
	}

	private ExpenseSnapshotDTO calculateApplicationCoursesTotals(List<ApplicationCourses> applicationCoursesList) {
		// deanhere 7 and 9
		Long tuningId = new Date().getTime();

		BigDecimal tuitionTotal = BigDecimal.ZERO;
		BigDecimal feesTotal = BigDecimal.ZERO;
		BigDecimal discountTotal = BigDecimal.ZERO;
		Boolean isEmpty = Boolean.TRUE;

		if (CollectionUtils.isNotEmpty(applicationCoursesList)) {
			isEmpty = Boolean.FALSE;
			for (ApplicationCourses currApplicationCoursesRecord : applicationCoursesList) {
				if (currApplicationCoursesRecord != null) {
					if (currApplicationCoursesRecord.getTuitionAmount() != null) {
						tuitionTotal = tuitionTotal.add(currApplicationCoursesRecord.getTuitionAmount());
					}

					if (currApplicationCoursesRecord.getFeesAmount() != null) {
						feesTotal = feesTotal.add(currApplicationCoursesRecord.getFeesAmount());
					}

					if (currApplicationCoursesRecord.getDiscountAmount() != null) {
						discountTotal = discountTotal.add(currApplicationCoursesRecord.getDiscountAmount());
					}
				}
			}
		}

		return getExpenseSnapshotDTO(isEmpty, tuitionTotal, feesTotal, discountTotal);
	}

	public Map<String, Object> currentTotals(Application application) throws DataAccessException, ExceededMaxResultsException {
		// deanhere 4
		// lookup application
		Long tuningId = new Date().getTime();

		BigDecimal tuitionTotal = BigDecimal.ZERO;
		BigDecimal feesTotal = BigDecimal.ZERO;
		BigDecimal discountTotal = BigDecimal.ZERO;

		// iterate through application courses
		List<ApplicationCourses> courseAndExpenseList = applicationCoursesService.findByParam("applicationID", application);
		Boolean isEmpty = Boolean.TRUE;

		if (CollectionUtils.isNotEmpty(courseAndExpenseList)) {
			isEmpty = Boolean.FALSE;
			for (ApplicationCourses currCourse : courseAndExpenseList) {
				if (currCourse != null) {
					if (currCourse.getTuitionAmount() != null) {
						tuitionTotal = tuitionTotal.add(currCourse.getTuitionAmount());
					}

					if (currCourse.getFeesAmount() != null) {
						feesTotal = feesTotal.add(currCourse.getFeesAmount());
					}

					if (currCourse.getDiscountAmount() != null) {
						discountTotal = discountTotal.add(currCourse.getDiscountAmount());
					}
				}
			}

		}

		return buildTotalsJsonItem(isEmpty, tuitionTotal, feesTotal, discountTotal);

	}

	private ExpenseSnapshotDTO calculateHistoryTotals(List<CourseHistory> courseHistoryList) {

		BigDecimal tuitionTotal = BigDecimal.ZERO;
		BigDecimal feesTotal = BigDecimal.ZERO;
		BigDecimal discountTotal = BigDecimal.ZERO;
		HashMap changeIds = new HashMap();

		Boolean isEmpty = Boolean.TRUE;
		if (CollectionUtils.isNotEmpty(courseHistoryList)) {
			isEmpty = Boolean.FALSE;
			for (CourseHistory currCourseHistoryRecord : courseHistoryList) {
				if (currCourseHistoryRecord != null) {
					/*
					 * TS-455 - fix to suppress the appearance of doubled up payment amounts
					 */
					if (changeIds.get(currCourseHistoryRecord.getChangeId() + "-" + currCourseHistoryRecord.getApplicationCourses().getApplicationCoursesID()) == null) {
						if (currCourseHistoryRecord.getTuitionAmount() != null) {
							tuitionTotal = tuitionTotal.add(currCourseHistoryRecord.getTuitionAmount());
						}

						if (currCourseHistoryRecord.getFeesAmount() != null) {
							feesTotal = feesTotal.add(currCourseHistoryRecord.getFeesAmount());
						}

						if (currCourseHistoryRecord.getDiscountAmount() != null) {
							discountTotal = discountTotal.add(currCourseHistoryRecord.getDiscountAmount());
						}
						changeIds.put(currCourseHistoryRecord.getChangeId() + "-" + currCourseHistoryRecord.getApplicationCourses().getApplicationCoursesID(), "X");
					}
				}
			}
		}

		return getExpenseSnapshotDTO(isEmpty, tuitionTotal, feesTotal, discountTotal);
	}

	private Map<String, Object> buildTotalsJsonItem(Boolean isEmpty, BigDecimal tuition, BigDecimal fees, BigDecimal discounts) {
		Map<String, Object> jsonItem = new HashMap<String, Object>();

		if (tuition == null) {
			tuition = BigDecimal.ZERO;
		}
		if (fees == null) {
			fees = BigDecimal.ZERO;
		}
		if (discounts == null) {
			discounts = BigDecimal.ZERO;
		}

		jsonItem.put("isEmpty", isEmpty);
		jsonItem.put("tuition", CommonUtil.toCurrencyString(tuition));
		jsonItem.put("fees", CommonUtil.toCurrencyString(fees));
		jsonItem.put("tuitionAndFees", CommonUtil.toCurrencyString((tuition.add(fees))));
		jsonItem.put("discounts", CommonUtil.toCurrencyString(discounts));
		jsonItem.put("tuitionAndFeesMinusDiscount", CommonUtil.toCurrencyString((tuition.add(fees).subtract(discounts))));
		jsonItem.put("success", Boolean.TRUE);

		return jsonItem;
	}

	private ExpenseSnapshotDTO getExpenseSnapshotDTO(Boolean isEmpty, BigDecimal tuition, BigDecimal fees, BigDecimal discounts) {
		ExpenseSnapshotDTO requestedTotalsDTO = new ExpenseSnapshotDTO();

		if (tuition == null) {
			tuition = BigDecimal.ZERO;
		}
		if (fees == null) {
			fees = BigDecimal.ZERO;
		}
		if (discounts == null) {
			discounts = BigDecimal.ZERO;
		}

		requestedTotalsDTO.setIsEmpty(isEmpty);
		requestedTotalsDTO.setTuition(tuition);
		requestedTotalsDTO.setFees(fees);
		requestedTotalsDTO.setTuitionAndFees(tuition.add(fees));
		requestedTotalsDTO.setDiscounts(discounts);
		requestedTotalsDTO.setTuitionAndFeesMinusDiscount(tuition.add(fees).subtract(discounts));

		return requestedTotalsDTO;
	}

	@Override
	public List<Payments> getPaymentHistory(ThinApp application) {
		List<Payments> paymentsList = paymentService.findByParam("applicationID", application);
		return paymentsList;
	}

	@Override
	public List<Refunds> getRefundsHistory(Long applicationId) {
		List<Refunds> refundsList = refundsService.findByParam("applicationID", applicationId);
		return refundsList;
	}

	@Override
	public List<PercentagePayOut> callFindPercentagePayoutForAppCourse(final Long applicationCoursesID) {
		return applicationDao.callFindPercentagePayoutForAppCourse(applicationCoursesID);
	}

	@Override
	public void processNewApplication(Application application, Program program, EducationalProviders currentProvider) throws Exception {
		// generate an application number
		List<ApplicationNumber> nextApplicationNumberList = this.getNextApplicationNumber();
		ApplicationNumber an = nextApplicationNumberList.get(0);
		application.setApplicationNumber(an.getApplicationNumber());
		Long userId = sessionService.getClaimAsLong(JWTTokenClaims.USER_ID);
		if (program.getDefaultBenefitPeriodID() == null) {
			throw new BadRequestException("Program's default benefit period must not be null.");
		}

		BenefitPeriod defaultBenefitPeriod = benefitPeriodService.findById(program.getDefaultBenefitPeriodID());
		if (defaultBenefitPeriod == null) {
			throw new BadRequestException("Program must have a default benefit period");
		}
		application.setBenefitPeriodID(defaultBenefitPeriod);

		if (program.getProgramTypeID() == null) {
			throw new BadRequestException("Program's programType must not be null");
		}

		if (program.getProgramTypeID().getProgramTypeCode() == null) {
			throw new BadRequestException("Program's programTypeCode must not be null");
		}

		if (ProgramAndApplicationTypeCode.TP.equals(program.getProgramTypeID().getProgramTypeCode())) {
			PrepayTuitionApp prepayApp = new PrepayTuitionApp();
			prepayApp.setApplicationID(application);
			prepayApp.setRanMissingInvoice(false);
			prepayApp.setCreatedBy(userId);
			prepayApp.setDateCreated(new Date());
			prepayApp.setDegreeObjectiveID(application.getDegreeObjectiveID());
			prepayApp.setCourseOfStudyID(application.getCourseOfStudyID());
			prepayApp.setOtherObjective(application.getOtherObjective());
			prepayApp.setOtherCourseOfStudy(application.getOtherCourseOfStudy());
			if (currentProvider != null) {
				prepayApp.setProviderAddress1(currentProvider.getProviderAddress1());
				prepayApp.setProviderAddress2(currentProvider.getProviderAddress2());
				prepayApp.setProviderCity(currentProvider.getProviderCity());
				prepayApp.setProviderCode(currentProvider.getProviderCode());
				prepayApp.setProviderName(currentProvider.getProviderName());
				prepayApp.setProviderPhone(currentProvider.getProviderPhone());
				if (currentProvider.getProviderState() != null) {
					List<State> providerStateList = stateService.findByParam("stateAbbreviation", currentProvider.getProviderState());
					if (providerStateList != null) {
						if (providerStateList.size() > 1) {
							throw new BadRequestException("Expected only 1 state to be returned, found [" + providerStateList.size() + "]");

						}
						prepayApp.setProviderStateID(providerStateList.get(0));
					}
				}
				prepayApp.setProviderZip(currentProvider.getProviderZip());
			}
			application.setPrepayTuitionApp(prepayApp);
		} else if (ProgramAndApplicationTypeCode.TR.equals(program.getProgramTypeID().getProgramTypeCode())) {
			ReimburseTuitionApp reimburseApp = new ReimburseTuitionApp();
			reimburseApp.setApplicationID(application);
			reimburseApp.setCreatedBy(userId);
			reimburseApp.setDateCreated(new Date());
			reimburseApp.setDegreeObjectiveID(application.getDegreeObjectiveID());
			reimburseApp.setCourseOfStudyID(application.getCourseOfStudyID());
			reimburseApp.setOtherObjective(application.getOtherObjective());
			reimburseApp.setOtherCourseOfStudy(application.getOtherCourseOfStudy());
			if (currentProvider != null) {
				reimburseApp.setProviderAddress1(currentProvider.getProviderAddress1());
				reimburseApp.setProviderAddress2(currentProvider.getProviderAddress2());
				reimburseApp.setProviderCity(currentProvider.getProviderCity());
				reimburseApp.setProviderCode(currentProvider.getProviderCode());
				reimburseApp.setProviderName(currentProvider.getProviderName());
				reimburseApp.setProviderPhone(currentProvider.getProviderPhone());
				if (currentProvider.getProviderState() != null) {
					List<State> providerStateList = stateService.findByParam("stateAbbreviation", currentProvider.getProviderState());
					if (providerStateList != null) {
						if (providerStateList.size() > 1) {
							throw new BadRequestException("Expected only 1 state to be returned, found [" + providerStateList.size() + "]");

						}
						reimburseApp.setProviderStateID(providerStateList.get(0));
					}
				}
				reimburseApp.setProviderZip(currentProvider.getProviderZip());
			}
			application.setReimburseTuitionApp(reimburseApp);
		} else if (ProgramAndApplicationTypeCode.BFR.equals(program.getProgramTypeID().getProgramTypeCode())) {
			ReimburseBookApp reimburseBookApp = new ReimburseBookApp();
			reimburseBookApp.setApplicationID(application);
			reimburseBookApp.setDegreeObjectiveID(application.getDegreeObjectiveID());
			reimburseBookApp.setCourseOfStudyID(application.getCourseOfStudyID());
			reimburseBookApp.setCreatedBy(userId);
			reimburseBookApp.setDateCreated(new Date());
			reimburseBookApp.setModifiedBy(userId);
			reimburseBookApp.setModifiedDate(new Date());
			if (currentProvider != null) {
				reimburseBookApp.setProviderCode(currentProvider.getProviderCode());
				reimburseBookApp.setProviderName(currentProvider.getProviderName());
			}
			application.setReimburseBookApp(reimburseBookApp);
		} else {
			if ((ProgramAndApplicationTypeCode.SLR.equals(program.getProgramTypeID().getProgramTypeCode()))
					|| (ProgramAndApplicationTypeCode.SLD.equals(program.getProgramTypeID().getProgramTypeCode()))) {
				ReimburseStudentLoanApp reimburseStudentLoanApp = new ReimburseStudentLoanApp();
				reimburseStudentLoanApp.setApplicationID(application);

				reimburseStudentLoanApp.setCreatedBy(userId);
				reimburseStudentLoanApp.setDateCreated(new Date());
				reimburseStudentLoanApp.setModifiedBy(null);
				reimburseStudentLoanApp.setModifiedDate(null);

				reimburseStudentLoanApp.setLoanAccountNumber("");
				reimburseStudentLoanApp.setSelectedStudentLoanType("");
				reimburseStudentLoanApp.setLoanRequestedPaymentAmount(new BigDecimal(0.0));
				reimburseStudentLoanApp.setLoanApprovedAmount(new BigDecimal(0.0));
				reimburseStudentLoanApp.setLoanPaidAmount(new BigDecimal(0.0));
				reimburseStudentLoanApp.setLoanPaymentPeriodStartDate(null);
				reimburseStudentLoanApp.setLoanPaymentPeriodEndDate(null);

				// StudentLoanServicer servicer =
				// this.getStudentLoanServicerService().findById(new Long(2));
				// reimburseStudentLoanApp.setStudentLoanServicerID(servicer);

				reimburseStudentLoanApp.setStudentLoanServicerID(null);
				reimburseStudentLoanApp.setPreviouslyApproved(false);

				application.setReimburseStudentLoanApp(reimburseStudentLoanApp);
				// This will be modified by Dean once merged to your code
				/*
				 * LoanAmount loanAmount = new LoanAmount(); loanAmount.setDateCreated(new Date()); loanAmount.setModifiedBy(null); loanAmount.setModifiedDate(null);
				 * loanAmount.setLoanAccountNumber(""); loanAmount.setLoanRequestedAmount(new BigDecimal(0.0)); loanAmount.setLoanApprovedAmount(new BigDecimal(0.0)); loanAmount.setLoanPaidAmount(new
				 * BigDecimal(0.0)); loanAmount.setLoanPaymentPeriodStartDate(null); loanAmount.setLoanPaymentPeriodEndDate(null); loanAmount.setStudentLoanServicerID(null);
				 * application.setLoanAmount(loanAmount);
				 */
			} else {
				throw new BadRequestException("Unsupport program type code: [" + program.getProgramTypeID().getProgramTypeCode() + "]");
			}
		}

		/*
		 * ED-489 Commented to persist the selected/pre-populated studentID in the application domain object to DB in step1 of new application creation
		 */
		// application.setStudentID("");

		List<ApplicationStatus> appStatuses = applicationStatusService.findByParam("applicationStatus", ApplicationStatus.DEFAULT_APPLICATION_STATUS);
		if (appStatuses == null || appStatuses.size() == 0) {
			throw new BadRequestException("The default application status [" + ApplicationStatus.DEFAULT_APPLICATION_STATUS + "] is missing from the database table.");
		}
		if (appStatuses.size() > 1) {
			throw new BadRequestException("Multiple instances of the default application status [" + ApplicationStatus.DEFAULT_APPLICATION_STATUS + "] were found in the database table.");
		}

		application.setApplicationStatusID(appStatuses.get(0), userId);

		// unfortunately there are two tables one for ProgramType and one for
		// ApplicationType even though they are they
		// same values. So we have to lookup the matching ApplicationType.
		List<ApplicationType> appTypesByCode = applicationTypeService.findByParam("applicationTypeCode", program.getProgramTypeID().getProgramTypeCode());
		if (appTypesByCode == null || appTypesByCode.size() != 1) {
			throw new BadRequestException("Expected 1 application type matching ApplicationTypeCode: [" + program.getProgramTypeID().getProgramTypeCode() + "]");
		}
		ApplicationType appType = appTypesByCode.get(0);
		application.setApplicationTypeID(appType);

		application.setApplicationFee(new BigDecimal(0));

		application.setApplicationSourceID(application.getApplicationSourceID());

		List<FinancialAidSource> defaultFinancialAidSourceList = financialAidSourceService.findByParam("financialAidDescription", FinancialAidSource.DEFAULT_FINANCIAL_AID_SOURCE_DESC);
		if (defaultFinancialAidSourceList == null || defaultFinancialAidSourceList.size() != 1) {
			throw new BadRequestException("Expected 1 financial aid source matching financialAidDescription: " + FinancialAidSource.DEFAULT_FINANCIAL_AID_SOURCE_DESC);
		}
		FinancialAidSource defaultFinancialAidSource = defaultFinancialAidSourceList.get(0);
		// TAM-1481
		// application.setFinancialAidSourceId(defaultFinancialAidSource);

		application.setRegistrationFee(new BigDecimal(0));

		application.setCreatedBy(userId);
		application.setDateCreated(new Date());

		application.setFeeInvoiced(false);

		application.setFinancialAidAmount(new BigDecimal(0));

		// Added to remediate error on step1
		this.saveOrUpdate(application);

		// TAM-2745 : Don't create approval history for related applications
		if (!ProgramAndApplicationTypeCode.BFR.equals(program.getProgramTypeID().getProgramTypeCode()) && !application.getIsRelatedApplication()) {
			// create approvers (ie application level supervisors)
			if (program.getApprovalLevels() != null) {
				if (Program.ONE_APPROVER.equals(program.getApprovalLevels())) {
					participantService.populateSupervisorLevels(application.getParticipantID());
					if (application.getParticipantID().getLevelOneSupervisor() != null) {
						applicationApprovalService.createApprover(1, application, application.getParticipantID().getLevelOneSupervisor());
					}
				} else if (Program.TWO_APPROVERS.equals(program.getApprovalLevels())) {
					participantService.populateSupervisorLevels(application.getParticipantID());

					if (application.getParticipantID().getLevelOneSupervisor() != null) {
						applicationApprovalService.createApprover(1, application, application.getParticipantID().getLevelOneSupervisor());
					}

					if (application.getParticipantID().getLevelTwoSupervisor() != null) {
						applicationApprovalService.createApprover(2, application, application.getParticipantID().getLevelTwoSupervisor());
					}
				}
			}
		}
	}

	@Override
	public void captureApplicationHistory(Application entity) throws Exception {
		try {
			/* initially, just LoanRepay Reimbursement applications */
			if (entity.getReimburseStudentLoanApp() != null && entity.getApplicationID() != null) {
				ReimburseStudentLoanApp newRsla = entity.getReimburseStudentLoanApp();
				ReimburseStudentLoanAppHistory history = new ReimburseStudentLoanAppHistory();

				history.setApplicationID(entity.getApplicationID());
				history.setApplicationStatusID(entity.getApplicationStatusID().getApplicationStatusID());
				history.setApplicationTypeID(entity.getApplicationTypeID().getApplicationTypeID());
				history.setBenefitPeriodID(entity.getBenefitPeriodID().getBenefitPeriodID());
				history.setEducationalProviderID((entity.getEducationalProvider() == null ? null : entity.getEducationalProvider().getEducationalProviderId()));

				history.setDegreeObjectiveID((newRsla.getDegreeObjectiveID() == null ? null : newRsla.getDegreeObjectiveID().getDegreeObjectiveID()));
				history.setCourseOfStudyID((newRsla.getCourseOfStudyID() == null ? null : newRsla.getCourseOfStudyID().getCourseOfStudyID()));
				history.setStudentLoanServicerID((newRsla.getStudentLoanServicerID() == null ? null : newRsla.getStudentLoanServicerID().getStudentLoanServicerID()));
				history.setCreatedBy(newRsla.getCreatedBy());
				history.setDateCreated(newRsla.getDateCreated());
				history.setSelectedStudentLoanType(newRsla.getSelectedStudentLoanType());
				history.setOtherFieldOfStudy(newRsla.getOtherFieldOfStudy());
				history.setGraduationDate(newRsla.getGraduationDate());
				history.setGradePointAverage(newRsla.getGradePointAverage());

				history.setLoanAccountNumber(newRsla.getLoanAccountNumber());
				history.setLoanRequestedPaymentAmount(newRsla.getLoanRequestedPaymentAmount());
				history.setLoanPaymentPeriodStartDate(newRsla.getLoanPaymentPeriodStartDate());
				history.setLoanPaymentPeriodEndDate(newRsla.getLoanPaymentPeriodEndDate());
				history.setLoanApprovedAmount(newRsla.getLoanApprovedAmount());
				history.setLoanPaidAmount(newRsla.getLoanPaidAmount());
				history.setTotalStudentLoanDept(newRsla.getTotalStudentLoanDept());
				history.setLoanBalance(newRsla.getLoanBalance());
				history.setMonthlyPaymentAmount(newRsla.getMonthlyPaymentAmount());
				history.setRefundAmount(newRsla.getRefundAmount());

				history.setPreviouslyApproved(newRsla.getPreviouslyApproved());
				history.setModifiedBy(newRsla.getModifiedBy());
				history.setModifiedDate(newRsla.getModifiedDate());

				// history.setHistoryCreatedBy((this.getSessionService() == null
				// ? new Long(6391047) :
				// this.getSessionService().retrieveCurrentUser().getUserId()));
				history.setHistoryDateCreated(new Date());

				reimburseStudentLoanAppHistoryService.saveOrUpdate(history);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Boolean isApplicationEditable(Application application) {
		Boolean isEditable = false;
		Long appStatus = application.getApplicationStatusID().getApplicationStatusID();
		// if the app in incomplete, saved not submitted, or submitted pending
		// review, then allow edits
		if (appStatus.equals(ApplicationConstants.APPLICATION_STATUS_SAVED_NOT_SUBMITTED) || appStatus.equals(ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE)
				|| appStatus.equals(ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW)) {
			isEditable = true;
		} else if (userTypeService.verfiyLoggedInUserType(UserTypeConstants.INTERNAL_ADMIN)) {
			// TODO: List the conditions where an EdAssist admin can edit an
			// application
			isEditable = true;
		}
		return isEditable;
	}

	@Override
	public Boolean isUploadDocumentAllowed(Application application) {
		Boolean isAllowed = false;
		Long appStatus = application.getApplicationStatusID().getApplicationStatusID();

		if (appStatus.equals(ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW) || appStatus.equals(ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE)
				|| appStatus.equals(ApplicationConstants.APPLICATION_STATUS_APPROVED) || appStatus.equals(ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW)
				|| appStatus.equals(ApplicationConstants.APPLICATION_STATUS_LOC) || appStatus.equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_REVIEW)
				|| appStatus.equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_PROCESSING_INCOMPLETE_INFORMATION_NEEDED)
				|| appStatus.equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_REIMBURSEMENT_PROGRESS) || appStatus.equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_APPROVED_FUND_PROGRESS)
				|| appStatus.equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_INCOMPLETE_COMPLETION_DOCUMENTS_REQUIRED)
				|| appStatus.equals(ApplicationConstants.APPLICATION_STATUS_REPAYMENT_REQUIRED) || appStatus.equals(ApplicationConstants.APPLICATION_STATUS_SENT_COLLECTIONS)
				|| appStatus.equals(ApplicationConstants.APPLICATION_STATUS_CLOSED)) {
			isAllowed = true;
		} else if (userTypeService.verfiyLoggedInUserType(UserTypeConstants.INTERNAL_ADMIN)) {
			// TODO: List the conditions where an EdAssist admin can upload document
			isAllowed = true;
		}
		return isAllowed;
	}

	@Override
	public void sendEmailNotificationOnStatusChange(Application application, Long originalAppStatusCode, String comment) {
		if (originalAppStatusCode == null || application == null || application.getApplicationStatusID() == null || application.getApplicationStatusID().getApplicationStatusID() == null) {
			throw new BadRequestException("Application status must not be null.");
		}

		Long currentAppStatusCode = application.getApplicationStatusID().getApplicationStatusCode();
		Long applicationId = application.getApplicationID();

		if (!originalAppStatusCode.equals(currentAppStatusCode) || currentAppStatusCode.equals(ApplicationConstants.APPLICATION_STATUS_PAYMENT_PROCESSING_INCOMPLETE_INFORMATION_NEEDED)) {

			if (ApplicationConstants.APPLICATION_STATUS_DENIED.equals(currentAppStatusCode)) {

				emailService.sendApplicationEventNotificationEmail(applicationId, EmailConstant.EMAIL_APP_DENIED_PARTICIPANT, comment);
				emailService.sendApplicationEventNotificationEmail(applicationId, EmailConstant.EMAIL_APP_DENIED_SUPERVISOR, comment);
			} else if (ApplicationConstants.APPLICATION_STATUS_PAYMENT_PROCESSING_INCOMPLETE_INFORMATION_NEEDED.equals(application.getApplicationStatusID().getApplicationStatusCode())) {
				emailService.sendApplicationEventNotificationEmail(applicationId, EmailConstant.EMAIL_PAYMENT_INCOMPLETE_PARTICIPANT, comment);
			} else if (ApplicationConstants.APPLICATION_STATUS_PAYMENT_REIMBURSEMENT_PROGRESS.equals(application.getApplicationStatusID().getApplicationStatusCode())) {
				emailService.sendApplicationEventNotificationEmail(applicationId, EmailConstant.EMAIL_PAYMENT_PROCESSED_PARTICIPANT, comment);
			} else if (ApplicationConstants.APPLICATION_STATUS_APPROVED.equals(application.getApplicationStatusID().getApplicationStatusCode())) {
				emailService.sendApplicationEventNotificationEmail(applicationId, EmailConstant.EMAIL_APP_APPROVAL_PARTICIPANT, comment);
				emailService.sendApplicationEventNotificationEmail(applicationId, EmailConstant.EMAIL_APP_APPROVAL_SUPERVISOR, comment);
			} else if (ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE.equals(currentAppStatusCode)) {
				emailService.sendApplicationEventNotificationEmail(applicationId, EmailConstant.EMAIL_APPLICATION_SUBMISSION_INCOMPLETE, comment);
			} else if (application.isParticipantAgreement() && ApplicationConstants.APPLICATION_STATUS_LOC.equals(application.getApplicationStatusID().getApplicationStatusCode())) {
				emailService.sendApplicationEventNotificationEmail(applicationId, EmailConstant.EMAIL_APP_APPROVAL_PARTICIPANT, comment);
				emailService.sendApplicationEventNotificationEmail(applicationId, EmailConstant.EMAIL_APP_APPROVAL_SUPERVISOR, comment);
			} else if ((ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW.equals(currentAppStatusCode))
					&& (application.getApplicationTypeID().getApplicationTypeCode().toString().trim().equals("SLD"))) {
				emailService.sendApplicationEventNotificationEmail(applicationId, EmailConstant.EMAIL_APPLICATION_SUBMISSION_MANUAL, comment);
			}
		}

	}

	@Override
	public ApplicationSubmissionDTO applicationSubmission(Application tuitionApplication, Long userId) {

		List<RulesDTO> rulesList = new ArrayList<RulesDTO>();

		ApplicationSubmissionDTO applicationSubmissionDTO = new ApplicationSubmissionDTO();

		if (tuitionApplication.getParticipantID() == null || tuitionApplication.getApplicationStatusID() == null || tuitionApplication.getApplicationStatusID().getApplicationStatusCode() == null) {
			throw new BadRequestException();
		}

		/*
		 * TODO After setting up CRM configuration if (tuitionApplication.getParticipantID().getClient().isMandatoryAdvising()) { this.getAdvisingService().updateMandatoryAdvising(tuitionApplication.
		 * getParticipantID()); }
		 */

		Long originalAppStatusCode = tuitionApplication.getApplicationStatusID().getApplicationStatusCode();
		ApplicationStatus statusAfterSubmission = applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW);
		tuitionApplication.setDateModified(new Date());

		if (userId != null) {
			tuitionApplication.setApplicationStatusID(statusAfterSubmission, userId);
			tuitionApplication.setModifiedBy(userId);
		}

		tuitionApplication.setSubmittedDate(new Date());
		this.saveOrUpdate(tuitionApplication);

		RulesOutputDTO ruleEngineOutput = rulesService.getApplicationSubmissionRuleEngineOutput(tuitionApplication.getApplicationID());

		Long statusSentByRules;
		String rulesStatusComment;
		ApplicationStatus statusSentByRulesAppStatus = null;
		if (ruleEngineOutput != null) {
			Long programId = tuitionApplication.getBenefitPeriodID().getProgramID().getProgramID();
			for (RulesDTO rule : ruleEngineOutput.getRules()) {
				rulesList.add(rule);
			}
			rulesStatusComment = rulesService.formatRulesComment(ruleEngineOutput, rulesList);

			statusSentByRules = rulesService.convertStatusToID(ruleEngineOutput.getStatus(), programId, tuitionApplication.getApplicationID());

			if (statusSentByRules != null) {
				statusSentByRulesAppStatus = applicationStatusService.findByCode(statusSentByRules);
				if (ApplicationConstants.APPLICATION_STATUS_LOC.equals(statusSentByRules) || ApplicationConstants.APPLICATION_STATUS_APPROVED.equals(statusSentByRules)) {
					if (ruleEngineOutput.getStatus() != "BYPASS" || ruleEngineOutput.getStatus() != "bypass") {
						applicationApprovalService.adjustApproversSet(tuitionApplication);
						statusSentByRulesAppStatus = applicationApprovalService.processApplicationApproval(tuitionApplication, statusSentByRulesAppStatus);
					}
				}

				tuitionApplication.setApplicationStatusID(statusSentByRulesAppStatus, userId);
			}

			if (statusSentByRulesAppStatus.getApplicationStatusID().equals(ApplicationConstants.APPLICATION_STATUS_APPROVED)
					|| statusSentByRulesAppStatus.getApplicationStatusID().equals(ApplicationConstants.APPLICATION_STATUS_LOC) || tuitionApplication.getIsRelatedApplication()) {
				tuitionApplication.setApprovedDate(new Date());

				if (statusSentByRulesAppStatus.getApplicationStatusID().equals(ApplicationConstants.APPLICATION_STATUS_LOC)) {
					tuitionApplication.getPrepayTuitionApp().setIssueDate(new Date());
				}

				String[] paramNames = { "firstName", "lastName" };
				String[] paramValues = { "Team", "_Application" };

				List<User> applicationTeamUsers = userService.findByParams(paramNames, paramValues, null, null);
				if (applicationTeamUsers != null && applicationTeamUsers.size() > 0) {
					User aTeam = applicationTeamUsers.get(0);
					tuitionApplication.setApprovedBy(aTeam.getUserId());
				} else {
					tuitionApplication.setApprovedBy(null);
				}

			}

			applicationSubmissionDTO.setApplicationId(tuitionApplication.getApplicationID());
			applicationSubmissionDTO.setApplicationNumber(tuitionApplication.getApplicationNumber());
			applicationSubmissionDTO.setApplicationStatus(applicationStatusMapper.toDTO(statusSentByRulesAppStatus));
			applicationSubmissionDTO.setRules(rulesList);

			if (tuitionApplication.getIsRelatedApplication()) {
				tuitionApplication.setApplicationStatusID((ApplicationStatus) applicationStatusService.findById(ApplicationConstants.APPLICATION_STATUS_PAYMENT_REVIEW), userId);
			}

			this.saveOrUpdate(tuitionApplication);

			try {
				this.sendEmailNotificationOnStatusChange(tuitionApplication, originalAppStatusCode, rulesStatusComment);
			} catch (Exception ex) {
				log.error(ex.getClass() + ":" + ex.getMessage(), ex.getCause());
			}
		}

		return applicationSubmissionDTO;
	}

	@Override
	public Boolean deleteAppNumber(Application application) {
		User user = new User();
		user.setFirstName(sessionService.getClaimAsString(JWTTokenClaims.USER_FIRST_NAME));
		user.setLastName(sessionService.getClaimAsString(JWTTokenClaims.USER_LAST_NAME));
		String requester = user.getFullNameFirstThenLast();
		return applicationDao.deleteAppNumber(application, requester);
	}

	@Override
	public PaginationResult<ThinApp> getActionNeededTaskList(Participant participant, int index, int recordsPerPage) {
		PaginationResult<ThinApp> appList = applicationDao.getActionNeededTaskList(participant, index, recordsPerPage);
		List<ThinApp> removeApps = new ArrayList<ThinApp>();
		for (ThinApp thinApp : appList.getResult()) {
			if (thinApp.getApplicationStatusID().getApplicationStatusCode() == ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW
					&& participant.getParticipantId().longValue() == thinApp.getParticipantID().getParticipantId().longValue()) {
				removeApps.add(thinApp);
			}
		}
		appList.getResult().removeAll(removeApps);
		return appList;
	}

	@Override
	public Long getNumberOfUnreadComments(Application application) {
		return applicationDao.getNumberOfUnreadComments(application);
	}

	@Override
	public List<Option> getTeamFilterOptions(Participant participant) {
		List<Option> filterOptionList = new ArrayList<>();

		// build the fixed options group options
		for (AppTeamFilterConstants filterConstant : AppTeamFilterConstants.values()) {
			String display = filterConstant.getDisplay();
			if (filterConstant.getValue().equals(AppTeamFilterConstants.TEAM_MEMBER_ME.getValue())) {
				display += " (" + participant.getUser().getFirstName() + " " + participant.getUser().getLastName() + ")";
			}

			Option filterOption = new Option(filterConstant.getGroup(), display, filterConstant.getValue());

			if ((participant.getUser().getUserType().getId() == UserTypeConstants.CLIENT_ADMIN) || (!filterConstant.getValue().equals(AppTeamFilterConstants.TEAM_MEMBER_COMPANY.getValue()))) {
				filterOptionList.add(filterOption);
			}
		}

		if ((participant.getUser().getUserType().getId() == UserTypeConstants.CLIENT_ADMIN) || (participant.getUser().getUserType().getId() == UserTypeConstants.SUPERVISOR)) {
			// build your team participant list group options
			final List<ParticipantSupervisor> supervisedParticipantList = participantService.findSupervisoredParticipantList(participant);
			for (ParticipantSupervisor participantSupervisor : supervisedParticipantList) {
				String participantName = participantSupervisor.getParticipantID().getUser().getFirstName() + " " + participantSupervisor.getParticipantID().getUser().getLastName();
				Option teamFilterOption = new Option("Team", participantName, participantSupervisor.getParticipantID().getParticipantId().toString());
				filterOptionList.add(teamFilterOption);
			}
		}

		return filterOptionList;
	}

	@Override
	public List<Option> getAppSortOptions() {
		List<Option> sortOptionList = new ArrayList<>();

		for (AppSortingConstants appSortingConstant : AppSortingConstants.values()) {
			if (!appSortingConstant.getValue().equals(AppSortingConstants.TEAM_MEMBER.getValue()) || userTypeService.verfiyLoggedInUserType(UserTypeConstants.SUPERVISOR)) {
				Option sortOption = new Option(appSortingConstant.getDisplay(), appSortingConstant.getValue() + " " + appSortingConstant.getDirection());
				sortOptionList.add(sortOption);
			}
		}

		return sortOptionList;
	}

	@Override
	public List<Option> getBenefitPeriodFilterOptions(Long participantId, String teamMemberType, Long defaultBenefitPeriodId) {
		List<Option> filterOptions = new ArrayList<>();
		filterOptions.add(new Option("All", "-1"));
		List<String> benefitPeriodNames = applicationDao.getBenefitPeriodFilterOptions(participantId, teamMemberType, defaultBenefitPeriodId);
		if (benefitPeriodNames != null) {
			for (String benefitPeriodName : benefitPeriodNames) {
				filterOptions.add(new Option(benefitPeriodName, benefitPeriodName));
			}
		}

		return filterOptions;
	}

	@Override
	public byte[] exportAppsToCsv(String teamMemberType, String sortingProperty, String benefitPeriods) {
		PaginationResult<ThinApp> paginationResult = applicationDao.findSelfAndSuperviseeApplciations(sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID), 1, 250, teamMemberType,
				sortingProperty, benefitPeriods);
		CSVFormat csvFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		Writer out = new BufferedWriter(new OutputStreamWriter(byteStream));
		CSVPrinter csvPrinter = null;
		try {
			csvPrinter = new CSVPrinter(out, csvFormat);
			List<String> headers = buildHeaders();
			csvPrinter.printRecord(headers);
			for (ThinApp app : paginationResult.getResult()) {
				List appRecord = buildAppCsvRow(app);
				csvPrinter.printRecord(appRecord);
			}
		} catch (IOException e) {
			throw new BadRequestException("Failed to Export CSV file");
		} finally {
			IOUtils.closeQuietly(csvPrinter);
			IOUtils.closeQuietly(out);
		}

		return byteStream.toByteArray();
	}

	private List<String> buildHeaders() {
		List<String> headers = new ArrayList<>(Arrays.asList("Application Status", "Application Number", "Submitted Date", "Participant Name", "Tuition Program", "Education Provider",
				"Degree Program", "Field of study", "Session Start", "End Date", "Benefit Period", "Requested Amount", "Adjustments", "Other Expenses", "Total", "Payment Processed date"));
		List<String> courseHeaders = new ArrayList<>(Arrays.asList("Course #", "Course Name", "Tuition Amount", "Expense Amount", "Book amount"));
		// Adding this course header set 5 times
		IntStream.rangeClosed(1, 5).forEach(i -> headers.addAll(courseHeaders));
		return headers;
	}

	private List buildAppCsvRow(ThinApp app) {
		List appRecord = new ArrayList();

		appRecord.add(app.getApplicationStatusID().getApplicationStatus());
		appRecord.add(String.valueOf(app.getApplicationNumber()));
		appRecord.add(CommonUtil.formatDate(app.getSubmittedDate(), CommonUtil.MMDDYYY));
		appRecord.add(CommonUtil.getFullName(app.getParticipantID().getUser().getFirstName(), app.getParticipantID().getUser().getMiddleInitial(), app.getParticipantID().getUser().getLastName()));
		appRecord.add(app.getBenefitPeriodID().getProgramID().getProgramName());
		appRecord.add(app.getEducationalProvider().getProviderName());
		appRecord.add(app.getDegreeObjective());
		appRecord.add(app.getCourseOfStudy());
		appRecord.add(CommonUtil.formatDate(app.getCourseStartDate(), CommonUtil.MMDDYYY));
		appRecord.add(CommonUtil.formatDate(app.getCourseEndDate(), CommonUtil.MMDDYYY));
		appRecord.add(app.getBenefitPeriodID().getBenefitPeriodName());

		List<ThinCourse> courses = app.getCourses();
		List<ThinExpense> nonCourseRelatedExpenses = app.getNonCourseRelatedExpenses();

		BigDecimal requestedAmount = getTotalRequestedAmount(app.getCourses(), nonCourseRelatedExpenses);
		BigDecimal adjustments = getTotalRefunds(app.getCourses(), nonCourseRelatedExpenses);
		BigDecimal nonCourseRelatedFees = getNonCourseRelatedExpenses(nonCourseRelatedExpenses);
		appRecord.add(requestedAmount);
		appRecord.add(adjustments);
		appRecord.add(nonCourseRelatedFees);
		appRecord.add(requestedAmount.subtract(adjustments));
		appRecord.add(CommonUtil.formatDate(app.getPaymentDate(), CommonUtil.MMDDYYY));

		// build course columns
		if (courses != null && courses.size() > 0) {
			for (ThinCourse course : courses) {
				buildCourseCsvColumns(appRecord, course);
			}
		}
		return appRecord;
	}

	private BigDecimal getNonCourseRelatedExpenses(List<ThinExpense> nonCourseRelatedExpenses) {
		BigDecimal fees = new BigDecimal(0);

		if (nonCourseRelatedExpenses != null) {
			for (ThinExpense expense : nonCourseRelatedExpenses) {
				fees.add(expense.getFeesAmount());
			}
		}
		return fees;
	}

	private List buildCourseCsvColumns(List courseRow, ThinCourse course) {
		BigDecimal relatedFeeAmount = new BigDecimal(0);
		BigDecimal bookAmount = new BigDecimal(0);
		List<ThinExpense> expenses = course.getRelatedExpenses();

		courseRow.add(course.getNumber());
		courseRow.add(course.getName());
		courseRow.add(String.valueOf(course.getTuitionAmount()));

		if (expenses != null && expenses.size() > 0) {
			for (ThinExpense expense : expenses) {
				if (expense.getExpenseType().isBookExpense() && expense.getFeesAmount() != null) {
					bookAmount = bookAmount.add(expense.getFeesAmount());
				} else if (expense.getExpenseType().isCourseRelatedFee()) {
					relatedFeeAmount = relatedFeeAmount.add(expense.getFeesAmount());
				}
			}
		}
		courseRow.add(relatedFeeAmount);
		courseRow.add(bookAmount);

		return courseRow;
	}

	@Override
	public BigDecimal getTotalRefunds(List<ThinCourse> courses, List<ThinExpense> nonCourseExpenses) {
		BigDecimal totalRefunds = BigDecimal.ZERO;
		if (courses != null) {
			List<ThinExpense> expenses = new ArrayList<>();

			for (ThinCourse course : courses) {
				if (course.getRefundAmount() != null) {
					expenses.addAll(course.getRelatedExpenses());
					totalRefunds = totalRefunds.add(course.getRefundAmount());
				}
			}

			for (ThinExpense expense : expenses) {
				if (expense.getRefundAmount() != null) {
					totalRefunds = totalRefunds.add(expense.getRefundAmount());
				}
			}

			for (ThinExpense nonCourseExpense : nonCourseExpenses) {
				if (nonCourseExpense.getRefundAmount() != null) {
					totalRefunds = totalRefunds.add(nonCourseExpense.getRefundAmount());
				}
			}
		}
		return totalRefunds;
	}

	@Override
	public BigDecimal getTotalRequestedAmount(List<ThinCourse> courses, List<ThinExpense> nonCourseExpenses) {
		BigDecimal totalRequestedAmount = BigDecimal.ZERO;
		List<ThinExpense> expenses = new ArrayList<>();

		if (courses != null) {
			BigDecimal requestedTuition = BigDecimal.ZERO;
			BigDecimal requestedFee = BigDecimal.ZERO;

			for (ThinCourse course : courses) {
				expenses.addAll(course.getRelatedExpenses());
				if (course.getTuitionAmount() != null) {
					requestedTuition = requestedTuition.add(course.getTuitionAmount());
				}
			}

			for (ThinExpense expense : expenses) {
				if (expense.getFeesAmount() != null) {
					requestedFee = requestedFee.add(expense.getFeesAmount());
				}
			}

			totalRequestedAmount = totalRequestedAmount.add(requestedTuition).add(requestedFee);
		}

		if (nonCourseExpenses != null) {
			BigDecimal nonCourseFees = BigDecimal.ZERO;

			for (ThinExpense nonCourseExpense : nonCourseExpenses) {
				if (nonCourseExpense.getFeesAmount() != null) {
					nonCourseFees = nonCourseFees.add(nonCourseExpense.getFeesAmount());
				}
			}

			totalRequestedAmount = totalRequestedAmount.add(nonCourseFees);
		}

		return totalRequestedAmount;
	}

	@Override
	public App findAppById(Long applicationId) {
		return applicationDao.findAppById(applicationId);
	}

	@Override
	public ThinApp findThinAppById(Long applicationId) {
		return applicationDao.findThinAppById(applicationId);
	}

	@Override
	public Application getRelatedBookApplication(Long applicationId) {
		List<ParentRelatedApplicationRelationship> relatedApplicationRelationshipList = parentRelatedApplicationRelationshipService.findByParam("parentApplicationID", applicationId);
		if (relatedApplicationRelationshipList.size() > 0) {
			for (ParentRelatedApplicationRelationship currRelationship : relatedApplicationRelationshipList) {
				Application relatedApplication = currRelationship.getRelatedApplicationID();
				if (ProgramAndApplicationTypeCode.BFR.equals(relatedApplication.getApplicationTypeID().getApplicationTypeCode())) {
					return relatedApplication;
				}
			}
		}
		return null;
	}

}
