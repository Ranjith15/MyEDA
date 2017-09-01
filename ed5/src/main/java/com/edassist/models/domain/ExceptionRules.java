package com.edassist.models.domain;

import javax.persistence.*;

@Entity
@Table(name = "ExceptionRules")
public class ExceptionRules {

	@Id
	@Column(name = "RuleId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ruleId;

	@JoinColumn(name = "clientId", referencedColumnName = "ClientID")
	@ManyToOne
	private Client clientId;

	public ExceptionRules() {
		super();
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public Client getClientId() {
		return clientId;
	}

	public void setClientId(Client clientId) {
		this.clientId = clientId;
	}

}
