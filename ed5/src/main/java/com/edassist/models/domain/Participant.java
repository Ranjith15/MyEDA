package com.edassist.models.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "Participant")
public class Participant implements Serializable {

	private static final long serialVersionUID = -3859477933726462762L;

	@Id
	@Column(name = "ParticipantID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long participantId;

	@Basic(optional = false)
	@Column(name = "SSN")
	private String ssn;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "UserID")
	private User user;

	@Basic(optional = false)
	@Column(name = "Active")
	@Enumerated(EnumType.STRING)
	private ACTIVE_TYPE active;

	@Column(name = "HireDate")
	private Date hireDate;

	@Column(name = "TerminationDate")
	private Date terminationDate;

	@Column(name = "TerminationReason")
	private String terminationReason;

	@Column(name = "JobTitle")
	private String jobTitle;

	@Column(name = "Email")
	private String email;

	@Column(name = "CostCenter")
	private String costCenter;

	@Column(name = "Department")
	private String department;

	@Column(name = "Region")
	private String region;

	@Column(name = "HourlySalaried")
	private String hourlySalaried;

	@Column(name = "isFullTime")
	private String fullTime;

	@Column(name = "HoursPerWeek")
	private Long hoursPerWeek;

	@Column(name = "isUnion")
	private String union;

	@Column(name = "GradeLevel")
	private String gradeLevel;

	@Column(name = "EmployeeId")
	private String employeeId;

	@Column(name = "ParticipantUnion")
	private String participantUnion;

	@Column(name = "Generic1")
	private String generic1;

	@Column(name = "Generic2")
	private String generic2;

	@Column(name = "Generic3")
	private String generic3;

	@Column(name = "Generic4")
	private String generic4;

	@Column(name = "Generic5")
	private String generic5;

	@Column(name = "Generic6")
	private String generic6;

	@Column(name = "Generic7")
	private String generic7;

	@Column(name = "Generic8")
	private String generic8;

	@Column(name = "Generic9")
	private String generic9;

	@Column(name = "Generic10")
	private String generic10;

	@Column(name = "CompanyId")
	private String companyId;

