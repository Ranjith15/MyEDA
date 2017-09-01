package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.edassist.models.contracts.loanaggregator.LoanAggregatorUser;
import com.edassist.models.domain.LoanAggregatorUserDomain;

@Mapper
public interface LoanAggregatorUserDomainMapper {

	LoanAggregatorUser toDTO(LoanAggregatorUserDomain loanAggregatorUserDomain);

	LoanAggregatorUserDomain toDomain(LoanAggregatorUser LoanAggregatorUser, @MappingTarget LoanAggregatorUserDomain loanAggregatorUserDomain);
}
