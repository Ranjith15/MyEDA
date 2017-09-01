package com.edassist.models.mappers;

import org.mapstruct.Mapper;

import com.edassist.models.domain.Participant;
import com.edassist.models.dto.ParticipantSupervisorsDTO;

@Mapper(uses = { ApproverMapper.class })
public interface ParticipantSupervisorsMapper {

	ParticipantSupervisorsDTO toDTO(Participant participant);

}
