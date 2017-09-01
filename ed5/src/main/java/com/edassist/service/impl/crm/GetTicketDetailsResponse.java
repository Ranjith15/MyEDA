package com.edassist.service.impl.crm;

import com.edassist.models.dto.NotesDTO;
import com.edassist.models.dto.TicketDetailsDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetTicketDetailsResponse extends CrmResponse {
	@JsonProperty("Ticket")
	private TicketDetailsDTO ticket;

	@JsonProperty("Notes")
	private NotesDTO[] notes;

	/**
	 * @return the notes
	 */
	public NotesDTO[] getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(NotesDTO[] notes) {
		this.notes = notes;
	}

	/**
	 * @return the ticket
	 */
	public TicketDetailsDTO getTicket() {
		return ticket;
	}

	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(TicketDetailsDTO ticket) {
		this.ticket = ticket;
	}

}
