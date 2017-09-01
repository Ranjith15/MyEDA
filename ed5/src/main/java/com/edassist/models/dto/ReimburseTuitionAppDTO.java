package com.edassist.models.dto;

import java.util.Date;

public class ReimburseTuitionAppDTO {

	private Date courseStartDate;
	private Date courseEndDate;
	private String otherCourseOfStudy;
	private Date certificationStartDate;
	private Date certificationEndDate;

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

	public String getOtherCourseOfStudy() {
		return otherCourseOfStudy;
	}

	public void setOtherCourseOfStudy(String otherCourseOfStudy) {
		this.otherCourseOfStudy = otherCourseOfStudy;
	}

	public Date getCertificationStartDate() {
		return certificationStartDate;
	}

	public void setCertificationStartDate(Date certificationStartDate) {
		this.certificationStartDate = certificationStartDate;
	}

	public Date getCertificationEndDate() {
		return certificationEndDate;
	}

	public void setCertificationEndDate(Date certificationEndDate) {
		this.certificationEndDate = certificationEndDate;
	}
}
