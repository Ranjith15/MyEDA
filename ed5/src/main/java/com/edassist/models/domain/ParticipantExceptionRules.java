package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ParticipantExceptionRules")
public class ParticipantExceptionRules {

	@Id
	@Column(name = "ExceptionId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long exceptionId;

	@JoinColumn(name = "ParticipantId", referencedColumnName = "ParticipantID")
	@ManyToOne
	private Participant participantID;

	@JoinColumn(name = "ExceptionRulesId", referencedColumnName = "RuleId")
	@ManyToOne(optional = false)
	private ExceptionRules exceptionRulesId;

	@JoinColumn(name = "ProgramId", referencedColumnName = "ProgramID")
	@ManyToOne()
	private Program programID;

	@JoinColumn(name = "BenefitPeriodId", referencedColumnName = "BenefitPeriodID")
	@ManyToOne()
	private BenefitPeriod benefitPeriodID;

	@Column(name = "Comment")
	private String comment;

	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;


	public ParticipantExceptionRules() {
		super();
	}

	public Long getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(Long exceptionId) {
		this.exceptionId = exceptionId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public BenefitPeriod getBenefitPeriodID() {
		return benefitPeriodID;
	}

	public void setBenefitPeriodID(BenefitPeriod benefitPeriodID) {
		this.benefitPeriodID = benefitPeriodID;
	}

	public ExceptionRules getExceptionRulesId() {
		return exceptionRulesId;
	}

	public void setExceptionRulesId(ExceptionRules exceptionRulesId) {
		this.exceptionRulesId = exceptionRulesId;
	}

	public Participant getParticipantID() {
		return participantID;
	}

	public void setParticipantID(Participant participantID) {
		this.participantID = participantID;
	}

	public Program getProgramID() {
		return programID;
	}

	public void setProgramID(Program programID) {
		this.programID = programID;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
