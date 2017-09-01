package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DegreeObjectives")
public class DegreeObjectives {

	@Id
	@Column(name = "DegreeObjectiveID")
	private Long degreeObjectiveID;

	@Basic(optional = false)
	@Column(name = "Degree")
	private String degree;

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

	public DegreeObjectives() {
		super();
	}

	public DegreeObjectives(Long degreeObjectiveID) {
		this.degreeObjectiveID = degreeObjectiveID;
	}

	public DegreeObjectives(Long degreeObjectiveID, String degree, Date dateCreated, int createdBy) {
		this.degreeObjectiveID = degreeObjectiveID;
		this.degree = degree;
		this.dateCreated = dateCreated;
		this.createdBy = createdBy;
	}

	public Long getDegreeObjectiveID() {
		return degreeObjectiveID;
	}

	public void setDegreeObjectiveID(Long degreeObjectiveID) {
		this.degreeObjectiveID = degreeObjectiveID;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
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
