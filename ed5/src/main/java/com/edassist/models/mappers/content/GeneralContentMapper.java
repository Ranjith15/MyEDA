package com.edassist.models.mappers.content;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.contracts.content.GeneralContent;
import com.edassist.models.dto.content.GeneralContentDTO;

@Mapper
public interface GeneralContentMapper {

	GeneralContentDTO toDTO(GeneralContent generalContent);

	List<GeneralContentDTO> toDTOList(List<GeneralContent> generalContentList);

	GeneralContent toDomain(GeneralContentDTO generalContentDTO);

}
