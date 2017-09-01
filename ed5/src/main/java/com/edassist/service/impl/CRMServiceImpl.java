package com.edassist.service.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.edassist.exception.*;
import com.edassist.models.contracts.crm.ContactWrapper;
import com.edassist.models.contracts.crm.advising.*;
import com.edassist.models.domain.crm.Code;
import com.edassist.models.domain.crm.ReferenceData;
import com.edassist.models.dto.*;
import com.edassist.service.CRMService;
import com.edassist.service.impl.crm.*;
import com.edassist.utils.CommonUtil;
import com.edassist.utils.RestUtils;

@Service
public class CRMServiceImpl implements CRMService {

	private static Logger log = Logger.getLogger(CRMServiceImpl.class);
	private static String authToken;

	@Autowired
	@Qualifier("crmRestTemplate")
	private RestTemplate restTemplate;
	private Properties responseCodes;

	@Override
	public String submitTicket(String subTopicId, String applicationNumber, String comment, ContactDTO contact, List<AttachmentDTO> attachments) throws CrmException {
		String svcUrl = this.getUrl(RestUtils.CRM_SUBMIT_TICKET);

		SubmitTicketRequest request = new SubmitTicketRequest();
		request.setContact(contact);
		request.setSubTopicId(subTopicId);
		request.setComment(comment);
		request.setApplicationNumber(applicationNumber);
		request.setAttachments(attachments);
		log.info("Submit Ticket Request : " + request);

		RestTemplate restTemplate = this.restTemplate;
		HttpEntity<SubmitTicketRequest> requestEntity = new HttpEntity<SubmitTicketRequest>(request, createHeaders());
		ResponseEntity<SubmitTicketResponse> response = null;
		try {
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, SubmitTicketResponse.class);
		} catch (CrmTokenExpiredException ex) {
			log.info("Auth Token Expired.Authenticating again");
			authToken = null;
			requestEntity = new HttpEntity<SubmitTicketRequest>(request, createHeaders());
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, SubmitTicketResponse.class);
		}

		log.info("Submit Ticket Response : " + response.getBody());

		return response.getBody().getTicketNumber();
	}

	@Override
	public ReferenceData getReferenceData() throws CrmException {
		String svcUrl = this.getUrl(RestUtils.CRM_GET_REFERENCEDATA);
		log.info("Get ReferenceData Request : ");
		RestTemplate restTemplate = this.restTemplate;
		HttpEntity requestEntity = new HttpEntity(createHeaders());
		ResponseEntity<GetReferenceDataResponse> response = null;
		try {

			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, GetReferenceDataResponse.class);

		} catch (CrmTokenExpiredException ex) {
			log.info("Auth Token Expired.Authenticating again");
			authToken = null;
			requestEntity = new HttpEntity(createHeaders());
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, GetReferenceDataResponse.class);
		}
		log.info("Get ReferenceData Response : " + response.getBody());

		ReferenceData data = new ReferenceData();

		data.setTopics(response.getBody().getListTopic());
		data.setStates(response.getBody().getTicketStates());
		data.setHelpDeskTopics(response.getBody().getHelpDeskTopics());
		return data;
	}

	@Override
	public List<Code> getReferenceData(String category) throws CrmException {
		String svcUrl = this.getUrl(RestUtils.CRM_GET_ADVREFDATA);
		RestTemplate restTemplate = this.restTemplate;
		GetAdvisingReferenceDataResquest request = new GetAdvisingReferenceDataResquest();
		request.setCategory(category);
		log.info("Get Advising ReferenceData Request : " + request);
		HttpEntity<GetAdvisingReferenceDataResquest> requestEntity = new HttpEntity<GetAdvisingReferenceDataResquest>(request, createHeaders());
		ResponseEntity<GetAdvisingReferenceDataResponse> response = null;
		try {
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, GetAdvisingReferenceDataResponse.class);
		} catch (CrmTokenExpiredException ex) {
			log.info("Auth Token Expired.Authenticating again");
			authToken = null;
			requestEntity = new HttpEntity<GetAdvisingReferenceDataResquest>(createHeaders());
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, GetAdvisingReferenceDataResponse.class);
		}
		log.info("Get ReferenceData Response : " + response.getBody());

		return (response.getBody().getReferenceData() != null ? response.getBody().getReferenceData() : new ArrayList<Code>());
	}

	@Override
	public List<TicketSummaryDTO> getTicketHistory(String key, List<String> values, long clientId) throws CrmException {
		String svcUrl = this.getUrl(RestUtils.CRM_GET_TICKETHISTORY);
		GetTicketHistoryRequest request = new GetTicketHistoryRequest();
		request.setKey(key);
		request.setValues(values.toArray(new String[values.size()]));
		if (clientId > 0) {
			request.setClientid(Long.toString(clientId));
		}
		log.info("getTicketHistory Request : " + request);

		RestTemplate restTemplate = this.restTemplate;
		HttpHeaders headers = createHeaders();
		HttpEntity<GetTicketHistoryRequest> requestEntity = new HttpEntity<GetTicketHistoryRequest>(request, headers);
		ResponseEntity<GetTicketHistoryResponse> response = null;
		try {
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, GetTicketHistoryResponse.class);
		} catch (CrmTokenExpiredException ex) {
			log.info("Auth Token Expired.Authenticating again");
			authToken = null;
			requestEntity = new HttpEntity<GetTicketHistoryRequest>(request, createHeaders());
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, GetTicketHistoryResponse.class);
		}
		log.info("getTicketHistory Response : " + response.getBody());
		return (response.getBody().getTickets() != null ? Arrays.asList(response.getBody().getTickets()) : new ArrayList<TicketSummaryDTO>());
	}

	@Override
	public TicketDetailsDTO getTicketDetails(String ticketId) throws CrmException {
		String svcUrl = this.getUrl(RestUtils.CRM_GET_TICKETDETAILS);

		GetTicketDetailsRequest request = new GetTicketDetailsRequest();
		request.setTicketId(ticketId);
		log.info("getTicketDetails Request : " + request);

		RestTemplate restTemplate = this.restTemplate;
		HttpHeaders headers = createHeaders();

		HttpEntity<GetTicketDetailsRequest> requestEntity = new HttpEntity<GetTicketDetailsRequest>(request, headers);
		ResponseEntity<GetTicketDetailsResponse> response = null;
		try {
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, GetTicketDetailsResponse.class);
		} catch (CrmTokenExpiredException ex) {
			log.info("Auth Token Expired.Authenticating again");
			authToken = null;
			requestEntity = new HttpEntity<GetTicketDetailsRequest>(request, createHeaders());
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, GetTicketDetailsResponse.class);
		}
		log.info("getTicketDetails Response : " + response.getBody());

		GetTicketDetailsResponse tkDtls = response.getBody();
		List<NotesDTO> notesList = (tkDtls.getNotes() != null) ? Arrays.asList(tkDtls.getNotes()) : new ArrayList<NotesDTO>();
		tkDtls.getTicket().setNotes(notesList);
		return tkDtls.getTicket();
	}

	@Override
	public boolean updateTicket(String ticketId, String comment, String status, String firstName, String lastName, List<AttachmentDTO> attachments) throws CrmException {
		String svcUrl = this.getUrl(RestUtils.CRM_UPDATE_TICKET);
		UpdateTicketRequest request = new UpdateTicketRequest();
		request.setTicketId(ticketId);
		request.setComment(comment);
		request.setStatus(status);
		request.setFirstName(firstName);
		request.setLastName(lastName);
		request.setAttachments(attachments);
		log.info("updateTicket Request : " + request);

		RestTemplate restTemplate = this.restTemplate;
		HttpHeaders headers = createHeaders();
		HttpEntity<UpdateTicketRequest> requestEntity = new HttpEntity<UpdateTicketRequest>(request, headers);
		ResponseEntity<UpdateTicketResponse> response = null;
		try {
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, UpdateTicketResponse.class);
		} catch (CrmTokenExpiredException ex) {
			log.info("Auth Token Expired.Authenticating again");
			authToken = null;
			requestEntity = new HttpEntity<UpdateTicketRequest>(request, createHeaders());
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, UpdateTicketResponse.class);
		}
		log.info("updateTicket Response : " + response.getBody());
		return response.getBody().isUpdated();
	}

	@Override
	public List<TicketSummaryDTO> searchTicket(String ticketNumber, String participantId, String employeeId, String clientName, String status, String createdFrom, String createdTo, String firstName,
			String lastName, String clientId) throws CrmException, ExceededMaxResultsException {
		String svcUrl = this.getUrl(RestUtils.CRM_SEARCH_TICKET);
		SearchTicketRequest request = new SearchTicketRequest(ticketNumber, participantId, employeeId, clientName, status, createdFrom, createdTo, firstName, lastName, clientId);

		log.info("searchTicket Request : " + request);

		RestTemplate restTemplate = this.restTemplate;
		HttpHeaders headers = createHeaders();
		HttpEntity<SearchTicketRequest> requestEntity = new HttpEntity<SearchTicketRequest>(request, headers);
		ResponseEntity<SearchTicketResponse> response = null;
		try {
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, SearchTicketResponse.class);
		} catch (CrmTokenExpiredException ex) {
			log.info("Auth Token Expired.Authenticating again");
			authToken = null;
			requestEntity = new HttpEntity<SearchTicketRequest>(request, createHeaders());
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, SearchTicketResponse.class);
		}
		SearchTicketResponse tktResponse = response.getBody();
		log.info("searchTicket Response : " + tktResponse);

		if (RestUtils.CRM_NARROW_SEARCH == response.getBody().getResponseCode()) {
			throw new ExceededMaxResultsException(this.getCrmResponseCode(response.getBody().getResponseCode()));
		}

		return tktResponse.getTickets() != null ? Arrays.asList(tktResponse.getTickets()) : new ArrayList<TicketSummaryDTO>();
	}

	@Override
	public AdvisingHistory getAdvisingHistory(ContactWrapper contact) throws CrmException {
		String svcUrl = this.getUrl(RestUtils.CRM_GET_ADVISINGHISTORY);

		HttpHeaders headers = createHeaders();
		HttpEntity<ContactWrapper> requestEntity = new HttpEntity<ContactWrapper>(contact, headers);
		ResponseEntity<AdvisingHistory> response = null;
		try {
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, AdvisingHistory.class);
		} catch (CrmTokenExpiredException ex) {
			log.info("Auth Token Expired.Authenticating again");
			authToken = null;
			requestEntity = new HttpEntity<ContactWrapper>(contact, createHeaders());
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, AdvisingHistory.class);
		}
		AdvisingHistory history = response.getBody();
		log.info("GetAdvisingHistory Response : " + history);

		return history;
	}

	@Override
	public List<AvailableSlot> getAvailableSlots(String date, int advisingTopic, int timeZone, long clientId) throws CrmException {
		String svcUrl = this.getUrl(RestUtils.CRM_GET_AVAILABLESLOTS);

		AdvisingDateInfo request = new AdvisingDateInfo();
		request.setDate(date);
		request.setAdvisingTopic(advisingTopic);
		request.setTimezoneCode(timeZone);
		request.setAccountId(Long.toString(clientId));
		log.info("GetAvailableSlots Request : " + request);

		RestTemplate restTemplate = this.restTemplate;
		HttpHeaders headers = createHeaders();
		HttpEntity<AdvisingDateInfo> requestEntity = new HttpEntity<AdvisingDateInfo>(request, headers);
		ResponseEntity<AvailableSlots> response = null;
		try {
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, AvailableSlots.class);
		} catch (CrmTokenExpiredException ex) {
			log.info("Auth Token Expired.Authenticating again");
			authToken = null;
			requestEntity = new HttpEntity<AdvisingDateInfo>(request, createHeaders());
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, AvailableSlots.class);
		}
		AvailableSlots slotsResponse = response.getBody();
		log.info("GetAvailableSlots Response : " + slotsResponse);

		return slotsResponse.getAdvisingSlots() != null ? slotsResponse.getAdvisingSlots() : new ArrayList<AvailableSlot>();
	}

	@Override
	public List<AvailableDate> getAvailableDates(String selectedDate, String numberOfDays, int advisingTopic, int timeZone, long clientId) throws CrmException {
		String svcUrl = this.getUrl(RestUtils.CRM_GET_AVAILABLEDATES);

		AdvisingCalendarInfo request = new AdvisingCalendarInfo();
		request.setDateSelected(selectedDate);
		request.setNumberOfDays(numberOfDays);
		request.setAdvisingTopic(advisingTopic);
		request.setTimeZoneCode(timeZone);
		request.setTamsAccountId(Long.toString(clientId));
		log.debug("GetAvailableDates Request : " + request);

		RestTemplate restTemplate = this.restTemplate;
		HttpHeaders headers = createHeaders();
		HttpEntity<AdvisingCalendarInfo> requestEntity = new HttpEntity<AdvisingCalendarInfo>(request, headers);
		ResponseEntity<AvailableDates> response = null;
		try {
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, AvailableDates.class);
		} catch (CrmTokenExpiredException ex) {
			log.info("Auth Token Expired.Authenticating again");
			authToken = null;
			requestEntity = new HttpEntity<AdvisingCalendarInfo>(request, createHeaders());
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, AvailableDates.class);
		}
		AvailableDates datesResponse = response.getBody();
		log.debug("GetAvailableDates Response : " + datesResponse);

		return datesResponse.getAvailableDates() != null ? datesResponse.getAvailableDates() : new ArrayList<AvailableDate>();
	}

	@Override
	public boolean cancelEvent(String eventId) throws CrmException {
		String svcUrl = this.getUrl(RestUtils.CRM_CANCEL_EVENT);
		AdvisingEvent request = new AdvisingEvent();
		request.setEventId(eventId);
		log.info("Cancel Event Rquest : " + request);
		RestTemplate restTemplate = this.restTemplate;
		HttpHeaders headers = createHeaders();
		HttpEntity<AdvisingEvent> requestEntity = new HttpEntity<AdvisingEvent>(request, headers);
		ResponseEntity<CancelEvent> response = null;
		try {
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, CancelEvent.class);
		} catch (CrmTokenExpiredException ex) {
			log.info("Auth Token Expired.Authenticating again");
			authToken = null;
			requestEntity = new HttpEntity<AdvisingEvent>(request, createHeaders());
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, CancelEvent.class);
		}
		CancelEvent cancelResponse = response.getBody();
		log.info("Cancel Event Response : " + cancelResponse);
		return cancelResponse.isEventCanceled();
	}

	@Override
	public String registerEvent(AdvisingAppointment appointment) throws CrmException {
		String svcUrl = this.getUrl(RestUtils.CRM_REGISTER_EVENT);
		log.info("Register Event Rquest : " + appointment);
		RestTemplate restTemplate = this.restTemplate;
		HttpHeaders headers = createHeaders();
		HttpEntity<AdvisingAppointment> requestEntity = new HttpEntity<AdvisingAppointment>(appointment, headers);
		ResponseEntity<AdvisingEvent> response = null;

		try {
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, AdvisingEvent.class);
		} catch (CrmTokenExpiredException ex) {
			log.info("Auth Token Expired.Authenticating again");
			authToken = null;
			requestEntity = new HttpEntity<AdvisingAppointment>(appointment, createHeaders());
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, AdvisingEvent.class);
		}
		AdvisingEvent registerResponse = response.getBody();
		if (RestUtils.CRM_SLOT_UNAVAILABLE == registerResponse.getResponseCode()) {
			throw new CrmAdvisingSlotException();
		}
		log.info("Register Event Response : " + registerResponse);
		return registerResponse.getEventId();
	}

	@Override
	public boolean rescheduleEvent(RescheduleAppointment appointment) throws CrmException {
		String svcUrl = this.getUrl(RestUtils.CRM_RESCEDULE_EVENT);

		log.info("Reschedule Event Rquest : " + appointment);
		RestTemplate restTemplate = this.restTemplate;
		HttpHeaders headers = createHeaders();
		HttpEntity<RescheduleAppointment> requestEntity = new HttpEntity<RescheduleAppointment>(appointment, headers);
		ResponseEntity<AdvisingEvent> response = null;
		try {
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, AdvisingEvent.class);
		} catch (CrmTokenExpiredException ex) {
			log.info("Auth Token Expired.Authenticating again");
			authToken = null;
			requestEntity = new HttpEntity<RescheduleAppointment>(appointment, createHeaders());
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, AdvisingEvent.class);
		}
		log.info("Reschedule Event Response : " + response.getBody());
		return response.getBody().isRescheduled();
	}

	@Override
	public AdviseeInformation getAdviseeInformation(String sessionId) throws CrmException {
		String svcUrl = this.getUrl(RestUtils.CRM_GET_ADVISEEINFO);
		AdvisingSessionInfo request = new AdvisingSessionInfo();
		request.setCaseId(sessionId);
		log.info("Ger Advisee Information Request : " + request);
		RestTemplate restTemplate = this.restTemplate;
		HttpHeaders headers = createHeaders();
		HttpEntity<AdvisingSessionInfo> requestEntity = new HttpEntity<AdvisingSessionInfo>(request, headers);
		ResponseEntity<AdviseeInformation> responseEntity = null;
		try {
			responseEntity = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, AdviseeInformation.class);
		} catch (CrmTokenExpiredException ex) {
			log.info("Auth Token Expired.Authenticating again");
			authToken = null;
			requestEntity = new HttpEntity<AdvisingSessionInfo>(request, createHeaders());
			responseEntity = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, AdviseeInformation.class);
		}
		AdviseeInformation info = responseEntity.getBody();
		log.info("Ger Advisee Information Response : " + info);

		return info;
	}

	@Override
	public AttachmentDTO getTicketAttachment(String ticketId, String annotationId, String noteId) throws CrmException {
		String svcUrl = this.getUrl(RestUtils.CRM_GET_TICKET_ATTACHMENT);
		GetTicketAttachmentRequest request = new GetTicketAttachmentRequest();
		request.setTicketId(ticketId);
		request.setAnnotationId(annotationId);
		request.setNoteId(noteId);
		log.info("getTicketAttachment Request : " + request);

		RestTemplate restTemplate = this.restTemplate;
		HttpHeaders headers = createHeaders();
		HttpEntity<GetTicketAttachmentRequest> requestEntity = new HttpEntity<GetTicketAttachmentRequest>(request, headers);
		ResponseEntity<GetTicketAttachmentResponse> response = null;
		try {
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, GetTicketAttachmentResponse.class);
		} catch (CrmTokenExpiredException ex) {
			log.info("Auth Token Expired.Authenticating again");
			authToken = null;
			requestEntity = new HttpEntity<GetTicketAttachmentRequest>(request, createHeaders());
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, GetTicketAttachmentResponse.class);
		}
		log.info("getLoanTicketAttachment Response : " + response.getBody());
		AttachmentDTO attachment = response.getBody().getCaseVHDAttachment().get(0);
		return attachment;
	}

	public Properties getResponseCodes() {
		return responseCodes;
	}

	public void setResponseCodes(Properties responseCodes) {
		log.debug("Properties : " + responseCodes);
		this.responseCodes = responseCodes;
	}

	private String getCrmResponseCode(long code) {
		return responseCodes.getProperty(Long.toString(code));
	}

	private void authenticate() throws CrmException {
		String svcUrl = this.getUrl(RestUtils.CRM_AUTHENTICATE);
		log.debug(".Net Web Service URL : " + svcUrl);
		AuthenticationRequest request = new AuthenticationRequest();
		request.setUserName(CommonUtil.getHotProperty(CommonUtil.CRM_DOMAIN) + "\\" + CommonUtil.getHotProperty(CommonUtil.CRM_USER_NAME));
		request.setPassword(CommonUtil.getHotProperty(CommonUtil.CRM_PWD));

		RestTemplate restTemplate = this.restTemplate;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		HttpEntity<AuthenticationRequest> requestEntity = new HttpEntity<AuthenticationRequest>(request, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(svcUrl, HttpMethod.POST, requestEntity, String.class);
		} catch (Exception ex) {
			log.error("Error while Authenticating with CRM");
			throw new CrmCommunicationFailureException(ex);
		}

		if (RestUtils.isError(response.getStatusCode())) {
			throw new CrmCommunicationFailureException();
		}
		String auth = response.getBody();
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
		authToken = new String(encodedAuth);
	}

	private HttpHeaders createHeaders() throws CrmException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		if (authToken == null) {
			this.authenticate();
		}
		headers.add("Authorization", "BrightHorizons " + authToken);
		return headers;
	}

	private String getUrl(String serviceUrl) {
		StringBuilder url = new StringBuilder();
		url.append(CommonUtil.getHotProperty(CommonUtil.CRM_WS_URL).trim()).append(serviceUrl);
		return url.toString();
	}

	private void checkForErrors(ResponseEntity response, long responseCode) throws CrmException {
		log.debug("Http Response : " + response.getStatusCode());
		log.debug("Response Code : " + responseCode);
		if (RestUtils.isError(response.getStatusCode())) {
			if (RestUtils.isClientError(response.getStatusCode())) {
				if (RestUtils.CRM_TOKEN_EXPIRED == responseCode || RestUtils.CRM_TOKEN_INVALID == responseCode) {
					authToken = null;
					throw new CrmTokenExpiredException();
				} else if (RestUtils.CRM_CONNECTION_FAILURE == responseCode || RestUtils.CRM_AUTENTICATION_FAILURE == responseCode) {
					throw new CrmCommunicationFailureException();
				}
			}
			throw new CrmCommunicationFailureException();
		} else {
			if (RestUtils.CRM_SUCCESS != responseCode && RestUtils.CRM_NARROW_SEARCH != responseCode && RestUtils.CRM_DATA_NOTFOUND != responseCode) {
				if (RestUtils.CRM_TOKEN_EXPIRED == responseCode || RestUtils.CRM_TOKEN_INVALID == responseCode) {
					authToken = null;
					throw new CrmTokenExpiredException(getCrmResponseCode(responseCode));
				} else {
					throw new CrmCommunicationFailureException(getCrmResponseCode(responseCode));
				}
			}
		}
	}
}
