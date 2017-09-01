package com.edassist.models.mappers;

import org.mapstruct.Mapper;

import com.edassist.models.domain.ProgramType;
import com.edassist.models.dto.ProgramTypeDTO;

@Mapper
public interface ProgramTypeMapper {

	ProgramTypeDTO toDTO(ProgramType programType);

	ProgramType toDomain(ProgramTypeDTO programTypeDTO);

}
