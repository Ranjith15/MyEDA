package com.edassist.models.mappers;

import org.mapstruct.Mapper;

import com.edassist.models.domain.CourseOfStudy;
import com.edassist.models.dto.CourseOfStudyDTO;

@Mapper
public interface CourseOfStudyMapper {

	CourseOfStudyDTO toDTO(CourseOfStudy courseOfStudy);

	CourseOfStudy toDomain(CourseOfStudyDTO courseOfStudyDTO);

}
