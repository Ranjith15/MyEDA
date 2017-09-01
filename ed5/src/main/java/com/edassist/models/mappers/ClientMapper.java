package com.edassist.models.mappers;

import java.util.List;

import com.edassist.models.domain.Client;
import com.edassist.models.dto.ClientDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {

	ClientDTO toDTO(Client client);

	List<ClientDTO> toDTOList(List<Client> clientList);

	Client toDomain(ClientDTO clientDTO);

}
