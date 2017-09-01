package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.contracts.Option;
import com.edassist.models.domain.crm.Code;
import com.edassist.models.dto.OptionDTO;

@Mapper
public interface OptionMapper {

	OptionDTO toDTO(Option filterOption);

	List<OptionDTO> toDTOList(List<Option> filterOptionList);

	@Mappings({ @Mapping(source = "benefitPeriodName", target = "display"), @Mapping(source = "benefitPeriodName", target = "value") })
	OptionDTO toFilterOptionDTO(String benefitPeriodName);

	List<OptionDTO> toFilterOptionDTOList(List<String> benefitPeriodNames);

	@Mappings({ @Mapping(source = "description", target = "display"), @Mapping(source = "ID", target = "value") })
	OptionDTO toOptionDTO(Code code);

	List<OptionDTO> toOptionDTOList(List<Code> codeList);

}
