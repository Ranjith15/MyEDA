package com.edassist.models.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ReimburseStudentLoanApp")
public class ReimburseStudentLoanApp {

	@Id
	@Column(name = "ReimburseStudentLoanAppID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reimburseStudentLoanAppID;

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

	@JoinColumn(name = "ApplicationID", referencedColumnName = "ApplicationID")
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	private Application applicationID;

	@Column(name = "LoanAccountNumber")
	private String loanAccountNumber;

	@Column(name = "LoanRequestedPaymentAmount")
	private BigDecimal loanRequestedPaymentAmount;

	@Column(name = "LoanApprovedAmount")
	private BigDecimal loanApprovedAmount;

	@Column(name = "LoanPaidAmount")
	private BigDecimal loanPaidAmount;

	@Column(name = "TotalStudentLoanDept")
	private BigDecimal totalStudentLoanDept;

	@Column(name = "RefundAmount")
	private BigDecimal refundAmount;

	@Column(name = "LoanBalance")
	private BigDecimal loanBalance;

	@Column(name = "MonthlyPaymentAmount")
	private BigDecimal monthlyPaymentAmount;

	@Column(name = "SelectedStudentLoanType")
	private String selectedStudentLoanType;

	@Column(name = "LoanPaymentPeriodStartDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date loanPaymentPeriodStartDate;

	@Column(name = "LoanPaymentPeriodEndDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date loanPaymentPeriodEndDate;

	@Column(name = "GraduationDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date graduationDate;

	@JoinColumn(name = "StudentLoanServicerID", referencedColumnName = "StudentLoanServicerID")
	@OneToOne(cascade = CascadeType.ALL)
	private StudentLoanServicer studentLoanServicerID;

	@JoinColumn(name = "DegreeObjectiveID", referencedColumnName = "DegreeObjectiveID")
	@OneToOne(cascade = CascadeType.ALL)
	private DegreeObjectives degreeObjectiveID;

	@JoinColumn(name = "CourseOfStudyID", referencedColumnName = "CourseOfStudyID")
	@OneToOne(cascade = CascadeType.ALL)
	private CourseOfStudy courseOfStudyID;

	@Column(name = "OtherCourseOfStudy")
	private String otherFieldOfStudy;

	@Column(name = "GradePointAverage")
	private BigDecimal gradePointAverage;

	@Column(name = "PreviouslyApproved")
	private Boolean previouslyApproved;

	@JoinColumn(name = "StudentLoanPaymentRequestID", referencedColumnName = "StudentLoanPaymentRequestID")
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private StudentLoanPaymentRequest studentLoanPaymentRequest;

	@Column(name = "PaymentDueDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDueDate;

	public Long getReimburseStudentLoanAppID() {
		return reimburseStudentLoanAppID;
	}

	public void setReimburseStudentLoanAppID(Long reimburseStudentLoanAppID) {
		this.reimburseStudentLoanAppID = reimburseStudentLoanAppID;
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

	public Application getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Application applicationID) {
		this.applicationID = applicationID;
	}

	public String getLoanAccountNumber() {
		return loanAccountNumber;
	}

	public void setLoanAccountNumber(String loanAccountNumber) {
		this.loanAccountNumber = loanAccountNumber;
	}

	public BigDecimal getLoanRequestedPaymentAmount() {
		return loanRequestedPaymentAmount;
	}

	public void setLoanRequestedPaymentAmount(BigDecimal loanRequestedPaymentAmount) {
		this.loanRequestedPaymentAmount = loanRequestedPaymentAmount;
	}

	public BigDecimal getLoanApprovedAmount() {
		return loanApprovedAmount;
	}

	public void setLoanApprovedAmount(BigDecimal loanApprovedAmount) {
		this.loanApprovedAmount = loanApprovedAmount;
	}

	public BigDecimal getLoanPaidAmount() {
		return loanPaidAmount;
	}

	public void setLoanPaidAmount(BigDecimal loanPaidAmount) {
		this.loanPaidAmount = loanPaidAmount;
	}

	public BigDecimal getTotalStudentLoanDept() {
		return totalStudentLoanDept;
	}

	public void setTotalStudentLoanDept(BigDecimal totalStudentLoanDept) {
		this.totalStudentLoanDept = totalStudentLoanDept;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public BigDecimal getLoanBalance() {
		return loanBalance;
	}

	public void setLoanBalance(BigDecimal loanBalance) {
		this.loanBalance = loanBalance;
	}

	public BigDecimal getMonthlyPaymentAmount() {
		return monthlyPaymentAmount;
	}

	public void setMonthlyPaymentAmount(BigDecimal monthlyPaymentAmount) {
		this.monthlyPaymentAmount = monthlyPaymentAmount;
	}

	public String getSelectedStudentLoanType() {
		return selectedStudentLoanType;
	}

	public void setSelectedStudentLoanType(String selectedStudentLoanType) {
		this.selectedStudentLoanType = selectedStudentLoanType;
	}

	public Date getLoanPaymentPeriodStartDate() {
		return loanPaymentPeriodStartDate;
	}

	public void setLoanPaymentPeriodStartDate(Date loanPaymentPeriodStartDate) {
		this.loanPaymentPeriodStartDate = loanPaymentPeriodStartDate;
	}

	public Date getLoanPaymentPeriodEndDate() {
		return loanPaymentPeriodEndDate;
	}

	public void setLoanPaymentPeriodEndDate(Date loanPaymentPeriodEndDate) {
		this.loanPaymentPeriodEndDate = loanPaymentPeriodEndDate;
	}

	public Date getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}

	public StudentLoanServicer getStudentLoanServicerID() {
		return studentLoanServicerID;
	}

	public void setStudentLoanServicerID(StudentLoanServicer studentLoanServicerID) {
		this.studentLoanServicerID = studentLoanServicerID;
	}

	public DegreeObjectives getDegreeObjectiveID() {
		return degreeObjectiveID;
	}

	public void setDegreeObjectiveID(DegreeObjectives degreeObjectiveID) {
		this.degreeObjectiveID = degreeObjectiveID;
	}

	public CourseOfStudy getCourseOfStudyID() {
		return courseOfStudyID;
	}

	public void setCourseOfStudyID(CourseOfStudy courseOfStudyID) {
		this.courseOfStudyID = courseOfStudyID;
	}

	public String getOtherFieldOfStudy() {
		return otherFieldOfStudy;
	}

	public void setOtherFieldOfStudy(String otherFieldOfStudy) {
		this.otherFieldOfStudy = otherFieldOfStudy;
	}

	public BigDecimal getGradePointAverage() {
		return gradePointAverage;
	}

	public void setGradePointAverage(BigDecimal gradePointAverage) {
		this.gradePointAverage = gradePointAverage;
	}

	public Boolean getPreviouslyApproved() {
		return previouslyApproved;
	}

	public void setPreviouslyApproved(Boolean previouslyApproved) {
		this.previouslyApproved = previouslyApproved;
	}

	public StudentLoanPaymentRequest getStudentLoanPaymentRequest() {
		return studentLoanPaymentRequest;
	}

	public void setStudentLoanPaymentRequest(StudentLoanPaymentRequest studentLoanPaymentRequest) {
		this.studentLoanPaymentRequest = studentLoanPaymentRequest;
	}

	public Date getPaymentDueDate() {
		return paymentDueDate;
	}

	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
}
