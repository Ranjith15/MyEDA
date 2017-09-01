package com.edassist.models.domain;

import com.edassist.constants.ApplicationConstants;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Application")
public class App {

	@Id
	@Column(name = "ApplicationID")
	private Long id;

	@Column(name = "ApplicationNumber")
	private Long applicationNumber;

	@JoinColumn(name = "ParticipantID", referencedColumnName = "ParticipantID")
	@ManyToOne
	private Participant participant;

	@JoinColumn(name = "EducationalProviderID", referencedColumnName = "EducationalProviderID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private EducationalProviders educationalProvider;

	@JoinColumn(name = "ApplicationStatusID", referencedColumnName = "ApplicationStatusID")
	@ManyToOne
	private ApplicationStatus applicationStatus;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "FinancialAidSourceID")
	private FinancialAidSource financialAidSourceId;

	@Column(name = "OtherFinancialAid")
	private String otherFinancialAid;

	@Column(name = "FinancialAidAmount")
	private BigDecimal financialAidAmount;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "BenefitPeriodID", referencedColumnName = "BenefitPeriodID")
	private BenefitPeriod benefitPeriod;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "application")
	@Where(clause = "ExpenseTypeID is null")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Course> coursesCollection;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "application", fetch = FetchType.EAGER)
	@Where(clause = "ExpenseTypeID is not null AND ExpenseTypeID != 3 AND ExpenseTypeID != 8 AND FeesAmount is not null")
	private List<Expense> nonCourseRelatedExpenses;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION)
	private List<AppStatusChange> appStatusChangeCollection;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION)
	private List<AppStatusChangeLive> appStatusChangeLiveCollection;

	@OneToMany(mappedBy = "applicationID", fetch = FetchType.LAZY)
	private List<ApplicationComment> applicationComments;

	@OneToMany(mappedBy = "applicationID", fetch = FetchType.LAZY)
	private List<ApprovalHistory> approvalHistorys;

	@OneToMany(mappedBy = "applicationID", fetch = FetchType.LAZY)
	@Where(clause = "AppStatusChange = 1 AND ViewableByParticipant = 1")
	private List<EligibilityEventComment> eligibilityEventComments;

	@OneToMany(mappedBy = "application", fetch = FetchType.LAZY)
	private List<RuleMessages> ruleMessages;

	@OneToMany(mappedBy = "applicationID", fetch = FetchType.LAZY)
	private List<Payments> payments;

	@OneToMany(mappedBy = "applicationID", fetch = FetchType.LAZY)
	private List<Refunds> refunds;

	@OneToMany(mappedBy = "applicationID", fetch = FetchType.LAZY)
	private List<ApplicationDocuments> applicationDocuments;

	@Column(name = "AgreementsDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date agreementsDate;

	@Transient
	private List<Course> courses;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public EducationalProviders getEducationalProvider() {
		return educationalProvider;
	}

	public void setEducationalProvider(EducationalProviders educationalProvider) {
		this.educationalProvider = educationalProvider;
	}

	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public FinancialAidSource getFinancialAidSourceId() {
		return financialAidSourceId;
	}

	public void setFinancialAidSourceId(FinancialAidSource financialAidSourceId) {
		this.financialAidSourceId = financialAidSourceId;
	}

	public String getOtherFinancialAid() {
		return otherFinancialAid;
	}

	public void setOtherFinancialAid(String otherFinancialAid) {
		this.otherFinancialAid = otherFinancialAid;
	}

	public BigDecimal getFinancialAidAmount() {
		return financialAidAmount;
	}

	public void setFinancialAidAmount(BigDecimal financialAidAmount) {
		this.financialAidAmount = financialAidAmount;
	}

	public BenefitPeriod getBenefitPeriod() {
		return benefitPeriod;
	}

	public void setBenefitPeriod(BenefitPeriod benefitPeriod) {
		this.benefitPeriod = benefitPeriod;
	}

	public List<Course> getCoursesCollection() {
		return coursesCollection;
	}

	public void setCoursesCollection(List<Course> coursesCollection) {
		this.coursesCollection = coursesCollection;
	}

	public List<Expense> getNonCourseRelatedExpenses() {
		return nonCourseRelatedExpenses;
	}

	public void setNonCourseRelatedExpenses(List<Expense> nonCourseRelatedExpenses) {
		this.nonCourseRelatedExpenses = nonCourseRelatedExpenses;
	}

	public List<AppStatusChange> getAppStatusChangeCollection() {
		return appStatusChangeCollection;
	}

	public void setAppStatusChangeCollection(List<AppStatusChange> appStatusChangeCollection) {
		this.appStatusChangeCollection = appStatusChangeCollection;
	}

	public List<AppStatusChangeLive> getAppStatusChangeLiveCollection() {
		return appStatusChangeLiveCollection;
	}

	public void setAppStatusChangeLiveCollection(List<AppStatusChangeLive> appStatusChangeLiveCollection) {
		this.appStatusChangeLiveCollection = appStatusChangeLiveCollection;
	}

	public List<ApplicationComment> getApplicationComments() {
		return applicationComments;
	}

	public void setApplicationComments(List<ApplicationComment> applicationComments) {
		this.applicationComments = applicationComments;
	}

	public List<RuleMessages> getRuleMessages() {
		return ruleMessages;
	}

	public void setRuleMessages(List<RuleMessages> ruleMessages) {
		this.ruleMessages = ruleMessages;
	}

	public List<EligibilityEventComment> getEligibilityEventComments() {
		return eligibilityEventComments;
	}

	public void setEligibilityEventComments(List<EligibilityEventComment> eligibilityEventComments) {
		this.eligibilityEventComments = eligibilityEventComments;
	}

	public List<Payments> getPayments() {
		return payments;
	}

	public void setPayments(List<Payments> payments) {
		this.payments = payments;
	}

	public List<Refunds> getRefunds() {
		return refunds;
	}

	public void setRefunds(List<Refunds> refunds) {
		this.refunds = refunds;
	}

	public List<ApplicationDocuments> getApplicationDocuments() {
		return applicationDocuments;
	}

	public void setApplicationDocuments(List<ApplicationDocuments> applicationDocuments) {
		this.applicationDocuments = applicationDocuments;
	}

	public Date getAgreementsDate() {
		return agreementsDate;
	}

	public void setAgreementsDate(Date agreementsDate) {
		this.agreementsDate = agreementsDate;
	}

	public List<ApprovalHistory> getApprovalHistorys() {
		return approvalHistorys;
	}

	public void setApprovalHistorys(List<ApprovalHistory> approvalHistorys) {
		this.approvalHistorys = approvalHistorys;
	}

	public List<Course> getCourses() {
		if (coursesCollection != null && coursesCollection.size() > 0) {
			coursesCollection.removeIf(course -> course.getName() != null && course.getName().startsWith("DELETED-"));
		}
		return coursesCollection;
	}

}
