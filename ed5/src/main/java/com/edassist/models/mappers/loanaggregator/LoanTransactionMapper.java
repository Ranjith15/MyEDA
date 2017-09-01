package com.edassist.models.mappers.loanaggregator;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.contracts.loanaggregator.LoanTransaction;
import com.edassist.models.domain.StudentLoan;
import com.edassist.models.domain.StudentLoanPaymentHistory;
import com.edassist.models.mappers.StudentLoanMapper;

@Mapper(uses = { StudentLoanMapper.class })
public interface LoanTransactionMapper {
	@Mappings({ @Mapping(source = "loanTransaction.date", target = "paymentDate"), @Mapping(source = "loanTransaction.amount.amount", target = "paymentAmount"),
			@Mapping(source = "studentLoan", target = "studentLoan"), @Mapping(source = "loanTransaction.loanTransactionId", target = "loanAggregatorTransactionId") })
	StudentLoanPaymentHistory toDomain(LoanTransaction loanTransaction, StudentLoan studentLoan);
}
