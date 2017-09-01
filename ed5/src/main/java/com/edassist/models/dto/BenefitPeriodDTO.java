package com.edassist.models.dto;

import java.util.Date;

public class BenefitPeriodDTO {

	private Long benefitPeriodID;
	private String benefitPeriodName;
	private ProgramDTO programID;
	private Date startDate;
	private Date endDate;

	public void setbenefitPeriodID(Long benefitPeriodID) {
		this.benefitPeriodID = benefitPeriodID;
	}

	public Long getbenefitPeriodID() {
		return benefitPeriodID;
	}

	public void setProgramID(ProgramDTO programID) {
		this.programID = programID;
	}

	public ProgramDTO getProgramID() {
		return programID;
	}

	public void setBenefitPeriodName(String benefitPeriodName) {
		this.benefitPeriodName = benefitPeriodName;
	}

	public String getBenefitPeriodName() {
		return benefitPeriodName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
