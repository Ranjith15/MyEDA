package com.edassist.models.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "StudentLoanPaymentHistory")
public class StudentLoanPaymentHistory {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "LoanAggregatorTransactionId")
	private Long loanAggregatorTransactionId;

	@JoinColumn(name = "StudentLoanID")
	@ManyToOne
	private StudentLoan studentLoan;

	@Column(name = "PaymentAmount")
	private BigDecimal paymentAmount;

	@Column(name = "PaymentDate")
	@Temporal(TemporalType.DATE)
	private Date paymentDate;

	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLoanAggregatorTransactionId() {
		return loanAggregatorTransactionId;
	}

	public void setLoanAggregatorTransactionId(Long loanAggregatorTransactionId) {
		this.loanAggregatorTransactionId = loanAggregatorTransactionId;
	}

	public StudentLoan getStudentLoan() {
		return studentLoan;
	}

	public void setStudentLoan(StudentLoan studentLoan) {
		this.studentLoan = studentLoan;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}