package com.edassist.models.contracts.crm.advising;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailableDate {

	@JsonProperty("AvailableDate")
	private String availableDate;

	@JsonProperty("IsAvailable")
	private boolean isAvailable;

	public String getAvailableDate() {
		return availableDate;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailableDate(String availableDate) {
		this.availableDate = availableDate;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

}
