package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.edassist.models.domain.ApplicationSource;
import com.edassist.models.dto.ApplicationSourceDTO;

@Mapper
public interface ApplicationSourceMapper {

	ApplicationSourceDTO toDTO(ApplicationSource applicationSource);

	List<ApplicationSourceDTO> toDTOList(List<ApplicationSource> ApplicationSourceList);

	ApplicationSource toDomain(ApplicationSourceDTO applicationSourceDTO, @MappingTarget ApplicationSource applicationSource);
}
