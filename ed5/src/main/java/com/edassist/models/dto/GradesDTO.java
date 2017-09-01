package com.edassist.models.dto;

public class GradesDTO {

	private Long gradeID;
	private String gradeName;

	public void setGradeID(Long gradeID) {
		this.gradeID = gradeID;
	}

	public Long getGradeID() {
		return gradeID;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getGradeName() {
		return gradeName;
	}

}
