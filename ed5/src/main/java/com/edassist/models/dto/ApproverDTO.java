package com.edassist.models.dto;

public class ApproverDTO {

	private UserDTO user;
	private AddressDTO address;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public AddressDTO getAddress() {
		return address;
	}
}
