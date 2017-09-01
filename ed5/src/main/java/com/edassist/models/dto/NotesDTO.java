package com.edassist.models.dto;

import java.util.List;

import com.edassist.exception.CrmException;
import com.edassist.models.domain.crm.CrmJsonObject;
import com.edassist.utils.RestUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotesDTO extends CrmJsonObject {

	private String comments;

	private String addedBy;

	private String addedOn;

	private List<AttachmentDTO> caseAttachments;

	private String noteId;
	private static final String BR = "<br/>";
	private static final String NBSP = "&nbsp;&nbsp;";

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @return the addedBy
	 */
	public String getAddedBy() {
		return addedBy;
	}

	/**
	 * @return the addedOn
	 */
	public String getAddedOn() {
		return addedOn;
	}

	/**
	 * @param comments the comments to set
	 */
	@JsonProperty("Comments")
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @param addedBy the addedBy to set
	 */
	@JsonProperty("AddedBy")
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	/**
	 * @param addedOn the addedOn to set
	 */
	@JsonProperty("AddedOn")
	public void setAddedOn(String addedOn) {
		this.addedOn = addedOn;
	}

	/**
	 * @return the caseAttachments
	 */

	public List<AttachmentDTO> getCaseAttachments() {
		return caseAttachments;
	}

	/**
	 * @param caseAttachments the caseAttachments to set
	 */
	@JsonProperty("CaseAttachments")
	public void setCaseAttachments(List<AttachmentDTO> caseAttachments) {
		this.caseAttachments = caseAttachments;
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
	@JsonProperty("NoteId")
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	/**
	 * @return
	 * @throws CrmException
	 */
	public String display() throws CrmException {
		StringBuilder builder = new StringBuilder();
		builder.append(getAddedBy()).append(NBSP).append(RestUtils.formatCrmDate(getAddedOn())).append(BR).append(getComments());
		if (getCaseAttachments() != null && getCaseAttachments().size() > 0) {
			builder.append(NBSP).append(getCaseAttachments().get(0).getFileName());
		}
		return builder.toString();
	}

	/**
	 * @return
	 */
	public String getFormattedAddedOn() {
		return RestUtils.formatCrmDate(getAddedOn());
	}
}
