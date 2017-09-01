package com.edassist.service;

import com.edassist.models.domain.Application;
import com.edassist.models.domain.ApplicationCourses;
import com.edassist.models.domain.ApplicationCourses.QUESTION_TYPE;
import com.edassist.models.domain.CourseHistory;

import java.math.BigDecimal;
import java.util.List;

public interface ApplicationCoursesService extends GenericService<ApplicationCourses> {

	void saveOrUpdate(ApplicationCourses entity);

	void remove(ApplicationCourses entity);

	ApplicationCourses createCourse(Long applicationId, String courseNumber, String courseName, BigDecimal tuitionAmount, BigDecimal refundAmount, Double creditHours, Long courseMethodId,
			String maintainSkillsYN, String meetMinimumQualsYN, String newCareerFieldYN, Long gradeId, String courseDescriptionURL, String courseSchedule);

	QUESTION_TYPE calculateTaxability(Application application, QUESTION_TYPE maintainSkillsYN, QUESTION_TYPE meetMinimumQualsYN, QUESTION_TYPE newCareerFieldYN);

	String getPopUpStringForCourse(String clientCode, String programCode, String ContentName);

	void persistApplicationCourseOrExpense(ApplicationCourses courseOrExpense);

	List<ApplicationCourses> getApplicationCoursesWithGradeCompliance(Application application);

	ApplicationCourses deleteCourse(Long courseId);

	BigDecimal getApprovedAmount(BigDecimal requestedAmount, Long appStatusId, List<CourseHistory> historyList, boolean isCourse);

	BigDecimal getPaidAmount(BigDecimal requestedAmount, Long appStatusId, List<CourseHistory> historyList, boolean isCourse);
}
