package com.edassist.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edassist.constants.ApplicationConstants;
import com.edassist.constants.RestConstants;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ForbiddenException;
import com.edassist.exception.UnprocessableEntityException;
import com.edassist.models.domain.*;
import com.edassist.models.dto.*;
import com.edassist.models.mappers.*;
import com.edassist.service.*;

@RestController
public class ApplicationController {

	private final AccessService accessService;
	private final ApplicationService applicationService;
	private final ApplicationCoursesService applicationCoursesService;
	private final ApplicationStatusService applicationStatusService;
	private final ApplicationSessionInfoMapper applicationSessionInfoMapper;
	private final ApplicationStatusMapper applicationStatusMapper;
	private final EligibilityEventHistoryMapper eligibilityEventHistoryMapper;
	private final EducationalProvidersService educationalProvidersService;
	private final ProgramService programService;
	private final TuitionApplicationMapper tuitionApplicationMapper;
	private final ThinBookApplicationMapper thinBookApplicationMapper;
	private final PaymentMapper paymentMapper;
	private final RefundsMapper refundMapper;
	private final ApplicationMapper applicationMapper;
	private final BenefitPeriodService benefitPeriodService;
	private final SessionService sessionService;

	@Autowired
	public ApplicationController(AccessService accessService, ApplicationService applicationService, BenefitPeriodService benefitPeriodService, ApplicationCoursesService applicationCoursesService,
			ApplicationStatusService applicationStatusService, ThinBookApplicationMapper thinBookApplicationMapper, RefundsMapper refundMapper, ApplicationMapper applicationMapper,
			EducationalProvidersService educationalProvidersService, ProgramService programService, TuitionApplicationMapper tuitionApplicationMapper,
			ApplicationSessionInfoMapper applicationSessionInfoMapper, PaymentMapper paymentMapper, ApplicationStatusMapper applicationStatusMapper,
			EligibilityEventHistoryMapper eligibilityEventHistoryMapper, SessionService sessionService) {
		this.accessService = accessService;
		this.applicationService = applicationService;
		this.benefitPeriodService = benefitPeriodService;
		this.applicationCoursesService = applicationCoursesService;
		this.applicationStatusService = applicationStatusService;
		this.thinBookApplicationMapper = thinBookApplicationMapper;
		this.refundMapper = refundMapper;
		this.applicationMapper = applicationMapper;
		this.educationalProvidersService = educationalProvidersService;
		this.programService = programService;
		this.tuitionApplicationMapper = tuitionApplicationMapper;
		this.applicationSessionInfoMapper = applicationSessionInfoMapper;
		this.paymentMapper = paymentMapper;
		this.applicationStatusMapper = applicationStatusMapper;
		this.eligibilityEventHistoryMapper = eligibilityEventHistoryMapper;
		this.sessionService = sessionService;
	}

	@RequestMapping(value = "/v1/applications/{applicationID}/submission", method = RequestMethod.POST)
	public ResponseEntity<ApplicationSubmissionDTO> applicationSubmission(@PathVariable("applicationID") Long applicationID) {

		Application tuitionApplication = applicationService.findById(applicationID);
		accessService.verifyUserOrClientAdminToSession(tuitionApplication.getParticipantID().getParticipantId(), tuitionApplication.getBenefitPeriodID().getProgramID().getClientID().getClientId());
		User currentUser = sessionService.getCurrentUser();

		if (tuitionApplication.getFinancialAidSourceId() != null && tuitionApplication.getFinancialAidSourceId().getFinancialAidSourceId() != null
				&& tuitionApplication.getReimburseTuitionApp() != null && tuitionApplication.getReimburseTuitionApp().getCourseStartDate() != null
				&& tuitionApplication.getReimburseTuitionApp().getCourseEndDate() != null) {
			ApplicationSubmissionDTO applicationSubmissionDTO = applicationService.applicationSubmission(tuitionApplication, currentUser.getUserId());
			return new ResponseEntity<>(applicationSubmissionDTO, HttpStatus.OK);
		} else {
			if (tuitionApplication.getReimburseTuitionApp() == null || tuitionApplication.getReimburseTuitionApp().getCourseStartDate() == null
					|| tuitionApplication.getReimburseTuitionApp().getCourseEndDate() == null || CollectionUtils
					.isEmpty(applicationCoursesService.getApplicationCoursesWithGradeCompliance(tuitionApplication))) {
				throw new UnprocessableEntityException(RestConstants.INCOMPLETE_APPLICATION_COURSES);
			} else if (tuitionApplication.getFinancialAidSourceId() == null || tuitionApplication.getFinancialAidSourceId().getFinancialAidSourceId() == null) {
				throw new UnprocessableEntityException(RestConstants.INCOMPLETE_APPLICATION_AGREEMENTS);
			} else {
				throw new UnprocessableEntityException(RestConstants.INCOMPLETE_APPLICATION);
			}
		}
	}

