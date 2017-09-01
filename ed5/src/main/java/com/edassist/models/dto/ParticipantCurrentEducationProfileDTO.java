package com.edassist.models.dto;

import java.util.Date;

public class ParticipantCurrentEducationProfileDTO {

	private Long currentEducationProfileId;
	private Date expGraduationDt;
	private DegreeObjectivesDTO degreeID;
	private CourseOfStudyDTO fieldOfStudyID;
	private ProgramDTO programID;
	private EducationalProviderDTO providerID;
	private String studentID;
	private String otherCourseOfStudy;

	public Long getCurrentEducationProfileId() {
		return currentEducationProfileId;
	}

	public void setCurrentEducationProfileId(Long currentEducationProfileId) {
		this.currentEducationProfileId = currentEducationProfileId;
	}

	public void setExpGraduationDt(Date expGraduationDt) {
		this.expGraduationDt = expGraduationDt;
	}

	public Date getExpGraduationDt() {
		return expGraduationDt;
	}

	public DegreeObjectivesDTO getDegreeID() {
		return degreeID;
	}

	public void setDegreeID(DegreeObjectivesDTO degreeID) {
		this.degreeID = degreeID;
	}

	public CourseOfStudyDTO getFieldOfStudyID() {
		return fieldOfStudyID;
	}

	public void setFieldOfStudyID(CourseOfStudyDTO fieldOfStudyID) {
		this.fieldOfStudyID = fieldOfStudyID;
	}

	public ProgramDTO getProgramID() {
		return programID;
	}

	public void setProgramID(ProgramDTO programID) {
		this.programID = programID;
	}

	public EducationalProviderDTO getProviderID() {
		return providerID;
	}

	public void setProviderID(EducationalProviderDTO providerID) {
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

}
