package com.edassist.models.dto;

public class EducationalProviderDetailDTO {

	private String studentID;
	private EducationalProviderDTO educationalProvider;
	private BenefitPeriodDTO benefitPeriodID;
	private CourseOfStudyDTO courseOfStudyID;
	private DegreeObjectivesDTO degreeObjectiveID;

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public EducationalProviderDTO getEducationalProvider() {
		return educationalProvider;
	}

	public void setEducationalProvider(EducationalProviderDTO educationalProvider) {
		this.educationalProvider = educationalProvider;
	}

	public BenefitPeriodDTO getBenefitPeriodID() {
		return benefitPeriodID;
	}

	public void setBenefitPeriodID(BenefitPeriodDTO benefitPeriodID) {
		this.benefitPeriodID = benefitPeriodID;
	}

	public CourseOfStudyDTO getCourseOfStudyID() {
		return courseOfStudyID;
	}

	public void setCourseOfStudyID(CourseOfStudyDTO courseOfStudyID) {
		this.courseOfStudyID = courseOfStudyID;
	}

	public DegreeObjectivesDTO getDegreeObjectiveID() {
		return degreeObjectiveID;
	}

	public void setDegreeObjectiveID(DegreeObjectivesDTO degreeObjectiveID) {
		this.degreeObjectiveID = degreeObjectiveID;
	}

}
