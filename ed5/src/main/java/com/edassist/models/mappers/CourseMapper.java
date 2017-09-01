package com.edassist.models.mappers;

import com.edassist.models.domain.Course;
import com.edassist.models.domain.ThinCourse;
import com.edassist.models.dto.CourseDTO;
import com.edassist.models.mappers.decorators.CourseMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = { ExpenseMapper.class, GradesMapper.class })
@DecoratedWith(CourseMapperDecorator.class)
public interface CourseMapper {
	CourseDTO toDTO(ThinCourse course);
	CourseDTO toDTO(Course course);

	List<CourseDTO> toDTOListFromThin(List<ThinCourse> courses);
	List<CourseDTO> toDTOList(List<Course> courses);

	ThinCourse toThin(Course course);
	List<ThinCourse> toThinList(List<Course> courses);
}
