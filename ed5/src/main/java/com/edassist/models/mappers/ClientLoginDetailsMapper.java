package com.edassist.models.mappers;

import org.mapstruct.Mapper;

import com.edassist.models.domain.Client;
import com.edassist.models.dto.ClientLoginDetailsDTO;

@Mapper
public interface ClientLoginDetailsMapper {

	ClientLoginDetailsDTO toDTO(Client client);

}
