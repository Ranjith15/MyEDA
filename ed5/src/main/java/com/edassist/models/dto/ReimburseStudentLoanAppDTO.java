package com.edassist.models.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ReimburseStudentLoanAppDTO {

	private Long reimburseStudentLoanAppID;

	// TODO: This is referenced as Application Type in existing ReimburseStudentLoanApp Class.
	// private long applicationID; Probably will not need this as this will come from LoanApplicationDTO

	private String loanAccountNumber;

	private BigDecimal loanRequestedPaymentAmount;

	private BigDecimal loanApprovedAmount;

	private BigDecimal loanPaidAmount;

	private BigDecimal totalStudentLoanDept;

	private BigDecimal loanBalance;

	private BigDecimal monthlyPaymentAmount;

	private String selectedStudentLoanType;

	private Date loanPaymentPeriodStartDate;

	private Date loanPaymentPeriodEndDate;

	private String graduationDate;

	private String paymentDueDate;

	private DegreeObjectivesDTO degreeObjectives;

	private CourseOfStudyDTO courseOfStudy;

	private StudentLoanPaymentRequestDTO studentLoanPaymentRequest;

	public Long getReimburseStudentLoanAppID() {
		return reimburseStudentLoanAppID;
	}

	public void setReimburseStudentLoanAppID(Long reimburseStudentLoanAppID) {
		this.reimburseStudentLoanAppID = reimburseStudentLoanAppID;
	}

	/*
	 * public long getApplicationID() { return applicationID; } public void setApplicationID(long applicationID) { this.applicationID = applicationID; }
	 */

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

	public String getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(String graduationDate) {
		this.graduationDate = graduationDate;
	}

	public String getPaymentDueDate() {
		return paymentDueDate;
	}

	public void setPaymentDueDate(String paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public DegreeObjectivesDTO getDegreeObjectives() {
		return degreeObjectives;
	}

	public void setDegreeObjectives(DegreeObjectivesDTO degreeObjectives) {
		this.degreeObjectives = degreeObjectives;
	}

	public CourseOfStudyDTO getCourseOfStudy() {
		return courseOfStudy;
	}

	public void setCourseOfStudy(CourseOfStudyDTO courseOfStudy) {
		this.courseOfStudy = courseOfStudy;
	}

	public StudentLoanPaymentRequestDTO getStudentLoanPaymentRequest() {
		return studentLoanPaymentRequest;
	}

	public void setStudentLoanPaymentRequest(StudentLoanPaymentRequestDTO studentLoanPaymentRequest) {
		this.studentLoanPaymentRequest = studentLoanPaymentRequest;
	}

}
