package com.edassist.models.mappers;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.edassist.models.domain.ProviderAccreditation;
import com.edassist.models.dto.ProviderAccreditationDTO;

@Mapper(uses = { AccreditingBodyMapper.class })
public interface ProviderAccreditationMapper {

	@Mappings({ @Mapping(source = "accreditingBodyID", target = "accreditingBody") })
	ProviderAccreditationDTO toDTO(ProviderAccreditation providerAccreditation);

	ProviderAccreditation toTarget(ProviderAccreditationDTO providerAccreditationDTO);

	Collection<ProviderAccreditation> toDomain(Collection<ProviderAccreditationDTO> providerAccreditationDTO, @MappingTarget Collection<ProviderAccreditation> providerAccreditation);

}
