package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PrepayTuitionApp")
public class ThinPrepayTuitionApp {

	@Id
	@Column(name = "PrepayTuitionAppID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prepayTuitionAppID;

	@OneToOne
	@JoinColumn(name = "ApplicationID")
	private ThinApp applicationID;

	@Column(name = "CourseStartDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date courseStartDate;

	@Column(name = "CourseEndDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date courseEndDate;

	@JoinColumn(name = "CourseOfStudyID", referencedColumnName = "CourseOfStudyID")
	@ManyToOne
	private CourseOfStudy courseOfStudy;

	@JoinColumn(name = "DegreeObjectiveID", referencedColumnName = "DegreeObjectiveID")
	@ManyToOne
	private DegreeObjectives degreeObjectiveID;

	public Long getPrepayTuitionAppID() {
		return prepayTuitionAppID;
	}

	public void setPrepayTuitionAppID(Long prepayTuitionAppID) {
		this.prepayTuitionAppID = prepayTuitionAppID;
	}

	public ThinApp getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(ThinApp applicationID) {
		this.applicationID = applicationID;
	}

	public Date getCourseStartDate() {
		return courseStartDate;
	}

	public void setCourseStartDate(Date courseStartDate) {
		this.courseStartDate = courseStartDate;
	}

	public Date getCourseEndDate() {
		return courseEndDate;
	}

	public void setCourseEndDate(Date courseEndDate) {
		this.courseEndDate = courseEndDate;
	}

	public CourseOfStudy getCourseOfStudy() {
		return courseOfStudy;
	}

	public void setCourseOfStudy(CourseOfStudy courseOfStudy) {
		this.courseOfStudy = courseOfStudy;
	}

	public DegreeObjectives getDegreeObjectiveID() { return degreeObjectiveID; }

	public void setDegreeObjectiveID(DegreeObjectives degreeObjectiveID) { this.degreeObjectiveID = degreeObjectiveID; }

}
