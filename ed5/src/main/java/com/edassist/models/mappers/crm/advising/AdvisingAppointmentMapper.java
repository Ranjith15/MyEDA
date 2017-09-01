package com.edassist.models.mappers.crm.advising;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.contracts.crm.advising.AdviseeInformation;
import com.edassist.models.contracts.crm.advising.AdvisingAppointment;
import com.edassist.models.contracts.crm.advising.RescheduleAppointment;
import com.edassist.models.dto.crm.advising.AdvisingAppointmentDTO;

@Mapper(uses = AttachmentMapper.class)
public interface AdvisingAppointmentMapper {
	@Mappings({ @Mapping(source = "selectedSlot.service", target = "service"), @Mapping(source = "selectedSlot.serviceId", target = "serviceId"),
			@Mapping(source = "selectedSlot.advisorId", target = "advisorId"), @Mapping(source = "selectedSlot.scheduledStart", target = "scheduledStart"),
			@Mapping(source = "selectedSlot.scheduledEnd", target = "scheduledEnd") })
	AdvisingAppointment toDomain(AdvisingAppointmentDTO advisingAppointmentDTO);

	@Mappings({ @Mapping(source = "selectedSlot.service", target = "service"), @Mapping(source = "selectedSlot.serviceId", target = "serviceId"),
			@Mapping(source = "selectedSlot.advisorId", target = "advisorId"), @Mapping(source = "selectedSlot.scheduledStart", target = "scheduledStart"),
			@Mapping(source = "selectedSlot.scheduledEnd", target = "scheduledEnd") })
	RescheduleAppointment toRescheduleAppointment(AdvisingAppointmentDTO advisingAppointmentDTO);

	@Mappings({ @Mapping(source = "adviseeInformation.preferredEmailAddress", target = "email"), @Mapping(source = "adviseeInformation.preferredPhoneNumber", target = "phoneNumber"),
			@Mapping(source = "adviseeInformation.secondaryPhoneNumber", target = "secondaryPhoneNumber") })
	AdvisingAppointmentDTO toDTO(AdviseeInformation adviseeInformation);

}