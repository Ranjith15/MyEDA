package com.edassist.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tika.Tika;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.edassist.exception.BadRequestException;
import com.edassist.exception.NotFoundException;
import com.edassist.models.domain.Client;
import com.edassist.models.domain.Participant;
import com.edassist.models.domain.Program;
import com.edassist.models.domain.crm.TicketStatus;
import com.edassist.models.domain.crm.Topic;
import com.edassist.models.dto.*;
import com.edassist.models.mappers.ContactMapper;
import com.edassist.models.mappers.TopicMapper;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.*;
import com.edassist.utils.CommonUtil;
import com.edassist.utils.RestUtils;

@RestController
public class VhdController {

	private final SessionService sessionService;
	private final ReferenceDataService referenceDataService;
	private final ProgramService programService;
	private final CRMService crmService;
	private final TopicMapper topicMapper;
	private final ContactMapper contactMapper;
	private final AccessService accessService;

	@Autowired
	public VhdController(SessionService sessionService, ReferenceDataService referenceDataService, ProgramService programService, CRMService crmService, TopicMapper topicMapper,
			ContactMapper contactMapper, AccessService accessService) {
		this.sessionService = sessionService;
		this.referenceDataService = referenceDataService;
		this.programService = programService;
		this.crmService = crmService;
		this.topicMapper = topicMapper;
		this.contactMapper = contactMapper;
		this.accessService = accessService;
	}

	@RequestMapping(value = "/v1/vhd/tickets", method = RequestMethod.GET)
	public ResponseEntity<List<TicketSummaryDTO>> getTicketHistory() {
		ArrayList<String> values = new ArrayList<>();
		String participantId = sessionService.getClaimAsString(JWTTokenClaims.PARTICIPANT_ID);
		Long clientId = sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID);
		values.add(participantId);
		List<TicketSummaryDTO> ticketList;
		ticketList = crmService.getTicketHistory("Participant Id", values, clientId);
		Collections.sort(ticketList);
		for (TicketSummaryDTO summary : ticketList) {
			Topic topic = referenceDataService.lookupTopicWithSubTopic(summary.getSubTopicId());
			if (topic != null) {
				summary.setTopicName(topic.getName());
			} else {
				summary.setTopicName("");
			}
			topic = referenceDataService.lookupSubTopic(summary.getSubTopicId());
			if (topic != null) {
				summary.setSubTopicName(topic.getName());
			} else {
				summary.setSubTopicName("");
			}
		}

