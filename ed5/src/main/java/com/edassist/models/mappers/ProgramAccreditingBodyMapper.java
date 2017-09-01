package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.domain.ProgramAccreditingBody;
import com.edassist.models.dto.ProgramAccreditingBodyDTO;

@Mapper(uses = { AccreditingBodyMapper.class })
public interface ProgramAccreditingBodyMapper {

	@Mappings({ @Mapping(source = "accreditingBodyID", target = "accreditingBody") })
	ProgramAccreditingBodyDTO toDTO(ProgramAccreditingBody programAccreditingBody);

	ProgramAccreditingBody toDomain(ProgramAccreditingBodyDTO programAccreditingBodyDTO);

}