package com.edassist.models.dto;

public class ProgramEligibleDocumentTypeDTO {

	private Long programEligibleDocumentTypeID;
	private Long programId;
	private Long defaultDocumentsID;
	private String documentName;

	public Long getProgramEligibleDocumentTypeID() {
		return programEligibleDocumentTypeID;
	}

	public void setProgramEligibleDocumentTypeID(Long programEligibleDocumentTypeID) {
		this.programEligibleDocumentTypeID = programEligibleDocumentTypeID;
	}

	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	public Long getDefaultDocumentsID() {
		return defaultDocumentsID;
	}

	public void setDefaultDocumentsID(Long defaultDocumentsID) {
		this.defaultDocumentsID = defaultDocumentsID;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

}
