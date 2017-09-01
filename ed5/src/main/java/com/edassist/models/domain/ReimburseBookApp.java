package com.edassist.models.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ReimburseBookApp")
public class ReimburseBookApp {

	@Id
	@Column(name = "ReimburseBookAppID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reimburseBookAppID;

	@Column(name = "ProviderName")
	private String providerName;

	@Column(name = "ProviderCode")
	private String providerCode;

	@Column(name = "CourseStartDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date courseStartDate;

	@Column(name = "CourseEndDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date courseEndDate;

	@Column(name = "PaymentAmount")
	private BigDecimal paymentAmount;

	@Column(name = "PaymentDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;

	@Column(name = "PaymentInfo")
	private String paymentInfo;

	@Column(name = "CreditHours")
	private BigDecimal creditHours;

	@Column(name = "RefundAmount")
	private BigDecimal refundAmount;

	@Column(name = "PaymentTotal")
	private BigDecimal paymentTotal;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Basic(optional = false)
	@Column(name = "CreatedBy")
	private Long createdBy;

	@Column(name = "ModifiedBy")
	private Long modifiedBy;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@JoinColumn(name = "ApplicationID", referencedColumnName = "ApplicationID")
	@OneToOne(optional = false)
	private Application applicationID;

	@Column(name = "AmtRptdPaid")
	private BigDecimal amtRptdPaid;

	@Column(name = "AmtPaid")
	private BigDecimal amtPaid;

	@Column(name = "TaxableAmt")
	private BigDecimal taxableAmt;

	@Column(name = "OtherObjective")
	private String otherObjective;

	@JoinColumn(name = "CourseOfStudyID", referencedColumnName = "CourseOfStudyID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private CourseOfStudy courseOfStudyID;

	@JoinColumn(name = "DegreeObjectiveID", referencedColumnName = "DegreeObjectiveID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private DegreeObjectives degreeObjectiveID;

	@Column(name = "OtherCourseOfStudy")
	private String otherCourseOfStudy;

	public ReimburseBookApp() {
	}

	public Long getReimburseBookAppID() {
		return reimburseBookAppID;
	}

	public void setReimburseBookAppID(Long reimburseBookAppID) {
		this.reimburseBookAppID = reimburseBookAppID;
	}

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

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public BigDecimal getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(BigDecimal creditHours) {
		this.creditHours = creditHours;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public BigDecimal getPaymentTotal() {
		return paymentTotal;
	}

	public void setPaymentTotal(BigDecimal paymentTotal) {
		this.paymentTotal = paymentTotal;
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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Application getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Application applicationID) {
		this.applicationID = applicationID;
	}

	public BigDecimal getAmtRptdPaid() {
		return amtRptdPaid;
	}

	public void setAmtRptdPaid(BigDecimal amtRptdPaid) {
		this.amtRptdPaid = amtRptdPaid;
	}

	public BigDecimal getAmtPaid() {
		return amtPaid;
	}

	public void setAmtPaid(BigDecimal amtPaid) {
		this.amtPaid = amtPaid;
	}

	public BigDecimal getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(BigDecimal taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public String getOtherObjective() {
		return otherObjective;
	}

	public void setOtherObjective(String otherObjective) {
		this.otherObjective = otherObjective;
	}

	public CourseOfStudy getCourseOfStudyID() {
		return courseOfStudyID;
	}

	public void setCourseOfStudyID(CourseOfStudy courseOfStudyID) {
		this.courseOfStudyID = courseOfStudyID;
	}

	public DegreeObjectives getDegreeObjectiveID() {
		return degreeObjectiveID;
	}

	public void setDegreeObjectiveID(DegreeObjectives degreeObjectiveID) {
		this.degreeObjectiveID = degreeObjectiveID;
	}

	public String getOtherCourseOfStudy() {
		return otherCourseOfStudy;
	}

	public void setOtherCourseOfStudy(String otherCourseOfStudy) {
		this.otherCourseOfStudy = otherCourseOfStudy;
	}
}
