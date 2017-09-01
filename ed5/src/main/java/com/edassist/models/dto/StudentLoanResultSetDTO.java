package com.edassist.models.dto;

import java.util.List;

public class StudentLoanResultSetDTO {
	private List<StudentLoanDTO> studentLoans;

	public List<StudentLoanDTO> getStudentLoans() {
		return studentLoans;
	}

	public void setStudentLoans(List<StudentLoanDTO> studentLoans) {
		this.studentLoans = studentLoans;
	}

	public PaginationDTO getPagination() {
		return pagination;
	}

	public void setPagination(PaginationDTO pagination) {
		this.pagination = pagination;
	}

	private PaginationDTO pagination;

}
