package com.edassist.controller;

import com.edassist.exception.BadRequestException;
import com.edassist.exception.ForbiddenException;
import com.edassist.models.domain.Application;
import com.edassist.models.domain.ApplicationComment;
import com.edassist.models.domain.User;
import com.edassist.models.domain.WF_Queue;
import com.edassist.models.dto.ApplicationCommentsDTO;
import com.edassist.models.mappers.ApplicationCommentsMapper;
import com.edassist.service.AccessService;
import com.edassist.service.ApplicationService;
import com.edassist.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ApplicationCommentsController {

	private final AccessService accessService;
	private final ApplicationService applicationService;
	private final ApplicationCommentsMapper applicationCommentsMapper;
	private final GenericService<ApplicationComment> applicationCommentService;
	private final GenericService<WF_Queue> wf_QueueService;

	@Autowired
	public ApplicationCommentsController(AccessService accessService, ApplicationService applicationService, ApplicationCommentsMapper applicationCommentsMapper,
			GenericService<ApplicationComment> applicationCommentService, GenericService<WF_Queue> wf_QueueService) {
		this.accessService = accessService;
		this.applicationService = applicationService;
		this.applicationCommentsMapper = applicationCommentsMapper;
		this.applicationCommentService = applicationCommentService;
		this.wf_QueueService = wf_QueueService;
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/comments", method = RequestMethod.GET)
	public ResponseEntity<List<ApplicationCommentsDTO>> getApplicationComments(@PathVariable("applicationId") Long applicationId) {

		List<ApplicationCommentsDTO> applicationCommentsDTOList;
		Application application = applicationService.findById(applicationId);
		accessService.verifyParticipantOrClientAdminAccess(application);

		List<ApplicationComment> applicationCommentsList = applicationCommentService.findByParam("applicationID", applicationId, "dateCreated");

		applicationCommentsList.removeIf(i -> !i.getViewableToParticipant().equals(Boolean.TRUE));

		applicationCommentsDTOList = applicationCommentsMapper.toDTOList(applicationCommentsList);

		return new ResponseEntity<>(applicationCommentsDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/comments", method = RequestMethod.POST)
	public ResponseEntity<ApplicationCommentsDTO> saveApplicationComments(@PathVariable("applicationId") Long applicationId, @RequestBody ApplicationCommentsDTO applicationCommentsDTO) {

		Application application = applicationService.findById(applicationId);
		Long applicationCommentIdFromDto = applicationCommentsDTO.getApplicationCommentId();
		User user = accessService.verifyUserToSession(application);

		ApplicationComment newApplicationComment = new ApplicationComment();

		if (applicationCommentIdFromDto == null || applicationCommentIdFromDto.equals(0L)) {
			newApplicationComment = applicationCommentsMapper.toDomain(applicationCommentsDTO, new ApplicationComment(), application);

			newApplicationComment.setCreatedBy(user);
			newApplicationComment.setDateCreated(new Date());
			// TODO Add this field to the DTO/mapping when the requirements for admin are set.
			newApplicationComment.setViewableToParticipant(Boolean.TRUE);

			applicationCommentService.saveOrUpdate(newApplicationComment);
		}
		ApplicationCommentsDTO newApplicationCommentDTO = applicationCommentsMapper.toDTO(newApplicationComment);

		return new ResponseEntity<>(newApplicationCommentDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/comments/{applicationCommentId}", method = RequestMethod.PUT)
	public ResponseEntity<ApplicationCommentsDTO> updateApplicationComments(@PathVariable("applicationId") Long applicationId, @PathVariable("applicationCommentId") Long applicationCommentId,
			@RequestBody ApplicationCommentsDTO applicationCommentsDTO) {

		Application application = applicationService.findById(applicationId);
		Long applicationCommentIdFromDto = applicationCommentsDTO.getApplicationCommentId();
		User user = accessService.verifyUserToSession(application);

		ApplicationComment applicationComment;

		if (applicationCommentIdFromDto != null || applicationCommentIdFromDto != (0L) || applicationCommentId != null || applicationCommentId != applicationCommentIdFromDto
				|| applicationCommentsDTO.getApplicationID() != applicationId) {
			applicationComment = applicationCommentService.findById(applicationCommentId);

			ApplicationComment updatedApplicationComment = applicationCommentsMapper.toDomain(applicationCommentsDTO, applicationComment, application);

			updatedApplicationComment.setModifiedBy(user);
			updatedApplicationComment.setModifiedDate(new Date());

			applicationCommentService.saveOrUpdate(updatedApplicationComment);
		} else {
			throw new BadRequestException();
		}

		ApplicationCommentsDTO updatedApplicationCommentDTO = applicationCommentsMapper.toDTO(applicationComment);

		return new ResponseEntity<>(updatedApplicationCommentDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/comments/{applicationCommentId}", method = RequestMethod.DELETE)
	public void deleteApplicationComments(@PathVariable("applicationId") Long applicationId, @PathVariable("applicationCommentId") Long applicationCommentId) {

		Application application = applicationService.findById(applicationId);
		accessService.verifyUserToSession(application);
		ApplicationComment applicationComment = applicationCommentService.findById(applicationCommentId);

		if (applicationComment != null && applicationComment.getApplicationID().equals(applicationId)) {
			applicationCommentService.remove(applicationCommentId);
		} else {
			throw new ForbiddenException();
		}

		List<WF_Queue> wfqList = wf_QueueService.findByParam("applicationIssuesId", applicationCommentId);
		for (WF_Queue wfq : wfqList) {
			wf_QueueService.remove(wfq);
		}
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/comments/review-app-comments", method = RequestMethod.POST)
	public void ReviewApplicationComments(@PathVariable("applicationId") Long applicationId, @RequestBody List<ApplicationCommentsDTO> applicationCommentsDtoList) throws Exception {

		Application application = applicationService.findById(applicationId);
		User user = accessService.verifyUserToSession(application);

		for (ApplicationCommentsDTO appCommentDto : applicationCommentsDtoList) {
			ApplicationComment originalAppComment = applicationCommentService.findById(appCommentDto.getApplicationCommentId());
			ApplicationComment appComment = applicationCommentsMapper.toDomain(appCommentDto, originalAppComment, application);

			appComment.setReviewed(Boolean.TRUE);
			appComment.setReviewedBy(user);
			appComment.setReviewedDate(new Date());

			applicationCommentService.saveOrUpdate(appComment);
		}

	}

}
