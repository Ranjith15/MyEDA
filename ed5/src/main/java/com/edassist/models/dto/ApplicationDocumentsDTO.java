package com.edassist.models.dto;

import java.util.Date;

public class ApplicationDocumentsDTO {

	private Long applicationDocumentID;
	private boolean received;
	private Date dateReceived;
	private boolean incomplete;
	private Date modifiedDate;
	private Long modifiedBy;
	private String dmsLocation;
	private boolean IDCVerified;
	private String Notes;
	private String documentName;

	public Long getApplicationDocumentID() {
		return applicationDocumentID;
	}

	public void setApplicationDocumentID(Long applicationDocumentID) {
		this.applicationDocumentID = applicationDocumentID;
	}

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public boolean isIncomplete() {
		return incomplete;
	}

	public void setIncomplete(boolean incomplete) {
		this.incomplete = incomplete;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getDmsLocation() {
		return dmsLocation;
	}

	public void setDmsLocation(String dmsLocation) {
		this.dmsLocation = dmsLocation;
	}

	public boolean isIDCVerified() {
		return IDCVerified;
	}

	public void setIDCVerified(boolean iDCVerified) {
		IDCVerified = iDCVerified;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String notes) {
		Notes = notes;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
}
