package com.edassist.models.dto;

import java.util.Date;

public class ApplicationSessionInfoDTO {

	private Date courseStartDate;
	private Date courseEndDate;
	private boolean degreeCompleteYN;
	private Date expGraduationDt;

	public Date getCourseStartDate() {
		return courseStartDate;
	}

	public void setCourseStartDate(Date courseStartDate) {
		this.courseStartDate = courseStartDate;
	}

	public Date getCourseEndDate() {
		return courseEndDate;
	}

	public void setCourseEndDate(Date courseEndDate) {
		this.courseEndDate = courseEndDate;
	}

	public boolean isDegreeCompleteYN() {
		return degreeCompleteYN;
	}

	public void setDegreeCompleteYN(boolean degreeCompleteYN) {
		this.degreeCompleteYN = degreeCompleteYN;
	}

	public Date getExpGraduationDt() {
		return expGraduationDt;
	}

	public void setExpGraduationDt(Date expGraduationDt) {
		this.expGraduationDt = expGraduationDt;
	}

}
