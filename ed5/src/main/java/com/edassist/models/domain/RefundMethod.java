package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RefundMethod")
public class RefundMethod {

	@Id
	@Column(name = "RefundMethodId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long refundMethodId;

	@Column(name = "RefundMethod")
	private String refundMethod;

	@Column(name = "DateCreated")
	private Date dateCreated;

	@Column(name = "ModifiedDate")
	private Date modifiedDate;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "CreatedBy")
	private User createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ModifiedBy")
	private User modifiedBy;

	public RefundMethod() {
	}

	public Long getRefundMethodId() {
		return refundMethodId;
	}

	public void setRefundMethodId(Long refundMethodId) {
		this.refundMethodId = refundMethodId;
	}

	public String getRefundMethod() {
		return refundMethod;
	}

	public void setRefundMethod(String refundMethod) {
		this.refundMethod = refundMethod;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
