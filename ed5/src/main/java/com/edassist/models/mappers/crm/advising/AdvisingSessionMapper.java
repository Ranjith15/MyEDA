package com.edassist.models.mappers.crm.advising;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.edassist.models.contracts.crm.advising.AdvisingSession;
import com.edassist.models.dto.crm.advising.AdvisingSessionDTO;

@Mapper
public interface AdvisingSessionMapper {

	AdvisingSessionDTO toDTO(AdvisingSession advisingSession);

	AdvisingSession toDomain(AdvisingSessionDTO dto, @MappingTarget AdvisingSession advisingSession);

	List<AdvisingSessionDTO> toDTOList(List<AdvisingSession> advisingSessionList);
}
