package com.edassist.models.dto;

import java.util.Date;

public class UserDetailsDTO {

	private Long userId;
	private String firstName;
	private String lastName;
	private String userName;
	// TODO: This needs to be removed when we fix the mobile app, using UserTypeId going forward
	private String role;
	private Long participantId;
	private Integer resetPassword;
	private Date mostRecentLogin;
	private int userTypeID;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public void setResetPassword(Integer resetPassword) {
		this.resetPassword = resetPassword;
	}

	public Integer getResetPassword() {
		return resetPassword;
	}

	public Date getMostRecentLogin() {
		return mostRecentLogin;
	}

	public void setMostRecentLogin(Date mostRecentLogin) {
		this.mostRecentLogin = mostRecentLogin;
	}

	public int getUserTypeID() {
		return userTypeID;
	}

	public void setUserTypeID(int userTypeID) {
		this.userTypeID = userTypeID;
	}

}
