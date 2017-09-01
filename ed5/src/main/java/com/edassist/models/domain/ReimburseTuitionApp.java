package com.edassist.models.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.edassist.utils.CommonUtil;

/**
 *
 */
@Entity
@Table(name = "ReimburseTuitionApp")
public class ReimburseTuitionApp implements Serializable {

	private static final long serialVersionUID = 8203156516721763365L;

	@Id
	@Basic(optional = false)
	@Column(name = "ReimburseTuitionAppID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reimburseTuitionAppID;
	@Column(name = "ProviderName")
	private String providerName;
	@Column(name = "ProviderCode")
	private String providerCode;
	@Column(name = "ProviderAddress1")
	private String providerAddress1;
	@Column(name = "ProviderAddress2")
	private String providerAddress2;
	@Column(name = "ProviderCity")
	private String providerCity;

	@JoinColumn(name = "ProviderStateID", referencedColumnName = "StateID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private State providerStateID;

	@Column(name = "ProviderZip")
	private String providerZip;
	@Column(name = "ProviderPhone")
	private String providerPhone;
	@Column(name = "Country")
	private String country;
	@Column(name = "CourseStartDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date courseStartDate;
	@Column(name = "CourseEndDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date courseEndDate;
	@Column(name = "OtherObjective")
	private String otherObjective;
	@Column(name = "PaymentAmount")
	private BigDecimal paymentAmount;
	@Column(name = "PaymentDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	@Column(name = "GradesComplete")
	@Temporal(TemporalType.TIMESTAMP)
	private Date gradesComplete;
	@Column(name = "RanCourseCompletion")
	private Integer ranCourseCompletion;
	@Column(name = "RanCourseCompletion2")
	private Integer ranCourseCompletion2;
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
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	private Application applicationID;

	@JoinColumn(name = "CourseOfStudyID", referencedColumnName = "CourseOfStudyID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private CourseOfStudy courseOfStudyID;

	@JoinColumn(name = "DegreeObjectiveID", referencedColumnName = "DegreeObjectiveID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private DegreeObjectives degreeObjectiveID;

	@JoinColumn(name = "SectorID", referencedColumnName = "SectorID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private Sector sectorID;

	@Column(name = "OtherCourseOfStudy")
	private String otherCourseOfStudy;

	@Column(name = "AmtRptdPaid")
	private BigDecimal amtRptdPaid;
	@Column(name = "AmtPaid")
	private BigDecimal amtPaid;
	@Column(name = "TaxableAmt")
	private BigDecimal taxableAmt;

	@Column(name = "CertificationStartDate")
	@Temporal(TemporalType.DATE)
	private Date certificationStartDate;

	@Column(name = "CertificationEndDate")
	@Temporal(TemporalType.DATE)
	private Date certificationEndDate;

	public State getProviderStateID() {
		return providerStateID;
	}

	public void setProviderStateID(State providerStateID) {
		this.providerStateID = providerStateID;
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

	public ReimburseTuitionApp() {
		providerCode = "";
	}

	public ReimburseTuitionApp(Long reimburseTuitionAppID) {
		this.reimburseTuitionAppID = reimburseTuitionAppID;
	}

	public ReimburseTuitionApp(Long reimburseTuitionAppID, Long createdBy, Date dateCreated) {
		this.reimburseTuitionAppID = reimburseTuitionAppID;
		this.createdBy = createdBy;
		this.dateCreated = dateCreated;
	}

	public Long getReimburseTuitionAppID() {
		return reimburseTuitionAppID;
	}

	public void setReimburseTuitionAppID(Long reimburseTuitionAppID) {
		this.reimburseTuitionAppID = reimburseTuitionAppID;
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

	public String getProviderAddress1() {
		return providerAddress1;
	}

	public void setProviderAddress1(String providerAddress1) {
		this.providerAddress1 = providerAddress1;
	}

	public String getProviderAddress2() {
		return providerAddress2;
	}

	public void setProviderAddress2(String providerAddress2) {
		this.providerAddress2 = providerAddress2;
	}

	public String getProviderCity() {
		return providerCity;
	}

	public void setProviderCity(String providerCity) {
		this.providerCity = providerCity;
	}

	public String getProviderZip() {
		return providerZip;
	}

	public void setProviderZip(String providerZip) {
		this.providerZip = providerZip;
	}

	public String getProviderPhone() {
		return providerPhone;
	}

	public void setProviderPhone(String providerPhone) {
		this.providerPhone = providerPhone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getCourseStartDate() {
		return courseStartDate;
	}

	@Transient
	public String getFormatCourseStartDate() {
		return CommonUtil.formatDate(courseStartDate, CommonUtil.MMDDYYY);
	}

	public void setCourseStartDate(Date courseStartDate) {
		this.courseStartDate = courseStartDate;
	}

	@Transient
	public String getFormatCourseEndDate() {
		return CommonUtil.formatDate(courseEndDate, CommonUtil.MMDDYYY);
	}

	public Date getCourseEndDate() {
		return courseEndDate;
	}

	public void setCourseEndDate(Date courseEndDate) {
		this.courseEndDate = courseEndDate;
	}

	public String getOtherObjective() {
		return otherObjective;
	}

	public void setOtherObjective(String otherObjective) {
		// TAMS4-2301 : Forcing the otherObjective column to reflect otherCourseOfStudy for backword compatability
		// this.otherObjective = otherObjective;
		this.otherObjective = this.getOtherObjective();
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

	public Date getGradesComplete() {
		return gradesComplete;
	}

	public void setGradesComplete(Date gradesComplete) {
		this.gradesComplete = gradesComplete;
	}

	public Integer getRanCourseCompletion() {
		return ranCourseCompletion;
	}

	public void setRanCourseCompletion(Integer ranCourseCompletion) {
		this.ranCourseCompletion = ranCourseCompletion;
	}

	public Integer getRanCourseCompletion2() {
		return ranCourseCompletion2;
	}

	public void setRanCourseCompletion2(Integer ranCourseCompletion2) {
		this.ranCourseCompletion2 = ranCourseCompletion2;
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

	public Sector getSectorID() {
		return sectorID;
	}

	public void setSectorID(Sector sectorID) {
		this.sectorID = sectorID;
	}

	@Override
	public String toString() {
		return "bean.ReimburseTuitionApp[reimburseTuitionAppID=" + reimburseTuitionAppID + "]";
	}

	public String getOtherCourseOfStudy() {
		return otherCourseOfStudy;
	}

	public void setOtherCourseOfStudy(String otherCourseOfStudy) {
		// TAMS4-2301 : Forcing the otherObjective column to reflect otherCourseOfStudy for backword compatability
		this.otherCourseOfStudy = otherCourseOfStudy;
		this.otherObjective = otherCourseOfStudy;
	}

	public Date getCertificationStartDate() {
		return certificationStartDate;
	}

	public void setCertificationStartDate(Date certificationStartDate) {
		this.certificationStartDate = certificationStartDate;
	}

	public Date getCertificationEndDate() {
		return certificationEndDate;
	}

	public void setCertificationEndDate(Date certificationEndDate) {
		this.certificationEndDate = certificationEndDate;
	}
}
