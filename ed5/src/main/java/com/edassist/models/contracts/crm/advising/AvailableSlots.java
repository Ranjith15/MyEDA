package com.edassist.models.contracts.crm.advising;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailableSlots {

	@JsonProperty("AdvisingSlots")
	private List<AvailableSlot> advisingSlots;

	@JsonProperty("Message")
	private String message;

	@JsonProperty("ResponseCode")
	private long responseCode;

	public List<AvailableSlot> getAdvisingSlots() {
		return advisingSlots;
	}

	public void setAdvisingSlots(List<AvailableSlot> advisingSlots) {
		this.advisingSlots = advisingSlots;
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
