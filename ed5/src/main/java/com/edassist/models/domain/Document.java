package com.edassist.models.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Documents")
public class Document implements Serializable {

	@Id
	@Column(name = "DocumentID")
	private Integer documentID;

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

	@JoinColumn(name = "DefaultDocumentsID", referencedColumnName = "DefaultDocumentsID")
	@ManyToOne
	private DefaultDocuments defaultDocumentsID;

	@Column(name = "ProgramID")
	private long programId;

	public Document() {
		super();
	}

	public Integer getDocumentID() {
		return documentID;
	}

	public void setDocumentID(Integer documentID) {
		this.documentID = documentID;
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

	public DefaultDocuments getDefaultDocumentsID() {
		return defaultDocumentsID;
	}

	public void setDefaultDocumentsID(DefaultDocuments defaultDocumentsID) {
		this.defaultDocumentsID = defaultDocumentsID;
	}

	public long getProgramId() {
		return programId;
	}

	public void setProgramId(long programId) {
		this.programId = programId;
	}

}
