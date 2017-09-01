package com.edassist.models.dto.loanaggregator;

import java.util.List;

public class UserAccessTokensDTO {

	private List<AccessTokensDTO> accessTokens;

	public List<AccessTokensDTO> getAccessTokens() {
		return accessTokens;
	}

	public void setAccessTokens(List<AccessTokensDTO> accessTokens) {
		this.accessTokens = accessTokens;
	}

}
