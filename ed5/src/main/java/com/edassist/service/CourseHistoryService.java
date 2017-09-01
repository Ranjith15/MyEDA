package com.edassist.service;

import java.util.List;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.Application;
import com.edassist.models.domain.ApplicationCourses;
import com.edassist.models.domain.CourseHistory;

public interface CourseHistoryService extends GenericService<CourseHistory> {

	List<CourseHistory> findApprovedEntities(Application application) throws ExceededMaxResultsException;

	List<CourseHistory> findPaidEntities(Application application) throws ExceededMaxResultsException;

	CourseHistory findRequestedEntity(ApplicationCourses course);

	CourseHistory findApprovedEntity(ApplicationCourses course);

	Long findApprovedChangeId(Application application);
}
