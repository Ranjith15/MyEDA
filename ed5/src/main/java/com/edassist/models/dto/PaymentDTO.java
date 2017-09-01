package com.edassist.models.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentDTO {

	private Integer paymentsID;
	private Date transactionDate;
	private String paymentInfo;
	private BigDecimal paymentAmount;
	private BigDecimal taxableAmount;

	public Integer getPaymentsID() {
		return paymentsID;
	}

	public void setPaymentsID(Integer paymentsID) {
		this.paymentsID = paymentsID;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public BigDecimal getTaxableAmount() {
		return taxableAmount;
	}

	public void setTaxableAmount(BigDecimal taxableAmount) {
		this.taxableAmount = taxableAmount;
	}

}