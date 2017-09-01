package com.edassist.models.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

public class ParticipantDTO {

	@NotNull
	private Long participantId;
	private Date hireDate;
	private Date terminationDate;
	private String terminationReason;
	private String jobTitle;
	private String costCenter;
	private String department;
	private String region;
	private String hourlySalaried;
	private String fullTime;
	private Long hoursPerWeek;
	private String union;
	private String gradeLevel;
	private String employeeId;
	private String participantUnion;
	private String generic1;
	private String generic2;
	private String generic3;
	private String generic4;
	private String generic5;
	private String generic6;
	private String generic7;
	private String generic8;
	private String generic9;
	private String generic10;
	private String companyId;
	private String companyName;
	private String uniqueId;
	private PaymentPreferenceDTO paymentPreference;
	private Long preferredAddress;
	@NotNull
	private String preferredEmail;
	private String preferredPhone;
	// @Valid TODO Not valid for direct pay programs. Disabling this for cisco
	private AddressDTO workAddress;
	// @Valid
	private AddressDTO homeAddress;
	private AddressDTO mailingAddress;
	private String otherPhone;
	private String otherEmail;
	private String employeeStatus;
	private ParticipantCurrentEducationProfileDTO currentEducationProfile;
	private UserDTO user;
	private ClientDTO client;

	private List<ThinAppDTO> applications;

	public ParticipantDTO() {

	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	public String getTerminationReason() {
		return terminationReason;
	}

	public void setTerminationReason(String terminationReason) {
		this.terminationReason = terminationReason;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getHourlySalaried() {
		return hourlySalaried;
	}

	public void setHourlySalaried(String hourlySalaried) {
		this.hourlySalaried = hourlySalaried;
	}

	public String getFullTime() {
		return fullTime;
	}

	public void setFullTime(String fullTime) {
		this.fullTime = fullTime;
	}

	public Long getHoursPerWeek() {
		return hoursPerWeek;
	}

	public void setHoursPerWeek(Long hoursPerWeek) {
		this.hoursPerWeek = hoursPerWeek;
	}

	public String getUnion() {
		return union;
	}

	public void setUnion(String union) {
		this.union = union;
	}

	public String getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getParticipantUnion() {
		return participantUnion;
	}

	public void setParticipantUnion(String participantUnion) {
		this.participantUnion = participantUnion;
	}

	public String getGeneric1() {
		return generic1;
	}

	public void setGeneric1(String generic1) {
		this.generic1 = generic1;
	}

	public String getGeneric2() {
		return generic2;
	}

	public void setGeneric2(String generic2) {
		this.generic2 = generic2;
	}

	public String getGeneric3() {
		return generic3;
	}

	public void setGeneric3(String generic3) {
		this.generic3 = generic3;
	}

	public String getGeneric4() {
		return generic4;
	}

	public void setGeneric4(String generic4) {
		this.generic4 = generic4;
	}

	public String getGeneric5() {
		return generic5;
	}

	public void setGeneric5(String generic5) {
		this.generic5 = generic5;
	}

	public String getGeneric6() {
		return generic6;
	}

	public void setGeneric6(String generic6) {
		this.generic6 = generic6;
	}

	public String getGeneric7() {
		return generic7;
	}

	public void setGeneric7(String generic7) {
		this.generic7 = generic7;
	}

	public String getGeneric8() {
		return generic8;
	}

	public void setGeneric8(String generic8) {
		this.generic8 = generic8;
	}

	public String getGeneric9() {
		return generic9;
	}

	public void setGeneric9(String generic9) {
		this.generic9 = generic9;
	}

	public String getGeneric10() {
		return generic10;
	}

	public void setGeneric10(String generic10) {
		this.generic10 = generic10;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public PaymentPreferenceDTO getPaymentPreference() {
		return paymentPreference;
	}

	public void setPaymentPreference(PaymentPreferenceDTO paymentPreference) {
		this.paymentPreference = paymentPreference;
	}

	public Long getPreferredAddress() {
		return preferredAddress;
	}

	public void setPreferredAddress(Long preferredAddress) {
		this.preferredAddress = preferredAddress;
	}

	public String getPreferredEmail() {
		return preferredEmail;
	}

	public void setPreferredEmail(String preferredEmail) {
		this.preferredEmail = preferredEmail;
	}

	public String getPreferredPhone() {
		return preferredPhone;
	}

	public void setPreferredPhone(String preferredPhone) {
		this.preferredPhone = preferredPhone;
	}

	public AddressDTO getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(AddressDTO workAddress) {
		this.workAddress = workAddress;
	}

	public AddressDTO getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(AddressDTO homeAddress) {
		this.homeAddress = homeAddress;
	}

	public AddressDTO getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(AddressDTO mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public String getOtherPhone() {
		return otherPhone;
	}

	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}

	public String getOtherEmail() {
		return otherEmail;
	}

	public void setOtherEmail(String otherEmail) {
		this.otherEmail = otherEmail;
	}

	public String getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public ParticipantCurrentEducationProfileDTO getCurrentEducationProfile() {
		return currentEducationProfile;
	}

	public void setCurrentEducationProfile(ParticipantCurrentEducationProfileDTO currentEducationProfile) {
		this.currentEducationProfile = currentEducationProfile;
	}

	public List<ThinAppDTO> getApplications() {
		return applications;
	}

	public void setApplications(List<ThinAppDTO> applications) {
		this.applications = applications;
	}

	public ClientDTO getClient() {
		return client;
	}

	public void setClient(ClientDTO client) {
		this.client = client;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}
