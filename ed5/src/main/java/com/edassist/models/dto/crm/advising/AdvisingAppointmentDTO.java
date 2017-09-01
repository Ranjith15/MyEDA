package com.edassist.models.dto.crm.advising;

import java.util.List;

import com.edassist.models.dto.AttachmentDTO;

public class AdvisingAppointmentDTO {

	private String appointmentId;
	private int category;
	private String timeZones;
	private String phoneNumber;
	private String secondaryPhoneNumber;
	private String anticipatedStartDate;
	private int highestLevelOfEducation;
	private int typeOfDegree;
	private int numberOfYears;
	private int schoolType;
	private int isReasonForAdvising;
	private int whereAreYouInProcess;
	private int noOfYearsInCurrentPosition;
	private int doUCurrentlyHaveStudentLoans;
	private String email;
	private AvailableSlotDTO selectedSlot;
	private int reasonExpertGuidance;
	private double totalLoanDebt;
	private int currentLoanRepayment;
	private String typeOfStudentLoan;
	private int yearsWithCurrentEmployer;
	private int yearsOfProfessionalExp;
	private int currentEmpStatus;
	private boolean currentlyEnrolledInUnivProgram;
	private boolean currentlyInProcessOfPayingBackStudentLoans;
	private List<AttachmentDTO> attachments;
	private String reasonForAdvising;

	public String getAppointmentId() {
		return appointmentId;
	}

	public int getCategory() {
		return category;
	}

	public String getTimeZones() {
		return timeZones;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getSecondaryPhoneNumber() {
		return secondaryPhoneNumber;
	}

	public String getAnticipatedStartDate() {
		return anticipatedStartDate;
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

	public AvailableSlotDTO getSelectedSlot() {
		return selectedSlot;
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

	public List<AttachmentDTO> getAttachments() {
		return attachments;
	}

	public String getReasonForAdvising() {
		return reasonForAdvising;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public void setTimeZones(String timeZones) {
		this.timeZones = timeZones;
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

	public void setSelectedSlot(AvailableSlotDTO selectedSlot) {
		this.selectedSlot = selectedSlot;
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

	public void setAttachments(List<AttachmentDTO> attachments) {
		this.attachments = attachments;
	}

	public void setReasonForAdvising(String reasonForAdvising) {
		this.reasonForAdvising = reasonForAdvising;
	}

}
