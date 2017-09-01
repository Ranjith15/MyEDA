
package com.edassist.models.dto;

public class LoanApplicationDTO {
	private Long applicationID;
	private Long applicationNumber;
	private Long participantId;
	private ApplicationStatusDTO applicationStatus;
	private EducationalProviderDTO educationalProvider;
	private ReimburseStudentLoanAppDTO reimburseStudentLoanApp;
	// private ProgramDTO program;

	public Long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public Long getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public ApplicationStatusDTO getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatusDTO applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public EducationalProviderDTO getEducationalProvider() {
		return educationalProvider;
	}

	public void setEducationalProvider(EducationalProviderDTO educationalProvider) {
		this.educationalProvider = educationalProvider;
	}

	public ReimburseStudentLoanAppDTO getReimburseStudentLoanApp() {
		return reimburseStudentLoanApp;
	}

	public void setReimburseStudentLoanApp(ReimburseStudentLoanAppDTO reimburseStudentLoanApp) {
		this.reimburseStudentLoanApp = reimburseStudentLoanApp;
	}

}