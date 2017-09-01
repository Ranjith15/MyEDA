package com.edassist.models.dto;

import java.util.List;

public class ThinBookApplicationDTO {

	private Long applicationID;
	private Long applicationNumber;
	private ReimburseBookAppDTO reimburseBookApp;
	private List<ApplicationExpensesDTO> applicationExpenses;
	private ApplicationStatusDTO applicationStatus;

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

	public ApplicationStatusDTO getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatusDTO applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
}
