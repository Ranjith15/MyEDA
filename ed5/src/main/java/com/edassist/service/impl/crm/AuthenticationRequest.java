package com.edassist.service.impl.crm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationRequest extends CrmRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1155201048452476744L;

	private String userName;

	private String password;

	/**
	 * @return the userName
	 */
	@JsonProperty("UserName")
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the password
	 */
	@JsonProperty("Password")
	public String getPassword() {
		return password;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
