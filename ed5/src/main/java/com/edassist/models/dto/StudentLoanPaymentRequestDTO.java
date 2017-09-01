package com.edassist.models.dto;

import java.math.BigDecimal;
import java.util.Date;

public class StudentLoanPaymentRequestDTO {

	private Long studentLoanPaymentRequestID;
	private Date paymentDueDate;
	private BigDecimal requestedPaymentAmount;
	private boolean recurring;
	StudentLoanDTO studentLoan;

	public Long getStudentLoanPaymentRequestID() {
		return studentLoanPaymentRequestID;
	}

	public void setStudentLoanPaymentRequestID(Long studentLoanPaymentRequestID) {
		this.studentLoanPaymentRequestID = studentLoanPaymentRequestID;
	}

	public Date getPaymentDueDate() {
		return paymentDueDate;
	}

	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public BigDecimal getRequestedPaymentAmount() {
		return requestedPaymentAmount;
	}

	public void setRequestedPaymentAmount(BigDecimal requestedPaymentAmount) {
		this.requestedPaymentAmount = requestedPaymentAmount;
	}

	public boolean isRecurring() {
		return recurring;
	}

	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}

	public StudentLoanDTO getStudentLoan() {
		return studentLoan;
	}

	public void setStudentLoan(StudentLoanDTO studentLoan) {
		this.studentLoan = studentLoan;
	}

}
