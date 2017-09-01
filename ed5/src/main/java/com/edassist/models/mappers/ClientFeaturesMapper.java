package com.edassist.models.mappers;

import org.mapstruct.Mapper;

import com.edassist.models.domain.Client;
import com.edassist.models.dto.ClientFeaturesDTO;

@Mapper
public interface ClientFeaturesMapper {

	ClientFeaturesDTO toDTO(Client client);

}
