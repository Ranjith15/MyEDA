package com.edassist.models.dto;

import java.util.Date;

public class SwapTokensDTO {

	private String accessToken;
	private String refreshToken;
	private String idToken;
	private String tokenType;
	private Date expiresIn;
	private String mobileClientCode;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccess_token(String access_token) {
		this.accessToken = access_token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refresh_token) {
		this.refreshToken = refresh_token;
	}

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String id_token) {
		this.idToken = id_token;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String token_type) {
		this.tokenType = token_type;
	}

	public Date getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Date expires_in) {
		this.expiresIn = expires_in;
	}

	public String getMobileClientCode() {
		return mobileClientCode;
	}

	public void setMobileClientCode(String mobileClientCode) {
		this.mobileClientCode = mobileClientCode;
	}
}
