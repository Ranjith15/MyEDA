package com.edassist.models.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ReimburseStudentLoanAppHistory")
public class ReimburseStudentLoanAppHistory {

	@Id
	@Column(name = "ReimburseStudentLoanAppHistoryID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reimburseStudentLoanAppHistoryID;

	@Column(name = "ApplicationID")
	private Long applicationID;

	@Column(name = "HistoryDateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date historyDateCreated;

	@Column(name = "ApplicationStatusID")
	private Long applicationStatusID;

	@Column(name = "BenefitPeriodID")
	private Long benefitPeriodID;

	@Column(name = "ApplicationTypeID")
	private Long applicationTypeID;

	@Column(name = "EducationalProviderID")
	private Long educationalProviderID;

	@Column(name = "CreatedBy")
	private Long createdBy;

	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "ModifiedBy")
	private Long modifiedBy;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

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

	@Column(name = "StudentLoanServicerID")
	private Long studentLoanServicerID;

	@Column(name = "DegreeObjectiveID")
	private Long degreeObjectiveID;

	@Column(name = "CourseOfStudyID")
	private Long courseOfStudyID;

	@Column(name = "OtherCourseOfStudy")
	private String otherFieldOfStudy;

	@Column(name = "GradePointAverage")
	private BigDecimal gradePointAverage;

	@Column(name = "PreviouslyApproved")
	private Boolean previouslyApproved;

	public Long getReimburseStudentLoanAppHistoryID() {
		return reimburseStudentLoanAppHistoryID;
	}

	public void setReimburseStudentLoanAppHistoryID(Long reimburseStudentLoanAppHistoryID) {
		this.reimburseStudentLoanAppHistoryID = reimburseStudentLoanAppHistoryID;
	}

	public Long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public Date getHistoryDateCreated() {
		return historyDateCreated;
	}

	public void setHistoryDateCreated(Date historyDateCreated) {
		this.historyDateCreated = historyDateCreated;
	}

	public Long getApplicationStatusID() {
		return applicationStatusID;
	}

	public void setApplicationStatusID(Long applicationStatusID) {
		this.applicationStatusID = applicationStatusID;
	}

	public Long getBenefitPeriodID() {
		return benefitPeriodID;
	}

	public void setBenefitPeriodID(Long benefitPeriodID) {
		this.benefitPeriodID = benefitPeriodID;
	}

	public Long getApplicationTypeID() {
		return applicationTypeID;
	}

	public void setApplicationTypeID(Long applicationTypeID) {
		this.applicationTypeID = applicationTypeID;
	}

	public Long getEducationalProviderID() {
		return educationalProviderID;
	}

	public void setEducationalProviderID(Long educationalProviderID) {
		this.educationalProviderID = educationalProviderID;
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

	public Long getStudentLoanServicerID() {
		return studentLoanServicerID;
	}

	public void setStudentLoanServicerID(Long studentLoanServicerID) {
		this.studentLoanServicerID = studentLoanServicerID;
	}

	public Long getDegreeObjectiveID() {
		return degreeObjectiveID;
	}

	public void setDegreeObjectiveID(Long degreeObjectiveID) {
		this.degreeObjectiveID = degreeObjectiveID;
	}

	public Long getCourseOfStudyID() {
		return courseOfStudyID;
	}

	public void setCourseOfStudyID(Long courseOfStudyID) {
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
}
