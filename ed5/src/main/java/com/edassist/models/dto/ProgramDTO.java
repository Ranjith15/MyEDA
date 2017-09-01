package com.edassist.models.dto;

import java.util.Collection;

public class ProgramDTO {

	private Long programID;
	private String programName;
	private String contactFAX;
	private boolean enableParticipantProgramCompletionDate;
	private Integer taxQuestionsCount;
	private boolean warnIneligibleAccredition;
	private Integer maxNumOfCourses;
	private ProgramTypeDTO programTypeID;
	private Long reimburseBookProgramId;
	private Collection<ProgramAccreditingBodyDTO> programAccreditingBodyCollection;
	private boolean nonCourseExpenses;
	private Boolean showCourseDescription;
	private Boolean showCourseSchedule;
	private boolean unknownCapLimitEnabled;
	private Long defaultBenefitPeriodID;
	private Boolean displayFaxCoverSheet;

	public void setProgramID(Long programID) {
		this.programID = programID;
	}

	public Long getProgramID() {
		return programID;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setEnableParticipantProgramCompletionDate(boolean enableParticipantProgramCompletionDate) {
		this.enableParticipantProgramCompletionDate = enableParticipantProgramCompletionDate;
	}

	public boolean isEnableParticipantProgramCompletionDate() {
		return enableParticipantProgramCompletionDate;
	}

	public void setTaxQuestionsCount(Integer taxQuestionsCount) {
		this.taxQuestionsCount = taxQuestionsCount;
	}

	public Integer getTaxQuestionsCount() {
		return taxQuestionsCount;
	}

	public boolean isWarnIneligibleAccredition() {
		return warnIneligibleAccredition;
	}

	public void setWarnIneligibleAccredition(boolean warnIneligibleAccredition) {
		this.warnIneligibleAccredition = warnIneligibleAccredition;
	}

	public Integer getMaxNumOfCourses() {
		return maxNumOfCourses;
	}

	public void setMaxNumOfCourses(Integer maxNumOfCourses) {
		this.maxNumOfCourses = maxNumOfCourses;
	}

	public ProgramTypeDTO getProgramTypeID() {
		return programTypeID;
	}

	public void setProgramTypeID(ProgramTypeDTO programTypeID) {
		this.programTypeID = programTypeID;
	}

	public Long getReimburseBookProgramId() {
		return reimburseBookProgramId;
	}

	public void setReimburseBookProgramId(Long reimburseBookProgramId) {
		this.reimburseBookProgramId = reimburseBookProgramId;
	}

	public Collection<ProgramAccreditingBodyDTO> getProgramAccreditingBodyCollection() {
		return programAccreditingBodyCollection;
	}

	public void setProgramAccreditingBodyCollection(Collection<ProgramAccreditingBodyDTO> programAccreditingBodyCollection) {
		this.programAccreditingBodyCollection = programAccreditingBodyCollection;
	}

	public String getContactFAX() {
		return contactFAX;
	}

	public void setContactFAX(String contactFAX) {
		this.contactFAX = contactFAX;
	}

	public boolean getNonCourseExpenses() {
		return nonCourseExpenses;
	}

	public void setNonCourseExpenses(boolean nonCourseExpenses) {
		this.nonCourseExpenses = nonCourseExpenses;
	}

	public Boolean getShowCourseDescription() {
		return showCourseDescription;
	}

	public void setShowCourseDescription(Boolean showCourseDescription) {
		this.showCourseDescription = showCourseDescription;
	}

	public Boolean getShowCourseSchedule() {
		return showCourseSchedule;
	}

	public void setShowCourseSchedule(Boolean showCourseSchedule) {
		this.showCourseSchedule = showCourseSchedule;
	}

	public boolean isUnknownCapLimitEnabled() {
		return unknownCapLimitEnabled;
	}

	public void setUnknownCapLimitEnabled(boolean unknownCapLimitEnabled) {
		this.unknownCapLimitEnabled = unknownCapLimitEnabled;
	}

	public Long getDefaultBenefitPeriodID() {
		return defaultBenefitPeriodID;
	}

	public void setDefaultBenefitPeriodID(Long defaultBenefitPeriodID) {
		this.defaultBenefitPeriodID = defaultBenefitPeriodID;
	}

	public Boolean getDisplayFaxCoverSheet() {
		return displayFaxCoverSheet;
	}

	public void setDisplayFaxCoverSheet(Boolean displayFaxCoverSheet) {
		this.displayFaxCoverSheet = displayFaxCoverSheet;
	}

}
