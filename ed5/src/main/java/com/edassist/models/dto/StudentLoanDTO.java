package com.edassist.models.dto;

import java.math.BigDecimal;
import java.util.Date;

public class StudentLoanDTO {

	private int studentLoanID;

	private int participantID;

	private String studentLoanType;

	private String paymentAddress1;

	private String paymentAddress2;

	private String paymentCity;

	private String paymentState;

	private String paymentZipCode;

	private String accountNumber;

	private String secondaryAccountNumber;

	private Float interestRate;

	private BigDecimal originalLoanAmount;

	private BigDecimal loanBalance;

	private BigDecimal minPaymentAmount;

	private Date paymentDueDate;

	private StudentLoanServicerDTO studentLoanServicer;

	public int getStudentLoanID() {
		return studentLoanID;
	}

	public void setStudentLoanID(int studentLoanID) {
		this.studentLoanID = studentLoanID;
	}

	public int getParticipantID() {
		return participantID;
	}

	public void setParticipantID(int participantID) {
		this.participantID = participantID;
	}

	public String getStudentLoanType() {
		return studentLoanType;
	}

	public void setStudentLoanType(String studentLoanType) {
		this.studentLoanType = studentLoanType;
	}

	public String getPaymentAddress1() {
		return paymentAddress1;
	}

	public void setPaymentAddress1(String paymentAddress1) {
		this.paymentAddress1 = paymentAddress1;
	}

	public String getPaymentAddress2() {
		return paymentAddress2;
	}

	public void setPaymentAddress2(String paymentAddress2) {
		this.paymentAddress2 = paymentAddress2;
	}

	public String getPaymentCity() {
		return paymentCity;
	}

	public void setPaymentCity(String paymentCity) {
		this.paymentCity = paymentCity;
	}

	public String getPaymentState() {
		return paymentState;
	}

	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}

	public String getPaymentZipCode() {
		return paymentZipCode;
	}

	public void setPaymentZipCode(String paymentZipCode) {
		this.paymentZipCode = paymentZipCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSecondaryAccountNumber() {
		return secondaryAccountNumber;
	}

	public void setSecondaryAccountNumber(String secondaryAccountNumber) {
		this.secondaryAccountNumber = secondaryAccountNumber;
	}

	public Float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Float interestRate) {
		this.interestRate = interestRate;
	}

	public BigDecimal getOriginalLoanAmount() {
		return originalLoanAmount;
	}

	public void setOriginalLoanAmount(BigDecimal originalLoanAmount) {
		this.originalLoanAmount = originalLoanAmount;
	}

	public BigDecimal getLoanBalance() {
		return loanBalance;
	}

	public void setLoanBalance(BigDecimal loanBalance) {
		this.loanBalance = loanBalance;
	}

	public BigDecimal getMinPaymentAmount() {
		return minPaymentAmount;
	}

	public void setMinPaymentAmount(BigDecimal minPaymentAmount) {
		this.minPaymentAmount = minPaymentAmount;
	}

	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public Date getPaymentDueDate() {
		return paymentDueDate;
	}

	public StudentLoanServicerDTO getStudentLoanServicer() {
		return studentLoanServicer;
	}

	public void setStudentLoanServicer(StudentLoanServicerDTO studentLoanServicer) {
		this.studentLoanServicer = studentLoanServicer;
	}

}
