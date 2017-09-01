package com.edassist.models.domain;

import com.edassist.models.domain.type.ProgramAndApplicationTypeCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ProgramType")
public class ProgramType {

	@Id
	@Column(name = "ProgramTypeID")
	private Long programTypeID;

	@Basic(optional = false)
	@Column(name = "ProgramType")
	private String programType;

	@Column(name = "ProgramTypeCode")
	@Enumerated(EnumType.STRING)
	private ProgramAndApplicationTypeCode programTypeCode;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Basic(optional = false)
	@Column(name = "CreatedBy")
	private int createdBy;

	@Column(name = "ModifiedBy")
	private Long modifiedBy;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	public ProgramType() {
	}

	public ProgramType(Long programTypeID) {
		this.programTypeID = programTypeID;
	}

	public ProgramType(Long programTypeID, String programType, int createdBy, Date dateCreated) {
		this.programTypeID = programTypeID;
		this.programType = programType;
		this.createdBy = createdBy;
		this.dateCreated = dateCreated;
	}

	public Long getProgramTypeID() {
		return programTypeID;
	}

	public void setProgramTypeID(Long programTypeID) {
		this.programTypeID = programTypeID;
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public ProgramAndApplicationTypeCode getProgramTypeCode() {
		return programTypeCode;
	}

	public void setProgramTypeCode(ProgramAndApplicationTypeCode programTypeCode) {
		this.programTypeCode = programTypeCode;
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

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

}
