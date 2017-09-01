package com.edassist.models.domain;

import javax.persistence.*;

@Entity
@Table(name = "ParticipantExceptionRules")
public class ThinParticipantExceptionRules {

	@Id
	@Column(name = "ExceptionId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long exceptionId;

	@JoinColumn(name = "ExceptionRulesId", referencedColumnName = "RuleId")
	@ManyToOne(optional = false)
	private ThinExceptionRules exceptionRulesId;

	public Long getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(Long exceptionId) {
		this.exceptionId = exceptionId;
	}

	public ThinExceptionRules getExceptionRulesId() {
		return exceptionRulesId;
	}

	public void setExceptionRulesId(ThinExceptionRules exceptionRulesId) {
		this.exceptionRulesId = exceptionRulesId;
	}

}
