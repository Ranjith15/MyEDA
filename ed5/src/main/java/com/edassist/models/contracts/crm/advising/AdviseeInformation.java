package com.edassist.models.contracts.crm.advising;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdviseeInformation {

	@JsonProperty("PreferredEmailAddress")
	private String preferredEmailAddress;
	@JsonProperty("PreferredPhoneNumber")
	private String preferredPhoneNumber;
	@JsonProperty("SecondaryPhoneNumber")
	private String secondaryPhoneNumber;

	@JsonProperty("ResponseCode")
	private long responseCode;

	public String getPreferredEmailAddress() {
		return preferredEmailAddress;
	}

	public String getPreferredPhoneNumber() {
		return preferredPhoneNumber;
	}

	public String getSecondaryPhoneNumber() {
		return secondaryPhoneNumber;
	}

	public void setPreferredEmailAddress(String preferredEmailAddress) {
		this.preferredEmailAddress = preferredEmailAddress;
	}

	public void setPreferredPhoneNumber(String primaryPhoneNumber) {
		this.preferredPhoneNumber = primaryPhoneNumber;
	}

	public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
		this.secondaryPhoneNumber = secondaryPhoneNumber;
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
