package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.edassist.models.domain.ReimburseBookApp;
import com.edassist.models.dto.ReimburseBookAppDTO;

@Mapper(uses = { CourseOfStudyMapper.class, DegreeObjectivesMapper.class })
public interface ReimburseBookAppMapper {

	ReimburseBookAppDTO toDTO(ReimburseBookApp reimburseBookApp);

	ReimburseBookApp toDomain(ReimburseBookAppDTO reimburseBookAppDTO, @MappingTarget ReimburseBookApp reimburseBookApp);
}
