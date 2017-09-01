package com.edassist.models.domain;

import javax.persistence.*;

@Entity
@Table(name = "ProgramDegreeType")
public class ProgramDegreeType {

	@Id
	@Column(name = "ProgramDegreeTypeID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long programDegreeTypeID;

	@ManyToOne
	@JoinColumn(name = "DegreeObjectiveID")
	private DegreeObjectives degreeObjectiveID;

	@ManyToOne
	@JoinColumn(name = "ProgramID")
	private Program programID;

	public ProgramDegreeType() {
		super();
	}

	public Long getProgramDegreeTypeID() {
		return programDegreeTypeID;
	}

	public void setProgramDegreeTypeID(Long programDegreeTypeID) {
		this.programDegreeTypeID = programDegreeTypeID;
	}

	public DegreeObjectives getDegreeObjectiveID() {
		return degreeObjectiveID;
	}

	public void setDegreeObjectiveID(DegreeObjectives degreeObjectiveID) {
		this.degreeObjectiveID = degreeObjectiveID;
	}

	public Program getProgramID() {
		return programID;
	}

	public void setProgramID(Program programID) {
		this.programID = programID;
	}

}
