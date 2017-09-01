package com.edassist.models.contracts.crm.advising;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvisingCalendarInfo {

	@JsonProperty("DateSelected")
	private String dateSelected;

	@JsonProperty("NumberOfDays")
	private String numberOfDays;

	@JsonProperty("AdvisingTopic")
	private int advisingTopic;

	@JsonProperty("Timezonecode")
	private int timeZoneCode;

	@JsonProperty("TamsAccountId")
	private String tamsAccountId;

	public String getTamsAccountId() {
		return tamsAccountId;
	}

	public void setTamsAccountId(String tamsAccountId) {
		this.tamsAccountId = tamsAccountId;
	}

	public String getDateSelected() {
		return dateSelected;
	}

	public int getAdvisingTopic() {
		return advisingTopic;
	}

	public int getTimeZoneCode() {
		return timeZoneCode;
	}

	public String getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(String numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public void setDateSelected(String dateSelected) {
		this.dateSelected = dateSelected;
	}

	public void setAdvisingTopic(int advisingTopic) {
		this.advisingTopic = advisingTopic;
	}

	public void setTimeZoneCode(int timeZoneCode) {
		this.timeZoneCode = timeZoneCode;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}
}
