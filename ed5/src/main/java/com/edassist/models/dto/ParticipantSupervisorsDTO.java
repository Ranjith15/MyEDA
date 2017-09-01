package com.edassist.models.dto;

public class ParticipantSupervisorsDTO {

	private ApproverDTO levelOneSupervisor;
	private ApproverDTO levelTwoSupervisor;

	public ApproverDTO getLevelOneSupervisor() {
		return levelOneSupervisor;
	}

	public void setLevelOneSupervisor(ApproverDTO levelOneSupervisor) {
		this.levelOneSupervisor = levelOneSupervisor;
	}

	public ApproverDTO getLevelTwoSupervisor() {
		return levelTwoSupervisor;
	}

	public void setLevelTwoSupervisor(ApproverDTO levelTwoSupervisor) {
		this.levelTwoSupervisor = levelTwoSupervisor;
	}

}
