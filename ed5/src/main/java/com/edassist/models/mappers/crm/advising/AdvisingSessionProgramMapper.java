package com.edassist.models.mappers.crm.advising;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.edassist.models.contracts.crm.advising.AdvisingSessionProgram;
import com.edassist.models.dto.crm.advising.AdvisingSessionProgramDTO;

@Mapper
public interface AdvisingSessionProgramMapper {

	AdvisingSessionProgramDTO toDTO(AdvisingSessionProgram advisingSessionProgram);

	AdvisingSessionProgram toDomain(AdvisingSessionProgramDTO dto, @MappingTarget AdvisingSessionProgram advisingSessionProgram);

	List<AdvisingSessionProgramDTO> toDTOList(List<AdvisingSessionProgram> advisingSessionProgram);
}
