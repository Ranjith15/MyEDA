package com.edassist.models.contracts.crm.advising;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvisingEvent {

	String eventId;

	@JsonProperty("IsRescheduled")
	private boolean rescheduled;

	@JsonProperty("ResponseCode")
	private long responseCode;

	@JsonProperty("EventId")
	public String getEventId() {
		return eventId;
	}

	@JsonProperty("EventID")
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public boolean isRescheduled() {
		return rescheduled;
	}

	public void setRescheduled(boolean rescheduled) {
		this.rescheduled = rescheduled;
	}

	public long getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(long responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}

}
