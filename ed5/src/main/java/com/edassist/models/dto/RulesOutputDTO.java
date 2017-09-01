package com.edassist.models.dto;

import java.util.List;

public class RulesOutputDTO {

	private String applicationID;
	private String participantID;
	private String stage;
	private String status;
	private double capLimit;
	private double capTuitionAmount;
	private boolean overCapIndicator;
	private List<ProgramDTO> programs;
	private List<RulesDTO> rules;

	public String getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}

	public String getParticipantID() {
		return participantID;
	}

	public void setParticipantID(String participantID) {
		this.participantID = participantID;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getCapLimit() {
		return capLimit;
	}

	public void setCapLimit(double capLimit) {
		this.capLimit = capLimit;
	}

	public double getCapTuitionAmount() {
		return capTuitionAmount;
	}

	public void setCapTuitionAmount(double capTuitionAmount) {
		this.capTuitionAmount = capTuitionAmount;
	}

	public boolean isOverCapIndicator() {
		return overCapIndicator;
	}

	public void setOverCapIndicator(boolean overCapIndicator) {
		this.overCapIndicator = overCapIndicator;
	}

	public List<ProgramDTO> getPrograms() {
		return programs;
	}

	public void setPrograms(List<ProgramDTO> programs) {
		this.programs = programs;
	}

	public List<RulesDTO> getRules() {
		return rules;
	}

	public void setRules(List<RulesDTO> rules) {
		this.rules = rules;
	}

}
