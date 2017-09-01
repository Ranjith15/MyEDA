package com.edassist.models.dto;

import java.math.BigDecimal;

public class CapInfoDTO {

	private BigDecimal inProgressAmount;
	private BigDecimal paidAmount;
	private BigDecimal cap;
	private BigDecimal remainingCapAmount;
	private Integer appCount;

	public BigDecimal getInProgressAmount() {
		return inProgressAmount;
	}

	public void setInProgressAmount(BigDecimal inProgressAmount) {
		this.inProgressAmount = inProgressAmount;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getCap() {
		return cap;
	}

	public void setCap(BigDecimal cap) {
		this.cap = cap;
	}

	public BigDecimal getRemainingCapAmount() {
		return remainingCapAmount;
	}

	public void setRemainingCapAmount(BigDecimal remainingCapAmount) {
		this.remainingCapAmount = remainingCapAmount;
	}

	public Integer getAppCount() {
		return appCount;
	}

	public void setAppCount(Integer appCount) {
		this.appCount = appCount;
	}
}
