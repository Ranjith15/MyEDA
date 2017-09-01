package com.edassist.service;

import java.util.List;

import com.edassist.exception.CrmException;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.contracts.crm.ContactWrapper;
import com.edassist.models.contracts.crm.advising.*;
import com.edassist.models.domain.crm.Code;
import com.edassist.models.domain.crm.ReferenceData;
import com.edassist.models.dto.AttachmentDTO;
import com.edassist.models.dto.ContactDTO;
import com.edassist.models.dto.TicketDetailsDTO;
import com.edassist.models.dto.TicketSummaryDTO;

public interface CRMService {

	ReferenceData getReferenceData() throws CrmException;

	List<Code> getReferenceData(String category) throws CrmException;

	String submitTicket(String subTopicId, String applicationNumber, String comment, ContactDTO contact, List<AttachmentDTO> attachments) throws CrmException;

	List<TicketSummaryDTO> getTicketHistory(String key, List<String> values, long clientId) throws CrmException;

	TicketDetailsDTO getTicketDetails(String ticketId) throws CrmException;

	boolean updateTicket(String ticketId, String comment, String status, String firstName, String lastName, List<AttachmentDTO> attachments) throws CrmException;

	List<TicketSummaryDTO> searchTicket(String ticketNumber, String participantId, String employeeId, String clientName, String status, String createdFrom, String createdTo, String firstName,
			String lastName, String clientId) throws CrmException, ExceededMaxResultsException;

	AdvisingHistory getAdvisingHistory(ContactWrapper contact) throws CrmException;

	List<AvailableDate> getAvailableDates(String date, String numberOfDays, int category, int timeZone, long clientId) throws CrmException;

	List<AvailableSlot> getAvailableSlots(String date, int category, int timeZone, long clientId) throws CrmException;

	boolean cancelEvent(String eventId) throws CrmException;

	String registerEvent(AdvisingAppointment appointment) throws CrmException;

	boolean rescheduleEvent(RescheduleAppointment appointment) throws CrmException;

	AdviseeInformation getAdviseeInformation(String sessionId) throws CrmException;

	AttachmentDTO getTicketAttachment(String ticketId, String annotationId, String noteId) throws CrmException;

}
