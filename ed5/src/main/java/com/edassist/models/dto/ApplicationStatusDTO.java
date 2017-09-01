package com.edassist.models.dto;

public class ApplicationStatusDTO {

	private Long applicationStatusID;
	private String applicationStatus;
	private Long applicationStatusCode;

	public Long getApplicationStatusID() {
		return applicationStatusID;
	}

	public void setApplicationStatusID(Long applicationStatusID) {
		this.applicationStatusID = applicationStatusID;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public Long getApplicationStatusCode() {
		return applicationStatusCode;
	}

	public void setApplicationStatusCode(Long applicationStatusCode) {
		this.applicationStatusCode = applicationStatusCode;
	}

}
