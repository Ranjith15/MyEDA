package com.edassist.models.domain;

import com.edassist.models.domain.type.ProgramAndApplicationTypeCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ApplicationType")
public class ApplicationType {

	@Id
	@Column(name = "ApplicationTypeID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long applicationTypeID;

	@Column(name = "ApplicationTypeName")
	private String applicationTypeName;

	@Column(name = "ApplicationTypeCode")
	@Enumerated(EnumType.STRING)
	private ProgramAndApplicationTypeCode applicationTypeCode;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Basic(optional = false)
	@Column(name = "CreatedBy")
	private int createdBy;

	@Column(name = "ModifiedBy")
	private Integer modifiedBy;

	@JoinColumn(name = "ProgramTypeID", referencedColumnName = "ProgramTypeID")
	@ManyToOne(optional = false)
	private ProgramType programTypeID;

	public ApplicationType() {
		super();
	}

	public ApplicationType(Long applicationTypeID) {
		this.applicationTypeID = applicationTypeID;
	}

	public ApplicationType(Long applicationTypeID, ProgramAndApplicationTypeCode applicationTypeCode, Date dateCreated, int createdBy) {
		this.applicationTypeID = applicationTypeID;
		this.applicationTypeCode = applicationTypeCode;
		this.dateCreated = dateCreated;
		this.createdBy = createdBy;
	}

	public Long getApplicationTypeID() {
		return applicationTypeID;
	}

	public void setApplicationTypeID(Long applicationTypeID) {
		this.applicationTypeID = applicationTypeID;
	}

	public String getApplicationTypeName() {
		return applicationTypeName;
	}

	public void setApplicationTypeName(String applicationTypeName) {
		this.applicationTypeName = applicationTypeName;
	}

	public ProgramAndApplicationTypeCode getApplicationTypeCode() {
		return applicationTypeCode;
	}

	public void setApplicationTypeCode(ProgramAndApplicationTypeCode applicationTypeCode) {
		this.applicationTypeCode = applicationTypeCode;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
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

	public ProgramType getProgramTypeID() {
		return programTypeID;
	}

	public void setProgramTypeID(ProgramType programTypeID) {
		this.programTypeID = programTypeID;
	}
}
