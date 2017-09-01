package com.edassist.models.domain;

import javax.persistence.*;

@Entity
@Table(name = "StudentLoanServicerType")
public class StudentLoanServicerType {

	@Id
	@Column(name = "studentLoanServicerTypeID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentLoanServicerTypeID;

	@Column(name = "Description")
	private String description;

	public Long getStudentLoanServicerTypeID() {
		return studentLoanServicerTypeID;
	}

	public void setStudentLoanServicerTypeID(Long studentLoanServicerTypeID) {
		this.studentLoanServicerTypeID = studentLoanServicerTypeID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
