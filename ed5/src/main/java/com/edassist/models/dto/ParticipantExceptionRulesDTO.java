package com.edassist.models.dto;

public class ParticipantExceptionRulesDTO {

	private Long exceptionId;

	private Long ruleId;

	private String ruleName;

	private String friendlyName;

	public Long getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(Long exceptionId) {
		this.exceptionId = exceptionId;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

}
