package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "State")
public class State {

	@Id
	@Column(name = "StateID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long stateId;

	@Column(name = "State")
	private String stateAbbreviation;

	@Column(name = "StateDesc")
	private String stateDescription;

	@Column(name = "ModifiedDate")
	private Date modifiedDate;

	@Column(name = "CreatedBy")
	private Integer createdBy;

	@Column(name = "ModifiedBy")
	private Integer modifiedBy;

	@Column(name = "DateCreated")
	private Date dateCreated;

	public State() {
		super();
	}

	public State(long stateId, String stateAbbreviation) {
		super();
		this.stateId = stateId;
		this.stateAbbreviation = stateAbbreviation;
	}

	public long getStateId() {
		return stateId;
	}

	public void setStateId(long stateId) {
		this.stateId = stateId;
	}

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}

	public void setStateAbbreviation(String stateAbbreviation) {
		this.stateAbbreviation = stateAbbreviation;
	}

	public String getStateDescription() {
		return stateDescription;
	}

	public void setStateDescription(String stateDescription) {
		this.stateDescription = stateDescription;
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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
