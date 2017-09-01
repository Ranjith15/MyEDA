package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.domain.User;
import com.edassist.models.domain.crm.Contact;
import com.edassist.models.dto.ContactDTO;

@Mapper

public interface ContactMapper {
	@Mappings({ @Mapping(source = "participantID.participantId", target = "participantId"), @Mapping(source = "participantID.employeeId", target = "employeeId"),
			@Mapping(source = "participantID.preferEmail", target = "email"), @Mapping(source = "participantID.homeAddress.phone", target = "phone"),
			@Mapping(source = "participantID.client.clientName", target = "clientName"), @Mapping(source = "participantID.client.clientId", target = "clientId"),
			@Mapping(source = "participantID.companyName", target = "department") })
	ContactDTO toDTO(User usr);

	@Mappings({ @Mapping(source = "participantID.participantId", target = "participantId"), @Mapping(source = "participantID.employeeId", target = "employeeId"),
			@Mapping(source = "participantID.preferEmail", target = "email"), @Mapping(source = "participantID.homeAddress.phone", target = "phone"),
			@Mapping(source = "participantID.client.clientName", target = "clientName"), @Mapping(source = "participantID.client.clientId", target = "clientId"),
			@Mapping(source = "participantID.companyName", target = "department") })
	Contact toDomain(User usr);

}
