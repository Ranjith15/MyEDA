package com.edassist.models.domain;

import javax.persistence.*;

@Entity
@Table(name = "ExceptionRules")
public class ThinExceptionRules {

	@Id
	@Column(name = "RuleId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ruleId;

	@Column(name = "RuleName")
	private String ruleName;

	@Column(name = "FriendlyName")
	private String friendlyName;

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
