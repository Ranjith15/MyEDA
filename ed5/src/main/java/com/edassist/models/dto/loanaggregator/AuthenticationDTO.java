package com.edassist.models.dto.loanaggregator;

public class AuthenticationDTO {

	private String cobrandId;
	private String applicationId;
	private AuthSessionDTO session;

	public String getCobrandId() {
		return cobrandId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public AuthSessionDTO getSession() {
		return session;
	}

	public void setCobrandId(String cobrandId) {
		this.cobrandId = cobrandId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public void setSession(AuthSessionDTO session) {
		this.session = session;
	}

}