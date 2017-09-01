package com.edassist.models.mappers;

import org.mapstruct.Mapper;

import com.edassist.models.domain.ApplicationType;
import com.edassist.models.dto.ApplicationTypeDTO;

@Mapper
public interface ApplicationTypeMapper {

	ApplicationTypeDTO toDTO(ApplicationType applicationType);

}
