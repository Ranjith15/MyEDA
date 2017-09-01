package com.edassist.service.impl.crm;

import com.edassist.models.dto.TicketSummaryDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTicketHistoryResponse extends CrmResponse {
	@JsonProperty("Tickets")
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