	@RequestMapping(value = "/v1/applications/tuition-applications/{applicationId}", method = RequestMethod.GET)
	public ResponseEntity<TuitionApplicationDTO> getApplicationDetails(@PathVariable("applicationId") Long applicationId) {
		TuitionApplicationDTO tuitionApplication = new TuitionApplicationDTO();

		Application application = applicationService.findById(applicationId);
		accessService.verifyParticipantOrHigherAccess(application);
		if (application != null) {
			tuitionApplication = tuitionApplicationMapper.toDTO(application);
		} else {
			throw new BadRequestException("Application not found");
		}

		ThinBookApplicationDTO bookApplication = thinBookApplicationMapper.toDTO(applicationService.getRelatedBookApplication(applicationId));
		tuitionApplication.setBookApplication(bookApplication);

		tuitionApplication.setUnreadMessages(applicationService.getNumberOfUnreadComments(application));

		return new ResponseEntity<>(tuitionApplication, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/tuition-applications/{applicationId}", method = RequestMethod.PUT)
	public ResponseEntity<TuitionApplicationDTO> updateTuitionApplication(@PathVariable("applicationId") Long applicationId, @RequestBody TuitionApplicationDTO tuitionApplicationDTO)
			throws Exception {

		Application tuitionApplication = applicationService.findById(applicationId);
		tuitionApplication = tuitionApplicationMapper.toDomain(tuitionApplicationDTO, tuitionApplication);

		applicationService.saveApplication(tuitionApplication);

		Application updatedTuitionApplication = applicationService.findById(applicationId);

		TuitionApplicationDTO updatedTuitionApplicationDTO = tuitionApplicationMapper.toDTO(updatedTuitionApplication);

		return new ResponseEntity<>(updatedTuitionApplicationDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/tuition-applications", method = RequestMethod.POST)
	public ResponseEntity<TuitionApplicationDTO> createApplication(@RequestBody TuitionApplicationDTO tuitionApplicationDTO) throws Exception {

		Application tuitionApplication = new Application();
		TuitionApplicationDTO applicationDTO = new TuitionApplicationDTO();

		if (tuitionApplicationDTO != null) {
			Participant participant = accessService.retrieveAndCompareParticipantToSession(tuitionApplicationDTO.getParticipantID().getParticipantId());

			tuitionApplication = tuitionApplicationMapper.toDomain(tuitionApplicationDTO, tuitionApplication);

			tuitionApplication.setParticipantID(participant);

			accessService.verifyUserToSession(tuitionApplication);

			if (tuitionApplication.getParticipantID().getCurrentEducationProfile() == null) {
				tuitionApplication.getParticipantID().setCurrentEducationProfile(new ParticipantCurrentEducationProfile());
			}

			EducationalProviders currentProvider = educationalProvidersService.findById(tuitionApplication.getEducationalProvider().getEducationalProviderId());
			tuitionApplication.getParticipantID().getCurrentEducationProfile().setProviderID(currentProvider);

			Program program = programService.findById(tuitionApplicationDTO.getBenefitPeriodID().getProgramID().getProgramID());
			applicationService.processNewApplication(tuitionApplication, program, currentProvider);

			applicationDTO = tuitionApplicationMapper.toDTO(tuitionApplication);

		} else {
			throw new BadRequestException();
		}

		return new ResponseEntity<>(applicationDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationID}/cancellation", method = RequestMethod.POST)
	public ResponseEntity<ApplicationStatusDTO> cancel(@PathVariable("applicationID") Long applicationID, @RequestBody ApplicationStatusChangeDTO applicationStatusChangeDTO) {

		ApplicationStatusDTO newStatus;
		Application application = applicationService.findById(applicationID);

		if (application == null) {
			throw new BadRequestException("Application does not exist");
		}

		Long originalAppStatusCode = application.getApplicationStatusID().getApplicationStatusCode();
		accessService.verifyUserOrClientAdminToSession(application.getParticipantID().getParticipantId(), application.getBenefitPeriodID().getProgramID().getClientID().getClientId());

		if (applicationStatusChangeDTO == null) {
			throw new BadRequestException("Application request can not be empty");
		}

		if (applicationStatusChangeDTO.getApplicationStatusCode() == null) {
			throw new BadRequestException("Requested application status code does not exist");
		}

		ApplicationStatus cancelledStatus = applicationStatusService.findByCode(applicationStatusChangeDTO.getApplicationStatusCode());

		if (cancelledStatus == null) {
			throw new BadRequestException("Application status is empty");
		}

		String comment = applicationStatusChangeDTO.getComment();

		applicationService.changeApplicationStatus(application, cancelledStatus, comment, true, ApplicationConstants.EVENT_HISTORY_MANUAL_TYPE, false);

		applicationService.saveApplication(application);

		if (!ApplicationConstants.APPLICATION_STATUS_SAVED_NOT_SUBMITTED.equals(originalAppStatusCode))
			applicationService.sendEmailNotificationOnStatusChange(application, originalAppStatusCode, comment);

		newStatus = applicationStatusMapper.toDTO(cancelledStatus);

		return new ResponseEntity<>(newStatus, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationID}/delete", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteAppNumber(@PathVariable("applicationID") Long applicationID) {
		boolean deleted;
		Application application = applicationService.findById(applicationID);
		if (application == null) {
			throw new BadRequestException("Application does not exist");
		}
		accessService.verifyUserOrClientAdminToSession(application.getParticipantID().getParticipantId(), application.getBenefitPeriodID().getProgramID().getClientID().getClientId());
		if (application.getApplicationStatusID().getApplicationStatusID() == 90L) {
			deleted = applicationService.deleteAppNumber(application);
		} else {
			throw new ForbiddenException(RestConstants.INVALID_APPLICATION_STATUS);
		}
		return new ResponseEntity<>(deleted, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationID}/agreements", method = RequestMethod.POST)
	public ResponseEntity<Boolean> signAgreements(@PathVariable("applicationID") Long applicationID, @RequestParam Boolean agreementVerify) {

		Application application = applicationService.findById(applicationID);

		if (application == null) {
			throw new BadRequestException("Application not found");
		}

		accessService.compareParticipantToSession(application.getParticipantID().getParticipantId());

		User currentUser = sessionService.getCurrentUser();

		if (!applicationService.isApplicationEditable(application)) {
			throw new ForbiddenException();
		}

		if (currentUser == null) {
			throw new BadRequestException("User not found");
		}

		application.setParticipantAgreement(agreementVerify);
		if (application.getAgreementsDate() == null) {
			application.setAgreementsDate(new Date());
		}
		application.setDateModified(new Date());
		application.setModifiedBy(currentUser.getUserId());

		applicationService.saveOrUpdate(application);

		return new ResponseEntity<>(agreementVerify, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/expense-snapshot", method = RequestMethod.GET)
	public ResponseEntity<ApplicationExpenseSnapshotDTO> getExpenseSnapshot(@PathVariable("applicationId") Long applicationId) {

		Application application = applicationService.findById(applicationId);
		ApplicationExpenseSnapshotDTO applicationExpenseSnapshotDTO = new ApplicationExpenseSnapshotDTO();

		if (application == null) {
			throw new BadRequestException("Application not found");
		}

		accessService.verifyParticipantOrHigherAccess(application);

		applicationExpenseSnapshotDTO.setRequestedExpense(applicationService.requestedTotals(applicationId));
		applicationExpenseSnapshotDTO.setApprovedExpense(applicationService.approvedTotals(applicationId));
		applicationExpenseSnapshotDTO.setPaidExpense(applicationService.paidTotals(applicationId));

		return new ResponseEntity<>(applicationExpenseSnapshotDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/session-info", method = RequestMethod.POST)
	public ResponseEntity<ApplicationSessionInfoDTO> saveApplicationSessionInfo(@PathVariable("applicationId") Long applicationID, @RequestBody ApplicationSessionInfoDTO applicationSessionInfoDTO) {

		Application application = applicationService.findById(applicationID);
		accessService.compareParticipantToSession(application.getParticipantID().getParticipantId());
		Application updatedApplication = applicationSessionInfoMapper.toDomain(applicationSessionInfoDTO, application);
		benefitPeriodService.adjustBenefitPeriod(updatedApplication);
		applicationService.saveOrUpdate(updatedApplication);
		ApplicationSessionInfoDTO savedApplicationSessionInfo = applicationSessionInfoMapper.toDTO(updatedApplication);

		return new ResponseEntity<>(savedApplicationSessionInfo, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/eligibility-event-history", method = RequestMethod.GET)
	public ResponseEntity<List<EligibilityEventHistoryDTO>> getEligibilityEventHistory(@PathVariable("applicationId") Long applicationId) {

		App application = applicationService.findAppById(applicationId);
		accessService.verifyParticipantOrHigherAccess(application.getParticipant());

		List<EligibilityEventHistoryDTO> eligibilityEventHistoryDTOs = eligibilityEventHistoryMapper.toDTOListFromRuleMessages(application.getRuleMessages());
		eligibilityEventHistoryDTOs.addAll(eligibilityEventHistoryMapper.toDTOListFromEventComments(application.getEligibilityEventComments()));
		eligibilityEventHistoryDTOs.addAll(eligibilityEventHistoryMapper.toDTOListFromAppStatusChange(application.getAppStatusChangeCollection()));
		eligibilityEventHistoryDTOs.addAll(eligibilityEventHistoryMapper.toDTOListFromAppStatusChangeLive(application.getAppStatusChangeLiveCollection()));

		return new ResponseEntity<>(eligibilityEventHistoryDTOs, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/payment-history", method = RequestMethod.GET)
	public ResponseEntity<PaymentHistoryDTO> getPaymentHistory(@PathVariable("applicationId") Long applicationId) {

		PaymentHistoryDTO paymentHistoryDTO = new PaymentHistoryDTO();
		ThinApp application = applicationService.findThinAppById(applicationId);
		accessService.verifyParticipantOrHigherAccess(application);
		List<Payments> paymentList = applicationService.getPaymentHistory(application);
		List<Refunds> refundList = applicationService.getRefundsHistory(applicationId);
		paymentHistoryDTO.setPayments(paymentMapper.toDTOList(paymentList));
		paymentHistoryDTO.setRefunds(refundMapper.toDTOList(refundList));

		return new ResponseEntity<>(paymentHistoryDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/csv-export", method = RequestMethod.POST)
	public ResponseEntity<byte[]> exportAppsToCsv(@RequestParam(value = "teamMemberType", required = false, defaultValue = "me") String teamMemberType,
			@RequestParam(value = "sortBy", required = false, defaultValue = "applicationNumber DESC") String sortingProperty,
			@RequestParam(value = "benefitPeriod", required = false, defaultValue = "-1") String benefitPeriods) throws MimeTypeException {
		byte[] fileBytes = applicationService.exportAppsToCsv(teamMemberType, sortingProperty, benefitPeriods);
		final HttpHeaders headers = new HttpHeaders();
		String mimeType = "text/csv";
		MimeTypes defaultMimeTypes = MimeTypes.getDefaultMimeTypes();
		String extension = defaultMimeTypes.forName(mimeType).getExtension();
		headers.add("file-ext", extension);
		headers.setContentType(MediaType.valueOf(mimeType));

		return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}", method = RequestMethod.GET)
	public ResponseEntity<AppDTO> getApp(@PathVariable("applicationId") Long applicationId) {
		App app = applicationService.findAppById(applicationId);
		accessService.verifyParticipantOrHigherAccess(app.getParticipant());
		AppDTO appDTO = applicationMapper.toAppDTO(app);

		return new ResponseEntity<>(appDTO, HttpStatus.OK);
	}

}