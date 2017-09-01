package com.edassist.models.contracts.crm.advising;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CancelEvent {

	@JsonProperty("IsEventCanceled")
	private boolean eventCanceled;

	@JsonProperty("ResponseCode")
	private long responseCode;

	public boolean isEventCanceled() {
		return eventCanceled;
	}

	public void setEventCanceled(boolean eventCanceled) {
		this.eventCanceled = eventCanceled;
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
