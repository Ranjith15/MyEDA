package com.edassist.models.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.edassist.constants.ApplicationConstants;
import com.edassist.exception.BadRequestException;
import com.edassist.utils.CommonUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Application")
public class Application implements Serializable {
	private static Logger log = Logger.getLogger(Application.class);
	private static final long serialVersionUID = 5880633941645937132L;

	public Application() {
		super();
	}

	private Long applicationID;

	private Long applicationNumber;

	private Date dateCreated;

	private Date dateModified;

	private String applicationInfo;

	private Boolean feeInvoiced;

	private Long enteredBy;

	private Long approvedBy;

	private Boolean degreeCompleteYN;

	private Long modifiedBy;

	private Date dateClosed;

	private Date approvedDate;

	private Date submittedDate;

	private Date emailPaymentProcessed;

	private FinancialAidSource financialAidSourceId;

	private String otherFinancialAid;

	private Long createdBy;

	private BigDecimal applicationFee;

	private BigDecimal registrationFee;

	private Long relatedAppId;

	private BigDecimal financialAidAmount;

	private Long relatedCarryOverAppID;

	private Date agreementsDate;

	private PrepayTuitionApp prepayTuitionApp;

	private ApplicationSource applicationSourceID;

	private ReimburseTuitionApp reimburseTuitionApp;

	private ReimburseStudentLoanApp reimburseStudentLoanApp;

	// private LoanAmount loanAmount;

	private ApplicationStatus applicationStatusID;

	// transient field
	private ApplicationStatus originalStatusID;

	private ApplicationType applicationTypeID;

	private BenefitPeriod benefitPeriodID;

	private Participant participantID;// = new Participant();

	private Long dependentID;

	private Long dependentEducationInformationID;

	private String dependentFullName;

	@Column(name = "StudentID")
	private String studentID;

	@Column(name = "StudentStatus")
	private String studentStatus;

	private String academicTerm;
	private String session;
	private CourseMethod courseMethod;

	private BigDecimal tuitionAmount;
	private Double courseCreditHours;

	@Column(name = "SessionCreditHours")
	private Double sessionCreditHours;

	private List<ApplicationCourses> applicationCoursesCollection;

	private List<RefundsDistribution> refundsDistributionCollection;

	private ReimburseBookApp reimburseBookApp;

	private String preferredAddress;

	private String preferredEmail;

	private String submitAction;

	private boolean appealsOnly;

	private boolean nonCompliantApplications;

	private EducationalProviders educationalProvider;

	private boolean participantAgreement;
	private boolean participantAgreement1;
	private boolean participantAgreement2;
	private boolean participantAgreement3;

	private Date createdEndDate;
	private Date createdStartDate;

	private Date readyForPaymentDate;

	private Boolean bypassLevel1;
	private Boolean bypassLevel2;

	@Column(name = "isRelatedApplication")
	private Boolean isRelatedApplication;

	@JsonIgnore
	public Boolean getIsRelatedApplication() {
		if (isRelatedApplication == null) {
			return Boolean.FALSE;
		}
		return isRelatedApplication;
	}

	public void setIsRelatedApplication(Boolean isRelatedApplication) {
		this.isRelatedApplication = isRelatedApplication;
	}

	/*
	 * private List<ParentRelatedApplicationRelationship> parentRelatedApplicationRelationship;
	 * @OneToMany(mappedBy = "ParentApplicationID", fetch=FetchType.LAZY) public List<ParentRelatedApplicationRelationship> getParentRelatedApplicationRelationshipCollection() { return
	 * parentRelatedApplicationRelationship; } public void setParentRelatedApplicationRelationshipCollection(List< ParentRelatedApplicationRelationship> parentRelatedApplicationRelationship) {
	 * this.parentRelatedApplicationRelationship = parentRelatedApplicationRelationship; }
	 */

	/**************************************************************************
	 *
	 * GETTERS
	 *
	 *************************************************************************/

	@Id
	@Basic(optional = false)
	@Column(name = "ApplicationID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getApplicationID() {
		return applicationID;
	}

	@Column(name = "RelatedAppID")
	public Long getRelatedAppId() {
		return relatedAppId;
	}

	@Column(name = "ApplicationNumber")
	public Long getApplicationNumber() {
		return applicationNumber;
	}

	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	public Date getDateCreated() {
		return dateCreated;
	}

	@Transient
	public String getFormatDateCreated() {
		return CommonUtil.formatDate(dateCreated, CommonUtil.MMDDYYY);
	}

	@Column(name = "DateModified")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	public Date getDateModified() {
		return dateModified;
	}

	@Column(name = "ApplicationInfo")
	@JsonIgnore
	public String getApplicationInfo() {
		return applicationInfo;
	}

	@Column(name = "FeeInvoiced")
	@JsonIgnore
	public Boolean getFeeInvoiced() {
		return feeInvoiced;
	}

	@Column(name = "EnteredBy")
	@JsonIgnore
	public Long getEnteredBy() {
		return enteredBy;
	}

	@Column(name = "ApprovedBy")
	@JsonIgnore
	public Long getApprovedBy() {
		return approvedBy;
	}

	@Column(name = "DegreeCompleteYN")
	@JsonIgnore
	public Boolean getDegreeCompleteYN() {
		return degreeCompleteYN;
	}

	@Column(name = "ModifiedBy")
	@JsonIgnore
	public Long getModifiedBy() {
		return modifiedBy;
	}

	@Column(name = "ApprovedDate")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	public Date getApprovedDate() {
		return approvedDate;
	}

	@Column(name = "SubmittedDate")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	public Date getSubmittedDate() {
		return submittedDate;
	}