	@Column(name = "CompanyName")
	private String companyName;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	private Date dateCreated;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "CreatedBy")
	private User createdBy;

	@ManyToOne()
	@JoinColumn(name = "ModifiedBy")
	private User modifiedBy;

	@Basic(optional = false)
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ClientID")
	private Client client;

	@Column(name = "EducationLevelId")
	private Integer educationLevelId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "HomeAddressID")
	@Basic()
	private Address homeAddress;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "WorkAddressID")
	@Basic()
	private Address workAddress;

	@Column(name = "PreferredAddresId")
	@Basic()
	private Long preferredAddress;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "participantID")
	private PaymentPreference paymentPreference;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "MailingAddressID")
	@Basic()
	private Address mailingAddress;

	@Column(name = "OtherEmail")
	private String otherEmail;

	@Column(name = "OtherPhone")
	private String otherPhone;

	@Column(name = "PreferredEmail")
	private String preferredEmail;

	@Column(name = "PreferredPhone")
	private String preferredPhone;

	@Column(name = "EmployeeStatus")
	private String employeeStatus;

	@Column(name = "UniqueId")
	private String uniqueId;

	@Column(name = "SSOid")
	private String ssoId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CurrentEducationProfileId")
	private ParticipantCurrentEducationProfile currentEducationProfile;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "participantID", fetch = FetchType.LAZY)
	private List<ThinApp> applicationSet;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ParticipantPrograms", joinColumns = { @JoinColumn(name = "ParticipantID") }, inverseJoinColumns = { @JoinColumn(name = "ProgramID") })
	private Set<Program> programs;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "participantID", fetch = FetchType.LAZY)
	private Set<ParticipantSupervisor> supervisors;

	@Transient
	private Long ACLSID_SIDID;

	@Transient
	private Participant levelOneSupervisor;

	@Transient
	private Participant levelTwoSupervisor;

	public Participant() {
		super();
		this.init();
	}

	public Participant(long id, String ssn) {
		super();
		this.init();
		this.participantId = id;
		this.ssn = ssn;
	}

	private void init() {
		this.user = new User();
		this.homeAddress = new Address();
		this.workAddress = new Address();
		this.preferredAddress = 0L;
		this.client = new Client();
		this.educationLevelId = 1;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ACTIVE_TYPE getActive() {
		return active;
	}

	public void setActive(ACTIVE_TYPE active) {
		this.active = active;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Integer getEducationLevelId() {
		return educationLevelId;
	}

	public void setEducationLevelId(Integer educationLevelId) {
		this.educationLevelId = educationLevelId;
	}

	public Address getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	public Address getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(Address workAddress) {
		this.workAddress = workAddress;
	}

	public Long getPreferredAddress() {
		if (preferredAddress == null) {
			if (workAddress != null) {
				return workAddress.getAddressId();
			} else if (homeAddress != null) {
				return homeAddress.getAddressId();
			}
		}
		return preferredAddress;
	}

	public void setPreferredAddress(Long preferredAddress) {
		this.preferredAddress = preferredAddress;
	}

	public PaymentPreference getPaymentPreference() {
		return paymentPreference;
	}

	public void setPaymentPreference(PaymentPreference paymentPreference) {
		this.paymentPreference = paymentPreference;
	}

	public Address getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(Address mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public String getOtherEmail() {
		return otherEmail;
	}

	public void setOtherEmail(String otherEmail) {
		this.otherEmail = otherEmail;
	}

	public String getOtherPhone() {
		return otherPhone;
	}

	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
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

	public String getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public ParticipantCurrentEducationProfile getCurrentEducationProfile() {
		return currentEducationProfile;
	}

	public void setCurrentEducationProfile(ParticipantCurrentEducationProfile currentEducationProfile) {
		this.currentEducationProfile = currentEducationProfile;
	}

	public List<ThinApp> getApplicationSet() {
		return applicationSet;
	}

	public void setApplicationSet(List<ThinApp> applicationSet) {
		this.applicationSet = applicationSet;
	}

	public Set<Program> getPrograms() {
		return programs;
	}

	public void setPrograms(Set<Program> programs) {
		this.programs = programs;
	}

	public Set<ParticipantSupervisor> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(Set<ParticipantSupervisor> supervisors) {
		this.supervisors = supervisors;
	}

	public Long getACLSID_SIDID() {
		return ACLSID_SIDID;
	}

	public void setACLSID_SIDID(Long aCLSIDSIDID) {
		ACLSID_SIDID = aCLSIDSIDID;
	}

	public Participant getLevelOneSupervisor() {
		return levelOneSupervisor;
	}

	public void setLevelOneSupervisor(Participant levelOneSupervisor) {
		this.levelOneSupervisor = levelOneSupervisor;
	}

	public Participant getLevelTwoSupervisor() {
		return levelTwoSupervisor;
	}

	public void setLevelTwoSupervisor(Participant levelTwoSupervisor) {
		this.levelTwoSupervisor = levelTwoSupervisor;
	}

	@Transient
	public String getPreferEmail() {
		String processed = getProcessedPreferredEmail();
		if (processed.equals("Work")) {
			if (StringUtils.isNotBlank(workAddress.getEmail())) {
				return workAddress.getEmail();
			} else {
				return homeAddress.getEmail();
			}
		} else {
			if (StringUtils.isNotBlank(homeAddress.getEmail())) {
				return homeAddress.getEmail();
			} else {
				return workAddress.getEmail();
			}
		}
	}

	@Transient
	public boolean getActiveIndicator() {
		return this.getActive() != null && this.getActive().isActive();
	}

	@Transient
	private String getProcessedPreferredEmail() {
		if (preferredEmail != null && !preferredEmail.equals("")) {
			return preferredEmail;
		} else {
			return this.getClient().getDefaultEmailAddress();
		}
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getSsoId() {
		return ssoId;
	}
	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof Participant)) {
			return false;
		}

		Participant objAsParticipant = (Participant) obj;
		if (objAsParticipant.participantId == null || objAsParticipant.participantId == 0) {
			return false;
		}
		return this.participantId.equals(objAsParticipant.participantId);
	}

	@Override
	public String toString() {
		return "Participant{" + "participantId=" + participantId + ", ssn='" + ssn + '\'' + ", user=" + user + ", active=" + active + ", jobTitle='" + jobTitle + '\'' + ", email='" + email + '\''
				+ ", employeeId='" + employeeId + '\'' + ", generic5='" + generic5 + '\'' + ", companyName='" + companyName + '\'' + ", dateCreated=" + dateCreated + ", modifiedDate=" + modifiedDate
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", client=" + client + ", educationLevelId=" + educationLevelId + ", homeAddress=" + homeAddress + ", workAddress="
				+ workAddress + ", preferredAddress=" + preferredAddress + ", paymentPreference=" + paymentPreference + ", mailingAddress=" + mailingAddress + ", otherEmail='" + otherEmail + '\''
				+ ", otherPhone='" + otherPhone + '\'' + ", preferredEmail='" + preferredEmail + '\'' + ", preferredPhone='" + preferredPhone + '\'' + ", currentEducationProfile="
				+ currentEducationProfile + ", applicationSet=" + applicationSet + ", programs=" + programs + ", supervisors=" + supervisors + ", ACLSID_SIDID=" + ACLSID_SIDID
				+ ", levelOneSupervisor=" + levelOneSupervisor + ", levelTwoSupervisor=" + levelTwoSupervisor + '}';
	}

	public enum ACTIVE_TYPE {
		Y("Active", true), S("Active", true), N("Inactive", false), B("Inactive", false);

		private final String displayText;
		private final boolean isActive;

		ACTIVE_TYPE(String display, boolean activeIndicator) {
			this.displayText = display;
			this.isActive = activeIndicator;
		}

		public String getDisplayText() {
			return displayText;
		}

		public boolean isActive() {
			return isActive;
		}
	}
}
