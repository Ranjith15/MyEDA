package com.edassist.models.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ThinAppDTO {
	private Long applicationID;
	private Long applicationNumber;
	private String providerName;
	private String applicationCourseNumber;
	private Long programId;
	private String programName;
	private Date sessionStartDate;
	private Date sessionEndDate;
	private String courseOfStudy;
	private String degreeObjective;
	private ApplicationStatusDTO applicationStatus;
	private ApplicationTypeDTO applicationType;
	private Long participantId;
	private String firstName;
	private String mI;
	private String lastName;
	private Date dateModified;
	private boolean reviewableComment;
	private Date submittedDate;
	private Date paymentDate;
	private BigDecimal totalRequestedAmount;
	private BigDecimal totalRefunds;
	private ThinBenefitPeriodDTO thinBenefitPeriodDTO;
	private List<CourseDTO> courses;
	private List<ExpenseDTO> nonCourseRelatedExpenses;

	public Long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public Long getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getApplicationCourseNumber() {
		return applicationCourseNumber;
	}

	public void setApplicationCourseNumber(String applicationCourseNumber) {
		this.applicationCourseNumber = applicationCourseNumber;
	}

	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	public ApplicationStatusDTO getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatusDTO applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public ApplicationTypeDTO getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(ApplicationTypeDTO applicationType) {
		this.applicationType = applicationType;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public Date getSessionStartDate() {
		return sessionStartDate;
	}

	public void setSessionStartDate(Date sessionStartDate) {
		this.sessionStartDate = sessionStartDate;
	}

	public Date getSessionEndDate() {
		return sessionEndDate;
	}

	public void setSessionEndDate(Date sessionEndDate) {
		this.sessionEndDate = sessionEndDate;
	}

	public String getCourseOfStudy() {
		return courseOfStudy;
	}

	public void setCourseOfStudy(String courseOfStudy) {
		this.courseOfStudy = courseOfStudy;
	}

	public String getDegreeObjective() {
		return degreeObjective;
	}

	public void setDegreeObjective(String degreeObjective) {
		this.degreeObjective = degreeObjective;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getmI() {
		return mI;
	}

	public void setmI(String mI) {
		this.mI = mI;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public boolean isReviewableComment() {
		return reviewableComment;
	}

	public void setReviewableComment(boolean reviewableComment) {
		this.reviewableComment = reviewableComment;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
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

	public ThinBenefitPeriodDTO getThinBenefitPeriodDTO() {
		return thinBenefitPeriodDTO;
	}

	public void setThinBenefitPeriodDTO(ThinBenefitPeriodDTO thinBenefitPeriodDTO) {
		this.thinBenefitPeriodDTO = thinBenefitPeriodDTO;
	}

	public List<CourseDTO> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseDTO> courses) {
		this.courses = courses;
	}

	public List<ExpenseDTO> getNonCourseRelatedExpenses() {
		return nonCourseRelatedExpenses;
	}

	public void setNonCourseRelatedExpenses(List<ExpenseDTO> nonCourseRelatedExpenses) {
		this.nonCourseRelatedExpenses = nonCourseRelatedExpenses;
	}

}
