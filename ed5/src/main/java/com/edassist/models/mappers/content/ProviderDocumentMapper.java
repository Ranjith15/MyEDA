package com.edassist.models.mappers.content;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.contracts.content.ProviderDocument;
import com.edassist.models.dto.content.ProviderDocumentDTO;

@Mapper
public interface ProviderDocumentMapper {

	ProviderDocumentDTO toDTO(ProviderDocument providerDocument);

	List<ProviderDocumentDTO> toDTOList(List<ProviderDocument> ProviderDocumentList);

}
