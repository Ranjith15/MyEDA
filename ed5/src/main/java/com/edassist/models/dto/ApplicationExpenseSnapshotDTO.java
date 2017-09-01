package com.edassist.models.dto;

public class ApplicationExpenseSnapshotDTO {

	private ExpenseSnapshotDTO paidExpense;
	private ExpenseSnapshotDTO requestedExpense;
	private ExpenseSnapshotDTO approvedExpense;

	public ExpenseSnapshotDTO getPaidExpense() {
		return paidExpense;
	}

	public void setPaidExpense(ExpenseSnapshotDTO paidExpense) {
		this.paidExpense = paidExpense;
	}

	public ExpenseSnapshotDTO getRequestedExpense() {
		return requestedExpense;
	}

	public void setRequestedExpense(ExpenseSnapshotDTO requestedExpense) {
		this.requestedExpense = requestedExpense;
	}

	public ExpenseSnapshotDTO getApprovedExpense() {
		return approvedExpense;
	}

	public void setApprovedExpense(ExpenseSnapshotDTO approvedExpense) {
		this.approvedExpense = approvedExpense;
	}

}
