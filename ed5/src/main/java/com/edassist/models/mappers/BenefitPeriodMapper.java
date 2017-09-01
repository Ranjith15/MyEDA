package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.edassist.models.domain.BenefitPeriod;
import com.edassist.models.dto.BenefitPeriodDTO;

@Mapper(uses = { ProgramMapper.class })
public interface BenefitPeriodMapper {

	BenefitPeriodDTO toDTO(BenefitPeriod benefitPeriod);

	List<BenefitPeriodDTO> toDTOList(List<BenefitPeriod> benefitPeriod);

	@Mappings({ @Mapping(target = "startDate", ignore = true), @Mapping(target = "endDate", ignore = true) })
	BenefitPeriod toDomain(BenefitPeriodDTO benefitPeriodDTO, @MappingTarget BenefitPeriod benefitPeriod);
}