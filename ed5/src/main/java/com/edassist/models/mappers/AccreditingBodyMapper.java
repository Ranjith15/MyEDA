package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.domain.AccreditingBody;
import com.edassist.models.dto.AccreditingBodyDTO;

@Mapper
public interface AccreditingBodyMapper {

	AccreditingBodyDTO toDTO(AccreditingBody accreditingBody);

	List<AccreditingBodyDTO> toDTOList(List<AccreditingBody> accreditingBodys);

}
