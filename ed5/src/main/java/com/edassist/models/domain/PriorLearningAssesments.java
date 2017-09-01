package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PriorLearningAssesments")
public class PriorLearningAssesments {

	@Id
	@Column(name = "PriorLearningAssesmentID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long priorLearningAssesmentID;

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

	@Basic(optional = false)
	@Column(name = "ModifiedBy")
	private int modifiedBy;

	@JoinColumn(name = "EducationalProviderID", referencedColumnName = "EducationalProviderID")
	@ManyToOne(optional = false)
	private EducationalProviders educationalProviderID;

	public PriorLearningAssesments() {
	}

	public Long getPriorLearningAssesmentID() {
		return priorLearningAssesmentID;
	}

	public void setPriorLearningAssesmentID(Long priorLearningAssesmentID) {
		this.priorLearningAssesmentID = priorLearningAssesmentID;
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

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public EducationalProviders getEducationalProviderID() {
		return educationalProviderID;
	}

	public void setEducationalProviderID(EducationalProviders educationalProviderID) {
		this.educationalProviderID = educationalProviderID;
	}
}
