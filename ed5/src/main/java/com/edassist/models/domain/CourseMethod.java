package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CourseMethod")
public class CourseMethod {

	@Id
	@Column(name = "CourseMethodId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long courseMethodId;

	@Column(name = "CourseMethod")
	private String courseMethod;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "CreatedBy")
	private User createdBy;

	@ManyToOne()
	@JoinColumn(name = "ModifiedBy")
	private User modifiedBy;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	public CourseMethod() {
		super();
	}

	public Long getCourseMethodId() {
		return courseMethodId;
	}

	public void setCourseMethodId(Long courseMethodId) {
		this.courseMethodId = courseMethodId;
	}

	public String getCourseMethod() {
		return courseMethod;
	}

	public void setCourseMethod(String courseMethod) {
		this.courseMethod = courseMethod;
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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

}
