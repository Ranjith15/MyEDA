package com.edassist.models.dto;

public class ExpenseTypeDTO {

	private Long expenseTypeID;
	private String code;
	private String description;
	private String expenseRelation;

	public void setExpenseTypeID(Long expenseTypeID) {
		this.expenseTypeID = expenseTypeID;
	}

	public Long getExpenseTypeID() {
		return expenseTypeID;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getExpenseRelation() {
		return expenseRelation;
	}

	public void setExpenseRelation(String expenseRelation) {
		this.expenseRelation = expenseRelation;
	}

}
