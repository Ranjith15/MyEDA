package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ProgramFailingGrade")
public class ProgramFailingGrade {

	@Id
	@Column(name = "ProgramFailingGradeID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long programFailingGradeID;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "CreatedBy")
	private int createdBy;

	@Column(name = "ModifiedBy")
	private Integer modifiedBy;

	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@JoinColumn(name = "GradeID", referencedColumnName = "GradeID")
	@ManyToOne(optional = false)
	private Grades gradeID;

	@JoinColumn(name = "ProgramID", referencedColumnName = "ProgramID")
	@ManyToOne(optional = false)
	private Program programID;

	public ProgramFailingGrade() {
	}

	public Long getProgramFailingGradeID() {
		return programFailingGradeID;
	}

	public void setProgramFailingGradeID(Long programFailingGradeID) {
		this.programFailingGradeID = programFailingGradeID;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Grades getGradeID() {
		return gradeID;
	}

	public void setGradeID(Grades gradeID) {
		this.gradeID = gradeID;
	}

	public Program getProgramID() {
		return programID;
	}

	public void setProgramID(Program programID) {
		this.programID = programID;
	}

}
