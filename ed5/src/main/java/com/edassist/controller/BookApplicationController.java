package com.edassist.controller;

import com.edassist.constants.ExpenseTypeConstants;
import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.*;
import com.edassist.models.domain.type.ProgramAndApplicationTypeCode;
import com.edassist.models.dto.ApplicationExpensesDTO;
import com.edassist.models.dto.ApplicationSubmissionDTO;
import com.edassist.models.dto.BookApplicationDTO;
import com.edassist.models.dto.RulesDTO;
import com.edassist.models.mappers.GrantScholarshipMapper;
import com.edassist.models.sp.ApplicationNumber;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class BookApplicationController {

	private final GrantScholarshipMapper grantScholarshipMapper;
	private final AccessService accessService;
	private final ApplicationService applicationService;
	private final SessionService sessionService;
	private final ApplicationCoursesService applicationCoursesService;
	private final ProgramService programService;
	private final GenericService<ApplicationType> applicationTypeService;
	private final GenericService<ParentRelatedApplicationRelationship> parentRelatedApplicationRelationshipService;
	private final GenericService<ExpenseType> expenseTypeService;
	private final GenericService<FinancialAidSource> financialAidSourceService;
	private final BenefitPeriodService benefitPeriodService;

	@Autowired
	public BookApplicationController(AccessService accessService, GrantScholarshipMapper grantScholarshipMapper, ApplicationService applicationService, SessionService sessionService,
			ApplicationCoursesService applicationCoursesService, ProgramService programService, GenericService<ApplicationType> applicationTypeService,
			GenericService<ParentRelatedApplicationRelationship> parentRelatedApplicationRelationshipService, GenericService<ExpenseType> expenseTypeService,
			GenericService<FinancialAidSource> financialAidSourceService, BenefitPeriodService benefitPeriodService) {
		this.accessService = accessService;
		this.grantScholarshipMapper = grantScholarshipMapper;
		this.applicationService = applicationService;
		this.sessionService = sessionService;
		this.applicationCoursesService = applicationCoursesService;
		this.programService = programService;
		this.applicationTypeService = applicationTypeService;
		this.parentRelatedApplicationRelationshipService = parentRelatedApplicationRelationshipService;
		this.expenseTypeService = expenseTypeService;
		this.financialAidSourceService = financialAidSourceService;
		this.benefitPeriodService = benefitPeriodService;
	}

	@RequestMapping(value = { "/v1/applications/book-applications" }, method = RequestMethod.POST)
	public ResponseEntity<List<RulesDTO>> createBookApplication(@RequestBody BookApplicationDTO bookApplicationDTO) throws Exception {

		Long parentApplicationId = bookApplicationDTO.getParentApplicationID();
		Application parentApplication = applicationService.findById(parentApplicationId);

		User currentUser = accessService.verifyUserToSession(parentApplication);

		List<ParentRelatedApplicationRelationship> relatedApplicationRelationshipList = parentRelatedApplicationRelationshipService.findByParam("parentApplicationID", parentApplicationId);
		if (relatedApplicationRelationshipList.size() > 0) {
			for (ParentRelatedApplicationRelationship currRelationship : relatedApplicationRelationshipList) {
				Application relatedApplication = currRelationship.getRelatedApplicationID();
				if (ProgramAndApplicationTypeCode.BFR.equals(relatedApplication.getApplicationTypeID().getApplicationTypeCode())) {
					throw new BadRequestException("Cannot create more than one book applications");
				}
			}
		}

		Application newBookApplication = new Application();
		EducationalProviders currentProvider = parentApplication.getEducationalProvider();
		newBookApplication.setEducationalProvider(currentProvider);
		newBookApplication.setParticipantID(parentApplication.getParticipantID());
		newBookApplication.setStudentID(parentApplication.getStudentID());
		newBookApplication.setCourseStartDate(parentApplication.getCourseStartDate());
		newBookApplication.setCourseEndDate(parentApplication.getCourseEndDate());
		newBookApplication.setSectorID(parentApplication.getSectorID());
		newBookApplication.setRelatedAppId(parentApplication.getApplicationID());
		newBookApplication.setDegreeCompleteYN(parentApplication.getDegreeCompleteYN());

		Program program = programService.findById(bookApplicationDTO.getProgramId());

		applicationService.processNewApplication(newBookApplication, program, currentProvider);

		for (ApplicationExpensesDTO applicationExpense : bookApplicationDTO.getApplicationExpenses()) {
			this.saveBookExpense(parentApplicationId, newBookApplication.getApplicationID(), applicationExpense.getRelatedCourseID().getApplicationCoursesID(), applicationExpense.getFeesAmount(),
					applicationExpense.getNumberOfBooks());
		}

		FinancialAidSource financialAidSource = financialAidSourceService.findById(bookApplicationDTO.getGrantScholarship().getFinancialAidSourceId().getFinancialAidSourceId());
		Application applicationAfterUpdate = grantScholarshipMapper.toDomain(bookApplicationDTO.getGrantScholarship(), financialAidSource, newBookApplication);

		newBookApplication.setParticipantAgreement(bookApplicationDTO.isAgreementVerify());
		if (newBookApplication.getAgreementsDate() == null) {
			newBookApplication.setAgreementsDate(new Date());
		}
		newBookApplication.setDateModified(new Date());
		newBookApplication.setModifiedBy(currentUser.getUserId());

		applicationService.saveOrUpdate(applicationAfterUpdate);

		ParentRelatedApplicationRelationship parentRelatedAppRelationship = new ParentRelatedApplicationRelationship();
		parentRelatedAppRelationship.setParentApplicationID(parentApplication.getApplicationID());
		parentRelatedAppRelationship.setRelatedApplicationID(newBookApplication);
		parentRelatedApplicationRelationshipService.saveOrUpdate(parentRelatedAppRelationship);

		ApplicationSubmissionDTO applicationSubmissionDTO = applicationService.applicationSubmission(newBookApplication, currentUser.getUserId());

		return new ResponseEntity<>(applicationSubmissionDTO.getRules(), HttpStatus.OK);
	}

	private void saveBookExpense(Long parentApplicationId, Long bookApplicationId, Long relatedCourseId, BigDecimal feesAmount, Integer numberOfBooks) throws Exception {
		Application parentApplication = null;
		if (parentApplicationId != null && parentApplicationId != 0L) {
			parentApplication = applicationService.findById(parentApplicationId);
		}

		String[] paramNames = { "code" };
		Object[] values = { ExpenseTypeConstants.BOOKS.getCode() };
		ExpenseType expenseType = expenseTypeService.findEntityByParams(paramNames, values, null, null);
		if (expenseType == null) {
			throw new BadRequestException("Expense Type not found");
		}

		ApplicationCourses relatedCourse = null;
		if (relatedCourseId != null) {
			relatedCourse = applicationCoursesService.findById(relatedCourseId);
		}
		this.create(parentApplication, bookApplicationId, expenseType, relatedCourse, feesAmount, numberOfBooks);
	}

	private void create(Application parentApplication, Long bookApplicationId, ExpenseType expenseType, ApplicationCourses relatedCourse, BigDecimal feesAmount, Integer numberOfBooks)
			throws Exception {
		Application bookApplication;
		if (bookApplicationId != null && bookApplicationId != 0L) {
			bookApplication = applicationService.findById(bookApplicationId);
		} else {
			List<Application> relatedBookApps = applicationService.findRelatedBookApplications(parentApplication.getApplicationID());
			if (relatedBookApps.size() != 0) {
				bookApplication = relatedBookApps.get(0);
			} else {
				List<ApplicationType> appTypesByCode = applicationTypeService.findByParam("applicationTypeCode", ProgramAndApplicationTypeCode.BFR);
				if (appTypesByCode == null || appTypesByCode.size() != 1) {
					throw new BadRequestException();
				}
				ApplicationType bookType = appTypesByCode.get(0);
				bookApplication = parentApplication.createBookApp(parentApplication.getPrepayTuitionApp(), bookType, 0.0, sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
				benefitPeriodService.adjustBenefitPeriod(bookApplication);
				List<ApplicationNumber> nextApplicationNumberList = applicationService.getNextApplicationNumber();
				ApplicationNumber newApplicationNumber = nextApplicationNumberList.get(0);
				bookApplication.setApplicationNumber(newApplicationNumber.getApplicationNumber());
				applicationService.saveOrUpdate(bookApplication);
			}
		}

		if (bookApplication.getApplicationCoursesCollection() == null) {
			bookApplication.setApplicationCoursesCollection(new ArrayList<>());
		}

		ApplicationCourses applicationCourse = new ApplicationCourses();
		applicationCourse.setExpenseType(expenseType);
		applicationCourse.setRelatedCourseID(relatedCourse);
		applicationCourse.setMaintainSkillsYN(relatedCourse.getMaintainSkillsYN());
		applicationCourse.setMeetMinimumQualsYN(relatedCourse.getMeetMinimumQualsYN());
		applicationCourse.setNewCareerFieldYN(relatedCourse.getNewCareerFieldYN());
		applicationCourse.setTaxability(relatedCourse.getTaxability());
		applicationCourse.setFeesAmount(feesAmount);
		applicationCourse.setNumberOfBooks(numberOfBooks);

		applicationCourse.setDateCreated(new Date());
		applicationCourse.setCreatedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
		applicationCourse.setApplicationID(bookApplication);

		bookApplication.getApplicationCoursesCollection().add(applicationCourse);
		applicationCoursesService.saveOrUpdate(applicationCourse);
		applicationService.saveOrUpdate(bookApplication);
	}
}
