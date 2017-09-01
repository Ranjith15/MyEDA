package com.edassist.models.domain;

import javax.persistence.*;

@Entity
@Table(name = "ApprovalHistory")
public class ThinAppActivityForMyTeam {

	@Id
	@Column(name = "ApprovalHistoryID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long approvalHistoryID;

	@JoinColumn(name = "Supervisor", referencedColumnName = "ParticipantID")
	@ManyToOne(optional = false, cascade = CascadeType.REFRESH)
	private Participant supervisor;

	@JoinColumn(name = "ApplicationID", referencedColumnName = "ApplicationID")
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	private ThinApp applicationID;

	@JoinColumn(name = "ApprovalTypeID", referencedColumnName = "ApprovalTypeID")
	@ManyToOne
	private ApprovalType approvalTypeID;

	public Long getApprovalHistoryID() {
		return approvalHistoryID;
	}

	public void setApprovalHistoryID(Long approvalHistoryID) {
		this.approvalHistoryID = approvalHistoryID;
	}

	public Participant getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Participant supervisor) {
		this.supervisor = supervisor;
	}

	public ThinApp getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(ThinApp applicationID) {
		this.applicationID = applicationID;
	}

	public ApprovalType getApprovalTypeID() {
		return approvalTypeID;
	}

	public void setApprovalTypeID(ApprovalType approvalTypeID) {
		this.approvalTypeID = approvalTypeID;
	}

}