package com.edassist.models.domain;

import javax.persistence.*;

@Entity
@Table(name = "ProgramAccreditingBody")
public class ProgramAccreditingBody {

	@Id
	@Column(name = "ProgramAccreditingBodyID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long programAccreditingBodyID;

	@JoinColumn(name = "ProgramID", referencedColumnName = "ProgramID")
	@ManyToOne(optional = false)
	private Program programID;

	@JoinColumn(name = "AccreditingBodyID", referencedColumnName = "AccreditingBodyID")
	@ManyToOne(optional = false)
	private AccreditingBody accreditingBodyID;

	public ProgramAccreditingBody() {
	}

	public Long getProgramAccreditingBodyID() {
		return programAccreditingBodyID;
	}

	public void setProgramAccreditingBodyID(Long programAccreditingBodyID) {
		this.programAccreditingBodyID = programAccreditingBodyID;
	}

	public Program getProgramID() {
		return programID;
	}

	public void setProgramID(Program programID) {
		this.programID = programID;
	}

	public AccreditingBody getAccreditingBodyID() {
		return accreditingBodyID;
	}

	public void setAccreditingBodyID(AccreditingBody accreditingBodyID) {
		this.accreditingBodyID = accreditingBodyID;
	}
}
