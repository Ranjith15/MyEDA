package com.edassist.models.contracts.crm.advising;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RescheduleAppointment {

	@JsonProperty("EventId")
	private String appointmentId;

	@JsonProperty("PreferredEmailAddress")
	private String email;

	@JsonProperty("PreferredPhoneNumber")
	private String phoneNumber;

	@JsonProperty("SecondaryPhoneNumber")
	private String secondaryPhoneNumber;

	@JsonProperty("ServiceName")
	private String service;

	@JsonProperty("ServiceId")
	private String serviceId;

	@JsonProperty("Advisor")
	private String advisorId;

	@JsonProperty("AppointmentStartDate")
	private String scheduledStart;

	@JsonProperty("AppointmentEndDate")
	private String scheduledEnd;

	@JsonProperty("Attachments")
	private List<Attachment> attachments;

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSecondaryPhoneNumber() {
		return secondaryPhoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
		this.secondaryPhoneNumber = secondaryPhoneNumber;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getAdvisorId() {
		return advisorId;
	}

	public void setAdvisorId(String advisorId) {
		this.advisorId = advisorId;
	}

	public String getScheduledStart() {
		return scheduledStart;
	}

	public void setScheduledStart(String scheduledStart) {
		this.scheduledStart = scheduledStart;
	}

	public String getScheduledEnd() {
		return scheduledEnd;
	}

	public void setScheduledEnd(String scheduledEnd) {
		this.scheduledEnd = scheduledEnd;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}

}
