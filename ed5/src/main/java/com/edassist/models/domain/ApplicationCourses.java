package com.edassist.models.domain;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Entity
@Table(name = "ApplicationCourses")
public class ApplicationCourses {

	@Id
	@Column(name = "ApplicationCoursesID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long applicationCoursesID;

	@Column(name = "CourseNumber")
	private String courseNumber;

	@Column(name = "CourseName")
	private String courseName;

	@Column(name = "TuitionAmount")
	private BigDecimal tuitionAmount;

	@Column(name = "FeesAmount")
	private BigDecimal feesAmount;

	@Column(name = "CreditHours")
	private Double creditHours;

	@Column(name = "NumberOfBooks")
	private Integer numberOfBooks;

	@Basic()
	@Column(name = "Taxability")
	private String taxability;

	@Basic()
	@Column(name = "MaintainSkillsYN")
	private String maintainSkillsYN;

	@Basic()
	@Column(name = "MeetMinimumQualsYN")
	private String meetMinimumQualsYN;

	@Basic()
	@Column(name = "NewCareerFieldYN")
	private String newCareerFieldYN;

	@Column(name = "RefundAmount")
	private BigDecimal refundAmount;

	@Column(name = "DiscountAmount")
	private BigDecimal discountAmount;

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

	@Column(name = "RequestedTuition")
	private BigDecimal requestedTuition;

	@Column(name = "RequestedFees")
	private BigDecimal requestedFees;

	@JoinColumn(name = "ApplicationID", referencedColumnName = "ApplicationID")
	@ManyToOne(optional = false)
	private Application applicationID;

	@JoinColumn(name = "GradeID", referencedColumnName = "GradeID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private Grades gradeID;

	@JoinColumn(name = "ExpenseTypeID", referencedColumnName = "ExpenseTypeID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private ExpenseType expenseType;

	@JoinColumn(name = "CourseMethodId", referencedColumnName = "CourseMethodId")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private CourseMethod courseMethod;

	@Column(name = "AmountReportedPaid")
	private BigDecimal amountReportedPaid;

	@Column(name = "AmountPaid")
	private BigDecimal amountPaid;

	@JoinColumn(name = "RelatedCourseID")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private ApplicationCourses relatedCourseID;

	@Column(name = "courseSchedule")
	private String courseSchedule;

	@Column(name = "courseDescriptionURL")
	private String courseDescriptionURL;

	@Column(name = "PercentagePayOut")
	private BigDecimal percentagePayOut;

	@Column(name = "OutsideWorkHours")
	private Boolean outsideWorkHours;

	@Transient
	private String GradeCompliance;

	@Transient
	private boolean expense;

	@Transient
	private boolean bookExpense;


	public ApplicationCourses() {
		super();
	}

	public ApplicationCourses(Long applicationCoursesID) {
		super();
		this.applicationCoursesID = applicationCoursesID;

	}

	public Long getApplicationCoursesID() {
		return applicationCoursesID;
	}

	public void setApplicationCoursesID(Long applicationCoursesID) {
		this.applicationCoursesID = applicationCoursesID;
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

	public Double getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(Double creditHours) {
		this.creditHours = creditHours;
	}

	public Integer getNumberOfBooks() {
		return numberOfBooks;
	}

	public void setNumberOfBooks(Integer numberOfBooks) {
		this.numberOfBooks = numberOfBooks;
	}

	public String getTaxability_Str() {
		return taxability;
	}

	public void setTaxability_Str(String taxability) {
		this.taxability = taxability;
	}

	public String getMaintainSkillsYN_Str() {
		return maintainSkillsYN;
	}

	public void setMaintainSkillsYN_Str(String maintainSkillsYN) {
		this.maintainSkillsYN = maintainSkillsYN;
	}

	public String getMeetMinimumQualsYN_Str() {
		return meetMinimumQualsYN;
	}

	public void setMeetMinimumQualsYN_Str(String meetMinimumQualsYN) {
		this.meetMinimumQualsYN = meetMinimumQualsYN;
	}

	public String getNewCareerFieldYN_Str() {
		return newCareerFieldYN;
	}

	public void setNewCareerFieldYN_Str(String newCareerFieldYN) {
		this.newCareerFieldYN = newCareerFieldYN;
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
		if (discountAmount != null) {
			this.discountAmount = discountAmount.setScale(2, RoundingMode.FLOOR);
		} else {
			this.discountAmount = discountAmount;
		}
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

	public BigDecimal getRequestedTuition() {
		return requestedTuition;
	}

	public void setRequestedTuition(BigDecimal requestedTuition) {
		this.requestedTuition = requestedTuition;
	}

	public BigDecimal getRequestedFees() {
		return requestedFees;
	}

	public void setRequestedFees(BigDecimal requestedFees) {
		this.requestedFees = requestedFees;
	}

	public Application getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Application applicationID) {
		this.applicationID = applicationID;
	}

	public Grades getGradeID() {
		return gradeID;
	}

	public void setGradeID(Grades gradeID) {
		this.gradeID = gradeID;
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	public CourseMethod getCourseMethod() {
		return courseMethod;
	}

	public void setCourseMethod(CourseMethod courseMethod) {
		this.courseMethod = courseMethod;
	}

	public BigDecimal getAmountReportedPaid() {
		return amountReportedPaid;
	}

	public void setAmountReportedPaid(BigDecimal amountReportedPaid) {
		this.amountReportedPaid = amountReportedPaid;
	}

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	public ApplicationCourses getRelatedCourseID() {
		return relatedCourseID;
	}

	public void setRelatedCourseID(ApplicationCourses relatedCourseID) {
		this.relatedCourseID = relatedCourseID;
	}

	public String getCourseSchedule() {
		return courseSchedule;
	}

	public void setCourseSchedule(String courseSchedule) {
		this.courseSchedule = courseSchedule;
	}

	public String getCourseDescriptionURL() {
		return courseDescriptionURL;
	}

	public void setCourseDescriptionURL(String courseDescriptionURL) {
		this.courseDescriptionURL = courseDescriptionURL;
	}

	public BigDecimal getPercentagePayOut() {
		return percentagePayOut;
	}

	public void setPercentagePayOut(BigDecimal percentagePayOut) {
		this.percentagePayOut = percentagePayOut;
	}

	public Boolean getOutsideWorkHours() {
		return outsideWorkHours;
	}

	public void setOutsideWorkHours(Boolean outsideWorkHours) {
		this.outsideWorkHours = outsideWorkHours;
	}

	public boolean isBookExpense() {
		return (this.isExpense() && this.expenseType.isBookExpense());
	}

	public void setBookExpense(boolean bookExpense) {
		this.bookExpense = bookExpense;
	}

	public boolean isExpense() {
		return (this.expenseType != null);
	}

	public void setExpense(boolean expense) {
		this.expense = expense;
	}

	public String getGradeCompliance() {
		return GradeCompliance;
	}

	public void setGradeCompliance(String gradeCompliance) {
		GradeCompliance = gradeCompliance;
	}

	public QUESTION_TYPE getMaintainSkillsYN() {
		if (this.getMaintainSkillsYN_Str() != null && this.getMaintainSkillsYN_Str().equals("Y")) {
			return QUESTION_TYPE.Y;
		} else {
			if (this.getMaintainSkillsYN_Str() != null && this.getMaintainSkillsYN_Str().equals("N")) {
				return QUESTION_TYPE.N;
			} else {
				if (this.getTaxability().equals(QUESTION_TYPE.Y)) {
					return QUESTION_TYPE.Y;
				} else {
					if (this.getTaxability().equals(QUESTION_TYPE.N)) {
						return QUESTION_TYPE.N;
					} else {
						return QUESTION_TYPE.N;
					}
				}
			}
		}
	}

	public void setMaintainSkillsYN(QUESTION_TYPE maintainSkillsYN) {
		if (maintainSkillsYN == null) {
			this.setMaintainSkillsYN_Str(null);
		} else {
			this.setMaintainSkillsYN_Str(maintainSkillsYN.equals(QUESTION_TYPE.Y) ? "Y" : "N");
		}
	}

	public QUESTION_TYPE getMeetMinimumQualsYN() {
		if (this.getMeetMinimumQualsYN_Str() != null && this.getMeetMinimumQualsYN_Str().equals("Y")) {
			return QUESTION_TYPE.Y;
		} else {
			if (this.getMeetMinimumQualsYN_Str() != null && this.getMeetMinimumQualsYN_Str().equals("N")) {
				return QUESTION_TYPE.N;
			} else {
				if (this.getTaxability().equals(QUESTION_TYPE.Y)) {
					return QUESTION_TYPE.N;
				} else {
					if (this.getTaxability().equals(QUESTION_TYPE.N)) {
						return QUESTION_TYPE.N;
					} else {
						return QUESTION_TYPE.N;
					}
				}
			}
		}
	}
	public void setMeetMinimumQualsYN(QUESTION_TYPE meetMinimumQualsYN) {
		if (meetMinimumQualsYN == null) {
			this.setMeetMinimumQualsYN_Str(null);
		} else {
			this.setMeetMinimumQualsYN_Str(meetMinimumQualsYN.equals(QUESTION_TYPE.Y) ? "Y" : "N");
		}
	}

	public QUESTION_TYPE getNewCareerFieldYN() {
		if (this.getNewCareerFieldYN_Str() != null && this.getNewCareerFieldYN_Str().equals("Y")) {
			return QUESTION_TYPE.Y;
		} else {
			if (this.getNewCareerFieldYN_Str() != null && this.getNewCareerFieldYN_Str().equals("N")) {
				return QUESTION_TYPE.N;
			} else {
				if (this.getTaxability().equals(QUESTION_TYPE.Y)) {
					return QUESTION_TYPE.N;
				} else {
					if (this.getTaxability().equals(QUESTION_TYPE.N)) {
						return QUESTION_TYPE.N;
					} else {
						return QUESTION_TYPE.N;
					}
				}
			}
		}
	}

	public void setNewCareerFieldYN(QUESTION_TYPE newCareerFieldYN) {
		if (newCareerFieldYN == null) {
			this.setNewCareerFieldYN_Str(null);
		} else {
			this.setNewCareerFieldYN_Str(newCareerFieldYN.equals(QUESTION_TYPE.Y) ? "Y" : "N");
		}
	}

	public QUESTION_TYPE getTaxability() {
		if (this.getTaxability_Str() != null && this.getTaxability_Str().equals("Y")) {
			return QUESTION_TYPE.Y;
		} else {
			if (this.getTaxability_Str() != null && this.getTaxability_Str().equals("N")) {
				return QUESTION_TYPE.N;
			} else {
				/* the default */
				return QUESTION_TYPE.N;
			}
		}
	}

	public void setTaxability(QUESTION_TYPE taxability) {
		if (taxability == null) {
			this.setTaxability_Str(null);
		} else {
			this.setTaxability_Str(taxability.equals(QUESTION_TYPE.Y) ? "Y" : "N");
		}
	}

	public enum QUESTION_TYPE {
		Y, N
	}

	public static QUESTION_TYPE convertToQuestionType(String val) {
		if (StringUtils.isEmpty(val)) {
			return null;
		}

		if (val.equalsIgnoreCase(String.valueOf(Boolean.FALSE)) || val.equalsIgnoreCase("off") || val.equals("0") || val.equalsIgnoreCase("no") || val.equalsIgnoreCase("N")) {
			return QUESTION_TYPE.N;
		} else if (val.equalsIgnoreCase(String.valueOf(Boolean.TRUE)) || val.equalsIgnoreCase("on") || val.equals("1") || val.equalsIgnoreCase("yes") || val.equalsIgnoreCase("Y")) {
			return QUESTION_TYPE.Y;
		} else {
			return null;
		}
	}

}
