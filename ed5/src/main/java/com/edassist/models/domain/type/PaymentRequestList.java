package com.edassist.models.domain.type;

import java.util.ArrayList;

import com.edassist.models.domain.StudentLoanPaymentRequest;

public class PaymentRequestList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 243157257639802919L;

	private ArrayList<StudentLoanPaymentRequest> studentLoanList;

	public void setPaymentRequestList(ArrayList<StudentLoanPaymentRequest> studentLoanList) {
		this.studentLoanList = studentLoanList;
	}

	public ArrayList<StudentLoanPaymentRequest> getPaymentRequestList() {
		return studentLoanList;
	}

}
