package com.edassist.models.dto;

public class ApplicationStatusChangeDTO {

	private Long applicationStatusCode;
	private String comment;

	public ApplicationStatusChangeDTO() {

	}

	public Long getApplicationStatusCode() {
		return applicationStatusCode;
	}

	public void setApplicationStatusCode(Long applicationStatusCode) {
		this.applicationStatusCode = applicationStatusCode;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
