package com.edassist.models.domain;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Table(name = "ApplicationCourses")
public class ThinExpense {

	@Id
	@Column(name = "ApplicationCoursesID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "RelatedCourseID")
	private Long relatedCourseID;

	@Column(name = "ApplicationID")
	private Long applicationID;

	@Column(name = "CourseName")
	private String name;

	@JoinColumn(name = "ExpenseTypeID", referencedColumnName = "ExpenseTypeID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private ExpenseType expenseType;

	@Column(name = "NumberOfBooks")
	private Integer numberOfBooks;

	@Column(name = "FeesAmount")
	private BigDecimal feesAmount;

	@Column(name = "RefundAmount")
	private BigDecimal refundAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRelatedCourseID() {
		return relatedCourseID;
	}

	public void setRelatedCourseID(Long relatedCourseID) {
		this.relatedCourseID = relatedCourseID;
	}

	public Long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	public Integer getNumberOfBooks() {
		return numberOfBooks;
	}

	public void setNumberOfBooks(Integer numberOfBooks) {
		this.numberOfBooks = numberOfBooks;
	}

	public BigDecimal getFeesAmount() {
		return feesAmount;
	}

	public void setFeesAmount(BigDecimal feesAmount) {
		this.feesAmount = feesAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
}
