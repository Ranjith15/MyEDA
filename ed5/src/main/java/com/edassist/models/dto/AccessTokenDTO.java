package com.edassist.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenDTO {

	@JsonProperty("SUBJECT")
	private String subject;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
