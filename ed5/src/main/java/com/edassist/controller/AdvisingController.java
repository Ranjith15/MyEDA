package com.edassist.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edassist.constants.RestConstants;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.NotFoundException;
import com.edassist.models.contracts.crm.advising.*;
import com.edassist.models.domain.Participant;
import com.edassist.models.domain.crm.Code;
import com.edassist.models.domain.crm.Contact;
import com.edassist.models.dto.OptionDTO;
import com.edassist.models.dto.crm.advising.AdvisingAppointmentDTO;
import com.edassist.models.dto.crm.advising.AdvisingSessionDTO;
import com.edassist.models.dto.crm.advising.AvailableDateDTO;
import com.edassist.models.dto.crm.advising.AvailableSlotDTO;
import com.edassist.models.mappers.ContactMapper;
import com.edassist.models.mappers.OptionMapper;
import com.edassist.models.mappers.crm.advising.AdvisingAppointmentMapper;
import com.edassist.models.mappers.crm.advising.AdvisingSessionMapper;
import com.edassist.models.mappers.crm.advising.AvailableDateMapper;
import com.edassist.models.mappers.crm.advising.AvailableSlotMapper;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.AccessService;
import com.edassist.service.CRMService;
import com.edassist.service.ReferenceDataService;
import com.edassist.service.SessionService;
import com.edassist.service.crm.advising.AdvisingService;
import com.edassist.utils.CommonUtil;
import com.edassist.utils.RestUtils;

@RestController
public class AdvisingController {

	private final SessionService sessionService;
	private final CRMService crmService;
	private final ContactMapper contactMapper;
	private final AccessService accessService;
	private final AdvisingAppointmentMapper advisingAppointmentMapper;
	private final ReferenceDataService referenceDataService;
	private final AvailableSlotMapper availableSlotMapper;
	private final AdvisingSessionMapper advisingSessionMapper;
	private final AdvisingService advisingService;
	private final OptionMapper optionMapper;
	private final AvailableDateMapper availableDateMapper;

	@Autowired
	public AdvisingController(SessionService sessionService, CRMService crmService, AccessService accessService, AdvisingService advisingService, ReferenceDataService referenceDataService,
			AdvisingAppointmentMapper advisingAppointmentMapper, ContactMapper contactMapper, AvailableSlotMapper availableSlotMapper, AdvisingSessionMapper advisingSessionMapper,
			OptionMapper optionMapper, AvailableDateMapper availableDateMapper) {
		this.sessionService = sessionService;
		this.crmService = crmService;
		this.accessService = accessService;
		this.advisingService = advisingService;
		this.referenceDataService = referenceDataService;
		this.advisingAppointmentMapper = advisingAppointmentMapper;
		this.contactMapper = contactMapper;
		this.availableSlotMapper = availableSlotMapper;
		this.advisingSessionMapper = advisingSessionMapper;
		this.optionMapper = optionMapper;
		this.availableDateMapper = availableDateMapper;

	}

