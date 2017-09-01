package com.edassist.models.contracts.loanaggregator;

import java.util.List;

public class UserAccessTokens {

	private List<AccessTokens> accessTokens;

	public List<AccessTokens> getAccessTokens() {
		return accessTokens;
	}

	public void setAccessTokens(List<AccessTokens> accessTokens) {
		this.accessTokens = accessTokens;
	}

}