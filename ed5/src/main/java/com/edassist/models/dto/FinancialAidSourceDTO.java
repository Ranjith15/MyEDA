package com.edassist.models.dto;

public class FinancialAidSourceDTO {

	private Long financialAidSourceId;
	private String financialAidDescription;

	public void setFinancialAidSourceId(Long financialAidSourceId) {
		this.financialAidSourceId = financialAidSourceId;
	}

	public Long getFinancialAidSourceId() {
		return financialAidSourceId;
	}

	public void setFinancialAidDescription(String financialAidDescription) {
		this.financialAidDescription = financialAidDescription;
	}

	public String getFinancialAidDescription() {
		return financialAidDescription;
	}

}
