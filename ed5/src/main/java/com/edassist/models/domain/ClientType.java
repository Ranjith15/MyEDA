package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ClientType")
public class ClientType {

	@Id
	@Column(name = "ClientTypeID")
	private Long clientTypeID;

	@Column(name = "ClientType")
	private String clientType;

	@Column(name = "CreatedBy")
	private Integer createdBy;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "ModifiedBy")
	private Integer modifiedBy;

	public ClientType() {
	}

	public Long getClientTypeID() {
		return clientTypeID;
	}

	public void setClientTypeID(Long clientTypeID) {
		this.clientTypeID = clientTypeID;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
