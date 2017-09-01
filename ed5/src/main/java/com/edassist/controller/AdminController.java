package com.edassist.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edassist.constants.ApplicationConstants;
import com.edassist.constants.UserTypeConstants;
import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.*;
import com.edassist.models.dto.*;
import com.edassist.models.mappers.ApplicationStatusMapper;
import com.edassist.models.mappers.ParticipantSupervisorMapper;
import com.edassist.models.mappers.ThinReviewAppMapper;
import com.edassist.models.mappers.ThinReviewApplicationMapper;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.*;

@RestController
public class AdminController {

	private final AccessService accessService;
	private final ApplicationService applicationService;
	private final ApplicationStatusService applicationStatusService;
	private final ApplicationApprovalService applicationApprovalService;
	private final ApprovalHistoryService approvalHistoryService;
	private final SessionService sessionService;
	private final RulesService rulesService;
	private final ParticipantService participantService;
	private final ParticipantSupervisorMapper participantSupervisorMapper;
	private final ThinReviewApplicationMapper thinReviewApplicationMapper;
	private final ThinReviewAppMapper thinReviewAppMapper;
	private final ApplicationStatusMapper applicationStatusMapper;
	private final UserTypeService userTypeService;
	private final ApprovalTypeService approvalTypeService;

	@Autowired
	public AdminController(SessionService sessionService, AccessService accessService, ApplicationService applicationService, ApplicationStatusService applicationStatusService,
			ApprovalTypeService approvalTypeService, ApplicationApprovalService applicationApprovalService, ApplicationStatusMapper applicationStatusMapper,
			ApprovalHistoryService approvalHistoryService, RulesService rulesService, ParticipantService participantService, UserTypeService userTypeService,
			ParticipantSupervisorMapper participantSupervisorMapper, ThinReviewApplicationMapper thinReviewApplicationMapper, ThinReviewAppMapper thinReviewAppMapper) {
		this.sessionService = sessionService;
		this.accessService = accessService;
		this.applicationService = applicationService;
		this.applicationStatusService = applicationStatusService;
		this.approvalTypeService = approvalTypeService;
		this.applicationApprovalService = applicationApprovalService;
		this.applicationStatusMapper = applicationStatusMapper;
		this.approvalHistoryService = approvalHistoryService;
		this.rulesService = rulesService;
		this.participantService = participantService;
		this.userTypeService = userTypeService;
		this.participantSupervisorMapper = participantSupervisorMapper;
		this.thinReviewApplicationMapper = thinReviewApplicationMapper;
		this.thinReviewAppMapper = thinReviewAppMapper;
	}