	@Column(name = "EmailPaymentProcessed")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	public Date getEmailPaymentProcessed() {
		return emailPaymentProcessed;
	}

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "FinancialAidSourceID")
	@JsonIgnore
	public FinancialAidSource getFinancialAidSourceId() {
		return financialAidSourceId;
	}

	@Column(name = "OtherFinancialAid")
	@JsonIgnore
	public String getOtherFinancialAid() {
		return otherFinancialAid;
	}

	@Column(name = "CreatedBy")
	@JsonIgnore
	public Long getCreatedBy() {
		return createdBy;
	}

	@Column(name = "ApplicationFee")
	@JsonIgnore
	public BigDecimal getApplicationFee() {
		return applicationFee;
	}

	@Column(name = "RegistrationFee")
	@JsonIgnore
	public BigDecimal getRegistrationFee() {
		return registrationFee;
	}

	@Column(name = "FinancialAidAmount")
	@JsonIgnore
	public BigDecimal getFinancialAidAmount() {
		return financialAidAmount;
	}

	@Column(name = "RelatedCarryOverAppID")
	@JsonIgnore
	public Long getRelatedCarryOverAppID() {
		return relatedCarryOverAppID;
	}

	@Column(name = "AgreementsDate")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	public Date getAgreementsDate() {
		return agreementsDate;
	}

	@OneToOne(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION_ID)
	@JsonIgnore
	public PrepayTuitionApp getPrepayTuitionApp() {
		return prepayTuitionApp;
	}

	@OneToOne(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION_ID)
	@JsonIgnore
	public ReimburseTuitionApp getReimburseTuitionApp() {
		return reimburseTuitionApp;
	}

	@OneToOne(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION_ID)
	@JsonBackReference
	public ReimburseStudentLoanApp getReimburseStudentLoanApp() {
		return reimburseStudentLoanApp;
	}

	/*
	 * @OneToOne(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION_ID) public LoanAmount getLoanAmount() { return loanAmount; }
	 */

	@OneToOne(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION_ID)
	@JsonIgnore
	public ReimburseBookApp getReimburseBookApp() {
		return reimburseBookApp;
	}

	@JoinColumn(name = "ApplicationSourceID", referencedColumnName = "ApplicationSourceID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	public ApplicationSource getApplicationSourceID() {
		return applicationSourceID;
	}

	@JoinColumn(name = "ApplicationStatusID", referencedColumnName = "ApplicationStatusID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JsonIgnore
	public ApplicationStatus getApplicationStatusID() {
		return applicationStatusID;
	}

	@JoinColumn(name = "ApplicationTypeID", referencedColumnName = "ApplicationTypeID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JsonIgnore
	public ApplicationType getApplicationTypeID() {
		return applicationTypeID;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "BenefitPeriodID", referencedColumnName = "BenefitPeriodID")
	@JsonIgnore
	public BenefitPeriod getBenefitPeriodID() {
		return benefitPeriodID;
	}

	@JoinColumn(name = "ParticipantID", referencedColumnName = "ParticipantID")
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	public Participant getParticipantID() {
		return participantID;
	}

	@Column(name = "DependentID")
	@JsonIgnore
	public Long getDependentID() {
		return dependentID;
	}

	public void setDependentID(Long dependentID) {
		this.dependentID = dependentID;
	}

	@Transient
	@JsonIgnore
	public String getDependentFullName() {
		return dependentFullName;
	}

	public void setDependentFullName(String dependentFullName) {
		this.dependentFullName = dependentFullName;
	}

	@Column(name = "DependentEducationInformationID")
	public Long getDependentEducationInformationID() {
		return dependentEducationInformationID;
	}

	public void setDependentEducationInformationID(Long dependentEducationInformationID) {
		this.dependentEducationInformationID = dependentEducationInformationID;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION_ID, fetch = FetchType.LAZY)
	public List<ApplicationCourses> getApplicationCoursesCollection() {
		if (applicationCoursesCollection != null && applicationCoursesCollection.size() > 0) {
			applicationCoursesCollection.removeIf(applicationCourseExpense -> applicationCourseExpense.getFeesAmount() == null && applicationCourseExpense.getTuitionAmount() == null);
		}
		return applicationCoursesCollection;
	}

	@OneToMany(mappedBy = ApplicationConstants.APPLICATION_ID, fetch = FetchType.LAZY)
	@JsonIgnore
	public List<RefundsDistribution> getRefundsDistributionCollection() {
		return refundsDistributionCollection;
	}

	@Column(name = "PreferredAddress")
	@JsonIgnore
	public String getPreferredAddress() {
		return preferredAddress;
	}

	@Column(name = "PreferredEmail")
	@JsonIgnore
	public String getPreferredEmail() {
		return preferredEmail;
	}

	@Column(name = "ParticipantAgreement")
	@JsonIgnore
	public boolean isParticipantAgreement() {
		return participantAgreement;
	}

	@JoinColumn(name = "EducationalProviderID", referencedColumnName = "EducationalProviderID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JsonIgnore
	public EducationalProviders getEducationalProvider() {
		return educationalProvider;
	}


	@JsonIgnore
	public Double getSessionCreditHours() {
		return sessionCreditHours;
	}

	/**************************************************************************
	 *
	 * SETTERS
	 *
	 *************************************************************************/

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setApplicationInfo(String applicationInfo) {
		this.applicationInfo = applicationInfo;
	}

	public void setFeeInvoiced(Boolean feeInvoiced) {
		this.feeInvoiced = feeInvoiced;
	}

	public void setEnteredBy(Long enteredBy) {
		this.enteredBy = enteredBy;
	}

	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}

	public void setDegreeCompleteYN(Boolean degreeCompleteYN) {
		this.degreeCompleteYN = degreeCompleteYN;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public void setEmailPaymentProcessed(Date emailPaymentProcessed) {
		this.emailPaymentProcessed = emailPaymentProcessed;
	}

	public void setFinancialAidSourceId(FinancialAidSource financialAidSourceId) {
		this.financialAidSourceId = financialAidSourceId;
	}

	public void setOtherFinancialAid(String otherFinancialAid) {
		this.otherFinancialAid = otherFinancialAid;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public void setApplicationFee(BigDecimal applicationFee) {
		this.applicationFee = applicationFee;
	}

	public void setRegistrationFee(BigDecimal registrationFee) {
		this.registrationFee = registrationFee;
	}

	public void setFinancialAidAmount(BigDecimal financialAidAmount) {
		this.financialAidAmount = financialAidAmount;
	}

	public void setRelatedCarryOverAppID(Long relatedCarryOverAppID) {
		this.relatedCarryOverAppID = relatedCarryOverAppID;
	}

	public void setAgreementsDate(Date agreementsDate) {
		this.agreementsDate = agreementsDate;
	}

	public void setPrepayTuitionApp(PrepayTuitionApp prepayTuitionApp) {
		this.prepayTuitionApp = prepayTuitionApp;
	}

	public void setApplicationSourceID(ApplicationSource applicationSourceID) {
		this.applicationSourceID = applicationSourceID;
	}

	public void setReimburseTuitionApp(ReimburseTuitionApp reimburseTuitionApp) {
		this.reimburseTuitionApp = reimburseTuitionApp;
	}

	public void setReimburseStudentLoanApp(ReimburseStudentLoanApp reimburseStudentLoanApp) {
		this.reimburseStudentLoanApp = reimburseStudentLoanApp;
	}

	public void setApplicationStatusID(ApplicationStatus applicationStatusID) {
		this.applicationStatusID = applicationStatusID;
		if (this.originalStatusID == null) {
			this.originalStatusID = applicationStatusID;
		}
	}

	public void setApplicationStatusID(ApplicationStatus applicationStatusID, Long userId) {
		this.applicationStatusID = applicationStatusID;
		if (this.originalStatusID == null) {
			this.originalStatusID = applicationStatusID;
		}
		this.setModifiedBy(userId);
		this.setDateModified(new Date());
	}

	public void setApplicationTypeID(ApplicationType applicationTypeID) {
		this.applicationTypeID = applicationTypeID;
	}

	public void setBenefitPeriodID(BenefitPeriod benefitPeriodID) {
		this.benefitPeriodID = benefitPeriodID;
	}

	public void setParticipantID(Participant participantID) {
		this.participantID = participantID;
	}

	public void setApplicationCoursesCollection(List<ApplicationCourses> applicationCoursesCollection) {
		this.applicationCoursesCollection = applicationCoursesCollection;
	}

	public void setRefundsDistributionCollection(List<RefundsDistribution> refundsDistributionCollection) {
		this.refundsDistributionCollection = refundsDistributionCollection;
	}

	public void setReimburseBookApp(ReimburseBookApp reimburseBookApp) {
		this.reimburseBookApp = reimburseBookApp;
	}

	public void setPreferredAddress(String preferredAddress) {
		this.preferredAddress = preferredAddress;
	}

	public void setPreferredEmail(String preferredEmail) {
		this.preferredEmail = preferredEmail;
	}

	public void setSubmitAction(String submitAction) {
		this.submitAction = submitAction;
	}

	public void setParticipantAgreement(boolean participantAgreement) {
		this.participantAgreement = participantAgreement;
	}

	public void setRelatedAppId(Long relatedAppId) {
		this.relatedAppId = relatedAppId;
	}

	public void setEducationalProvider(EducationalProviders educationalProvider) {
		this.educationalProvider = educationalProvider;
	}

	public void setNonCompliantApplications(boolean nonCompliantApplications) {
		this.nonCompliantApplications = nonCompliantApplications;
	}

	public void setAppealsOnly(boolean appealsOnly) {
		this.appealsOnly = appealsOnly;
	}

	public void setSessionCreditHours(Double sessionCreditHours) {
		this.sessionCreditHours = sessionCreditHours;
	}

	/**************************************************************************
	 *
	 * METHODS
	 *
	 *************************************************************************/

	@Transient
	public boolean isAppealsOnly() {
		return appealsOnly;
	}

	@Transient
	public boolean isNonCompliantApplications() {
		return nonCompliantApplications;
	}

	@Transient
	public String getFormatAgreementsDate() {
		return CommonUtil.formatDate(agreementsDate, CommonUtil.MMDDYYY);
	}

	@Transient
	public String getSubmitAction() {
		return submitAction;
	}

	@Transient
	public boolean isEditable() {
		if (applicationStatusID == null || applicationStatusID.getApplicationStatusCode() == null) {
			return false;
		}
		return (ApplicationConstants.APPLICATION_STATUS_SAVED_NOT_SUBMITTED.equals(applicationStatusID.getApplicationStatusCode())
				|| ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE.equals(applicationStatusID.getApplicationStatusCode())
				|| ApplicationConstants.APPLICATION_STATUS_PAYMENT_PROCESSING_INCOMPLETE_INFORMATION_NEEDED.equals(applicationStatusID.getApplicationStatusCode())
				|| ApplicationConstants.APPLICATION_STATUS_PAYMENT_REVIEW.equals(applicationStatusID.getApplicationStatusCode())
				|| ApplicationConstants.APPLICATION_STATUS_PAYMENT_REIMBURSEMENT_PROGRESS.equals(applicationStatusID.getApplicationStatusCode())
				|| ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW.equals(applicationStatusID.getApplicationStatusCode())
				|| ApplicationConstants.APPLICATION_STATUS_CARRYOVER_PAYMENT_APPROVED.equals(applicationStatusID.getApplicationStatusCode())
				|| ApplicationConstants.APPLICATION_STATUS_PAYMENT_COMPLETE_COMPLETION_DOCUMENTS_REQUIRED.equals(applicationStatusID.getApplicationStatusCode())
				|| ApplicationConstants.APPLICATION_STATUS_SENT_COLLECTIONS.equals(applicationStatusID.getApplicationStatusCode())
				|| ApplicationConstants.APPLICATION_STATUS_REPAYMENT_REQUIRED.equals(applicationStatusID.getApplicationStatusCode())
				|| ApplicationConstants.APPLICATION_STATUS_CLOSED.equals(applicationStatusID.getApplicationStatusCode())
				|| ApplicationConstants.APPLICATION_STATUS_DENIED.equals(applicationStatusID.getApplicationStatusCode()));
	}

	@Transient
	public boolean isSavedNotSubmitted() {
		if (applicationStatusID == null || applicationStatusID.getApplicationStatusCode() == null) {
			return false;
		}

		return (ApplicationConstants.APPLICATION_STATUS_SAVED_NOT_SUBMITTED.equals(applicationStatusID.getApplicationStatusCode()));
	}

	@Transient
	public boolean isSubmittedIncomplete() {
		if (applicationStatusID == null || applicationStatusID.getApplicationStatusCode() == null) {
			return false;
		}

		return (ApplicationConstants.APPLICATION_STATUS_SUBMITTED_INCOMPLETE.equals(applicationStatusID.getApplicationStatusCode()));
	}

	@Transient
	public boolean isSubmittedPendingReview() {
		if (applicationStatusID == null || applicationStatusID.getApplicationStatusCode() == null) {
			return false;
		}

		return (ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW.equals(applicationStatusID.getApplicationStatusCode()));
	}

	@Transient
	public boolean isApproved() {
		if (applicationStatusID == null || applicationStatusID.getApplicationStatusCode() == null) {
			return false;
		}

		return (applicationStatusID.getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_APPROVED)
				|| applicationStatusID.getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_LOC));
	}

	@Transient
	public boolean isDenied() {
		if (applicationStatusID == null || applicationStatusID.getApplicationStatusCode() == null) {
			return false;
		}

		return (applicationStatusID.getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_DENIED));
	}

	@Transient
	public boolean isCancelled() {
		if (applicationStatusID == null || applicationStatusID.getApplicationStatusCode() == null) {
			return false;
		}

		return (applicationStatusID.getApplicationStatusCode().equals(ApplicationConstants.APPLICATION_STATUS_CANCELLED));
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}

		if (!(object instanceof Application)) {
			return false;
		}

		Application other = (Application) object;
		if ((this.applicationID == null && other.applicationID != null) || (this.applicationID != null && !this.applicationID.equals(other.applicationID))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ApplicationId: ").append(getApplicationID()).append(", ");
		sb.append("ApplicationInfo: ").append(getApplicationInfo()).append(", ");
		sb.append("BenefitPeriod: {").append(getBenefitPeriodID()).append("}, ");
		return sb.toString();
	}

	@Transient
	public boolean isPrePaymentApplication() {
		return (this.prepayTuitionApp != null);
	}

	@Transient
	public boolean isReimbursementApplication() {
		return (this.reimburseTuitionApp != null);
	}

	@Transient
	public boolean isReimburseStudentLoanApplication() {
		return (this.reimburseStudentLoanApp != null);
	}

	/*
	 * @Transient public boolean isLoanAmount() { return (this.loanAmount != null); }
	 */

	@Transient
	public boolean isBookApplication() {
		return (this.reimburseBookApp != null);
	}

	@Transient
	public Application createBookApp(PrepayTuitionApp prepayApp, ApplicationType bookType, Double taxableAmt, Long userId) {
		if (createdBy == null) {
			throw new BadRequestException("createdBy must not be null.");
		}

		Application bookApp = new Application();
		bookApp.setParticipantID(this.participantID);
		bookApp.setApplicationSourceID(this.getApplicationSourceID());
		bookApp.setApplicationStatusID(this.getApplicationStatusID());
		bookApp.setApplicationTypeID(bookType);
		bookApp.setRelatedAppId(this.applicationID);
		bookApp.setApplicationFee(BigDecimal.valueOf(0.0));
		bookApp.setRegistrationFee(BigDecimal.valueOf(0.0));
		bookApp.setCreatedBy(userId);
		bookApp.setDateCreated(new Date());
		bookApp.setFeeInvoiced(false);
		bookApp.setFinancialAidAmount(new BigDecimal(0));
		bookApp.updateProviderFields(this.getEducationalProvider(), this.getProviderStateID());

		ReimburseBookApp newReimburseBookApp = new ReimburseBookApp();
		newReimburseBookApp.setApplicationID(bookApp);
		newReimburseBookApp.setCourseStartDate(prepayApp.getCourseStartDate());
		newReimburseBookApp.setCourseEndDate(prepayApp.getCourseEndDate());
		newReimburseBookApp.setCreatedBy(userId);
		newReimburseBookApp.setCreditHours(prepayApp.getCreditHours());
		newReimburseBookApp.setDateCreated(new Date());
		newReimburseBookApp.setTaxableAmt(BigDecimal.valueOf(taxableAmt));
		// TAM-3175
		// newReimburseBookApp.setDegreeObjectiveID(this.getDegreeObjectiveID());
		// newReimburseBookApp.setOtherObjective(this.getOtherObjective());
		// newReimburseBookApp.setCourseOfStudyID(this.getCourseOfStudyID());
		// newReimburseBookApp.setOtherCourseOfStudy(this.getOtherCourseOfStudy());
		bookApp.setReimburseBookApp(newReimburseBookApp);
		return bookApp;
	}

	@Transient
	public String getCourseStartDateString() {
		if (this.getCourseStartDate() == null) {
			return CommonUtil.formatDate(this.getDateCreated(), CommonUtil.MMDDYYY);
		} else {
			return CommonUtil.formatDate(this.getCourseStartDate(), CommonUtil.MMDDYYY);
		}
	}

	@Transient
	public String getCourseEndDateString() {
		if (this.getCourseEndDate() == null) {
			return CommonUtil.formatDate(this.getDateCreated(), CommonUtil.MMDDYYY);
		} else {
			return CommonUtil.formatDate(this.getCourseEndDate(), CommonUtil.MMDDYYY);
		}
	}

	@Transient
	public String getExpectedGraduationDateString() {
		if (this.participantID != null && this.participantID.getCurrentEducationProfile() != null) {
			return this.participantID.getCurrentEducationProfile().getExpGraduationDtStr();
		} else {
			return "";
		}
	}

	@Transient
	public boolean isNewApplication() {
		if (this.applicationID == null || this.applicationID == 0) {
			return true;
		}
		return false;
	}

	@Transient
	public static Long generateApplicationNumber() {
		// TODO THIS SHOULD ONLY BE 7 CHARS LONG
		// CURRENTLY USING DATE TO THE MILLISECOND
		StringBuilder appNum = new StringBuilder();
		GregorianCalendar gc = new GregorianCalendar();
		appNum.append(gc.get(Calendar.YEAR));
		appNum.append(gc.get(Calendar.MONTH));
		appNum.append(gc.get(Calendar.DAY_OF_MONTH));
		appNum.append(gc.get(Calendar.HOUR_OF_DAY));
		appNum.append(gc.get(Calendar.MINUTE));
		appNum.append(gc.get(Calendar.SECOND));
		appNum.append(gc.get(Calendar.MILLISECOND));
		return Long.valueOf(appNum.toString());
	}

	@Transient
	public String getProviderCityStateZipDisplay() {
		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isEmpty(this.getProviderCity())) {
			sb.append(this.getProviderCity());
			if (this.getProviderStateID() != null && !StringUtils.isEmpty(this.getProviderStateID().getStateAbbreviation())) {
				sb.append(", ");
				sb.append(this.getProviderStateID().getStateAbbreviation());
				if (!StringUtils.isEmpty(this.getProviderZip())) {
					sb.append(" ");
					sb.append(this.getProviderZip());
				}
			}
		} else if (this.getProviderStateID() != null && !StringUtils.isEmpty(this.getProviderStateID().getStateAbbreviation())) {
			sb.append(this.getProviderStateID().getStateAbbreviation());
			if (!StringUtils.isEmpty(this.getProviderZip())) {
				sb.append(" ");
				sb.append(this.getProviderZip());
			}
		} else {
			if (!StringUtils.isEmpty(this.getProviderZip())) {
				sb.append(this.getProviderZip());
			}
		}

		return sb.toString();
	}

	@Transient
	public void updateProviderFields(EducationalProviders provider, State providerStateId) {
		if (provider == null) {
			return;
		}

		this.setEducationalProvider(provider);
		this.setProviderAddress1(provider.getProviderAddress1());
		this.setProviderAddress2(provider.getProviderAddress2());
		this.setProviderCity(provider.getProviderCity());
		this.setProviderCode(provider.getProviderCode());
		this.setProviderName(provider.getProviderName());
		this.setProviderPhone(provider.getProviderPhone());
		this.setProviderStateID(providerStateId);
		this.setProviderZip(provider.getProviderZip());
	}

	/**************************************************************************
	 *
	 * PREPAY/REIMBURSE RELATED FIELDS/METHODS The fields below exist on the application's reimburseTuitionApp, prepayTuitionApp or reimburseBookApp Due to the fact that the data model is not
	 * normalized, we have to take extra efforts to ensure that the data is saved properly. When creating/updating an application the form will bind to these transient fields The applicationService
	 * will transfer these transient fields when persisting.
	 *
	 *************************************************************************/
	private String providerName;
	private String providerCode;
	private String providerAddress1;
	private String providerAddress2;
	private String providerCity;
	private State providerStateID;
	private String providerZip;
	private String providerPhone;
	private String country;
	private Date courseStartDate;
	private Date courseEndDate;
	private String otherObjective;
	private BigDecimal paymentAmount;
	private Date paymentDate;
	private Date gradesComplete;
	private Integer ranCourseCompletion;
	private Integer ranCourseCompletion2;
	private String paymentInfo;
	private BigDecimal creditHours;
	private BigDecimal refundAmount;
	private BigDecimal paymentTotal;
	private CourseOfStudy courseOfStudyID;
	private DegreeObjectives degreeObjectiveID;
	private Sector sectorID;
	private String otherCourseOfStudy;
	private BigDecimal amtRptdPaid;
	private BigDecimal amtPaid;
	private BigDecimal taxableAmt;

	// reimburseLoan only Fields

	private String loanAccountNumber;

	@Transient
	public String getLoanAccountNumber() {
		if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getLoanAccountNumber();
		}
		return loanAccountNumber;
	}

	private BigDecimal loanRequestedPaymentAmount;

	@Transient
	public BigDecimal getLoanRequestedPaymentAmount() {
		if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getLoanRequestedPaymentAmount();
		}
		return loanRequestedPaymentAmount;
	}

	private BigDecimal loanApprovedAmount;

	@Transient
	public BigDecimal getLoanApprovedAmount() {
		if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getLoanApprovedAmount();
		}
		return loanApprovedAmount;
	}

	private BigDecimal loanPaidAmount;

	@Transient
	public BigDecimal getLoanPaidAmount() {
		if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getLoanPaidAmount();
		}
		return loanPaidAmount;
	}

	private BigDecimal totalStudentLoanDept;

	@Transient
	public BigDecimal getTotalStudentLoanDept() {
		if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getTotalStudentLoanDept();
		}
		return totalStudentLoanDept;
	}

	private BigDecimal loanBalance;

	@Transient
	public BigDecimal getLoanBalance() {
		if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getLoanBalance();
		}
		return loanBalance;
	}

	private BigDecimal monthlyPaymentAmount;

	@Transient
	public BigDecimal getMonthlyPaymentAmount() {

		if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getMonthlyPaymentAmount();
		}

		return monthlyPaymentAmount;
	}

	private Date loanPaymentPeriodStartDate;

	@Transient
	public Date getLoanPaymentPeriodStartDate() {
		if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getLoanPaymentPeriodStartDate();
		}

		return loanPaymentPeriodStartDate;
	}

	private Date loanPaymentPeriodEndDate;

	@Transient
	public Date getLoanPaymentPeriodEndDate() {
		if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getLoanPaymentPeriodEndDate();
		}

		return loanPaymentPeriodEndDate;
	}

	private Date graduationDate;

	@Transient
	public Date getGraduationDate() {
		if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getGraduationDate();
		}

		return graduationDate;
	}

	private long studentLoanServicerID;

	@Transient
	public long getStudentLoanServicerID() {
		if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getStudentLoanServicerID().getStudentLoanServicerID();
		}
		return studentLoanServicerID;

	}

	private BigDecimal gradePointAverage;

	@Transient
	public BigDecimal getGradePointAverage() {

		if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getGradePointAverage();
		}

		return gradePointAverage;
	}

	// Prepay Only Fields
	private Date issueDate;
	private Date missingInvoiceLetterSent;
	private boolean ranMissingInvoice;

	@Transient
	public String getProviderName() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getProviderName();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getProviderName();
		} else if (isBookApplication()) {
			return getReimburseBookApp().getProviderName();
		}

		return providerName;
	}

	@Transient
	public String getProviderCode() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getProviderCode();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getProviderCode();
		} else if (isBookApplication()) {
			return getReimburseBookApp().getProviderCode();
		}
		return providerCode;
	}

	@Transient
	public String getProviderAddress1() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getProviderAddress1();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getProviderAddress1();
		}
		return providerAddress1;
	}

	@Transient
	public String getProviderAddress2() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getProviderAddress2();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getProviderAddress2();
		}
		return providerAddress2;
	}

	@Transient
	public String getProviderCity() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getProviderCity();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getProviderCity();
		}
		return providerCity;
	}

	@Transient
	public State getProviderStateID() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getProviderStateID();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getProviderStateID();
		}
		return providerStateID;
	}

	@Transient
	public String getProviderZip() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getProviderZip();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getProviderZip();
		}
		return providerZip;
	}

	@Transient
	public String getProviderPhone() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getProviderPhone();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getProviderPhone();
		}
		return providerPhone;
	}

	@Transient
	public String getCountry() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getCountry();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getCountry();
		}
		return country;
	}

	@Transient
	public Date getCourseStartDate() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getCourseStartDate();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getCourseStartDate();
		} else if (isBookApplication()) {
			return getReimburseBookApp().getCourseStartDate();
		}

		return courseStartDate;
	}

	@Transient
	public Date getCourseEndDate() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getCourseEndDate();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getCourseEndDate();
		} else if (isBookApplication()) {
			return getReimburseBookApp().getCourseEndDate();
		}
		return courseEndDate;
	}

	private Date projectedProgramCompletionDate;

	/**
	 * @return the projectedProgramCompletionDate
	 */
	@Transient
	public Date getProjectedProgramCompletionDate() {
		return projectedProgramCompletionDate;
	}

	/**
	 * @param projectedProgramCompletionDate the projectedProgramCompletionDate to set
	 */
	public void setProjectedProgramCompletionDate(Date projectedProgramCompletionDate) {
		this.projectedProgramCompletionDate = projectedProgramCompletionDate;
	}

	@Transient
	public String getProjectedProgramCompletionDateString() {
		if (this.getProjectedProgramCompletionDate() == null) {
			return "Unknown";
		} else {
			return CommonUtil.formatDate(this.getProjectedProgramCompletionDate(), "MM/dd/yyyy");
		}
	}

	@Transient
	public String getOtherObjective() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getOtherObjective();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getOtherObjective();
		} else if (isBookApplication()) {
			return this.getReimburseBookApp().getOtherObjective();
		}
		return otherObjective;
	}

	@Transient
	public BigDecimal getPaymentAmount() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getPaymentAmount();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getPaymentAmount();
		} else if (isBookApplication()) {
			return getReimburseBookApp().getPaymentAmount();
		}
		return paymentAmount;
	}

	@Transient
	public Date getPaymentDate() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getPaymentDate();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getPaymentDate();
		} else if (isBookApplication()) {
			return getReimburseBookApp().getPaymentDate();
		}
		return paymentDate;
	}

	@Transient
	public String getFormattedPaymentDate() {
		return CommonUtil.formatDate(getPaymentDate(), CommonUtil.MMDDYYY);
	}

	@Transient
	public Date getGradesComplete() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getGradesComplete();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getGradesComplete();
		}
		return gradesComplete;
	}

	@Transient
	public Integer getRanCourseCompletion() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getRanCourseCompletion();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getRanCourseCompletion();
		}
		return ranCourseCompletion;
	}

	@Transient
	public Integer getRanCourseCompletion2() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getRanCourseCompletion2();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getRanCourseCompletion2();
		}
		return ranCourseCompletion2;
	}

	@Transient
	public String getPaymentInfo() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getPaymentInfo();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getPaymentInfo();
		} else if (isBookApplication()) {
			return getReimburseBookApp().getPaymentInfo();
		}
		return paymentInfo;
	}

	@Transient
	public BigDecimal getCreditHours() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getCreditHours();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getCreditHours();
		} else if (isBookApplication()) {
			return getReimburseBookApp().getCreditHours();
		}
		return creditHours;
	}

	@Transient
	public BigDecimal getRefundAmount() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getRefundAmount();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getRefundAmount();
		} else if (isBookApplication()) {
			return getReimburseBookApp().getRefundAmount();
		} else if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getRefundAmount();
		}
		return refundAmount;
	}

	@Transient
	public BigDecimal getPaymentTotal() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getPaymentTotal();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getPaymentTotal();
		} else if (isBookApplication()) {
			return getReimburseBookApp().getPaymentTotal();
		} else if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getLoanPaidAmount();
		}
		return paymentTotal;
	}

	@Transient
	@JsonIgnore
	public CourseOfStudy getCourseOfStudyID() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getCourseOfStudyID();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getCourseOfStudyID();
		} else if (isBookApplication()) {
			return this.getReimburseBookApp().getCourseOfStudyID();
		} else if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getCourseOfStudyID();
		}
		return courseOfStudyID;
	}

	@Transient
	@JsonIgnore
	public DegreeObjectives getDegreeObjectiveID() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getDegreeObjectiveID();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getDegreeObjectiveID();
		} else if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getDegreeObjectiveID();
		} else if (isBookApplication()) {
			return this.getReimburseBookApp().getDegreeObjectiveID();
		}
		return degreeObjectiveID;
	}

	@Transient
	@JsonIgnore
	public Sector getSectorID() {
		return sectorID;
	}

	@Transient
	@JsonIgnore
	public String getOtherCourseOfStudy() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getOtherCourseOfStudy();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getOtherCourseOfStudy();
		} else if (isBookApplication()) {
			return this.getReimburseBookApp().getOtherCourseOfStudy();
		} else if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getOtherFieldOfStudy();
		}
		return otherCourseOfStudy;
	}

	@Transient
	@JsonIgnore
	public BigDecimal getAmtRptdPaid() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getAmtRptdPaid();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getAmtRptdPaid();
		} else if (isBookApplication()) {
			return getReimburseBookApp().getAmtRptdPaid();
		} else if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getLoanPaidAmount();
		}
		return amtRptdPaid;
	}

	@Transient
	@JsonIgnore
	public BigDecimal getAmtPaid() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getAmtPaid();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getAmtPaid();
		} else if (isBookApplication()) {
			return getReimburseBookApp().getAmtPaid();
		} else if (isReimburseStudentLoanApplication()) {
			return getReimburseStudentLoanApp().getLoanPaidAmount();
		}
		return amtPaid;
	}

	@Transient
	@JsonIgnore
	public BigDecimal getTaxableAmt() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getTaxableAmt();
		} else if (isReimbursementApplication()) {
			return getReimburseTuitionApp().getTaxableAmt();
		} else if (isBookApplication()) {
			return getReimburseBookApp().getTaxableAmt();
		}
		return taxableAmt;
	}

	@Transient
	@JsonIgnore
	public Date getIssueDate() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getIssueDate();
		}

		return issueDate;
	}

	@Transient
	@JsonIgnore
	public String getFormattedIssueDate() {
		return CommonUtil.formatDate(getIssueDate(), CommonUtil.MMDDYYY);
	}

	@Transient
	@JsonIgnore
	public Date getMissingInvoiceLetterSent() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getMissingInvoiceLetterSent();
		}

		return missingInvoiceLetterSent;
	}

	@Transient
	@JsonIgnore
	public boolean isRanMissingInvoice() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getRanMissingInvoice();
		}
		return ranMissingInvoice;
	}

	@Transient
	@JsonIgnore
	public Integer isRanCourseCompletion() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getRanCourseCompletion();
		} else if (isReimbursementApplication()) {

			return getReimburseTuitionApp().getRanCourseCompletion();
		} else {
			return null;
		}
	}

	@Transient
	@JsonIgnore
	public Integer isRanCourseCompletion2() {
		if (isPrePaymentApplication()) {
			return getPrepayTuitionApp().getRanCourseCompletion2();
		} else if (isReimbursementApplication()) {

			return getReimburseTuitionApp().getRanCourseCompletion2();
		} else {
			return null;

		}
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;

		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setProviderName(providerName);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setProviderName(providerName);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setProviderName(providerName);
		}
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;

		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setProviderCode(providerCode);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setProviderCode(providerCode);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setProviderCode(providerCode);
		}
	}

	public void setProviderAddress1(String providerAddress1) {
		this.providerAddress1 = providerAddress1;

		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setProviderAddress1(providerAddress1);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setProviderAddress1(providerAddress1);
		}
	}

	public void setProviderAddress2(String providerAddress2) {
		this.providerAddress2 = providerAddress2;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setProviderAddress2(providerAddress2);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setProviderAddress2(providerAddress2);
		}
	}

	public void setProviderCity(String providerCity) {
		this.providerCity = providerCity;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setProviderCity(providerCity);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setProviderCity(providerCity);
		}
	}

	public void setProviderStateID(State providerStateID) {
		this.providerStateID = providerStateID;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setProviderStateID(providerStateID);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setProviderStateID(providerStateID);
		}
	}

	public void setProviderZip(String providerZip) {
		this.providerZip = providerZip;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setProviderZip(providerZip);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setProviderZip(providerZip);
		}
	}

	public void setProviderPhone(String providerPhone) {
		this.providerPhone = providerPhone;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setProviderPhone(providerPhone);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setProviderPhone(providerPhone);
		}
	}

	public void setCountry(String country) {
		this.country = country;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setCountry(country);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setCountry(country);
		}
	}

	public void setCourseStartDate(Date courseStartDate) {
		this.courseStartDate = courseStartDate;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setCourseStartDate(courseStartDate);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setCourseStartDate(courseStartDate);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setCourseStartDate(courseStartDate);
		}
	}

	public void setCourseEndDate(Date courseEndDate) {
		this.courseEndDate = courseEndDate;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setCourseEndDate(courseEndDate);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setCourseEndDate(courseEndDate);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setCourseEndDate(courseEndDate);
		}
	}

	public void setOtherObjective(String otherObjective) {
		this.otherObjective = otherObjective;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setOtherObjective(otherObjective);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setOtherObjective(otherObjective);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setOtherObjective(otherObjective);
		}
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setPaymentAmount(paymentAmount);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setPaymentAmount(paymentAmount);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setPaymentAmount(paymentAmount);
		}
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setPaymentDate(paymentDate);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setPaymentDate(paymentDate);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setPaymentDate(paymentDate);
		}
	}

	public void setGradesComplete(Date gradesComplete) {
		this.gradesComplete = gradesComplete;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setGradesComplete(gradesComplete);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setGradesComplete(gradesComplete);
		}
	}

	public void setRanCourseCompletion(Integer ranCourseCompletion) {
		this.ranCourseCompletion = ranCourseCompletion;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setRanCourseCompletion(ranCourseCompletion);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setRanCourseCompletion(ranCourseCompletion);
		}
	}

	public void setRanCourseCompletion2(Integer ranCourseCompletion2) {
		this.ranCourseCompletion2 = ranCourseCompletion2;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setRanCourseCompletion2(ranCourseCompletion2);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setRanCourseCompletion2(ranCourseCompletion2);
		}
	}

	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setPaymentInfo(paymentInfo);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setPaymentInfo(paymentInfo);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setPaymentInfo(paymentInfo);
		}
	}

	public void setCreditHours(BigDecimal creditHours) {
		this.creditHours = creditHours;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setCreditHours(creditHours);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setCreditHours(creditHours);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setCreditHours(creditHours);
		}
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setRefundAmount(refundAmount);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setRefundAmount(refundAmount);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setRefundAmount(refundAmount);
		}
	}

	public void setPaymentTotal(BigDecimal paymentTotal) {
		this.paymentTotal = paymentTotal;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setPaymentTotal(paymentTotal);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setPaymentTotal(paymentTotal);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setPaymentTotal(paymentTotal);
		}
	}

	public void setCourseOfStudyID(CourseOfStudy courseOfStudyID) {
		this.courseOfStudyID = courseOfStudyID;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setCourseOfStudyID(courseOfStudyID);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setCourseOfStudyID(courseOfStudyID);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setCourseOfStudyID(courseOfStudyID);
		}
	}

	public void setDegreeObjectiveID(DegreeObjectives degreeObjectiveID) {
		this.degreeObjectiveID = degreeObjectiveID;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setDegreeObjectiveID(degreeObjectiveID);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setDegreeObjectiveID(degreeObjectiveID);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setDegreeObjectiveID(degreeObjectiveID);
		}
	}

	public void setSectorID(Sector sectorID) {
		this.sectorID = sectorID;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setSectorID(sectorID);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setSectorID(sectorID);
		}
	}

	public void setOtherCourseOfStudy(String otherCourseOfStudy) {
		this.otherCourseOfStudy = otherCourseOfStudy;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setOtherCourseOfStudy(otherCourseOfStudy);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setOtherCourseOfStudy(otherCourseOfStudy);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setOtherCourseOfStudy(otherCourseOfStudy);
		}
	}

	public void setAmtRptdPaid(BigDecimal amtRptdPaid) {
		this.amtRptdPaid = amtRptdPaid;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setAmtRptdPaid(amtRptdPaid);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setAmtRptdPaid(amtRptdPaid);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setAmtRptdPaid(amtRptdPaid);
		}
	}

	public void setAmtPaid(BigDecimal amtPaid) {
		this.amtPaid = amtPaid;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setAmtPaid(amtPaid);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setAmtPaid(amtPaid);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setAmtPaid(amtPaid);
		}
	}

	public void setTaxableAmt(BigDecimal taxableAmt) {
		this.taxableAmt = taxableAmt;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setTaxableAmt(taxableAmt);
		} else if (isReimbursementApplication()) {
			this.getReimburseTuitionApp().setTaxableAmt(taxableAmt);
		} else if (isBookApplication()) {
			this.getReimburseBookApp().setTaxableAmt(taxableAmt);
		}
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setIssueDate(issueDate);
		}
	}

	public void setMissingInvoiceLetterSent(Date missingInvoiceLetterSent) {
		this.missingInvoiceLetterSent = missingInvoiceLetterSent;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setMissingInvoiceLetterSent(missingInvoiceLetterSent);
		}
	}

	public void setRanMissingInvoice(boolean ranMissingInvoice) {
		this.ranMissingInvoice = ranMissingInvoice;
		if (isPrePaymentApplication()) {
			this.getPrepayTuitionApp().setRanMissingInvoice(ranMissingInvoice);
		}
	}

	@Transient
	public ApplicationStatus getOriginalStatusID() {
		return originalStatusID;
	}

	public void setOrigialStatusID(ApplicationStatus statusId) {
		this.originalStatusID = statusId;
	}

	@Transient
	public boolean hasStatusChanged() {
		if (originalStatusID == null && this.applicationStatusID == null) {
			return false;
		}

		if ((originalStatusID == null && this.applicationStatusID != null) || (originalStatusID != null && this.applicationStatusID == null)) {
			return true;
		}

		return (!originalStatusID.getApplicationStatusID().equals(applicationStatusID.getApplicationStatusID()));
	}

	private List<AppStatusChange> appStatusChangeCollection;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION, fetch = FetchType.LAZY)
	@JsonIgnore
	public List<AppStatusChange> getAppStatusChangeCollection() {
		return appStatusChangeCollection;
	}

	public void setAppStatusChangeCollection(List<AppStatusChange> appStatusChangeCollection) {
		this.appStatusChangeCollection = appStatusChangeCollection;
	}

	private List<AppStatusChangeLive> appStatusChangeLiveCollection;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION, fetch = FetchType.LAZY)
	@JsonIgnore
	public List<AppStatusChangeLive> getAppStatusChangeLiveCollection() {
		return appStatusChangeLiveCollection;
	}

	public void setAppStatusChangeLiveCollection(List<AppStatusChangeLive> appStatusChangeLiveCollection) {
		this.appStatusChangeLiveCollection = appStatusChangeLiveCollection;
	}

	@Transient
	public boolean isParticipantAgreement1() {
		return participantAgreement1;
	}

	public void setParticipantAgreement1(boolean participantAgreement1) {
		this.participantAgreement1 = participantAgreement1;
	}

	@Transient
	public boolean isParticipantAgreement2() {
		return participantAgreement2;
	}

	public void setParticipantAgreement2(boolean participantAgreement2) {
		this.participantAgreement2 = participantAgreement2;
	}

	@Transient
	public boolean isParticipantAgreement3() {
		return participantAgreement3;
	}

	public void setParticipantAgreement3(boolean participantAgreement3) {
		this.participantAgreement3 = participantAgreement3;
	}

	@Column(name = "DateClosed")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	public Date getDateClosed() {
		return dateClosed;
	}

	public void setDateClosed(Date dateClosed) {
		this.dateClosed = dateClosed;
	}

	@Transient
	@JsonIgnore
	public Date getCreatedEndDate() {
		return createdEndDate;
	}

	@Transient
	@JsonIgnore
	public Date getCreatedStartDate() {
		return createdStartDate;
	}

	public void setCreatedEndDate(Date createdEndDate) {
		this.createdEndDate = createdEndDate;
	}

	public void setCreatedStartDate(Date createdStartDate) {
		this.createdStartDate = createdStartDate;
	}

	@JsonIgnore
	public String getStudentID() {
		return studentID;
	}

	@JsonIgnore
	public String getStudentStatus() {
		return studentStatus;
	}

	public void setStudentID(String studentID) {
		/*
		 * DAS - 2/25/2015 - for reasons unknown (at least unknown to me) the Step 4 Submit process is appending a comma onto the end of these damn Student IDs. This is meant as a temp fix until we
		 * have more slack time to chase this stuff down and fix it properly.
		 */
		String localStudentID = studentID.replace(",", "");
		this.studentID = localStudentID;
	}

	public void setStudentStatus(String studentStatus) {
		this.studentStatus = studentStatus;
	}

	@Transient
	@JsonIgnore
	public String getAcademicTerm() {
		return academicTerm;
	}

	@Transient
	@JsonIgnore
	public String getSession() {
		return session;
	}

	@Transient
	@JsonIgnore
	public BigDecimal getTuitionAmount() {
		return tuitionAmount;
	}

	@Transient
	@JsonIgnore
	public Double getCourseCreditHours() {
		return courseCreditHours;
	}

	public void setAcademicTerm(String academicTerm) {
		this.academicTerm = academicTerm;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public void setTuitionAmount(BigDecimal tuitionAmount) {
		this.tuitionAmount = tuitionAmount;
	}

	public void setCourseCreditHours(Double courseCreditHours) {
		this.courseCreditHours = courseCreditHours;
	}

	@Transient
	public CourseMethod getCourseMethod() {

		return courseMethod;
	}

	public void setCourseMethod(CourseMethod courseMethod) {
		this.courseMethod = courseMethod;
	}

	@Column(name = "ReadyForPaymentDate")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getReadyForPaymentDate() {
		return readyForPaymentDate;
	}

	public void setReadyForPaymentDate(Date readyForPaymentDate) {
		this.readyForPaymentDate = readyForPaymentDate;
	}

	// ED-104
	private Boolean eligibleForDenialAppeal = Boolean.FALSE;

	/**
	 * @return the eligibleForDenialAppeal
	 */
	@Column(name = "EligibleForDenialAppeal")
	@JsonIgnore
	public Boolean getEligibleForDenialAppeal() {
		return eligibleForDenialAppeal;
	}

	/**
	 * @param eligibleForDenialAppeal the eligibleForDenialAppeal to set
	 */
	public void setEligibleForDenialAppeal(Boolean eligibleForDenialAppeal) {
		this.eligibleForDenialAppeal = eligibleForDenialAppeal;
	}

	@Transient
	@JsonIgnore
	public String getEligibleForDenialAppealDesc() {
		if (getEligibleForDenialAppeal()) {
			return "Yes";
		} else {
			return "No";
		}
	}

	@Column(name = "BypassLevel1")
	@JsonIgnore
	public Boolean getBypassLevel1() {
		return bypassLevel1;
	}

	public void setBypassLevel1(Boolean bypassLevel1) {
		this.bypassLevel1 = bypassLevel1;
	}

	@Column(name = "BypassLevel2")
	@JsonIgnore
	public Boolean getBypassLevel2() {
		return bypassLevel2;
	}

	public void setBypassLevel2(Boolean bypassLevel2) {
		this.bypassLevel2 = bypassLevel2;
	}

}
