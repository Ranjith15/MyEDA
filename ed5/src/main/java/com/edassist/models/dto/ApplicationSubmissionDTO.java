package com.edassist.models.dto;

import java.util.List;

public class ApplicationSubmissionDTO {

	private Long applicationId;
	private Long ApplicationNumber;
	private ApplicationStatusDTO applicationStatus;
	private List<RulesDTO> rules;

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getApplicationNumber() {
		return ApplicationNumber;
	}

	public void setApplicationNumber(Long applicationNumber) {
		ApplicationNumber = applicationNumber;
	}

	public ApplicationStatusDTO getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatusDTO applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public List<RulesDTO> getRules() {
		return rules;
	}

	public void setRules(List<RulesDTO> rules) {
		this.rules = rules;
	}

}
