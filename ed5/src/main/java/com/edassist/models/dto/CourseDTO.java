package com.edassist.models.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CourseDTO {

	private Long id;
	private String number;
	private String name;
	private BigDecimal tuitionAmount;
	private BigDecimal requestedAmount;
	private BigDecimal approvedAmount;
	private BigDecimal paidAmount;
	private List<ExpenseDTO> relatedExpenses;
	private Double creditHours;
	private Boolean maintainSkills;
	private Boolean meetMinimumQuals;
	private Boolean newCareerField;
	private GradesDTO grades;
	private Boolean gradeVerified;
	private Date gradeVerifiedDate;
	private String courseDescriptionURL;
	private String courseSchedule;
	private BigDecimal refundAmount;
	private BigDecimal discountAmount;
	private Boolean taxExempt;
	private Boolean outsideWorkHours;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getTuitionAmount() {
		return tuitionAmount;
	}

	public void setTuitionAmount(BigDecimal tuitionAmount) {
		this.tuitionAmount = tuitionAmount;
	}

	public BigDecimal getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(BigDecimal requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public BigDecimal getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(BigDecimal approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public List<ExpenseDTO> getRelatedExpenses() {
		return relatedExpenses;
	}

	public void setRelatedExpenses(List<ExpenseDTO> relatedExpenses) {
		this.relatedExpenses = relatedExpenses;
	}

	public Double getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(Double creditHours) {
		this.creditHours = creditHours;
	}

	public Boolean getMaintainSkills() {
		return maintainSkills;
	}

	public void setMaintainSkills(Boolean maintainSkills) {
		this.maintainSkills = maintainSkills;
	}

	public Boolean getMeetMinimumQuals() {
		return meetMinimumQuals;
	}

	public void setMeetMinimumQuals(Boolean meetMinimumQuals) {
		this.meetMinimumQuals = meetMinimumQuals;
	}

	public Boolean getNewCareerField() {
		return newCareerField;
	}

	public void setNewCareerField(Boolean newCareerField) {
		this.newCareerField = newCareerField;
	}

	public GradesDTO getGrades() {
		return grades;
	}

	public void setGrades(GradesDTO grades) {
		this.grades = grades;
	}

	public Boolean getGradeVerified() {
		return gradeVerified;
	}

	public void setGradeVerified(Boolean gradeVerified) {
		this.gradeVerified = gradeVerified;
	}

	public Date getGradeVerifiedDate() {
		return gradeVerifiedDate;
	}

	public void setGradeVerifiedDate(Date gradeVerifiedDate) {
		this.gradeVerifiedDate = gradeVerifiedDate;
	}

	public String getCourseDescriptionURL() {
		return courseDescriptionURL;
	}

	public void setCourseDescriptionURL(String courseDescriptionURL) {
		this.courseDescriptionURL = courseDescriptionURL;
	}

	public String getCourseSchedule() {
		return courseSchedule;
	}

	public void setCourseSchedule(String courseSchedule) {
		this.courseSchedule = courseSchedule;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Boolean getTaxExempt() {
		return taxExempt;
	}

	public void setTaxExempt(Boolean taxExempt) {
		this.taxExempt = taxExempt;
	}

	public Boolean getOutsideWorkHours() {
		return outsideWorkHours;
	}

	public void setOutsideWorkHours(Boolean outsideWorkHours) {
		this.outsideWorkHours = outsideWorkHours;
	}
}
