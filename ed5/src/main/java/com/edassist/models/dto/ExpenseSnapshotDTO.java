package com.edassist.models.dto;

import java.math.BigDecimal;

public class ExpenseSnapshotDTO {

	private BigDecimal tuitionAndFees;
	private BigDecimal fees;
	private BigDecimal discounts;;
	private Boolean isEmpty;
	private BigDecimal tuition;
	private BigDecimal tuitionAndFeesMinusDiscount;

	public BigDecimal getTuitionAndFees() {
		return tuitionAndFees;
	}

	public void setTuitionAndFees(BigDecimal tuitionAndFees) {
		this.tuitionAndFees = tuitionAndFees;
	}

	public BigDecimal getFees() {
		return fees;
	}

	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}

	public BigDecimal getDiscounts() {
		return discounts;
	}

	public void setDiscounts(BigDecimal discounts) {
		this.discounts = discounts;
	}

	public Boolean getIsEmpty() {
		return isEmpty;
	}

	public void setIsEmpty(Boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public BigDecimal getTuition() {
		return tuition;
	}

	public void setTuition(BigDecimal tuition) {
		this.tuition = tuition;
	}

	public BigDecimal getTuitionAndFeesMinusDiscount() {
		return tuitionAndFeesMinusDiscount;
	}

	public void setTuitionAndFeesMinusDiscount(BigDecimal tuitionAndFeesMinusDiscount) {
		this.tuitionAndFeesMinusDiscount = tuitionAndFeesMinusDiscount;
	}

}
