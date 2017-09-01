package com.edassist.models.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "BenefitPeriod")
public class BenefitPeriod {

	@Id
	@Column(name = "BenefitPeriodID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long benefitPeriodID;

	@Column(name = "BenefitPeriodName")
	private String benefitPeriodName;

	@Column(name = "StartDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Column(name = "EndDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "CreatedBy")
	private Integer createdBy;

	@Column(name = "ModifiedBy")
	private Integer modifiedBy;

	@JoinColumn(name = "ProgramID", referencedColumnName = "ProgramID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private Program programID;

	public BenefitPeriod() {
		super();
	}

	public BenefitPeriod(long benefitPeriodID) {
		this.benefitPeriodID = benefitPeriodID;
	}

	public Long getBenefitPeriodID() {
		return benefitPeriodID;
	}

	public void setBenefitPeriodID(Long benefitPeriodID) {
		this.benefitPeriodID = benefitPeriodID;
	}

	public String getBenefitPeriodName() {
		return benefitPeriodName;
	}

	public void setBenefitPeriodName(String benefitPeriodName) {
		this.benefitPeriodName = benefitPeriodName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public Program getProgramID() {
		return programID;
	}

	public void setProgramID(Program programID) {
		this.programID = programID;
	}

}
