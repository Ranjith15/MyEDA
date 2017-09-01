package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.edassist.models.domain.Application;
import com.edassist.models.domain.FinancialAidSource;
import com.edassist.models.dto.FinancialAidSourceDTO;
import com.edassist.models.dto.GrantScholarshipDTO;

@Mapper
public interface GrantScholarshipMapper {
	@Mappings({ @Mapping(target = "financialAidSourceId", source = "financialAidSource") })
	GrantScholarshipDTO toDTO(Application application, FinancialAidSourceDTO financialAidSource);

	@Mappings({ @Mapping(source = "financialAidSource", target = "financialAidSourceId"), @Mapping(target = "dateCreated", ignore = true), @Mapping(target = "modifiedBy", ignore = true),
			@Mapping(target = "createdBy", ignore = true), @Mapping(target = "dateModified", expression = "java(new java.util.Date())") })

	Application toDomain(GrantScholarshipDTO grantScholarshipDTO, FinancialAidSource financialAidSource, @MappingTarget Application application);

}