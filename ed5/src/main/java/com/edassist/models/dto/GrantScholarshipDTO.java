package com.edassist.models.dto;

import java.math.BigDecimal;

public class GrantScholarshipDTO {

	private FinancialAidSourceDTO financialAidSourceId;
	private String otherFinancialAid;
	private BigDecimal financialAidAmount;

	public FinancialAidSourceDTO getFinancialAidSourceId() {
		return financialAidSourceId;
	}

	public void setFinancialAidSourceId(FinancialAidSourceDTO financialAidSourceId) {
		this.financialAidSourceId = financialAidSourceId;
	}

	public String getOtherFinancialAid() {
		return otherFinancialAid;
	}

	public void setOtherFinancialAid(String otherFinancialAid) {
		this.otherFinancialAid = otherFinancialAid;
	}

	public BigDecimal getFinancialAidAmount() {
		return financialAidAmount;
	}

	public void setFinancialAidAmount(BigDecimal financialAidAmount) {
		this.financialAidAmount = financialAidAmount;
	}
}
