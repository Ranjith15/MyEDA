package com.edassist.models.dto;

import java.util.Date;

public class PrepayTuitionAppDTO {

	private Date courseStartDate;
	private Date courseEndDate;
	private String otherCourseOfStudy;

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
}
