package com.edassist.models.mappers;

import org.mapstruct.Mapper;

import com.edassist.models.domain.DegreeObjectives;
import com.edassist.models.dto.DegreeObjectivesDTO;

@Mapper
public interface DegreeObjectivesMapper {

	DegreeObjectivesDTO toDTO(DegreeObjectives degreeObjectives);

	DegreeObjectives toDomain(DegreeObjectivesDTO degreeObjectivesDTO);

}