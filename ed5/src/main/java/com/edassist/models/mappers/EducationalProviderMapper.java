package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.domain.EducationalProviders;
import com.edassist.models.dto.EducationalProviderDTO;

@Mapper(uses = { ProviderAccreditationMapper.class })
public interface EducationalProviderMapper {

	EducationalProviderDTO toDTO(EducationalProviders educationalProviders);

	List<EducationalProviderDTO> toDTOList(List<EducationalProviders> educationalProviders);

	EducationalProviders toDomain(EducationalProviderDTO educationalProviderDTO);
}
