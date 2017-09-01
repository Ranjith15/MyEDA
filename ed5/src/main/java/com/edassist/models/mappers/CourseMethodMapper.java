package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.domain.CourseMethod;
import com.edassist.models.dto.CourseMethodDTO;

@Mapper
public interface CourseMethodMapper {

	CourseMethodDTO toDTO(CourseMethod courseMethod);

	List<CourseMethodDTO> toDTOList(List<CourseMethod> courseMethods);

	CourseMethod toDomain(CourseMethodDTO courseMethodDTO);

}
