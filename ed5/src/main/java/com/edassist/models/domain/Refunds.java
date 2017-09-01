package com.edassist.models.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Refunds")
public class Refunds {

	@Id
	@Column(name = "RefundID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long refundID;

	@Column(name = "ApplicationID")
	private Long applicationID;

	@JoinColumn(name = "RefundMethodId", referencedColumnName = "RefundMethodId")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private RefundMethod refundMethodId;

	@Column(name = "CheckRef")
	private String checkRef;

	@Column(name = "DateReceived")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateReceived;

	@Column(name = "RefundAmount")
	private BigDecimal refundAmount;

	@Column(name = "AppliedAmount")
	private BigDecimal appliedAmount;

	@Column(name = "Comments")
	private String comments;

	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@JoinColumn(name = "CreatedBy", referencedColumnName = "UserID")
	@ManyToOne(optional = false)
	private ThinUser createdBy;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "ModifiedBy")
	private Long modifiedBy;

	public Long getRefundID() {
		return refundID;
	}

	public void setRefundID(Long refundID) {
		this.refundID = refundID;
	}

	public Long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public RefundMethod getRefundMethodId() {
		return refundMethodId;
	}

	public void setRefundMethodId(RefundMethod refundMethodId) {
		this.refundMethodId = refundMethodId;
	}

	public String getCheckRef() {
		return checkRef;
	}

	public void setCheckRef(String checkRef) {
		this.checkRef = checkRef;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public BigDecimal getAppliedAmount() {
		return appliedAmount;
	}

	public void setAppliedAmount(BigDecimal appliedAmount) {
		this.appliedAmount = appliedAmount;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public ThinUser getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(ThinUser createdBy) {
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
}