	@RequestMapping(value = "/v1/advising/referenceData/{category}", method = RequestMethod.GET)
	public ResponseEntity<List<OptionDTO>> getAdvisingReferenceData(@PathVariable("category") String category) {
		List<Code> advisingReferenceDataList = referenceDataService.getReferenceData(category);
		if (advisingReferenceDataList == null || advisingReferenceDataList.size() == 0) {
			throw new NotFoundException("Code not found for the category " + category);
		}
		List<OptionDTO> codeDTOList = optionMapper.toOptionDTOList(advisingReferenceDataList);
		return new ResponseEntity<>(codeDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/advising/available-slots", method = RequestMethod.GET)
	public ResponseEntity<List<AvailableSlotDTO>> getAvaliableSlots(@RequestParam String categ, @RequestParam String selectedDate, @RequestParam String offSet) {
		Long clientId = sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID);
		List<AvailableSlot> checkedList = new ArrayList<AvailableSlot>();
		selectedDate = CommonUtil.formatStringToString(selectedDate, CommonUtil.YYYYMMDD);
		int category = Integer.parseInt(categ);
		List<AvailableSlot> availableList = crmService.getAvailableSlots(selectedDate, category, RestConstants.CRM_TIMEZONE, clientId);

		for (AvailableSlot availableSlot : availableList) {
			if (selectedDate.trim().equals(RestUtils.formatAdvisingSlotDate(availableSlot.getScheduledStart(), "yyyy-MM-dd", offSet).trim())) {
				if (RestUtils.checkAdvisingLeadTime(category, offSet, availableSlot.getScheduledStart())) {
					availableSlot.setFormattedStart(RestUtils.formatAdvisingSlotDate(availableSlot.getScheduledStart(), "hh:mm a", offSet));
					availableSlot.setFormattedEnd(RestUtils.formatAdvisingSlotDate(availableSlot.getScheduledEnd(), "hh:mm a", offSet));
					checkedList.add(availableSlot);
				}
			}
		}
		List<AvailableSlotDTO> availableSlotDTOList = availableSlotMapper.toDTOList(checkedList);
		return new ResponseEntity<>(availableSlotDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/advising/available-dates", method = RequestMethod.GET)
	public ResponseEntity<List<AvailableDateDTO>> getAvaliableDates(@RequestParam String advisingCategory, @RequestParam String numberOfDays, @RequestParam String selectedDate) {
		Long clientId = sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID);
		int category = Integer.parseInt(advisingCategory);
		selectedDate = CommonUtil.formatStringToString(selectedDate, CommonUtil.YYYYMMDD);
		List<AvailableDate> availableDateList = crmService.getAvailableDates(selectedDate, numberOfDays, category, RestConstants.CRM_TIMEZONE, clientId);

		List<AvailableDateDTO> availableDateDTOList = availableDateMapper.toDTOList(availableDateList);
		return new ResponseEntity<>(availableDateDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/advising/advising-appointment", method = RequestMethod.POST)
	public ResponseEntity<Void> saveAdvisingAppointment(@RequestBody AdvisingAppointmentDTO advisingAppointmentDTO) {
		String eventId = null;
		AdvisingAppointment advisingAppointment = advisingAppointmentMapper.toDomain(advisingAppointmentDTO);
		Participant participant = accessService.retrieveAndCompareParticipantToSession(sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID));
		Contact contact = contactMapper.toDomain(participant.getUser());
		advisingAppointment.setContact(contact);
		eventId = crmService.registerEvent(advisingAppointment);
		if (eventId != null) {
			if (eventId.trim().length() <= 0) {
				throw new BadRequestException();
			}
		} else {
			throw new BadRequestException();
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/advising/advising-appointments", method = RequestMethod.GET)
	public ResponseEntity<List<AdvisingSessionDTO>> getAdvisingAppointments() {
		Participant participant = accessService.retrieveAndCompareParticipantToSession(sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID));
		List<AdvisingSession> advisingSessionList = advisingService.updateMandatoryAdvising(participant);
		List<AdvisingSessionDTO> advisingSessionDTOList = advisingSessionMapper.toDTOList(advisingSessionList);
		return new ResponseEntity<>(advisingSessionDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/advising/advising-appointment/{appointmentId}", method = RequestMethod.GET)
	public ResponseEntity<AdvisingAppointmentDTO> getCurrentAdvisingAppointment(@PathVariable("appointmentId") String appointmentId) {
		AdviseeInformation adviseeInformation = crmService.getAdviseeInformation(appointmentId);
		AdvisingAppointmentDTO advisingAppointmentDTO = advisingAppointmentMapper.toDTO(adviseeInformation);
		return new ResponseEntity<>(advisingAppointmentDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/advising/advising-appointment", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateAdvisingAppointment(@RequestBody AdvisingAppointmentDTO advisingAppointmentDTO) {
		boolean rescheduleSuccess = false;
		RescheduleAppointment appointment = advisingAppointmentMapper.toRescheduleAppointment(advisingAppointmentDTO);

		rescheduleSuccess = crmService.rescheduleEvent(appointment);
		if (!rescheduleSuccess) {
			throw new BadRequestException();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/advising/advising-appointment/{appointmentId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> cancelAdvisingAppointment(@PathVariable("appointmentId") String appointmentId) {
		boolean isSuccess = false;
		isSuccess = crmService.cancelEvent(appointmentId);
		if (!isSuccess) {
			throw new BadRequestException();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
