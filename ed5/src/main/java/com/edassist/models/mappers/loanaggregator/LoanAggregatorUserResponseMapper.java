package com.edassist.models.mappers.loanaggregator;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.contracts.loanaggregator.LoanAggregatorUserResponse;
import com.edassist.models.dto.loanaggregator.LoanAggregatorUserResponseDTO;

@Mapper(uses = { LoanAggregatorUserMapper.class })
public interface LoanAggregatorUserResponseMapper {
	LoanAggregatorUserResponseDTO toDTO(LoanAggregatorUserResponse loanAggregatorUserResponse);

	List<LoanAggregatorUserResponseDTO> toDTOList(List<LoanAggregatorUserResponse> loanAggregatorUserResponse);

}
