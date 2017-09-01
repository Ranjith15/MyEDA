package com.edassist.models.contracts.loanaggregator;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoanTransaction {

	@JsonProperty("id")
	private Long loanTransactionId;
	private Date date;
	private Long accountId;
	private Amount amount;

	public Long getLoanTransactionId() {
		return loanTransactionId;
	}

	public void setLoanTransactionId(Long loanTransactionId) {
		this.loanTransactionId = loanTransactionId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

}
