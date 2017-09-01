package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ApprovalHistory")
public class ApprovalHistory {

	@Id
	@Column(name = "ApprovalHistoryID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long approvalHistoryID;

	@Basic(optional = false)
	@Column(name = "ApprovalLevel")
	private int approvalLevel;

	@Deprecated
	@Column(name = "SupervisorID")
	private String supervisorID;

	@Deprecated
	@Basic(optional = false)
	@Column(name = "IdType")
	private char idType;

	@JoinColumn(name = "Supervisor", referencedColumnName = "ParticipantID")
	@ManyToOne(optional = false, cascade = CascadeType.REFRESH)
	private Participant supervisor;

	@Column(name = "ActionDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date actionDate;

	@JoinColumn(name = "ApprovalTypeID", referencedColumnName = "ApprovalTypeID")
	@ManyToOne
	private ApprovalType approvalTypeID;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@JoinColumn(name = "CreatedBy", referencedColumnName = "UserID")
	@ManyToOne(optional = false)
	private ThinUser createdBy;

	@Column(name = "ModifiedBy")
	private Long modifiedBy;

	@Column(name = "ApplicationID")
	private Long applicationID;

	@Basic(optional = true)
	@Column(name = "EligibleForReassignment")
	private Boolean eligibleForReassignment = Boolean.TRUE;

	public ApprovalHistory() {
		super();
	}

	public ApprovalHistory(Long approvalHistoryID) {
		super();
		this.approvalHistoryID = approvalHistoryID;
	}

	public ApprovalHistory(int approvalLevel, ApprovalType approvalType, Long application, Participant supervisor, Date dateCreated, ThinUser createdBy) {
		super();
		this.applicationID = application;
		this.approvalLevel = approvalLevel;
		this.approvalTypeID = approvalType;
		this.supervisor = supervisor;
		this.dateCreated = dateCreated;
		this.createdBy = createdBy;
	}

	public Long getApprovalHistoryID() {
		return approvalHistoryID;
	}

	public void setApprovalHistoryID(Long approvalHistoryID) {
		this.approvalHistoryID = approvalHistoryID;
	}

	public ApprovalType getApprovalTypeID() {
		return approvalTypeID;
	}

	public void setApprovalTypeID(ApprovalType approvalTypeID) {
		this.approvalTypeID = approvalTypeID;
	}

	public String getSupervisorID() {
		return supervisorID;
	}

	public void setSupervisorID(String supervisorID) {
		this.supervisorID = supervisorID;
	}

	public int getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(int approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public char getIdType() {
		return idType;
	}

	public void setIdType(char idType) {
		this.idType = idType;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
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

	public ThinUser getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(ThinUser createdBy) {
		this.createdBy = createdBy;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public Participant getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Participant supervisor) {
		this.supervisor = supervisor;
	}
}
