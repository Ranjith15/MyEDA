package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.edassist.models.domain.FinancialAidSource;
import com.edassist.models.dto.FinancialAidSourceDTO;

@Mapper
public interface FinancialAidSourceMapper {

	FinancialAidSourceDTO toDTO(FinancialAidSource financialAidSource);

	List<FinancialAidSourceDTO> toDTOList(List<FinancialAidSource> financialAidSourceList);

	FinancialAidSource toDomain(FinancialAidSourceDTO financialAidSourceDTO, @MappingTarget FinancialAidSource financialAidSource);
}
