package com.edassist.models.domain;

import com.edassist.models.domain.ApplicationCourses.QUESTION_TYPE;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "CourseHistory")
public class CourseHistory {

	@Id
	@Column(name = "CourseHistoryId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long courseHistoryId;

	@Column(name = "ChangeId")
	private Long changeId;

	@JoinColumn(name = "ApplicationID", referencedColumnName = "ApplicationID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private Application application;

	@JoinColumn(name = "OldApplicationStatusID", referencedColumnName = "ApplicationStatusID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private ApplicationStatus oldApplicationStatus;

	@JoinColumn(name = "ApplicationStatusID", referencedColumnName = "ApplicationStatusID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private ApplicationStatus applicationStatus;

	@JoinColumn(name = "ApplicationCoursesID", referencedColumnName = "ApplicationCoursesID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private ApplicationCourses applicationCourses;

	@Column(name = "CourseNumber")
	private String courseNumber;

	@Column(name = "CourseName")
	private String courseName;

	@Column(name = "TuitionAmount")
	private BigDecimal tuitionAmount;

	@Column(name = "FeesAmount")
	private BigDecimal feesAmount;

	@Column(name = "DiscountAmount")
	private BigDecimal discountAmount;

	@JoinColumn(name = "GradeID", referencedColumnName = "GradeID")
	@ManyToOne(optional = true, cascade = CascadeType.REFRESH)
	private Grades grade;

	@Enumerated(EnumType.STRING)
	@Column(name = "Taxability")
	private QUESTION_TYPE taxability;

	@Column(name = "CreditHours")
	private Double creditHours;

	@Enumerated(EnumType.STRING)
	@Column(name = "MaintainSkillsYN")
	private QUESTION_TYPE maintainSkillsYN;

	@Enumerated(EnumType.STRING)
	@Column(name = "MeetMinimumQualsYN")
	private QUESTION_TYPE meetMinimumQualsYN;

	@Enumerated(EnumType.STRING)
	@Column(name = "NewCareerFieldYN")
	private QUESTION_TYPE newCareerFieldYN;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Basic(optional = false)
	@Column(name = "CreatedBy")
	private Long createdBy;

	@Column(name = "ModifiedBy")
	private Long modifiedBy;

	public CourseHistory() {
		super();
	}

	public CourseHistory(Application application, Long changeId, ApplicationStatus oldApplicationStatus, ApplicationStatus applicationStatus, ApplicationCourses applicationCourses,
			String courseNumber, String courseName, BigDecimal tuitionAmount, BigDecimal feesAmount, BigDecimal discountAmount, Grades grade, QUESTION_TYPE taxability, Double creditHours,
			QUESTION_TYPE maintainSkillsYN, QUESTION_TYPE meetMinimumQualsYN, QUESTION_TYPE newCareerFieldYN, Date dateCreated, Date modifiedDate, Long createdBy, Long modifiedBy) {
		super();
		this.application = application;
		this.changeId = changeId;
		this.oldApplicationStatus = oldApplicationStatus;
		this.applicationStatus = applicationStatus;
		this.applicationCourses = applicationCourses;
		this.courseNumber = courseNumber;
		this.courseName = courseName;
		this.tuitionAmount = tuitionAmount;
		this.feesAmount = feesAmount;
		this.discountAmount = discountAmount;
		this.grade = grade;
		this.taxability = taxability;
		this.creditHours = creditHours;
		this.maintainSkillsYN = maintainSkillsYN;
		this.meetMinimumQualsYN = meetMinimumQualsYN;
		this.newCareerFieldYN = newCareerFieldYN;
		this.dateCreated = dateCreated;
		this.modifiedDate = modifiedDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
	}

	public Long getCourseHistoryId() {
		return courseHistoryId;
	}

	public void setCourseHistoryId(Long courseHistoryId) {
		this.courseHistoryId = courseHistoryId;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public ApplicationCourses getApplicationCourses() {
		return applicationCourses;
	}

	public void setApplicationCourses(ApplicationCourses applicationCourses) {
		this.applicationCourses = applicationCourses;
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

	public Double getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(Double creditHours) {
		this.creditHours = creditHours;
	}

	public QUESTION_TYPE getMaintainSkillsYN() {
		return maintainSkillsYN;
	}

	public void setMaintainSkillsYN(QUESTION_TYPE maintainSkillsYN) {
		this.maintainSkillsYN = maintainSkillsYN;
	}

	public QUESTION_TYPE getMeetMinimumQualsYN() {
		return meetMinimumQualsYN;
	}

	public void setMeetMinimumQualsYN(QUESTION_TYPE meetMinimumQualsYN) {
		this.meetMinimumQualsYN = meetMinimumQualsYN;
	}

	public QUESTION_TYPE getNewCareerFieldYN() {
		return newCareerFieldYN;
	}

	public void setNewCareerFieldYN(QUESTION_TYPE newCareerFieldYN) {
		this.newCareerFieldYN = newCareerFieldYN;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Long getChangeId() {
		return changeId;
	}

	public void setChangeId(Long changeId) {
		this.changeId = changeId;
	}

}
