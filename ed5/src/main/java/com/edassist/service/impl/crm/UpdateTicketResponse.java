package com.edassist.service.impl.crm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateTicketResponse extends CrmResponse {
	@JsonProperty("IsUpdated")
	private boolean isUpdated;
	@JsonProperty("NoteId")
	private String noteId;

	/**
	 * @return the isUpdated
	 */
	public boolean isUpdated() {
		return isUpdated;
	}

	/**
	 * @param isUpdated the isUpdated to set
	 */
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	/**
	 * @return the noteId
	 */
	public String getNoteId() {
		return noteId;
	}

	/**
	 * @param noteId the noteId to set
	 */
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

}
