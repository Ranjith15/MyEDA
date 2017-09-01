package com.edassist.service.impl.crm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {
	@JsonProperty("SecurityToken")
	private String securityToken;

	/**
	 * @return the securityToken
	 */
	public String getSecurityToken() {
		return securityToken;
	}

	/**
	 * @param securityToken the securityToken to set
	 */
	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}

}
