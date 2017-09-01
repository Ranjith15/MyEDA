package com.edassist.models.dto;

public class RulesDTO {
	private String ruleName;
	private String ruleStatus;
	private String ruleMessage;

	public String getRuleMessage() {
		return ruleMessage;
	}

	public void setRuleMessage(String s) {
		ruleMessage = s;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String s) {
		ruleName = s;
	}

	public String getRuleStatus() {
		return ruleStatus;
	}

	public void setRuleStatus(String s) {
		ruleStatus = s;
	}
}
