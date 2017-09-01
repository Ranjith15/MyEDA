package com.edassist.models.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "DefaultDocuments")
public class DefaultDocuments implements Serializable {

	@Id
	@Column(name = "DefaultDocumentsID")
	private Long defaultDocumentsID;

	@Basic(optional = false)
	@Column(name = "DocumentName")
	private String documentName;

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
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ProgramType programTypeID;

	public DefaultDocuments() {
	}

	public Long getDefaultDocumentsID() {
		return defaultDocumentsID;
	}

	public void setDefaultDocumentsID(Long defaultDocumentsID) {
		this.defaultDocumentsID = defaultDocumentsID;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
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
