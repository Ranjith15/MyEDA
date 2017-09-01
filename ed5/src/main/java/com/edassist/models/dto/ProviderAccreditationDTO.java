package com.edassist.models.dto;

import java.util.Date;

public class ProviderAccreditationDTO {

	private Integer createdBy;
	private Date dateCreated;
	private Date modifiedDate;
	private Integer modifiedBy;
	private AccreditingBodyDTO accreditingBody;

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getDateCreated() {
		return dateCreated;
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

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setAccreditingBody(AccreditingBodyDTO accreditingBody) {
		this.accreditingBody = accreditingBody;
	}

	public AccreditingBodyDTO getAccreditingBody() {
		return accreditingBody;
	}

}
