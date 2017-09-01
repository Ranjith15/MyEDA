package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.domain.ProgramEligibleDocumentType;
import com.edassist.models.dto.ProgramEligibleDocumentTypeDTO;

@Mapper
public interface ProgramEligibleDocumentTypeMapper {

	@Mappings({ @Mapping(target = "programId", source = "programID.programID"), @Mapping(target = "defaultDocumentsID", source = "defaultDocumentsID.defaultDocumentsID"),
			@Mapping(target = "documentName", source = "defaultDocumentsID.documentName") })

	ProgramEligibleDocumentTypeDTO toDTO(ProgramEligibleDocumentType programEligibleDocumentType);

	List<ProgramEligibleDocumentTypeDTO> toDTOList(List<ProgramEligibleDocumentType> programEligibleDocumentTypes);

}
