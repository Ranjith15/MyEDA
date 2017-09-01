package com.edassist.models.dto;

public class UserDTO {

	private Long userId;
	private String firstName;
	private String lastName;
	private String userName;
	private UserTypeDTO userType;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public UserTypeDTO getUserType() {
		return userType;
	}

	public void setUserType(UserTypeDTO userType) {
		this.userType = userType;
	}
}
