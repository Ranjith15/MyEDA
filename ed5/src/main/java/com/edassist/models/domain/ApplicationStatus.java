package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ApplicationStatus")
public class ApplicationStatus {

	public static final String DEFAULT_APPLICATION_STATUS = "Saved - Not Submitted";
	public static final long STATUS_CODE_CANCELLED = 910L;

	@Id
	@Column(name = "ApplicationStatusID")
	private Long applicationStatusID;

	@Basic(optional = false)
	@Column(name = "ApplicationStatusCode")
	private Long applicationStatusCode;

	@Basic(optional = false)
	@Column(name = "ApplicationStatus")
	private String applicationStatus;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Basic(optional = false)
	@Column(name = "CreatedBy")
	private int createdBy;

	@Column(name = "ModifiedBy")
	private Integer modifiedBy;

	@Column(name = "Active")
	private String active;

	public ApplicationStatus() {
		super();
	}

	public ApplicationStatus(Long applicationStatusID) {
		this.applicationStatusID = applicationStatusID;
	}

	public ApplicationStatus(Long applicationStatusID, String applicationStatus, Date dateCreated, int createdBy) {
		this.applicationStatusID = applicationStatusID;
		this.applicationStatus = applicationStatus;
		this.dateCreated = dateCreated;
		this.createdBy = createdBy;
	}

	public Long getApplicationStatusID() {
		return applicationStatusID;
	}

	public void setApplicationStatusID(Long applicationStatusID) {
		this.applicationStatusID = applicationStatusID;
	}

	public Long getApplicationStatusCode() {
		return applicationStatusCode;
	}

	public void setApplicationStatusCode(Long applicationStatusCode) {
		this.applicationStatusCode = applicationStatusCode;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
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

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
}
