package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.domain.ApplicationDocuments;
import com.edassist.models.dto.ApplicationDocumentsDTO;

@Mapper
public interface ApplicationDocumentsMapper {

	@Mappings({ @Mapping(source = "defaultDocumentsID.documentName", target = "documentName") })

	ApplicationDocumentsDTO toDTO(ApplicationDocuments applicationDocument);

	List<ApplicationDocumentsDTO> toDTOList(List<ApplicationDocuments> applicationDocuments);

}
