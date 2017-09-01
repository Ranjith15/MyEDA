package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.edassist.models.domain.ApplicationCourses;
import com.edassist.models.dto.ApplicationCoursesDTO;

@Mapper(uses = { GradesMapper.class, CourseMethodMapper.class, ExpenseTypeMapper.class })
public interface ApplicationCoursesMapper {

	@Mappings({ @Mapping(source = "gradeID", target = "grades"), @Mapping(source = "taxability", target = "taxExempt") })
	ApplicationCoursesDTO toDTO(ApplicationCourses applicationCourses);

	List<ApplicationCoursesDTO> toDTOList(List<ApplicationCourses> applicationCourses);

	ApplicationCourses toDomain(ApplicationCoursesDTO applicationCoursesDTO, @MappingTarget ApplicationCourses applicationCourses);
}
