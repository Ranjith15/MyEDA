package com.edassist.service.impl.crm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAdviseeInfoResponse extends CrmResponse {

	private static final long serialVersionUID = -4641103634776101408L;
	private String preferredEmailAddress;
	private String preferredPhoneNumber;
	private String secondaryPhoneNumber;

	public String getPreferredEmailAddress() {
		return preferredEmailAddress;
	}

	public String getPreferredPhoneNumber() {
		return preferredPhoneNumber;
	}

	public String getSecondaryPhoneNumber() {
		return secondaryPhoneNumber;
	}

	@JsonProperty("PreferredEmailAddress")
	public void setPreferredEmailAddress(String preferredEmailAddress) {
		this.preferredEmailAddress = preferredEmailAddress;
	}

	@JsonProperty("PreferredPhoneNumber")
	public void setPreferredPhoneNumber(String primaryPhoneNumber) {
		this.preferredPhoneNumber = primaryPhoneNumber;
	}

	@JsonProperty("SecondaryPhoneNumber")
	public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
		this.secondaryPhoneNumber = secondaryPhoneNumber;
	}

}
