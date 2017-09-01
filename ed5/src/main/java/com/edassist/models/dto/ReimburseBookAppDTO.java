package com.edassist.models.dto;

import java.util.Date;

public class ReimburseBookAppDTO {

	private String providerName;
	private String providerCode;
	private Date courseStartDate;
	private Date courseEndDate;
	private CourseOfStudyDTO courseOfStudyID;
	private DegreeObjectivesDTO degreeObjectiveID;

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

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

	public CourseOfStudyDTO getCourseOfStudyID() {
		return courseOfStudyID;
	}

	public void setCourseOfStudyID(CourseOfStudyDTO courseOfStudyID) {
		this.courseOfStudyID = courseOfStudyID;
	}

	public DegreeObjectivesDTO getDegreeObjectiveID() {
		return degreeObjectiveID;
	}

	public void setDegreeObjectiveID(DegreeObjectivesDTO degreeObjectiveID) {
		this.degreeObjectiveID = degreeObjectiveID;
	}
}
