package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "AccreditingBody")
public class AccreditingBody  {

	@Id
	@Column(name = "AccreditingBodyID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accreditingBodyID;

	@Column(name = "AccreditingBodyName")
	private String accreditingBodyName;

	@Column(name = "AccreditingType")
	private String accreditingType;

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

	@Column(name = "AccreditingBodyDisplayName")
	private String accreditingBodyDisplayName;

	@Column(name = "AccreditingTypeDisplayName")
	private String accreditingTypeDisplayName;

	@Column(name = "Active")
	private boolean active;

	@OneToMany(mappedBy = "accreditingBodyID", fetch = FetchType.LAZY)
	private Collection<ProviderAccreditation> providerAccreditationCollection;

	@OneToMany(mappedBy = "accreditingBodyID", fetch = FetchType.LAZY)
	private Collection<ProgramAccreditingBody> programAccreditingBodyCollection;


	public AccreditingBody() {
	}

	public Long getAccreditingBodyID() {
		return accreditingBodyID;
	}

	public void setAccreditingBodyID(Long accreditingBodyID) {
		this.accreditingBodyID = accreditingBodyID;
	}

	public String getAccreditingBodyName() {
		return accreditingBodyName;
	}

	public void setAccreditingBodyName(String accreditingBodyName) {
		this.accreditingBodyName = accreditingBodyName;
	}

	public String getAccreditingType() {
		return accreditingType;
	}

	public void setAccreditingType(String accreditingType) {
		this.accreditingType = accreditingType;
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

	public String getAccreditingBodyDisplayName() {
		return accreditingBodyDisplayName;
	}

	public void setAccreditingBodyDisplayName(String accreditingBodyDisplayName) {
		this.accreditingBodyDisplayName = accreditingBodyDisplayName;
	}

	public String getAccreditingTypeDisplayName() {
		return accreditingTypeDisplayName;
	}

	public void setAccreditingTypeDisplayName(String accreditingTypeDisplayName) {
		this.accreditingTypeDisplayName = accreditingTypeDisplayName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Collection<ProviderAccreditation> getProviderAccreditationCollection() {
		return providerAccreditationCollection;
	}

	public void setProviderAccreditationCollection(Collection<ProviderAccreditation> providerAccreditationCollection) {
		this.providerAccreditationCollection = providerAccreditationCollection;
	}

	public Collection<ProgramAccreditingBody> getProgramAccreditingBodyCollection() {
		return programAccreditingBodyCollection;
	}

	public void setProgramAccreditingBodyCollection(Collection<ProgramAccreditingBody> programAccreditingBodyCollection) {
		this.programAccreditingBodyCollection = programAccreditingBodyCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (accreditingBodyID != null ? accreditingBodyID.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof AccreditingBody)) {
			return false;
		}
		AccreditingBody other = (AccreditingBody) object;
		if ((this.accreditingBodyID == null && other.accreditingBodyID != null) || (this.accreditingBodyID != null && !this.accreditingBodyID.equals(other.accreditingBodyID))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "bean.AccreditingBody[accreditingBodyID=" + accreditingBodyID + "]";
	}

}
