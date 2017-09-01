package com.edassist.models.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AppDTO {

	private Long id;
	private Long applicationNumber;
	private ParticipantDTO participant;
	private EducationalProviderDTO educationalProvider;
	private ApplicationStatusDTO applicationStatus;
	private FinancialAidSourceDTO financialAidSourceId;
	private String otherFinancialAid;
	private BigDecimal financialAidAmount;
	private BenefitPeriodDTO benefitPeriod;
	private List<ExpenseDTO> nonCourseRelatedExpenses;
	private List<CourseDTO> courses;
	private List<ApplicationCommentsDTO> applicationComments;
	private BigDecimal totalRequestedAmount;
	private BigDecimal totalRefunds;
	private List<PaymentDTO> payments;
	private List<RefundDTO> refunds;
	private List<ApplicationDocumentsDTO> applicationDocuments;
	private List<EligibilityEventHistoryDTO> statusHistory;
	private Date agreementsDate;

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

	public ParticipantDTO getParticipant() {
		return participant;
	}

	public void setParticipant(ParticipantDTO participant) {
		this.participant = participant;
	}

	public EducationalProviderDTO getEducationalProvider() {
		return educationalProvider;
	}

	public void setEducationalProvider(EducationalProviderDTO educationalProvider) {
		this.educationalProvider = educationalProvider;
	}

	public ApplicationStatusDTO getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatusDTO applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public FinancialAidSourceDTO getFinancialAidSourceId() {
		return financialAidSourceId;
	}

	public void setFinancialAidSourceId(FinancialAidSourceDTO financialAidSourceId) {
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

	public BenefitPeriodDTO getBenefitPeriod() {
		return benefitPeriod;
	}

	public void setBenefitPeriod(BenefitPeriodDTO benefitPeriod) {
		this.benefitPeriod = benefitPeriod;
	}

	public List<ExpenseDTO> getNonCourseRelatedExpenses() {
		return nonCourseRelatedExpenses;
	}

	public void setNonCourseRelatedExpenses(List<ExpenseDTO> nonCourseRelatedExpenses) {
		this.nonCourseRelatedExpenses = nonCourseRelatedExpenses;
	}

	public List<CourseDTO> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseDTO> courses) {
		this.courses = courses;
	}

	public List<ApplicationCommentsDTO> getApplicationComments() {
		return applicationComments;
	}

	public void setApplicationComments(List<ApplicationCommentsDTO> applicationComments) {
		this.applicationComments = applicationComments;
	}

	public BigDecimal getTotalRequestedAmount() {
		return totalRequestedAmount;
	}

	public void setTotalRequestedAmount(BigDecimal totalRequestedAmount) {
		this.totalRequestedAmount = totalRequestedAmount;
	}

	public BigDecimal getTotalRefunds() {
		return totalRefunds;
	}

	public void setTotalRefunds(BigDecimal totalRefunds) {
		this.totalRefunds = totalRefunds;
	}

	public List<PaymentDTO> getPayments() {
		return payments;
	}

	public void setPayments(List<PaymentDTO> payments) {
		this.payments = payments;
	}

	public List<RefundDTO> getRefunds() {
		return refunds;
	}

	public void setRefunds(List<RefundDTO> refunds) {
		this.refunds = refunds;
	}

	public List<ApplicationDocumentsDTO> getApplicationDocuments() {
		return applicationDocuments;
	}

	public void setApplicationDocuments(List<ApplicationDocumentsDTO> applicationDocuments) {
		this.applicationDocuments = applicationDocuments;
	}

	public List<EligibilityEventHistoryDTO> getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(List<EligibilityEventHistoryDTO> statusHistory) {
		this.statusHistory = statusHistory;
	}

	public Date getAgreementsDate() {
		return agreementsDate;
	}

	public void setAgreementsDate(Date agreementsDate) {
		this.agreementsDate = agreementsDate;
	}
}
