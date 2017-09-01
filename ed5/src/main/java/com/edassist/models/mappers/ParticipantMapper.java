package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.edassist.models.domain.Participant;
import com.edassist.models.domain.ThinParticipantSearch;
import com.edassist.models.dto.ParticipantDTO;

@Mapper(uses = { PaymentPreferenceMapper.class, AddressMapper.class, ParticipantCurrentEducationProfileMapper.class, ClientMapper.class, UserMapper.class })
public interface ParticipantMapper {

	ParticipantDTO toDTO(Participant participant);

	List<ParticipantDTO> toDTOList(List<Participant> participants);

	Participant toDomain(ParticipantDTO participantDTO, @MappingTarget Participant participant);

	ParticipantDTO toDTO(ThinParticipantSearch participant);

	List<ParticipantDTO> toThinDTOList(List<ThinParticipantSearch> participants);

}
