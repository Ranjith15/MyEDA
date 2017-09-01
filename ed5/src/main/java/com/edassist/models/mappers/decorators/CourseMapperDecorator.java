package com.edassist.models.mappers.decorators;

import com.edassist.models.domain.Course;
import com.edassist.models.domain.CourseHistory;
import com.edassist.models.domain.ThinCourse;
import com.edassist.models.dto.CourseDTO;
import com.edassist.models.dto.ExpenseDTO;
import com.edassist.models.mappers.CourseMapper;
import com.edassist.service.ApplicationCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CourseMapperDecorator implements CourseMapper {

	@Autowired
	@Qualifier("delegate")
	private CourseMapper delegate;

	@Autowired
	private ApplicationCoursesService applicationCoursesService;

	@Override
	public CourseDTO toDTO(ThinCourse course) {
		return delegate.toDTO(course);
	}

	@Override
	public CourseDTO toDTO(Course course) {
		CourseDTO courseDto = delegate.toDTO(course);
		final Long applicationStatusId = course.getApplication().getApplicationStatus().getApplicationStatusID();
		final List<CourseHistory> courseHistoryList = course.getHistoryList();
		BigDecimal requested = course.getTuitionAmount();
		BigDecimal approved = applicationCoursesService.getApprovedAmount(requested, applicationStatusId, courseHistoryList, true);
		BigDecimal paid = applicationCoursesService.getPaidAmount(requested, applicationStatusId, courseHistoryList, true);
		List<ExpenseDTO> expenses = courseDto.getRelatedExpenses();
		if (expenses != null && !expenses.isEmpty()) {
			for (ExpenseDTO expense : expenses) {
				approved = approved.add(expense.getApprovedAmount() != null ? expense.getApprovedAmount() : BigDecimal.ZERO);
				paid = paid.add(expense.getPaidAmount() != null ? expense.getPaidAmount() : BigDecimal.ZERO);
				if (expense.getFeesAmount() != null) {
					requested = requested.add(expense.getFeesAmount());
				}
			}
		}
		courseDto.setRequestedAmount(requested);
		courseDto.setApprovedAmount(approved);
		courseDto.setPaidAmount(paid);
		return courseDto;
	}

	@Override
	public List<CourseDTO> toDTOListFromThin(List<ThinCourse> courses) {
		List<CourseDTO> courseDTOList = new ArrayList<>();
		for (ThinCourse course : courses) {
			courseDTOList.add(toDTO(course));
		}
		return courseDTOList;
	}

	@Override
	public List<CourseDTO> toDTOList(List<Course> courses) {
		List<CourseDTO> courseDTOList = new ArrayList<>();
		for (Course course : courses) {
			courseDTOList.add(toDTO(course));
		}
		return courseDTOList;
	}

	@Override
	public ThinCourse toThin(Course course) {
		return delegate.toThin(course);
	}

	@Override
	public List<ThinCourse> toThinList(List<Course> courses) {
		return delegate.toThinList(courses);
	}
}
