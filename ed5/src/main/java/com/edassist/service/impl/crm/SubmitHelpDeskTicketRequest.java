package com.edassist.service.impl.crm;

import java.util.List;

import com.edassist.models.dto.AttachmentDTO;
import com.edassist.models.dto.ContactDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmitHelpDeskTicketRequest extends CrmRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1554013910835745486L;

	private String subTopicId;

	private String applicationNumber;

	private String comment;

	private ContactDTO contact;

	private List<AttachmentDTO> attachments;

	/**
	 * @return the subTopicId
	 */
	@JsonProperty("SubTopicId")
	public String getSubTopicId() {
		return subTopicId;
	}

	/**
	 * @return the applicationNumber
	 */
	@JsonProperty("ApplicationNumber")
	public String getApplicationNumber() {
		return applicationNumber;
	}

	/**
	 * @return the comment
	 */
	@JsonProperty("Comment")
	public String getComment() {
		return comment;
	}

	/**
	 * @return the contact
	 */
	@JsonProperty("Contact")
	public ContactDTO getContact() {
		return contact;
	}

	/**
	 * @param subTopicId the subTopicId to set
	 */
	public void setSubTopicId(String subTopicId) {
		this.subTopicId = subTopicId;
	}

	/**
	 * @param applicationNumber the applicationNumber to set
	 */
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(ContactDTO contact) {
		this.contact = contact;
	}

	/**
	 * @return the attachments
	 */
	@JsonProperty("Attachments")
	public List<AttachmentDTO> getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(List<AttachmentDTO> attachments) {
		this.attachments = attachments;
	}
}
