package com.edassist.models.dto;

import java.math.BigDecimal;

public class ApplicationCoursesDTO {

	private Long applicationCoursesID;
	private String courseNumber;
	private String courseName;
	private BigDecimal tuitionAmount;
	private Double creditHours;
	private CourseMethodDTO courseMethod;
	private String maintainSkillsYN;
	private String meetMinimumQualsYN;
	private String newCareerFieldYN;
	private GradesDTO grades;
	private String courseDescriptionURL;
	private String courseSchedule;
	private BigDecimal refundAmount;
	private String gradeCompliance;
	private String taxExempt;
	private Boolean outsideWorkHours;

	public void setApplicationCoursesID(Long applicationCoursesID) {
		this.applicationCoursesID = applicationCoursesID;
	}

	public Long getApplicationCoursesID() {
		return applicationCoursesID;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setTuitionAmount(BigDecimal tuitionAmount) {
		this.tuitionAmount = tuitionAmount;
	}

	public BigDecimal getTuitionAmount() {
		return tuitionAmount;
	}

	public void setCreditHours(Double creditHours) {
		this.creditHours = creditHours;
	}

	public Double getCreditHours() {
		return creditHours;
	}

	public String getCourseDescriptionURL() {
		return courseDescriptionURL;
	}

	public void setCourseDescriptionURL(String courseDescriptionURL) {
		this.courseDescriptionURL = courseDescriptionURL;
	}

	public String getCourseSchedule() {
		return courseSchedule;
	}

	public void setCourseSchedule(String courseSchedule) {
		this.courseSchedule = courseSchedule;
	}

	public void setMaintainSkillsYN(String maintainSkillsYN) {
		this.maintainSkillsYN = maintainSkillsYN;
	}

	public String getMaintainSkillsYN() {
		return maintainSkillsYN;
	}

	public String getMeetMinimumQualsYN() {
		return meetMinimumQualsYN;
	}

	public void setMeetMinimumQualsYN(String meetMinimumQualsYN) {
		this.meetMinimumQualsYN = meetMinimumQualsYN;
	}

	public String getNewCareerFieldYN() {
		return newCareerFieldYN;
	}

	public void setNewCareerFieldYN(String newCareerFieldYN) {
		this.newCareerFieldYN = newCareerFieldYN;
	}

	public GradesDTO getGrades() {
		return grades;
	}

	public void setGrades(GradesDTO grades) {
		this.grades = grades;
	}

	public CourseMethodDTO getCourseMethod() {
		return courseMethod;
	}

	public void setCourseMethod(CourseMethodDTO courseMethod) {
		this.courseMethod = courseMethod;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setGradeCompliance(String gradeCompliance) {
		this.gradeCompliance = gradeCompliance;
	}

	public String getGradeCompliance() {
		return gradeCompliance;
	}

	public void setTaxExempt(String taxExempt) {
		this.taxExempt = taxExempt;
	}

	public String getTaxExempt() {
		return taxExempt;
	}

	public Boolean getOutsideWorkHours() {
		return outsideWorkHours;
	}

	public void setOutsideWorkHours(Boolean outsideWorkHours) {
		this.outsideWorkHours = outsideWorkHours;
	}
}
