package com.edassist.models.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "StudentLoan")
public class StudentLoan {

	@Id
	@Column(name = "StudentLoanID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studentLoanID;

	@Column(name = "ParticipantID")
	private int participantID;

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "LoanServicerID", referencedColumnName = "StudentLoanServicerID")
	private StudentLoanServicer loanServicerID;

	@Column(name = "StudentLoanType")
	private String studentLoanType;

	@Column(name = "PaymentAddress1")
	private String paymentAddress1;

	@Column(name = "PaymentAddress2")
	private String paymentAddress2;

	@Column(name = "PaymentCity")
	private String paymentCity;

	@Column(name = "PaymentState")
	private String paymentState;

	@Column(name = "PaymentZipCode")
	private String paymentZipCode;

	@Column(name = "AccountNumber")
	private String accountNumber;

	@Column(name = "SecondaryAccountNumber")
	private String secondaryAccountNumber;

	@Column(name = "InterestRate")
	private Float interestRate;

	@Column(name = "OriginalLoanAmount")
	private BigDecimal originalLoanAmount;

	@Column(name = "LoanBalance")
	private BigDecimal loanBalance;

	@Column(name = "MinPaymentAmount")
	private BigDecimal minPaymentAmount;

	@Temporal(TemporalType.DATE)
	@Column(name = "PaymentDueDate")
	private Date paymentDueDate;

	@Column(name = "AmountDue")
	private BigDecimal amountDue;

	@Column(name = "AccountStatus")
	private String accountStatus;

	@Column(name = "CreatedBy")
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateCreated", insertable = false)
	private Date dateCreated;

	@Column(name = "ModifiedBy", insertable = false, updatable = false)
	private Integer modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ModifiedDate")
	private Date modifiedDate;

	@Column(name = "LoanAggregatorUserAccountId")
	private Integer loanAggregatorUserAccountId;

	@Temporal(TemporalType.DATE)
	@Column(name = "LastRefreshedDate")
	private Date lastRefreshedDate;

	@Column(name = "Active")
	private boolean active;

	public StudentLoan() {
	}

	public StudentLoan(int participantID) {
		this.participantID = participantID;
	}

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

	public StudentLoanServicer getLoanServicerID() {
		return loanServicerID;
	}

	public void setLoanServicerID(StudentLoanServicer loanServicerID) {
		this.loanServicerID = loanServicerID;
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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Integer getLoanAggregatorUserAccountId() {
		return loanAggregatorUserAccountId;
	}

	public void setLoanAggregatorUserAccountId(Integer loanAggregatorUserAccountId) {
		this.loanAggregatorUserAccountId = loanAggregatorUserAccountId;
	}

	public Date getLastRefreshedDate() {
		return lastRefreshedDate;
	}

	public void setLastRefreshedDate(Date lastRefreshedDate) {
		this.lastRefreshedDate = lastRefreshedDate;
	}

	public BigDecimal getMinPaymentAmount() {
		return minPaymentAmount;
	}

	public void setMinPaymentAmount(BigDecimal minPaymentAmount) {
		this.minPaymentAmount = minPaymentAmount;
	}

	public Date getPaymentDueDate() {
		return paymentDueDate;
	}

	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public BigDecimal getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(BigDecimal amountDue) {
		this.amountDue = amountDue;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
