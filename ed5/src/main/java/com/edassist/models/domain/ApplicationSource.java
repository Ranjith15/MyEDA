package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ApplicationSource")
public class ApplicationSource {

	@Id
	@Column(name = "ApplicationSourceID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long applicationSourceID;

	@Column(name = "ApplicationSourceDesc")
	private String applicationSourceDesc;

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
	private Long modifiedBy;

	public ApplicationSource() {
		super();
	}

	public Long getApplicationSourceID() {
		return applicationSourceID;
	}

	public void setApplicationSourceID(Long applicationSourceID) {
		this.applicationSourceID = applicationSourceID;
	}

	public String getApplicationSourceDesc() {
		return applicationSourceDesc;
	}

	public void setApplicationSourceDesc(String applicationSourceDesc) {
		this.applicationSourceDesc = applicationSourceDesc;
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

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
