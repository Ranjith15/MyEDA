package com.edassist.service.impl.crm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetTicketAttachmentRequest extends CrmRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4974725694318553843L;

	private String ticketId;

	private String annotationId;

	private String noteId;

	/**
	 * @return the ticketId
	 */
	@JsonProperty("TicketId")
	public String getTicketId() {
		return ticketId;
	}

	/**
	 * @return the annotationId
	 */
	@JsonProperty("AnnotationId")
	public String getAnnotationId() {
		return annotationId;
	}

	/**
	 * @return the noteId
	 */
	@JsonProperty("NoteId")
	public String getNoteId() {
		return noteId;
	}

	/**
	 * @param ticketId the ticketId to set
	 */
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * @param annotationId the annotationId to set
	 */
	public void setAnnotationId(String annotationId) {
		this.annotationId = annotationId;
	}

	/**
	 * @param noteId the noteId to set
	 */
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

}
