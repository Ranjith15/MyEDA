package com.edassist.models.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ProgramApplicationSource")
public class ProgramApplicationSource implements Serializable {

	@Id
	@Column(name = "ProgramApplicationSourceID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long programApplicationSourceID;

	@JoinColumn(name = "ProgramID", referencedColumnName = "ProgramID")
	@ManyToOne(optional = false)
	private Program programID;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Basic(optional = false)
	@Column(name = "CreatedBy")
	private int createdBy;

	@Column(name = "ModifiedBy")
	private Integer modifiedBy;

	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	public ProgramApplicationSource() {
	}

	public Long getProgramApplicationSourceID() {
		return programApplicationSourceID;
	}

	public void setProgramApplicationSourceID(Long programApplicationSourceID) {
		this.programApplicationSourceID = programApplicationSourceID;
	}

	public Program getProgramID() {
		return programID;
	}

	public void setProgramID(Program programID) {
		this.programID = programID;
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
}
