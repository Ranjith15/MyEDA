package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.domain.Application;
import com.edassist.models.dto.ThinBookApplicationDTO;

@Mapper(uses = { ApplicationStatusMapper.class, ReimburseBookAppMapper.class, ApplicationExpensesMapper.class })
public interface ThinBookApplicationMapper {

	@Mappings({ @Mapping(source = "applicationCoursesCollection", target = "applicationExpenses") })
	ThinBookApplicationDTO toDTO(Application bookApplication);
}
