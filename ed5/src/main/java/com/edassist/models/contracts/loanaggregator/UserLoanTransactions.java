package com.edassist.models.contracts.loanaggregator;

import java.util.List;

public class UserLoanTransactions {

	private List<LoanTransaction> transaction;

	public List<LoanTransaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<LoanTransaction> transaction) {
		this.transaction = transaction;
	}
}
