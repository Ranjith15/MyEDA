package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.edassist.models.domain.State;
import com.edassist.models.dto.StateDTO;

@Mapper
public interface StateMapper {

	StateMapper INSTANCE = Mappers.getMapper(StateMapper.class);

	StateDTO toDTO(State state);

	State toDomain(StateDTO stateDTO, @MappingTarget State state);

}
