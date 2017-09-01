package com.edassist.models.domain.crm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketState extends CrmJsonObject {

	@JsonProperty("Value")
	private String value;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Statuses")
	private TicketStatus[] statuses;

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the statuses
	 */
	public TicketStatus[] getStatuses() {
		return statuses;
	}

	/**
	 * @param statuses the statuses to set
	 */
	public void setStatuses(TicketStatus[] statuses) {
		this.statuses = statuses;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
