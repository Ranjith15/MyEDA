package com.edassist.service.impl.crm;

import com.edassist.models.dto.TicketSummaryDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchTicketResponse extends CrmResponse {
	@JsonProperty("Ticket")
	private TicketSummaryDTO[] tickets;

	/**
	 * @return the tickets
	 */
	public TicketSummaryDTO[] getTickets() {
		return tickets;
	}

	/**
	 * @param tickets the tickets to set
	 */
	public void setTickets(TicketSummaryDTO[] tickets) {
		this.tickets = tickets;
	}
}
