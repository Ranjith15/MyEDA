package com.edassist.models.dto;

import java.math.BigDecimal;

public class ExpenseDTO {
	private Long id;
	private Long relatedCourseID;
	private Long applicationID;
	private String name;
	private ExpenseTypeDTO expenseType;
	private Integer numberOfBooks;
	private BigDecimal feesAmount;
	private BigDecimal approvedAmount;
	private BigDecimal paidAmount;
	private BigDecimal discountAmount;
	private Boolean taxExempt;
	private Boolean maintainSkills;
	private Boolean meetMinimumQuals;
	private Boolean newCareerField;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRelatedCourseID() {
		return relatedCourseID;
	}

	public void setRelatedCourseID(Long relatedCourseID) {
		this.relatedCourseID = relatedCourseID;
	}

	public Long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ExpenseTypeDTO getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseTypeDTO expenseType) {
		this.expenseType = expenseType;
	}

	public Integer getNumberOfBooks() {
		return numberOfBooks;
	}

	public void setNumberOfBooks(Integer numberOfBooks) {
		this.numberOfBooks = numberOfBooks;
	}

	public BigDecimal getFeesAmount() {
		return feesAmount;
	}

	public void setFeesAmount(BigDecimal feesAmount) {
		this.feesAmount = feesAmount;
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
}
