package com.edassist.models.dto;

import java.math.BigDecimal;
import java.util.Date;

public class RefundDTO {

	private Long refundID;
	private Long applicationID;
	private RefundMethodDTO refundMethodId;
	private String checkRef;
	private Date dateReceived;
	private BigDecimal refundAmount;
	private BigDecimal appliedAmount;

	public Long getRefundID() {
		return refundID;
	}

	public void setRefundID(Long refundID) {
		this.refundID = refundID;
	}

	public Long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public RefundMethodDTO getRefundMethodId() {
		return refundMethodId;
	}

	public void setRefundMethodId(RefundMethodDTO refundMethodId) {
		this.refundMethodId = refundMethodId;
	}

	public String getCheckRef() {
		return checkRef;
	}

	public void setCheckRef(String checkRef) {
		this.checkRef = checkRef;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public BigDecimal getAppliedAmount() {
		return appliedAmount;
	}

	public void setAppliedAmount(BigDecimal appliedAmount) {
		this.appliedAmount = appliedAmount;
	}

}