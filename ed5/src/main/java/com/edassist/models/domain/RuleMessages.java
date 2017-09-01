package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RuleMessages")
public class RuleMessages {

	@Id
	@Column(name = "RuleMessagesId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ruleMessagesId;

	@Column(name = "RuleStatus")
	private String ruleStatus;

	@Column(name = "RuleName")
	private String ruleName;

	@Column(name = "RuleMessage")
	private String ruleMessage;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@JoinColumn(name = "ApplicationID", referencedColumnName = "ApplicationID")
	@ManyToOne(optional = false)
	private App application;

	@Column(name = "RuleStatusID")
	private Long ruleStatusId;

	public RuleMessages() {}

	public Long getRuleMessagesId() {
		return ruleMessagesId;
	}

	public void setRuleMessagesId(Long ruleMessagesId) {
		this.ruleMessagesId = ruleMessagesId;
	}

	public String getRuleStatus() {
		return ruleStatus;
	}

	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleMessage() {
		return ruleMessage;
	}

	public void setRuleMessage(String ruleMessage) {
		this.ruleMessage = ruleMessage;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public App getApplication() {
		return application;
	}

	public void setApplication(App application) {
		this.application = application;
	}

	public Long getRuleStatusId() {
		return ruleStatusId;
	}

	public void setRuleStatusId(Long ruleStatusId) {
		this.ruleStatusId = ruleStatusId;
	}

}
