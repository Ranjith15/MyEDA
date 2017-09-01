package com.edassist.models.dto;

public class LoginDTO {

	private AuthTokensDTO tokens;
	private ClientFeaturesDTO clientFeatures;
	private UserDetailsDTO userDetails;

	public void setTokens(AuthTokensDTO tokens) {
		this.tokens = tokens;
	}

	public AuthTokensDTO getTokens() {
		return tokens;
	}

	public void setClientFeatures(ClientFeaturesDTO clientFeatures) {
		this.clientFeatures = clientFeatures;
	}

	public ClientFeaturesDTO getClientFeatures() {
		return clientFeatures;
	}

	public void setUserDetails(UserDetailsDTO userDetails) {
		this.userDetails = userDetails;
	}

	public UserDetailsDTO getUserDetails() {
		return userDetails;
	}

}
