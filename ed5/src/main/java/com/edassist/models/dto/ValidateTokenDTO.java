package com.edassist.models.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidateTokenDTO {

	private String scope;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("client_id")
	private String clientId;

	@JsonProperty("expires_in")
	private Date expiresIn;

	@JsonProperty("access_token")
	private AccessTokenDTO accessToken;

	public AccessTokenDTO getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(AccessTokenDTO accessToken) {
		this.accessToken = accessToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public Date getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Date expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
