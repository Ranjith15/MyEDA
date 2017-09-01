package com.edassist.models.sp;

import com.edassist.models.domain.ThinUser;

import javax.persistence.*;

@Entity
@NamedStoredProcedureQuery(
		name = "findThinSupervisor",
		procedureName = "findThinSupervisor",
		parameters = {
				@StoredProcedureParameter(type = Long.class, name = "participantID")
		},
		resultClasses = ThinSupervisor.class)
public class ThinSupervisor {

	@Id
	private Long participantSupervisorId;

	@Column(name = "ApprovalLevel")
	private int approvalLevel;

	@Column(name = "Supervisor")
	private Long supervisor;

	@Column(name = "AppealDesignee")
	private boolean appealDesignee;

	@Column(name = "UserID")
	private Long userID;

	@Transient
	private ThinUser userName = new ThinUser();

	public void setParticipantSupervisorId(Long participantSupervisorId) {
		this.participantSupervisorId = participantSupervisorId;
	}

	public Long getParticipantSupervisorId() {
		return participantSupervisorId;
	}

	public void setApprovalLevel(int approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public int getApprovalLevel() {
		return approvalLevel;
	}

	public void setSupervisor(Long supervisor) {
		this.supervisor = supervisor;
	}

	public Long getSupervisor() {
		return supervisor;
	}

	public void setAppealDesignee(boolean appealDesignee) {
		this.appealDesignee = appealDesignee;
	}

	public boolean isAppealDesignee() {
		return appealDesignee;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserName(ThinUser userName) {
		this.userName = userName;
	}

	public ThinUser getUserName() {
		return userName;
	}

	public boolean isLevelOne() {
		boolean levelOne = false;
		if (this.approvalLevel == 1) {
			levelOne = true;
		}

		return levelOne;
	}

	public boolean isLevelTwo() {
		boolean levelTwo = false;
		if (this.approvalLevel == 2) {
			levelTwo = true;
		}

		return levelTwo;
	}
}
