package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "FinancialAidSource")
public class FinancialAidSource {

	public static final String DEFAULT_FINANCIAL_AID_SOURCE_DESC = "None";

	@Id
	@Column(name = "FinancialAidSourceID")
	private Long financialAidSourceId;

	@Column(name = "FinancialAidDesc")
	private String financialAidDescription;

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

	public FinancialAidSource() {
		super();
	}

	public Long getFinancialAidSourceId() {
		return financialAidSourceId;
	}

	public void setFinancialAidSourceId(Long financialAidSourceId) {
		this.financialAidSourceId = financialAidSourceId;
	}

	public String getFinancialAidDescription() {
		return financialAidDescription;
	}

	public void setFinancialAidDescription(String financialAidDescription) {
		this.financialAidDescription = financialAidDescription;
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
