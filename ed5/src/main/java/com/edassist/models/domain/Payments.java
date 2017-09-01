package com.edassist.models.domain;

import com.edassist.utils.CommonUtil;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Payments")
public class Payments {

	@Id
	@Column(name = "PaymentsID")
	private Integer paymentsID;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ApplicationID")
	private ThinApp applicationID;

	@Column(name = "TransactionDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;

	@Column(name = "PaymentInfo")
	private String paymentInfo;

	@Column(name = "PaymentAmount")
	private BigDecimal paymentAmount;

	@Column(name = "TaxableAmount")
	private BigDecimal taxableAmount;

	public Payments() {

	}
	public Payments(Integer paymentsID) {
		this.paymentsID = paymentsID;
	}

	public Integer getPaymentsID() {
		return paymentsID;
	}

	public void setPaymentsID(Integer paymentsID) {
		this.paymentsID = paymentsID;
	}

	public ThinApp getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(ThinApp applicationID) {
		this.applicationID = applicationID;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	@Transient
	public String getFormatTransactionDate() {
		return CommonUtil.formatDate(transactionDate, CommonUtil.MMDDYYY);
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
