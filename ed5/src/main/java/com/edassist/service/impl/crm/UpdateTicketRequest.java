package com.edassist.service.impl.crm;

import java.util.List;

import com.edassist.models.dto.AttachmentDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateTicketRequest extends CrmRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3723726257683680388L;

	@JsonProperty("TicketId")
	private String ticketId;
	@JsonProperty("Comment")
	private String comment;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("LastName")
	private String lastName;
	@JsonProperty("NoteId")
	private String noteId;
	private List<AttachmentDTO> attachments;

	/**
	 * @return the ticketId
	 */
	public String getTicketId() {
		return ticketId;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param ticketId the ticketId to set
	 */
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the noteId
	 */
	public String getNoteId() {
		return noteId;
	}

	/**
	 * @return the attachments
	 */
	@JsonProperty("Attachments")
	public List<AttachmentDTO> getAttachments() {
		return attachments;
	}

	/**
	 * @param noteId the noteId to set
	 */
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(List<AttachmentDTO> attachments) {
		this.attachments = attachments;
	}

}
