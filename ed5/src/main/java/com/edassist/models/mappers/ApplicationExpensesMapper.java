package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.edassist.models.domain.ApplicationCourses;
import com.edassist.models.dto.ApplicationExpensesDTO;

@Mapper(uses = { ExpenseTypeMapper.class, ApplicationCoursesMapper.class })
public interface ApplicationExpensesMapper {

	@Mappings({ @Mapping(source = "applicationCoursesID", target = "applicationExpensesID"), @Mapping(source = "taxability", target = "taxExempt") })

	ApplicationExpensesDTO toDTO(ApplicationCourses applicationExpenses);

	List<ApplicationExpensesDTO> toDTOList(List<ApplicationCourses> applicationExpenses);

	ApplicationCourses toDomain(ApplicationExpensesDTO applicationExpensesDTO, @MappingTarget ApplicationCourses applicationExpenses);
}