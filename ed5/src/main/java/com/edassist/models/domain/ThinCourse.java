package com.edassist.models.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "ApplicationCourses")
public class ThinCourse {

	@Id
	@Column(name = "ApplicationCoursesID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ApplicationID")
	private Long applicationID;

	@Column(name = "CourseNumber")
	private String number;

	@Column(name = "CourseName")
	private String name;

	@Column(name = "TuitionAmount")
	private BigDecimal tuitionAmount;

	@Column(name = "RefundAmount")
	private BigDecimal refundAmount;

	@OneToMany(mappedBy = "relatedCourseID", fetch = FetchType.EAGER)
	private List<ThinExpense> relatedExpenses;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getTuitionAmount() {
		return tuitionAmount;
	}

	public void setTuitionAmount(BigDecimal tuitionAmount) {
		this.tuitionAmount = tuitionAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public List<ThinExpense> getRelatedExpenses() {
		return relatedExpenses;
	}

	public void setRelatedExpenses(List<ThinExpense> relatedExpenses) {
		this.relatedExpenses = relatedExpenses;
	}
}
