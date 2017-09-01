package com.edassist.models.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "StudentLoanPaymentRequest")
public class StudentLoanPaymentRequest implements Serializable {

	@Id
	@Column(name = "StudentLoanPaymentRequestID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentLoanPaymentRequestID;

	@Basic(optional = false)
	@Column(name = "CreatedBy")
	private Long createdBy;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "ModifiedBy")
	private Long modifiedBy;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@JoinColumn(name = "StudentLoanID", referencedColumnName = "StudentLoanID")
	@ManyToOne(cascade = CascadeType.ALL)
	private StudentLoan studentLoanID;

	@Column(name = "PaymentDueDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDueDate;

	@Column(name = "RequestedPaymentAmount")
	private BigDecimal requestedPaymentAmount;

	@Column(name = "IsRecurring")
	private boolean recurring;

	public Long getStudentLoanPaymentRequestID() {
		return studentLoanPaymentRequestID;
	}

	public void setStudentLoanPaymentRequestID(Long studentLoanPaymentRequestID) {
		this.studentLoanPaymentRequestID = studentLoanPaymentRequestID;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public StudentLoan getStudentLoanID() {
		return studentLoanID;
	}

	public void setStudentLoanID(StudentLoan studentLoanID) {
		this.studentLoanID = studentLoanID;
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

}
