package com.edassist.dao;

import com.edassist.models.domain.CourseHistory;

public interface CourseHistoryDao extends GenericDao<CourseHistory> {

	Long findLatestChangeByStatus(Long applicationId, Long primaryStatus, Long secondaryStatus);

}
