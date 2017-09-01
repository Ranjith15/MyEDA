package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "WF_Queue")
public class WF_Queue {

	@Id
	@Column(name = "WF_QueueId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long WF_QueueId;

	@Column(name = "ApplicationId")
	private Long applicationID;

	@Column(name = "CreatedBy")
	private Long createdBy;

	@Column(name = "ModifiedDate")
	private Date modifiedDate;

	@Column(name = "ModifiedBy")
	private Long modifiedBy;

	// TRANSIENT

	@Transient
	private Long clientID;

	@Transient
	private String clientName;

	@Transient
	private Long applicationNumber;

	@Transient
	private Long applicationStatusID;

	@Transient
	private String applicationStatus;

	@Transient
	private String providerName;

	public Long getWF_QueueId() {
		return WF_QueueId;
	}

	public void setWF_QueueId(Long WF_QueueId) {
		this.WF_QueueId = WF_QueueId;
	}

	public Long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	// TRANSIENT

	public Long getClientID() {
		return clientID;
	}

	public void setClientID(Long clientID) {
		this.clientID = clientID;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Long getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public Long getApplicationStatusID() {
		return applicationStatusID;
	}

	public void setApplicationStatusID(Long applicationStatusID) {
		this.applicationStatusID = applicationStatusID;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

}
