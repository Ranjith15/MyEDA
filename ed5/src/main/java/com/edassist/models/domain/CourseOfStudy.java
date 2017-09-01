package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CourseOfStudy")
public class CourseOfStudy {

	@Id
	@Column(name = "CourseOfStudyID")
	private Long courseOfStudyID;

	@Column(name = "CourseOfStudy")
	private String courseOfStudy;

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

	public CourseOfStudy() {
		super();
	}

	public CourseOfStudy(Long courseOfStudyID) {
		this.courseOfStudyID = courseOfStudyID;
	}

	public CourseOfStudy(Long courseOfStudyID, Date dateCreated, int createdBy) {
		this.courseOfStudyID = courseOfStudyID;
		this.dateCreated = dateCreated;
		this.createdBy = createdBy;
	}

	public Long getCourseOfStudyID() {
		return courseOfStudyID;
	}

	public void setCourseOfStudyID(Long courseOfStudyID) {
		this.courseOfStudyID = courseOfStudyID;
	}

	public String getCourseOfStudy() {
		return courseOfStudy;
	}

	public void setCourseOfStudy(String courseOfStudy) {
		this.courseOfStudy = courseOfStudy;
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
