package com.edassist.models.dto.loanaggregator;

public class LoanAggregatorUserDTO {

	private String id;
	private String loginName;
	private String password;
	private String email;
	private UserSessionDTO session;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserSessionDTO getSession() {
		return session;
	}

	public void setSession(UserSessionDTO session) {
		this.session = session;
	}

}
