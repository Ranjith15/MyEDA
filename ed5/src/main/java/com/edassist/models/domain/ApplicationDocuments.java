package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ApplicationDocuments")
public class ApplicationDocuments {

	@Id
	@Column(name = "ApplicationDocumentID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long applicationDocumentID;

	@Column(name = "Received")
	private boolean received;

	@Column(name = "DateReceived")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateReceived;

	@Column(name = "Incomplete")
	private boolean incomplete;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Basic(optional = false)
	@Column(name = "CreatedBy")
	private Long createdBy;

	@Column(name = "ModifiedBy")
	private Long modifiedBy;

	@Column(name = "ApplicationID")
	private Long applicationID;

	@JoinColumn(name = "DefaultDocumentsID", referencedColumnName = "DefaultDocumentsID")
	@ManyToOne(optional = false)
	private DefaultDocuments defaultDocumentsID;

	@Column(name = "DMSLocation")
	private String dmsLocation;

	@Column(name = "VisibleToParticipant")
	private boolean visibleToParticipant;

	public ApplicationDocuments() {
		super();
	}

	public Long getApplicationDocumentID() {
		return applicationDocumentID;
	}

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public boolean isIncomplete() {
		return incomplete;
	}

	public void setIncomplete(boolean incomplete) {
		this.incomplete = incomplete;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public DefaultDocuments getDefaultDocumentsID() {
		return defaultDocumentsID;
	}

	public void setDefaultDocumentsID(DefaultDocuments defaultDocumentsID) {
		this.defaultDocumentsID = defaultDocumentsID;
	}

	public String getDmsLocation() {
		return dmsLocation;
	}

	public void setDmsLocation(String dmsLocation) {
		this.dmsLocation = dmsLocation;
	}

	public boolean isVisibleToParticipant() {
		return visibleToParticipant;
	}

	public void setVisibleToParticipant(boolean visibleToParticipant) {
		this.visibleToParticipant = visibleToParticipant;
	}

}
