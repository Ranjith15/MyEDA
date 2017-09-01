package com.edassist.service.loanaggregator;

import com.edassist.models.contracts.loanaggregator.FastLinkUser;
import com.edassist.models.contracts.loanaggregator.LoanAggregatorUserResponse;
import com.edassist.models.contracts.loanaggregator.UserAccountDetails;
import com.edassist.models.domain.StudentLoan;

import java.util.List;

public interface LoanAggregatorUserService {

	LoanAggregatorUserResponse authenticateUser(Long participantId) throws Exception;

	List<UserAccountDetails> getUserAccounts(String userSession) throws Exception;

	List<UserAccountDetails> refreshUserAccount(Long id, String userSession) throws Exception;

	FastLinkUser getUserAccessTokens(String userSession) throws Exception;

	String getFastLinkAppID() throws Exception;

	List<StudentLoan> fetchLoanAggregatorUserAccountNumbers(Long participantId) throws Exception;

	List<StudentLoan> fetchUserAcctNumbersWithActiveApps() throws Exception;

	LoanAggregatorUserResponse refreshUserAccountDetailsFromLogin(List<StudentLoan> studentLoans) throws Exception;

	void refreshUserAccountDetailsFromJob(Long participantId) throws Exception;

	boolean logoutUser(String userSession);
}
