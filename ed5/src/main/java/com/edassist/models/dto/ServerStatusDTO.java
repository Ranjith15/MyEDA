package com.edassist.models.dto;

public class ServerStatusDTO {

	private String name;
	private boolean status;
	private String purpose;
	private Long responseTime;
	private boolean isRestEndpoint;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
	}

	public boolean isRestEndpoint() {
		return isRestEndpoint;
	}

	public void setRestEndpoint(boolean isRestEndpoint) {
		this.isRestEndpoint = isRestEndpoint;
	}
}
