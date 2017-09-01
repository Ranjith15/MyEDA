package com.edassist.models.dto;

public class CourseMethodDTO implements Comparable<CourseMethodDTO> {

	private Long courseMethodId;
	private String courseMethod;

	public void setCourseMethodId(Long courseMethodId) {
		this.courseMethodId = courseMethodId;
	}

	public Long getCourseMethodId() {
		return courseMethodId;
	}

	public void setCourseMethod(String courseMethod) {
		this.courseMethod = courseMethod;
	}

	public String getCourseMethod() {
		return courseMethod;
	}

	@Override
	public int compareTo(CourseMethodDTO courseMethodDTO) {
		if (courseMethod == null) {
			return 0;
		}
		return courseMethod.compareTo(courseMethodDTO.courseMethod);
	}
}
