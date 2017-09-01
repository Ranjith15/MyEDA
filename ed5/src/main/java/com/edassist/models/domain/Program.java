package com.edassist.models.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "Program")
public class Program implements Serializable {

	public static final Long ONE_APPROVER = 1L;
	public static final Long TWO_APPROVERS = 2L;
	public static final String NOT_ACTIVE = "0";
	public static final String LEVEL_1_ONLY = "1";
	public static final String LEVEL_2_ONLY = "2";
	public static final String ALL = "3";

	@Id
	@Column(name = "ProgramID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long programID;

	@Column(name = "ProgramName")
	private String programName;

	@Column(name = "Active")
	private String active;

	@JoinColumn(name = "ProgramTypeID", referencedColumnName = "ProgramTypeID")
	@ManyToOne
	private ProgramType programTypeID;

	@JoinColumn(name = "ClientID", referencedColumnName = "ClientID")
	@ManyToOne
	private Client clientID;

	@Column(name = "ContactFAX")
	private String contactFAX;

	@Column(name = "DefaultBenefitPeriodID")
	private Long defaultBenefitPeriodID;

	@Column(name = "ApprovalLevels")
	private Long approvalLevels;

	@Enumerated(EnumType.STRING)
	@Column(name = "DeterminationDate")
	private DETERMINATION_TYPE determinationDate;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "CreatedBy")
	private Long createdBy;

