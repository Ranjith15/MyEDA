package com.edassist.models.domain;

import javax.persistence.*;

@Entity
@Table(name = "ParticipantSupervisor")
public class ParticipantSupervisor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ParticipantSupervisorID")
	private Long participantSupervisorID;

	@Column(name = "SupervisorID")
	private String supervisorID;

	@Basic(optional = false)
	@Column(name = "ApprovalLevel")
	private int approvalLevel;

	@Basic(optional = false)
	@Column(name = "IdType")
	private char idType;

	@JoinColumn(name = "ParticipantID", referencedColumnName = "ParticipantID")
	@ManyToOne(optional = false, cascade = CascadeType.REFRESH)
	private Participant participantID;

	@Column(name = "Supervisor")
	private Long supervisor;

	public ParticipantSupervisor() {
		super();
	}

	public ParticipantSupervisor(Long participantSupervisorID) {
		this.participantSupervisorID = participantSupervisorID;
	}

	public ParticipantSupervisor(Long participantSupervisorID, int approvalLevel, char idType) {
		this.participantSupervisorID = participantSupervisorID;
		this.approvalLevel = approvalLevel;
		this.idType = idType;
	}

	public Long getParticipantSupervisorID() {
		return participantSupervisorID;
	}

	public void setParticipantSupervisorID(Long participantSupervisorID) {
		this.participantSupervisorID = participantSupervisorID;
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

	public Participant getParticipantID() {
		return participantID;
	}

	public void setParticipantID(Participant participantID) {
		this.participantID = participantID;
	}

	public Long getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Long supervisor) {
		this.supervisor = supervisor;
	}
}
