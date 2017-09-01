package com.edassist.service.impl.crm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetTicketDetailsRequest extends CrmRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4256624353471995572L;

	@JsonProperty("TicketId")
	private String ticketId;

	/**
	 * @return the ticketId
	 */
	public String getTicketId() {
		return ticketId;
	}

	/**
	 * @param ticketId the ticketId to set
	 */
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

}
