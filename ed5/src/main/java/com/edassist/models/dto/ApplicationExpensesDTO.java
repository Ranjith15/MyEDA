package com.edassist.models.dto;

import java.math.BigDecimal;

public class ApplicationExpensesDTO {

	private Long applicationExpensesID;
	private ApplicationCoursesDTO relatedCourseID;
	private ExpenseTypeDTO expenseType;
	private BigDecimal feesAmount;
	private Integer numberOfBooks;
	private String maintainSkillsYN;
	private String meetMinimumQualsYN;
	private String newCareerFieldYN;
	private String taxExempt;

	public ApplicationCoursesDTO getRelatedCourseID() {
		return relatedCourseID;
	}

	public void setRelatedCourseID(ApplicationCoursesDTO relatedCourseID) {
		this.relatedCourseID = relatedCourseID;
	}

	public ExpenseTypeDTO getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseTypeDTO expenseType) {
		this.expenseType = expenseType;
	}

	public BigDecimal getFeesAmount() {
		return feesAmount;
	}

	public void setFeesAmount(BigDecimal feesAmount) {
		this.feesAmount = feesAmount;
	}

	public Integer getNumberOfBooks() {
		return numberOfBooks;
	}

	public void setNumberOfBooks(Integer numberOfBooks) {
		this.numberOfBooks = numberOfBooks;
	}

	public String getMaintainSkillsYN() {
		return maintainSkillsYN;
	}

	public void setMaintainSkillsYN(String maintainSkillsYN) {
		this.maintainSkillsYN = maintainSkillsYN;
	}

	public String getMeetMinimumQualsYN() {
		return meetMinimumQualsYN;
	}

	public void setMeetMinimumQualsYN(String meetMinimumQualsYN) {
		this.meetMinimumQualsYN = meetMinimumQualsYN;
	}

	public String getNewCareerFieldYN() {
		return newCareerFieldYN;
	}

	public void setNewCareerFieldYN(String newCareerFieldYN) {
		this.newCareerFieldYN = newCareerFieldYN;
	}

	public void setApplicationExpensesID(Long applicationExpensesID) {
		this.applicationExpensesID = applicationExpensesID;
	}

	public Long getApplicationExpensesID() {
		return applicationExpensesID;
	}

	public void setTaxExempt(String taxExempt) {
		this.taxExempt = taxExempt;
	}

	public String getTaxExempt() {
		return taxExempt;
	}

}
