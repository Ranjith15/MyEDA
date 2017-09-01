package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.domain.RuleMessages;
import com.edassist.models.dto.RuleMessageDTO;

@Mapper
public interface RuleMessageMapper {

	RuleMessageDTO toDTO(RuleMessages ruleMessage);

	List<RuleMessageDTO> toDTOList(List<RuleMessages> ruleMessages);

}
