package com.edassist.models.domain;

import com.edassist.utils.CommonUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ProviderAccreditation")
public class ProviderAccreditation {

	@Id
	@Column(name = "ProviderAccreditationID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long providerAccreditationID;

	@Column(name = "EffectiveStartDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveStartDate;

	@Column(name = "EffectiveEndDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveEndDate;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Basic(optional = false)
	@Column(name = "CreatedBy")
	private Integer createdBy;

	@Column(name = "ModifiedBy")
	private Integer modifiedBy;

	@Column(name = "ProviderCode")
	private String providerCode;

	@JoinColumn(name = "AccreditingBodyID", referencedColumnName = "AccreditingBodyID")
	@ManyToOne
	private AccreditingBody accreditingBodyID;

	@JoinColumn(name = "EducationalProviderID", referencedColumnName = "EducationalProviderID")
	@ManyToOne
	private EducationalProviders educationalProviderID;

	public ProviderAccreditation() {
	}

	public long getProviderAccreditationID() {
		return providerAccreditationID;
	}

	public void setProviderAccreditationID(long providerAccreditationID) {
		this.providerAccreditationID = providerAccreditationID;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	@Transient
	public String getFormatEffectiveEndDate() {
		return CommonUtil.formatDate(effectiveEndDate, CommonUtil.MMDDYYY);
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
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

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public AccreditingBody getAccreditingBodyID() {
		return accreditingBodyID;
	}

	public void setAccreditingBodyID(AccreditingBody accreditingBodyID) {
		this.accreditingBodyID = accreditingBodyID;
	}

	public EducationalProviders getEducationalProviderID() {
		return educationalProviderID;
	}

	public void setEducationalProviderID(EducationalProviders educationalProviderID) {
		this.educationalProviderID = educationalProviderID;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

}