	@Column(name = "ModifiedBy")
	private Long modifiedBy;

	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "TaxQuestionsCount")
	private Integer taxQuestionsCount;

	@Column(name = "ProgramCode")
	private String programCode;

	@Column(name = "ShowCourseDescription")
	private Boolean showCourseDescription;

	@Column(name = "ShowCourseSchedule")
	private Boolean showCourseSchedule;

	@Column(name = "MaxNumOfCourses")
	private Integer maxNumOfCourses;

	@Column(name = "reimburseBookProgramID")
	private Long reimburseBookProgramId;

	@Column(name = "WarnIneligibleAccredition")
	private Boolean warnIneligibleAccredition;

	@Column(name = "ApplyPercentagePayOut")
	private Boolean applyPercentagePayOut;

	@Column(name = "DegreeComparisonCompanyApprovalBypass")
	private String degreeComparisonCompanyApprovalBypass = "0";

	@Column(name = "EnableParticipantProgramCompletionDate")
	private Boolean enableParticipantProgramCompletionDate;

	@Column(name = "NonCourseExpenses")
	private boolean nonCourseExpenses;

	@Column(name = "EnableUnknownCapLimit")
	private boolean unknownCapLimitEnabled;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "programID", fetch = FetchType.LAZY)
	private Collection<ProgramAccreditingBody> programAccreditingBodyCollection;

	public Program() {
	}

	public Program(Long programID) {
		this.programID = programID;
	}

	public Long getProgramID() {
		return programID;
	}

	public void setProgramID(Long programID) {
		this.programID = programID;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public ProgramType getProgramTypeID() {
		return programTypeID;
	}

	public void setProgramTypeID(ProgramType programTypeID) {
		this.programTypeID = programTypeID;
	}

	public Client getClientID() {
		return clientID;
	}

	public void setClientID(Client clientID) {
		this.clientID = clientID;
	}

	public String getContactFAX() {
		return contactFAX;
	}

	public void setContactFAX(String contactFAX) {
		this.contactFAX = contactFAX;
	}

	public Long getDefaultBenefitPeriodID() {
		return defaultBenefitPeriodID;
	}

	public void setDefaultBenefitPeriodID(Long defaultBenefitPeriodID) {
		this.defaultBenefitPeriodID = defaultBenefitPeriodID;
	}

	public Long getApprovalLevels() {
		return approvalLevels;
	}

	public void setApprovalLevels(Long approvalLevels) {
		this.approvalLevels = approvalLevels;
	}

	public DETERMINATION_TYPE getDeterminationDate() {
		return determinationDate;
	}

	public void setDeterminationDate(DETERMINATION_TYPE determinationDate) {
		this.determinationDate = determinationDate;
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

	public Integer getTaxQuestionsCount() {
		return taxQuestionsCount;
	}

	public void setTaxQuestionsCount(Integer taxQuestionsCount) {
		this.taxQuestionsCount = taxQuestionsCount;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public Boolean getShowCourseDescription() {
		if (showCourseDescription == null) {
			return Boolean.FALSE;
		}

		return showCourseDescription;
	}

	public void setShowCourseDescription(Boolean showCourseDescription) {
		this.showCourseDescription = showCourseDescription;
	}

	public Boolean getShowCourseSchedule() {
		if (showCourseSchedule == null) {
			return Boolean.FALSE;
		}
		return showCourseSchedule;
	}

	public void setShowCourseSchedule(Boolean showCourseSchedule) {
		this.showCourseSchedule = showCourseSchedule;
	}

	public Integer getMaxNumOfCourses() {
		return maxNumOfCourses;
	}

	public void setMaxNumOfCourses(Integer maxNumOfCourses) {
		this.maxNumOfCourses = maxNumOfCourses;
	}

	public Long getReimburseBookProgramId() {
		return reimburseBookProgramId;
	}

	public void setReimburseBookProgramId(Long reimburseBookProgramId) {
		this.reimburseBookProgramId = reimburseBookProgramId;
	}

	public Boolean getWarnIneligibleAccredition() {
		return warnIneligibleAccredition;
	}

	public void setWarnIneligibleAccredition(Boolean warnIneligibleAccredition) {
		this.warnIneligibleAccredition = warnIneligibleAccredition;
	}

	public Boolean getApplyPercentagePayOut() {
		if (applyPercentagePayOut == null) {
			return Boolean.FALSE;
		}
		return applyPercentagePayOut;
	}

	public void setApplyPercentagePayOut(Boolean applyPercentagePayOut) {
		if (applyPercentagePayOut == null) {
			applyPercentagePayOut = Boolean.FALSE;
		}
		this.applyPercentagePayOut = applyPercentagePayOut;
	}

	public String getDegreeComparisonCompanyApprovalBypass() {
		return degreeComparisonCompanyApprovalBypass;
	}

	public void setDegreeComparisonCompanyApprovalBypass(String degreeComparisonCompanyApprovalBypass) {
		this.degreeComparisonCompanyApprovalBypass = degreeComparisonCompanyApprovalBypass;
	}

	public Boolean getEnableParticipantProgramCompletionDate() {
		return enableParticipantProgramCompletionDate;
	}

	public void setEnableParticipantProgramCompletionDate(Boolean enableParticipantProgramCompletionDate) {
		this.enableParticipantProgramCompletionDate = enableParticipantProgramCompletionDate;
	}

	public boolean getNonCourseExpenses() {
		return nonCourseExpenses;
	}

	public void setNonCourseExpenses(boolean nonCourseExpenses) {
		this.nonCourseExpenses = nonCourseExpenses;
	}

	public boolean isUnknownCapLimitEnabled() {
		return unknownCapLimitEnabled;
	}

	public void setUnknownCapLimitEnabled(boolean unknownCapLimitEnabled) {
		this.unknownCapLimitEnabled = unknownCapLimitEnabled;
	}

	public Collection<ProgramAccreditingBody> getProgramAccreditingBodyCollection() {
		return programAccreditingBodyCollection;
	}

	public void setProgramAccreditingBodyCollection(Collection<ProgramAccreditingBody> programAccreditingBodyCollection) {
		this.programAccreditingBodyCollection = programAccreditingBodyCollection;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}

		if (!(object instanceof Program)) {
			return false;
		}

		Program other = (Program) object;
		if ((this.programID == null && other.programID != null) || (this.programID != null && !this.programID.equals(other.programID))) {
			return false;
		}
		return true;
	}

	public enum DETERMINATION_TYPE {
		CourseStartDate("CourseStartDate", "Course Start Date"), CourseEndDate("CourseEndDate", "Course End Date"), PaymentDate("PaymentDate",
				"Payment Date"), BillingPeriodStartDate("BillingPeriodStartDate", "Billing Period Start Date"), BillingPeriodEndDate("BillingPeriodEndDate", "Billing Period End Date");

		private final String value;
		private final String displayText;

		DETERMINATION_TYPE(String val, String display) {
			this.value = val;
			this.displayText = display;
		}

		public String getValue() {
			return value;
		}

		public String getDisplayText() {
			return displayText;
		}
	}

	public enum ACTIVE_TYPE {
		ACTIVE("1", "Active"), INACTIVE("2", "Inactive");

		private final String value;
		private final String displayText;

		ACTIVE_TYPE(String val, String display) {
			this.value = val;
			this.displayText = display;
		}

		public String getValue() {
			return value;
		}

		public String getDisplayText() {
			return displayText;
		}
	}
}
