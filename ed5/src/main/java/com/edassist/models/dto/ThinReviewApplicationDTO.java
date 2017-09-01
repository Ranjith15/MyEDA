package com.edassist.models.dto;

import java.util.Date;

public class ThinReviewApplicationDTO {

	private Long applicationID;
	private Long applicationNumber;
	private Long applicationStatusID;
	private String applicationStatus;
	private Long participantId;
	private String firstName;
	private String mI;
	private String lastName;
	private Date sessionStartDate;
	private String applicationTypeCode;
	private String programName;
	private Date dateModified;

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

	public Long getApplicationStatusID() {
		return applicationStatusID;
	}

	public void setApplicationStatusID(Long applicationStatusID) {
		this.applicationStatusID = applicationStatusID;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public Long getParticipantId() {
		return participantId;
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

	public Date getSessionStartDate() {
		return sessionStartDate;
	}

	public void setSessionStartDate(Date sessionStartDate) {
		this.sessionStartDate = sessionStartDate;
	}

	public String getApplicationTypeCode() {
		return applicationTypeCode;
	}

	public void setApplicationTypeCode(String applicationTypeCode) {
		this.applicationTypeCode = applicationTypeCode;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
}
