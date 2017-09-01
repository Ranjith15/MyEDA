package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.domain.ThinParticipantExceptionRules;
import com.edassist.models.dto.ParticipantExceptionRulesDTO;

@Mapper
public interface ParticipantExceptionRulesMapper {
	@Mappings({ @Mapping(source = "exceptionRulesId.ruleId", target = "ruleId"), @Mapping(source = "exceptionRulesId.ruleName", target = "ruleName"),
			@Mapping(source = "exceptionRulesId.friendlyName", target = "friendlyName") })
	ParticipantExceptionRulesDTO toDTO(ThinParticipantExceptionRules exception);

	List<ParticipantExceptionRulesDTO> toDTOList(List<ThinParticipantExceptionRules> exceptions);
}
