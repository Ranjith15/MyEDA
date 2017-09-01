package com.edassist.models.domain;

import javax.persistence.*;

@Entity
@Table(name = "ProgramExpenseType")
public class ProgramExpenseType {

	@Id
	@Column(name = "ProgramExpenseTypeID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long programExpenseTypeID;

	@Column(name = "ExpenseType")
	private String expenseType;

	@JoinColumn(name = "ExpenseTypeID", referencedColumnName = "ExpenseTypeID")
	@ManyToOne
	private ExpenseType expenseTypeID;

	@JoinColumn(name = "ProgramID", referencedColumnName = "ProgramID")
	@ManyToOne
	private Program programID;

	public ProgramExpenseType() {
	}

	public Long getProgramExpenseTypeID() {
		return programExpenseTypeID;
	}

	public void setProgramExpenseTypeID(Long programExpenseTypeID) {
		this.programExpenseTypeID = programExpenseTypeID;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public ExpenseType getExpenseTypeID() {
		return expenseTypeID;
	}

	public void setExpenseTypeID(ExpenseType expenseTypeID) {
		this.expenseTypeID = expenseTypeID;
	}

	public Program getProgramID() {
		return programID;
	}

	public void setProgramID(Program programID) {
		this.programID = programID;
	}

}
