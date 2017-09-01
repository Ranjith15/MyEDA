package com.edassist.service.impl.crm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmitTicketResponse extends CrmResponse {

	@JsonProperty("TicketNumber")
	private String ticketNumber;

	/**
	 * @return the ticketNumber
	 */
	public String getTicketNumber() {
		return ticketNumber;
	}

	/**
	 * @param ticketNumber the ticketNumber to set
	 */
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

}
