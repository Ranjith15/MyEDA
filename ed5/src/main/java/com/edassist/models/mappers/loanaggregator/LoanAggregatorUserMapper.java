package com.edassist.models.mappers.loanaggregator;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.contracts.loanaggregator.LoanAggregatorUser;
import com.edassist.models.dto.loanaggregator.LoanAggregatorUserDTO;

@Mapper(uses = { UserSessionMapper.class })
public interface LoanAggregatorUserMapper {
	LoanAggregatorUserDTO toDTO(LoanAggregatorUser loanAggregatorUser);

	List<LoanAggregatorUserDTO> toDTOList(List<LoanAggregatorUser> loanAggregatorUser);
}
