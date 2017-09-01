package com.edassist.models.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "ApplicationCourses")
public class Expense {

	@Id
	@Column(name = "ApplicationCoursesID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "RelatedCourseID")
	private Long relatedCourseID;

	@ManyToOne
	@JoinColumn(name = "ApplicationID", referencedColumnName = "ApplicationID")
	private App application;

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

	@Column(name = "DiscountAmount")
	private BigDecimal discountAmount;

	@Type(type="yes_no")
	@Column(name = "MaintainSkillsYN")
	private Boolean maintainSkills;

	@Type(type="yes_no")
	@Column(name = "Taxability")
	private Boolean taxExempt;

	@Type(type="yes_no")
	@Column(name = "MeetMinimumQualsYN")
	private Boolean meetMinimumQuals;

	@Type(type="yes_no")
	@Column(name = "NewCareerFieldYN")
	private Boolean newCareerField;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "applicationCourses")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CourseHistory> historyList;

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

	public App getApplication() {
		return application;
	}

	public void setApplication(App application) {
		this.application = application;
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

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Boolean getMaintainSkills() {
		return maintainSkills;
	}

	public void setMaintainSkills(Boolean maintainSkills) {
		this.maintainSkills = maintainSkills;
	}

	public Boolean getTaxExempt() {
		return taxExempt;
	}

	public void setTaxExempt(Boolean taxExempt) {
		this.taxExempt = taxExempt;
	}

	public Boolean getMeetMinimumQuals() {
		return meetMinimumQuals;
	}

	public void setMeetMinimumQuals(Boolean meetMinimumQuals) {
		this.meetMinimumQuals = meetMinimumQuals;
	}

	public Boolean getNewCareerField() {
		return newCareerField;
	}

	public void setNewCareerField(Boolean newCareerField) {
		this.newCareerField = newCareerField;
	}

	public List<CourseHistory> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List<CourseHistory> historyList) {
		this.historyList = historyList;
	}
}
