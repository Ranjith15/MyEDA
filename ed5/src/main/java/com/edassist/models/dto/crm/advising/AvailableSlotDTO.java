package com.edassist.models.dto.crm.advising;

public class AvailableSlotDTO {

	private String service;
	private String serviceId;
	private String advisor;
	private String advisorId;
	private String scheduledStart;
	private String scheduledEnd;
	private String formattedStart;
	private String formattedEnd;

	public String getService() {
		return service;
	}

	public String getServiceId() {
		return serviceId;
	}

	public String getAdvisor() {
		return advisor;
	}

	public String getAdvisorId() {
		return advisorId;
	}

	public String getScheduledStart() {
		return scheduledStart;
	}

	public String getScheduledEnd() {
		return scheduledEnd;
	}

	public String getFormattedStart() {
		return formattedStart;
	}

	public String getFormattedEnd() {
		return formattedEnd;
	}

	public void setService(String service) {
		this.service = service;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public void setAdvisor(String advisor) {
		this.advisor = advisor;
	}

	public void setAdvisorId(String advisorId) {
		this.advisorId = advisorId;
	}

	public void setScheduledStart(String scheduledStart) {
		this.scheduledStart = scheduledStart;
	}

	public void setScheduledEnd(String scheduledEnd) {
		this.scheduledEnd = scheduledEnd;
	}

	public void setFormattedStart(String formattedStart) {
		this.formattedStart = formattedStart;
	}

	public void setFormattedEnd(String formattedEnd) {
		this.formattedEnd = formattedEnd;
	}

}
