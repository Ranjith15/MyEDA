package com.edassist.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class UserLoginDTO {

	@NotEmpty
	@Size(min = 1, max = 16)
	@Pattern(regexp = "[a-zA-Z0-9]+")
	private String name;

	@NotEmpty
	@Size(min = 1, max = 12)
	private String password;

	@NotNull
	private Long clientId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

}
