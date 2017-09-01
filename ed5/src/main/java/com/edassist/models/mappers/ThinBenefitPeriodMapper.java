package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.domain.BenefitPeriod;
import com.edassist.models.sp.ThinBenefitPeriod;
import com.edassist.models.dto.ThinBenefitPeriodDTO;

@Mapper
public interface ThinBenefitPeriodMapper {

	ThinBenefitPeriodDTO toDTO(ThinBenefitPeriod thinBenefitPeriod);

	List<ThinBenefitPeriodDTO> toDTOList(List<ThinBenefitPeriod> thinBenefitPeriods);

	ThinBenefitPeriodDTO toFilterOptionDTO(BenefitPeriod benefitPeriod);

}