		return new ResponseEntity<>(ticketList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/vhd/tickets", method = RequestMethod.POST)
	public ResponseEntity<TicketDetailsDTO> submitTicket(@RequestParam boolean isFile, @RequestParam String subTopicId, @RequestParam String commentText, HttpServletRequest servletRequest)
			throws IOException {
		Participant participant = accessService.retrieveAndCompareParticipantToSession(sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID));
		ContactDTO contact = contactMapper.toDTO(participant.getUser());
		List<AttachmentDTO> attachments = null;
		if (isFile) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) servletRequest;
			MultipartFile file = multipartRequest.getFile("file");
			validateAttachment(file);
			AttachmentDTO attachment = new AttachmentDTO();
			attachment.setFileName(file.getOriginalFilename());
			attachment.setContent(RestUtils.convertToBase64(file.getBytes()));
			attachments = new ArrayList<>();
			attachments.add(attachment);
		}

		String ticketNumber = crmService.submitTicket(subTopicId, "", commentText, contact, attachments);
		TicketDetailsDTO dtoObj = new TicketDetailsDTO();
		dtoObj.setTicketNumber(ticketNumber);
		return new ResponseEntity<>(dtoObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/vhd/tickets/{ticketId}", method = RequestMethod.GET)
	public ResponseEntity<TicketDetailsDTO> getTicketDetails(@PathVariable("ticketId") String ticketId) {
		TicketDetailsDTO tktDetails = crmService.getTicketDetails(ticketId);
		if (tktDetails == null || tktDetails.getTicketNumber() == null || tktDetails.getTicketNumber().trim().length() == 0) {
			throw new BadRequestException("Ticket not found");
		}
		tktDetails.setCreatedOn(RestUtils.formatCrmDate(tktDetails.getCreatedOn()));
		tktDetails.setModifiedOn(RestUtils.formatCrmDate(tktDetails.getModifiedOn()));

		Topic topic = referenceDataService.lookupTopicWithSubTopic(tktDetails.getSubTopicId());
		if (topic != null) {
			tktDetails.setTopic(topic.getName());
		} else {
			tktDetails.setTopic("");
		}
		topic = referenceDataService.lookupSubTopic(tktDetails.getSubTopicId());
		if (topic != null) {
			tktDetails.setSubTopic(topic.getName());
		} else {
			tktDetails.setSubTopic("");
		}
		Comparator<NotesDTO> compareNotes = (NotesDTO n1, NotesDTO n2) -> n1.getFormattedAddedOn().compareTo(n2.getFormattedAddedOn());
		tktDetails.getNotes().sort(compareNotes.reversed());

		return new ResponseEntity<>(tktDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/vhd/tickets/{ticketId}", method = RequestMethod.POST)
	public ResponseEntity<Boolean> updateTicket(@PathVariable("ticketId") String ticketId, @RequestParam boolean isFile, @RequestParam(required = false) String status,
			@RequestParam String commentText, HttpServletRequest servletRequest) throws IOException {
		List<AttachmentDTO> attachments = null;

		if (isFile) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) servletRequest;
			MultipartFile file = multipartRequest.getFile("file");
			validateAttachment(file);

			AttachmentDTO attachment = new AttachmentDTO();
			attachment.setFileName(file.getOriginalFilename());
			attachment.setContent(RestUtils.convertToBase64(file.getBytes()));
			attachments = new ArrayList<>();
			attachments.add(attachment);
		}

		String firstName = sessionService.getClaimAsString(JWTTokenClaims.USER_FIRST_NAME);
		String lastName = sessionService.getClaimAsString(JWTTokenClaims.USER_LAST_NAME);
		boolean responseStatus = crmService.updateTicket(ticketId, commentText, status, firstName, lastName, attachments);

		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/vhd/tickets/{ticketId}/notes/{notesId}/{annotationId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadAttachment(@PathVariable String ticketId, @PathVariable String notesId, @PathVariable String annotationId) throws IOException, MimeTypeException {

		AttachmentDTO attachment = crmService.getTicketAttachment(ticketId, annotationId, notesId);
		if (attachment == null || attachment.getContent() == null || attachment.getContent().trim().length() == 0) {
			throw new BadRequestException("Attachment not found");
		}

		final HttpHeaders headers = new HttpHeaders();
		byte[] fileBytes = RestUtils.convertFromBase64(attachment.getContent());

		TikaInputStream tikaStream = TikaInputStream.get(fileBytes);
		Tika tika = new Tika();
		String mimeType = tika.detect(tikaStream);
		headers.setContentType(MediaType.valueOf(mimeType));

		MimeTypes defaultMimeTypes = MimeTypes.getDefaultMimeTypes();
		String extension = defaultMimeTypes.forName(mimeType).getExtension();
		headers.add("file-ext", extension);

		return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/vhd/tickets/search", method = RequestMethod.GET)
	public ResponseEntity<List<TicketSummaryDTO>> searchTickets(@RequestParam(required = false) String ticketNumber, @RequestParam(required = false) String participantId,
			@RequestParam(required = false) String employeeId, @RequestParam(required = false) String clientName, @RequestParam(required = false) String status,
			@RequestParam(required = false) String createdFrom, @RequestParam(required = false) String createdTo, @RequestParam(required = false) String firstName,
			@RequestParam(required = false) String lastName, @RequestParam(required = false) String clientId) {
		List<TicketSummaryDTO> tktSummaryList = crmService.searchTicket(ticketNumber, participantId, employeeId, clientName, status, createdFrom, createdTo, firstName, lastName, clientId);

		Collections.sort(tktSummaryList);
		for (TicketSummaryDTO summary : tktSummaryList) {
			Topic topic = referenceDataService.lookupTopicWithSubTopic(summary.getSubTopicId());
			if (topic != null) {
				summary.setTopicName(topic.getName());
			} else {
				summary.setTopicName("");
			}
			topic = referenceDataService.lookupSubTopic(summary.getSubTopicId());
			if (topic != null) {
				summary.setSubTopicName(topic.getName());
			} else {
				summary.setSubTopicName("");
			}
		}

		return new ResponseEntity<>(tktSummaryList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/vhd/topics", method = RequestMethod.GET)
	public ResponseEntity<List<TopicDTO>> gethelpdeskTopics() {
		List<Topic> topicList = referenceDataService.getTopics();
		List<Topic> clientTopicList = new ArrayList<>();

		Long participantId = sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID);
		Long clientId = sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID);
		if (participantId != null) {
			if (clientId != null) {
				List<Program> programList;
				Client clientObj = new Client();
				clientObj.setClientId(clientId);
				programList = programService.findByParam("clientID", clientObj);

				boolean match;
				for (Topic topic : topicList) {
					match = false;
					programLoop: for (Program program : programList) {
						for (com.edassist.models.domain.crm.ProgramType type : topic.getProgramTypes()) {
							if (program.getProgramTypeID().getProgramTypeID() == Long.parseLong(type.getProgramTypeID().trim())) {
								match = true;
								break programLoop;
							}
						}
					}
					if (match) {
						clientTopicList.add(topic);
					}
				}
			}
		}
		List<TopicDTO> topicDToLst = topicMapper.toDTOList(clientTopicList);
		return new ResponseEntity<>(topicDToLst, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/vhd/topics/{topicId}", method = RequestMethod.GET)
	public ResponseEntity<List<TopicDTO>> gethelpdeskSubTopics(@PathVariable("topicId") String topicId) {
		List<Topic> subTopicList = referenceDataService.getSubTopics(topicId);
		if (subTopicList == null || subTopicList.size() == 0) {
			throw new NotFoundException("Sub Topics not found for the Topic " + topicId);
		}
		List<TopicDTO> topicDToLst = topicMapper.toDTOList(subTopicList);
		return new ResponseEntity<>(topicDToLst, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/vhd/tickets/status", method = RequestMethod.GET)
	public ResponseEntity<List<TicketStatus>> getHelpDeskTicketStatusList() {
		List<TicketStatus> statusList = referenceDataService.getStatuses();
		return new ResponseEntity<>(statusList, HttpStatus.OK);
	}

	private void validateAttachment(MultipartFile file) throws BadRequestException {
		final int fileSize = new Long(file.getSize()).intValue();
		int allowedSize = new Integer(CommonUtil.getHotProperty(CommonUtil.HOT_UPLOAD_VHD_DOCUMENT_MAX_FILE_SIZE));
		final String simpleFileName = CommonUtil.getSimpleFileName(file.getOriginalFilename());
		if (simpleFileName.length() > 0) {
			if (!RestUtils.isValidCrmFileType(simpleFileName)) {
				throw new BadRequestException("Invalid File Type");
			}
			if (fileSize > allowedSize) {
				throw new BadRequestException("Invalid File Size");
			}
		} else {
			throw new BadRequestException("Invalid File Type");
		}
	}

}