	@RequestMapping(value = "/v1/admins/{adminParticipantID}/participants", method = RequestMethod.GET)
	public ResponseEntity<List<ParticipantSupervisorDTO>> getManagerTeamPpts(@PathVariable("adminParticipantID") Long adminParticipantID) {

		Participant adminParticipant = accessService.compareParticipantOrClientAdminToSession(adminParticipantID);

		if (adminParticipant.getUser().getUserType().getId() != 3) {
			throw new BadRequestException("User is not an admin");
		}

		List<ParticipantSupervisor> participantSupervisorList = participantService.findSupervisoredParticipantList(adminParticipant);

		List<ParticipantSupervisorDTO> participantSupervisorDTOs = participantSupervisorMapper.toDTOList(participantSupervisorList);

		return new ResponseEntity<>(participantSupervisorDTOs, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/admins/{adminParticipantID}/applications", method = RequestMethod.GET)
	public ResponseEntity<ThinReviewApplicationResultSetDTO> getManagerTeamApps(@PathVariable("adminParticipantID") Long adminParticipantID,
			@RequestParam(value = "index", required = false, defaultValue = "1") int index, @RequestParam(value = "recordsPerPage", required = false, defaultValue = "200") int recordsPerPage) {
		ThinReviewApplicationResultSetDTO thinReviewApplicationResultSetDTO = new ThinReviewApplicationResultSetDTO();
		List<ThinReviewApplicationDTO> thinReviewApplicationDTOs;
		PaginationDTO paginationDTO = new PaginationDTO();

		Participant supervisorParticipant = accessService.retrieveAndCompareParticipantToSession(adminParticipantID);

		PaginationResult<ThinApp> superviseesApplicationList = participantService.searchThinAppActivityForMyTeam(supervisorParticipant, index, recordsPerPage);

		thinReviewApplicationDTOs = thinReviewAppMapper.toDTOList(superviseesApplicationList.getResult());

		paginationDTO.setTotal(superviseesApplicationList.getTotalRecordsCount());
		paginationDTO.setIndex(index);

		thinReviewApplicationResultSetDTO.setApplications(thinReviewApplicationDTOs);
		thinReviewApplicationResultSetDTO.setPagination(paginationDTO);

		return new ResponseEntity<>(thinReviewApplicationResultSetDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/v1/admins/{adminParticipantID}/tasklist", method = RequestMethod.GET)
	public ResponseEntity<ThinReviewApplicationResultSetDTO> getManagerTasklist(@PathVariable("adminParticipantID") Long adminParticipantID,
			@RequestParam(value = "index", required = false, defaultValue = "1") int index, @RequestParam(value = "recordsPerPage", required = false, defaultValue = "200") int recordsPerPage) {

		ThinReviewApplicationResultSetDTO thinReviewApplicationResultSetDTO = new ThinReviewApplicationResultSetDTO();
		PaginationDTO paginationDTO = new PaginationDTO();
		Participant participant = participantService.findById(adminParticipantID);
		accessService.verifyParticipantOrHigherAccess(participant);

		PaginationResult<ThinAppActivityForMyTeam> superviseeApplications = applicationApprovalService.findActionRequiredApplications(participant, index, recordsPerPage);

		List<ThinReviewApplicationDTO> thinReviewApplicationDTOs = thinReviewApplicationMapper.toDTOList(superviseeApplications.getResult());

		paginationDTO.setTotal(superviseeApplications.getTotalRecordsCount());
		paginationDTO.setIndex(index);

		thinReviewApplicationResultSetDTO.setApplications(thinReviewApplicationDTOs);
		thinReviewApplicationResultSetDTO.setPagination(paginationDTO);

		return new ResponseEntity<>(thinReviewApplicationResultSetDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/v1/admins/{userID}/applications/{applicationID}/application-status", method = RequestMethod.POST)
	public ResponseEntity<ApplicationStatusDTO> updateApplicationStatus(@PathVariable("userID") Long userID, @PathVariable("applicationID") Long applicationID,
			@RequestBody ApplicationStatusChangeDTO applicationStatusChangeDTO) {

		ApplicationStatusDTO newStatus = null;

		accessService.compareUserToSession(userID);

		Application application = applicationService.findById(applicationID);

		if (application == null) {
			throw new BadRequestException("Application does not exist");
		}

		if (applicationStatusChangeDTO == null) {
			throw new BadRequestException("Application request can not be empty");
		}

		if (applicationStatusChangeDTO.getApplicationStatusCode() == null) {
			throw new BadRequestException("Requested application status code does not exist");
		}

		ApplicationStatus appStatus = applicationStatusService.findByCode(applicationStatusChangeDTO.getApplicationStatusCode());

		if (appStatus == null) {
			throw new BadRequestException("Application status is empty");
		}

		String statusChangeComment = applicationStatusChangeDTO.getComment();

		boolean isCurrentUserAdmin = userTypeService.verfiyLoggedInUserType(UserTypeConstants.CLIENT_ADMIN);
		boolean isCurrentUserSupervisor = applicationApprovalService.isCurrentUserApprover(application);

		if (!isCurrentUserAdmin) {
			if (!isCurrentUserSupervisor || !applicationApprovalService.isCurrentUserSupervisorApprovalValid(application)) {
				throw new BadRequestException("You are not authorized to view this page.");
			}
		}

		if (application.getApplicationStatusID() == null || application.getApplicationStatusID().getApplicationStatusCode() == null) {
			throw new BadRequestException("Application status must not be null.");
		}

		Long originalAppStatusCode = application.getApplicationStatusID().getApplicationStatusCode();

		if (StringUtils.isEmpty(statusChangeComment)) {
			statusChangeComment = appStatus.getApplicationStatus();
		}

		if (ApplicationConstants.APPLICATION_STATUS_DENIED.equals(appStatus.getApplicationStatusCode())) {
			applicationService.changeApplicationStatus(application, appStatus, statusChangeComment, true, ApplicationConstants.EVENT_HISTORY_MANUAL_TYPE, true);

			if (userTypeService.verfiyLoggedInUserType(UserTypeConstants.SUPERVISOR)) {
				List<ApprovalHistory> approvalHistoryList = approvalHistoryService.findByParam("applicationID", application.getApplicationID());
				for (ApprovalHistory approvalHistory : approvalHistoryList) {
					if (approvalHistory.getSupervisor().getParticipantId().equals(sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID))) {
						applicationApprovalService.updateApprovalHistory(approvalHistory, approvalTypeService.fetchDeniedApprovalType());
						newStatus = applicationStatusMapper.toDTO(appStatus);
					}
				}
			}
		} else if (ApplicationConstants.APPLICATION_STATUS_APPROVED.equals(appStatus.getApplicationStatusCode())) {
			ApplicationStatus newAppStatus;

			if (ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW.equals(application.getApplicationStatusID().getApplicationStatusID())) {

				if (application.isPrePaymentApplication()) {
					newAppStatus = applicationApprovalService.processApplicationApproval(application, applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_LOC));
					newStatus = applicationStatusMapper.toDTO(newAppStatus);
				} else {
					newAppStatus = applicationApprovalService.processApplicationApproval(application, applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_APPROVED));
					newStatus = applicationStatusMapper.toDTO(newAppStatus);
				}
				if (newStatus.getApplicationStatusID().equals(ApplicationConstants.APPLICATION_STATUS_APPROVED)
						|| newStatus.getApplicationStatusID().equals(ApplicationConstants.APPLICATION_STATUS_LOC)) {
					application.setApprovedDate(new Date());
					application.setApprovedBy(userID);
					if (newStatus.getApplicationStatusID().equals(ApplicationConstants.APPLICATION_STATUS_LOC)) {
						application.getPrepayTuitionApp().setIssueDate(new Date());
					}
				}

				applicationService.changeApplicationStatus(application, newAppStatus, statusChangeComment, true, ApplicationConstants.EVENT_HISTORY_MANUAL_TYPE, false);

			} else {
				applicationService.saveApplication(application);

				newStatus = rulesService.getApplicationSubmissionStatus(applicationID);

				if (newStatus == null) {
					throw new BadRequestException("Status returned from rules must not be null.");
				}

				if (!ApplicationConstants.APPLICATION_STATUS_LOC.equals(newStatus.getApplicationStatusID())
						&& !ApplicationConstants.APPLICATION_STATUS_APPROVED.equals(newStatus.getApplicationStatusID())) {
					// TODO: What to do in this case?

				}
				applicationApprovalService.resetApprovers(application);
				newAppStatus = applicationApprovalService.processApplicationApproval(application, applicationStatusService.findByCode(newStatus.getApplicationStatusCode()));
				newStatus = applicationStatusMapper.toDTO(newAppStatus);

				applicationService.changeApplicationStatus(application, newAppStatus, statusChangeComment, true, ApplicationConstants.EVENT_HISTORY_MANUAL_TYPE, false);
			}
		} else {
			throw new BadRequestException("Application status is not valid");
		}

		applicationService.saveApplication(application);

		applicationService.sendEmailNotificationOnStatusChange(application, originalAppStatusCode, statusChangeComment);

		return new ResponseEntity<>(newStatus, HttpStatus.OK);
	}
}