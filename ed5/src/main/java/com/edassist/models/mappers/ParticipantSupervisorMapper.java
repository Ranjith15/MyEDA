package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.domain.ParticipantSupervisor;
import com.edassist.models.dto.ParticipantSupervisorDTO;

@Mapper
public interface ParticipantSupervisorMapper {

	@Mappings({ @Mapping(source = "participantSupervisor.participantID.user.firstName", target = "firstName"),
			@Mapping(source = "participantSupervisor.participantID.user.lastName", target = "lastName"), @Mapping(source = "participantSupervisor.participantID.employeeId", target = "employeeId"),
			@Mapping(source = "participantSupervisor.participantID.jobTitle", target = "role"), @Mapping(source = "participantSupervisor.participantID.active", target = "status") })
	ParticipantSupervisorDTO toDTO(ParticipantSupervisor participantSupervisor);

	List<ParticipantSupervisorDTO> toDTOList(List<ParticipantSupervisor> participantSupervisors);
}
