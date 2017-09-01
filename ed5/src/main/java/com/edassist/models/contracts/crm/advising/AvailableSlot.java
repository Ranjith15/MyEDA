package com.edassist.models.contracts.crm.advising;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailableSlot {

	@JsonProperty("Service")
	private String service;

	@JsonProperty("ServiceId")
	private String serviceId;

	@JsonProperty("Advisor")
	private String advisor;

	@JsonProperty("AdvisorId")
	private String advisorId;

	@JsonProperty("ScheduledStart")
	private String scheduledStart;

	@JsonProperty("ScheduledEnd")
	private String scheduledEnd;

	private String formattedStart;
	private String formattedEnd;

	public String getService() {
		return service;
	}

	public String getAdvisor() {
		return advisor;
	}

	public String getScheduledStart() {
		return scheduledStart;
	}

	public String getScheduledEnd() {
		return scheduledEnd;
	}

	public void setService(String service) {
		this.service = service;
	}

	public void setAdvisor(String advisor) {
		this.advisor = advisor;
	}

	public void setScheduledStart(String scheduledStart) {
		this.scheduledStart = scheduledStart;
	}

	public void setScheduledEnd(String scheduledEnd) {
		this.scheduledEnd = scheduledEnd;
	}

	public String getServiceId() {
		return serviceId;
	}

	public String getAdvisorId() {
		return advisorId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public void setAdvisorId(String advisorId) {
		this.advisorId = advisorId;
	}

	public String getFormattedStart() {
		return formattedStart;
	}

	public void setFormattedStart(String formattedStart) {
		this.formattedStart = formattedStart;
	}

	public String getFormattedEnd() {
		return formattedEnd;
	}

	public void setFormattedEnd(String formattedEnd) {
		this.formattedEnd = formattedEnd;
	}

}
