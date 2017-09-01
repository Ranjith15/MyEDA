package com.edassist.models.dto;

import java.util.Date;

public class EligibilityEventHistoryDTO {

	private String status;
	private Long statusId;
	private String message;
	private OptionDTO messageSource;
	private Date dateCreated;
	private String createdByName;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public OptionDTO getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(OptionDTO messageSource) {
		this.messageSource = messageSource;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
}
