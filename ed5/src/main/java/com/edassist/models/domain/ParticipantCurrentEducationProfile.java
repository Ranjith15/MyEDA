package com.edassist.models.domain;

import com.edassist.utils.CommonUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ParticipantCurrentEducationProfile")
public class ParticipantCurrentEducationProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CurrentEducationProfileId")
	private Long currentEducationProfileId;

	@JoinColumn(name = "DegreeID", referencedColumnName = "DegreeObjectiveID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private DegreeObjectives degreeID;

	@Column(name = "ExpGraduationDt")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expGraduationDt;

	@JoinColumn(name = "FieldOfStudyID", referencedColumnName = "CourseOfStudyID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private CourseOfStudy fieldOfStudyID;

	@JoinColumn(name = "ProgramID", referencedColumnName = "programID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private Program programID;

	@JoinColumn(name = "ProviderID", referencedColumnName = "EducationalProviderID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private EducationalProviders providerID;

	@Column(name = "StudentIDString")
	private String studentID;

	@Column(name = "OtherCourseOfStudy")
	private String otherCourseOfStudy;

	public ParticipantCurrentEducationProfile() {
		super();
	}

	public ParticipantCurrentEducationProfile(Long currentEducationProfileId) {
		this.currentEducationProfileId = currentEducationProfileId;
	}

	public Long getCurrentEducationProfileId() {
		return currentEducationProfileId;
	}

	public void setCurrentEducationProfileId(Long currentEducationProfileId) {
		this.currentEducationProfileId = currentEducationProfileId;
	}

	public DegreeObjectives getDegreeID() {
		return degreeID;
	}

	public void setDegreeID(DegreeObjectives degreeID) {
		this.degreeID = degreeID;
	}

	public Date getExpGraduationDt() {
		return expGraduationDt;
	}

	public void setExpGraduationDt(Date expGraduationDt) {
		this.expGraduationDt = expGraduationDt;
	}

	public CourseOfStudy getFieldOfStudyID() {
		return fieldOfStudyID;
	}

	public void setFieldOfStudyID(CourseOfStudy fieldOfStudyID) {
		this.fieldOfStudyID = fieldOfStudyID;
	}

	public Program getProgramID() {
		return programID;
	}

	public void setProgramID(Program programID) {
		this.programID = programID;
	}

	public EducationalProviders getProviderID() {
		return providerID;
	}

	public void setProviderID(EducationalProviders providerID) {
		this.providerID = providerID;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getOtherCourseOfStudy() {
		return otherCourseOfStudy;
	}

	public void setOtherCourseOfStudy(String otherCourseOfStudy) {
		this.otherCourseOfStudy = otherCourseOfStudy;
	}

	@Transient
	public String getExpGraduationDtStr() {
		return CommonUtil.formatDate(expGraduationDt, CommonUtil.MMDDYYY);
	}

}
