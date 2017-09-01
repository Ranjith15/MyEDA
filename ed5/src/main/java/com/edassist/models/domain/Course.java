package com.edassist.models.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ApplicationCourses")
public class Course {

	@Id
	@Basic(optional = false)
	@Column(name = "ApplicationCoursesID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ApplicationID", referencedColumnName = "ApplicationID")
	private App application;

	@Column(name = "CourseNumber")
	private String number;

	@Column(name = "CourseName")
	private String name;

	@Column(name = "TuitionAmount")
	private BigDecimal tuitionAmount;

	@Column(name = "FeesAmount")
	private BigDecimal feesAmount;

	@Column(name = "RefundAmount")
	private BigDecimal refundAmount;

	@Column(name = "DiscountAmount")
	private BigDecimal discountAmount;

	@OneToMany(mappedBy = "relatedCourseID", fetch = FetchType.EAGER)
	private List<Expense> relatedExpenses;

	@Column(name = "CreditHours")
	private Double creditHours;

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

	@JoinColumn(name = "GradeID", referencedColumnName = "GradeID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private Grades grades;

	@Column(name = "GradeVerified")
	private boolean gradeVerified;

	@Column(name = "GradeVerifiedDate")
	private Date gradeVerifiedDate;

	@Column(name = "courseDescriptionURL")
	private String courseDescriptionURL;

	@Column(name = "courseSchedule")
	private String courseSchedule;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "applicationCourses")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CourseHistory> historyList;

	@Column(name = "OutsideWorkHours")
	private Boolean outsideWorkHours;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public App getApplication() {
		return application;
	}

	public void setApplication(App application) {
		this.application = application;
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

	public List<Expense> getRelatedExpenses() {
		return relatedExpenses;
	}

	public void setRelatedExpenses(List<Expense> relatedExpenses) {
		this.relatedExpenses = relatedExpenses;
	}

	public Double getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(Double creditHours) {
		this.creditHours = creditHours;
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

	public Grades getGrades() {
		return grades;
	}

	public void setGrades(Grades grades) {
		this.grades = grades;
	}

	public boolean isGradeVerified() {
		return gradeVerified;
	}

	public void setGradeVerified(boolean gradeVerified) {
		this.gradeVerified = gradeVerified;
	}

	public Date getGradeVerifiedDate() {
		return gradeVerifiedDate;
	}

	public void setGradeVerifiedDate(Date gradeVerifiedDate) {
		this.gradeVerifiedDate = gradeVerifiedDate;
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

	public List<CourseHistory> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List<CourseHistory> historyList) {
		this.historyList = historyList;
	}

	public Boolean getOutsideWorkHours() {
		return outsideWorkHours;
	}

	public void setOutsideWorkHours(Boolean outsideWorkHours) {
		this.outsideWorkHours = outsideWorkHours;
	}
}
