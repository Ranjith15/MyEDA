package com.edassist.models.contracts.crm.advising;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edassist.models.domain.crm.Contact;
import com.edassist.utils.RestUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvisingAppointment {

	@JsonProperty("EventId")
	private String appointmentId;

	@JsonProperty("Category")
	private int category;

	@JsonProperty("PreferredContactNumber")
	private String phoneNumber;

	@JsonProperty("SecondaryContactNumber")
	private String secondaryPhoneNumber;

	@JsonProperty("AnticipatedStartDate")
	private String anticipatedStartDate;

	@JsonProperty("HighestLevelOfEducationEarned")
	private int highestLevelOfEducation;

	@JsonProperty("LevelOfEducationAreYouCurrentlySeeking")
	private int typeOfDegree;

	@JsonProperty("TotalNumberOfYearsAtTheCompany")
	private int numberOfYears;

	@JsonProperty("SchoolType")
	private int schoolType;

	@JsonProperty("ReasonForSchedulingAcademicOrFinancialSession")
	private int isReasonForAdvising;

	@JsonProperty("IsWhereAreYouInTheProcess")
	private int whereAreYouInProcess;

	@JsonProperty("NumberOfYears")
	private int noOfYearsInCurrentPosition;

	@JsonProperty("DoYouCurrentlyHaveStudentLoans")
	private int doUCurrentlyHaveStudentLoans;

	@JsonProperty("Email")
	private String email;

	@JsonProperty("ReasonExpertGuidance")
	private int reasonExpertGuidance;

	@JsonProperty("TotalLoanDebt")
	private double totalLoanDebt;

	@JsonProperty("CurrentLoanRepayment")
	private int currentLoanRepayment;

	@JsonProperty("TypeOfStudentLoan")
	private String typeOfStudentLoan;

	@JsonProperty("YearsWithCurrentEmployer")
	private int yearsWithCurrentEmployer;

	@JsonProperty("YearsOfProfessionalExperience")
	private int yearsOfProfessionalExp;

	@JsonProperty("CurrentEmploymentStatus")
	private int currentEmpStatus;

	@JsonProperty("CurrentlyEnrolledInACollegeOrUniversityProgram")
	private boolean currentlyEnrolledInUnivProgram;

	@JsonProperty("CurrentlyInTheProcessOfPayingBackStudentLoans")
	private boolean currentlyInProcessOfPayingBackStudentLoans;

	@JsonProperty("AdvisingReason")
	private String reasonForAdvising;

	@JsonProperty("ServiceName")
	private String service;

	@JsonProperty("ServiceId")
	private String serviceId;

	@JsonProperty("Advisor")
	private String advisorId;

	@JsonProperty("AppointmentStartDate")
	private String scheduledStart;

	@JsonProperty("AppointmentEndDate")
	private String scheduledEnd;

	@JsonProperty("Contact")
	private Contact contact;

	@JsonProperty("Attachments")
	private List<Attachment> attachments;

	public String getAppointmentId() {
		return appointmentId;
	}

	public int getCategory() {
		return category;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getSecondaryPhoneNumber() {
		return secondaryPhoneNumber;
	}

	public String getAnticipatedStartDate() {
		return RestUtils.formatDateToString(anticipatedStartDate);
	}

	public int getHighestLevelOfEducation() {
		return highestLevelOfEducation;
	}

	public int getTypeOfDegree() {
		return typeOfDegree;
	}

	public int getNumberOfYears() {
		return numberOfYears;
	}

	public int getSchoolType() {
		return schoolType;
	}

	public int getIsReasonForAdvising() {
		return isReasonForAdvising;
	}

	public int getWhereAreYouInProcess() {
		return whereAreYouInProcess;
	}

	public int getNoOfYearsInCurrentPosition() {
		return noOfYearsInCurrentPosition;
	}

	public int getDoUCurrentlyHaveStudentLoans() {
		return doUCurrentlyHaveStudentLoans;
	}

	public String getEmail() {
		return email;
	}

	public int getReasonExpertGuidance() {
		return reasonExpertGuidance;
	}

	public double getTotalLoanDebt() {
		return totalLoanDebt;
	}

	public int getCurrentLoanRepayment() {
		return currentLoanRepayment;
	}

	public String getTypeOfStudentLoan() {
		return typeOfStudentLoan;
	}

	public int getYearsWithCurrentEmployer() {
		return yearsWithCurrentEmployer;
	}

	public int getYearsOfProfessionalExp() {
		return yearsOfProfessionalExp;
	}

	public int getCurrentEmpStatus() {
		return currentEmpStatus;
	}

	public boolean isCurrentlyEnrolledInUnivProgram() {
		return currentlyEnrolledInUnivProgram;
	}

	public boolean isCurrentlyInProcessOfPayingBackStudentLoans() {
		return currentlyInProcessOfPayingBackStudentLoans;
	}

	public String getReasonForAdvising() {
		return reasonForAdvising;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getAdvisorId() {
		return advisorId;
	}

	public void setAdvisorId(String advisorId) {
		this.advisorId = advisorId;
	}

	public String getScheduledStart() {
		return scheduledStart;
	}

	public void setScheduledStart(String scheduledStart) {
		this.scheduledStart = scheduledStart;
	}

	public String getScheduledEnd() {
		return scheduledEnd;
	}

	public void setScheduledEnd(String scheduledEnd) {
		this.scheduledEnd = scheduledEnd;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
		this.secondaryPhoneNumber = secondaryPhoneNumber;
	}

	public void setAnticipatedStartDate(String anticipatedStartDate) {
		this.anticipatedStartDate = anticipatedStartDate;
	}

	public void setHighestLevelOfEducation(int highestLevelOfEducation) {
		this.highestLevelOfEducation = highestLevelOfEducation;
	}

	public void setTypeOfDegree(int typeOfDegree) {
		this.typeOfDegree = typeOfDegree;
	}

	public void setNumberOfYears(int numberOfYears) {
		this.numberOfYears = numberOfYears;
	}

	public void setSchoolType(int schoolType) {
		this.schoolType = schoolType;
	}

	public void setIsReasonForAdvising(int isReasonForAdvising) {
		this.isReasonForAdvising = isReasonForAdvising;
	}

	public void setWhereAreYouInProcess(int whereAreYouInProcess) {
		this.whereAreYouInProcess = whereAreYouInProcess;
	}

	public void setNoOfYearsInCurrentPosition(int noOfYearsInCurrentPosition) {
		this.noOfYearsInCurrentPosition = noOfYearsInCurrentPosition;
	}

	public void setDoUCurrentlyHaveStudentLoans(int doUCurrentlyHaveStudentLoans) {
		this.doUCurrentlyHaveStudentLoans = doUCurrentlyHaveStudentLoans;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setReasonExpertGuidance(int reasonExpertGuidance) {
		this.reasonExpertGuidance = reasonExpertGuidance;
	}

	public void setTotalLoanDebt(double totalLoanDebt) {
		this.totalLoanDebt = totalLoanDebt;
	}

	public void setCurrentLoanRepayment(int currentLoanRepayment) {
		this.currentLoanRepayment = currentLoanRepayment;
	}

	public void setTypeOfStudentLoan(String typeOfStudentLoan) {
		this.typeOfStudentLoan = typeOfStudentLoan;
	}

	public void setYearsWithCurrentEmployer(int yearsWithCurrentEmployer) {
		this.yearsWithCurrentEmployer = yearsWithCurrentEmployer;
	}

	public void setYearsOfProfessionalExp(int yearsOfProfessionalExp) {
		this.yearsOfProfessionalExp = yearsOfProfessionalExp;
	}

	public void setCurrentEmpStatus(int currentEmpStatus) {
		this.currentEmpStatus = currentEmpStatus;
	}

	public void setCurrentlyEnrolledInUnivProgram(boolean currentlyEnrolledInUnivProgram) {
		this.currentlyEnrolledInUnivProgram = currentlyEnrolledInUnivProgram;
	}

	public void setCurrentlyInProcessOfPayingBackStudentLoans(boolean currentlyInProcessOfPayingBackStudentLoans) {
		this.currentlyInProcessOfPayingBackStudentLoans = currentlyInProcessOfPayingBackStudentLoans;
	}

	public void setReasonForAdvising(String reasonForAdvising) {
		this.reasonForAdvising = reasonForAdvising;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}

}
