package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.domain.Program;
import com.edassist.models.dto.ProgramDTO;

@Mapper(uses = { ProgramTypeMapper.class, ProgramAccreditingBodyMapper.class })
public interface ProgramMapper {

	ProgramDTO toDTO(Program program);

	List<ProgramDTO> toDTOList(List<Program> programList);

	Program toDomain(ProgramDTO programDTO);

}