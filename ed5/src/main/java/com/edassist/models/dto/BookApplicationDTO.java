package com.edassist.models.dto;

import java.util.List;

public class BookApplicationDTO {

	private Long parentApplicationID;
	private Long programId;
	private boolean agreementVerify;
	private GrantScholarshipDTO grantScholarship;
	private ReimburseBookAppDTO reimburseBookApp;
	private List<ApplicationExpensesDTO> applicationExpenses;

	public Long getParentApplicationID() {
		return parentApplicationID;
	}

	public void setParentApplicationID(Long parentApplicationID) {
		this.parentApplicationID = parentApplicationID;
	}

	public GrantScholarshipDTO getGrantScholarship() {
		return grantScholarship;
	}

	public void setGrantScholarship(GrantScholarshipDTO grantScholarship) {
		this.grantScholarship = grantScholarship;
	}

	public ReimburseBookAppDTO getReimburseBookApp() {
		return reimburseBookApp;
	}

	public void setReimburseBookApp(ReimburseBookAppDTO reimburseBookApp) {
		this.reimburseBookApp = reimburseBookApp;
	}

	public List<ApplicationExpensesDTO> getApplicationExpenses() {
		return applicationExpenses;
	}

	public void setApplicationExpenses(List<ApplicationExpensesDTO> applicationExpenses) {
		this.applicationExpenses = applicationExpenses;
	}

	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	public boolean isAgreementVerify() {
		return agreementVerify;
	}

	public void setAgreementVerify(boolean agreementVerify) {
		this.agreementVerify = agreementVerify;
	}
}
