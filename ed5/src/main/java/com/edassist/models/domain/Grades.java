package com.edassist.models.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "Grades")
public class Grades {

	@Id
	@Column(name = "GradeID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gradeID;

	@Basic(optional = false)
	@Column(name = "GradeDesc")
	private String gradeDesc;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "CreatedBy")
	private User createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ModifiedBy")
	private User modifiedBy;

	@NotNull
	@Column(name = "active")
	private Boolean active;

	public Grades() {

	}

	public Grades(Long gradeID) {
		this.gradeID = gradeID;
	}

	public Grades(Long gradeID, String gradeDesc, Date dateCreated, User createdBy) {
		this.gradeID = gradeID;
		this.gradeDesc = gradeDesc;
		this.dateCreated = dateCreated;
		this.createdBy = createdBy;
	}

	public Long getGradeID() {
		return gradeID;
	}

	public void setGradeID(Long gradeID) {
		this.gradeID = gradeID;
	}

	public String getGradeDesc() {
		return gradeDesc;
	}

	public void setGradeDesc(String gradeDesc) {
		this.gradeDesc = gradeDesc;
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

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
