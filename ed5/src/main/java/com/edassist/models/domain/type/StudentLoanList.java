package com.edassist.models.domain.type;

import java.util.ArrayList;

import com.edassist.models.domain.StudentLoan;

public class StudentLoanList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 243157257639802919L;

	private ArrayList<StudentLoan> studentLoanList;

	public void setStudentLoanList(ArrayList<StudentLoan> studentLoanList) {
		this.studentLoanList = studentLoanList;
	}

	public ArrayList<StudentLoan> getStudentLoanList() {
		return studentLoanList;
	}

}
