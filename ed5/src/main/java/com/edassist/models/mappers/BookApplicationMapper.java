package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.edassist.models.domain.Application;
import com.edassist.models.dto.BookApplicationDTO;

@Mapper(uses = { GrantScholarshipMapper.class, ReimburseBookAppMapper.class, ApplicationExpensesMapper.class })
public interface BookApplicationMapper {

	@Mappings({ @Mapping(source = "applicationCoursesCollection", target = "applicationExpenses"), })

	BookApplicationDTO toDTO(Application bookApplication);

	Application toDomain(BookApplicationDTO bookApplicationDTO, @MappingTarget Application application);
}
