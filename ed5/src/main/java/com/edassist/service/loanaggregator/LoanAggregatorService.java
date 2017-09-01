package com.edassist.service.loanaggregator;

import java.util.List;

import org.springframework.http.HttpHeaders;

import com.edassist.exception.ForbiddenException;
import com.edassist.exception.LoanAggregatorException;
import com.edassist.models.contracts.loanaggregator.LoanTransaction;

public interface LoanAggregatorService {

	String getLoanAggregatorAuthTokens() throws ForbiddenException, LoanAggregatorException;

	void refreshLoanAggregatorAuthTokens() throws LoanAggregatorException;

	HttpHeaders getHeaders(String userSession) throws ForbiddenException, LoanAggregatorException;

	HttpHeaders getHeaders() throws ForbiddenException, LoanAggregatorException;

	List<LoanTransaction> getUserTransactions(String userSession, int accountId) throws LoanAggregatorException;

}