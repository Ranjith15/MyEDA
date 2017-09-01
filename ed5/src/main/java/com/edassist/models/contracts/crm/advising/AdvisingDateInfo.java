package com.edassist.models.contracts.crm.advising;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvisingDateInfo {

	@JsonProperty("Date")
	private String date;

	@JsonProperty("AdvisingTopic")
	private int advisingTopic;

	@JsonProperty("TimezoneCode")
	private int timezoneCode;

	@JsonProperty("TamsAccountId")
	private String accountId;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getDate() {
		return date;
	}

	public int getAdvisingTopic() {
		return advisingTopic;
	}

	public int getTimezoneCode() {
		return timezoneCode;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setAdvisingTopic(int advisingTopic) {
		this.advisingTopic = advisingTopic;
	}

	public void setTimezoneCode(int timezoneCode) {
		this.timezoneCode = timezoneCode;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}
}
