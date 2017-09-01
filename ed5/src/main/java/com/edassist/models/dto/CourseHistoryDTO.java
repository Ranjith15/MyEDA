package com.edassist.models.dto;

import java.math.BigDecimal;

public class CourseHistoryDTO {
	private Long courseHistoryId;
	private Long changeId;
	private String courseNumber;
	private String courseName;
	private BigDecimal tuitionAmount;
	private BigDecimal feesAmount;
	private BigDecimal discountAmount;

	public Long getCourseHistoryId() {
		return courseHistoryId;
	}

	public void setCourseHistoryId(Long courseHistoryId) {
		this.courseHistoryId = courseHistoryId;
	}

	public Long getChangeId() {
		return changeId;
	}

	public void setChangeId(Long changeId) {
		this.changeId = changeId;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public BigDecimal getTuitionAmount() {
		return tuitionAmount;
	}

	public void setTuitionAmount(BigDecimal tuitionAmount) {
		this.tuitionAmount = tuitionAmount;
	}

	public BigDecimal getFeesAmount() {
		return feesAmount;
	}

	public void setFeesAmount(BigDecimal feesAmount) {
		this.feesAmount = feesAmount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

}
