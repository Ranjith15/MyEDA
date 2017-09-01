package com.edassist.models.contracts.loanaggregator;

import java.util.List;

public class UserAccounts {

	private List<UserAccountDetails> account;

	public List<UserAccountDetails> getAccount() {
		return account;
	}

	public void setAccount(List<UserAccountDetails> account) {
		this.account = account;
	}
}
