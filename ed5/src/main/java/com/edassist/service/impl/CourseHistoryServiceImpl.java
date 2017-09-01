package com.edassist.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edassist.constants.ApplicationConstants;
import com.edassist.dao.CourseHistoryDao;
import com.edassist.dao.GenericDao;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.Application;
import com.edassist.models.domain.ApplicationCourses;
import com.edassist.models.domain.ApplicationStatus;
import com.edassist.models.domain.CourseHistory;
import com.edassist.service.ApplicationStatusService;
import com.edassist.service.CourseHistoryService;

@Service
public class CourseHistoryServiceImpl extends GenericServiceImpl<CourseHistory> implements CourseHistoryService {

	private static Logger log = Logger.getLogger(CourseHistoryServiceImpl.class);

	@Autowired
	private ApplicationStatusService applicationStatusService;

	private CourseHistoryDao courseHistoryDao;

	public CourseHistoryServiceImpl() {
	}

	@Autowired
	public CourseHistoryServiceImpl(@Qualifier("courseHistoryDaoImpl") GenericDao<CourseHistory> genericDao) {
		super(genericDao);
		this.courseHistoryDao = (CourseHistoryDao) genericDao;
	}

	@Override
	public List<CourseHistory> findPaidEntities(Application application) throws ExceededMaxResultsException {
		Long mostRecentChangeId = courseHistoryDao.findLatestChangeByStatus(application.getApplicationID(), ApplicationConstants.APPLICATION_STATUS_PAYMENT_REIMBURSEMENT_PROGRESS, null);
		String[] eparamNames = { "changeId", "application" };
		Object[] eparamValues = { mostRecentChangeId, application };
		return courseHistoryDao.findByParams(eparamNames, eparamValues, null, null);
	}

	@Override
	public List<CourseHistory> findApprovedEntities(Application application) throws ExceededMaxResultsException {
		// first find the most recent changeId
		Long mostRecentChangeId = courseHistoryDao.findLatestChangeByStatus(application.getApplicationID(), ApplicationConstants.APPLICATION_STATUS_APPROVED,
				ApplicationConstants.APPLICATION_STATUS_LOC);

		ApplicationStatus status120 = applicationStatusService.findById(new Long(120));
		String[] eparamNames120 = { "changeId", "application", "applicationStatus" };
		Object[] eparamValues120 = { mostRecentChangeId, application, status120 };
		List<CourseHistory> chList = courseHistoryDao.findByParams(eparamNames120, eparamValues120, null, null);

		if (chList == null || chList.size() < 1) {
			ApplicationStatus status400 = applicationStatusService.findById(new Long(400));
			String[] eparamNames400 = { "changeId", "application", "applicationStatus" };
			Object[] eparamValues400 = { mostRecentChangeId, application, status400 };
			chList = courseHistoryDao.findByParams(eparamNames400, eparamValues400, null, null);
		}

		return chList;
	}

	@Override
	public Long findApprovedChangeId(Application application) {
		Long changeId = courseHistoryDao.findLatestChangeByStatus(application.getApplicationID(), ApplicationConstants.APPLICATION_STATUS_APPROVED, ApplicationConstants.APPLICATION_STATUS_LOC);
		return changeId;
	}

	@Override
	public CourseHistory findApprovedEntity(ApplicationCourses course) {
		if (course == null) {
			throw new BadRequestException("Course must not be null");
		}

		if (course.getApplicationID() == null) {
			throw new BadRequestException("Application must not be null");
		}

		if (course.getApplicationID().getApplicationID() == null) {
			throw new BadRequestException("ApplicationID must not be null");
		}

		Long changeId = courseHistoryDao.findLatestChangeByStatus(course.getApplicationID().getApplicationID(), ApplicationConstants.APPLICATION_STATUS_APPROVED,
				ApplicationConstants.APPLICATION_STATUS_LOC);
		return findHistoryEntity(course, changeId);
	}

	@Override
	public CourseHistory findRequestedEntity(ApplicationCourses course) {
		if (course == null) {
			throw new BadRequestException("Course must not be null");
		}

		if (course.getApplicationID() == null) {
			throw new BadRequestException("Application must not be null");
		}

		if (course.getApplicationID().getApplicationID() == null) {
			throw new BadRequestException("ApplicationID must not be null");
		}
		// M: Always retrive the latest row from CourseHistory table with the CurrentStatus
		// Long changeId = ((CourseHistoryDao)this.getDao()).findLatestChangeByOldStatus(course.getApplicationID().getApplicationID(), ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW,
		// null);
		Long changeId;
		if (course.getApplicationID().getApplicationStatusID().getApplicationStatusCode() >= 120 || course.getApplicationID().getApplicationStatusID().getApplicationStatusCode() == 110) {
			changeId = courseHistoryDao.findLatestChangeByStatus(course.getApplicationID().getApplicationID(), ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW, null);
		} else {
			changeId = courseHistoryDao.findLatestChangeByStatus(course.getApplicationID().getApplicationID(), course.getApplicationID().getApplicationStatusID().getApplicationStatusCode(), null);
		}
		return findHistoryEntity(course, changeId);
	}

	private CourseHistory findHistoryEntity(ApplicationCourses course, Long changeId) {
		if (changeId == null) {
			return null;
		}

		String[] paramNames = { "changeId", "applicationCourses" };
		Object[] paramValues = { changeId, course };
		List<CourseHistory> courseHistoryList = null;
		try {
			courseHistoryList = this.findByParams(paramNames, paramValues, null, null);
		} catch (ExceededMaxResultsException e) {
			throw new BadRequestException("Expected only 1 coursehistory item to match criteria.  changeId: [" + changeId + "] courseId: [" + course + "]");
		}

		if (courseHistoryList == null || courseHistoryList.size() == 0) {
			return null;
		} else if (courseHistoryList.size() > 1) {
			// throw new TAMSException("Expected only 1 coursehistory item to match criteria. changeId: ["+changeId+"] courseId: ["+course+"]");
			log.fatal("Needs to be fixed");
			log.fatal("Expected only 1 coursehistory item to match criteria.  changeId: [" + changeId + "] courseId: [" + course + "]");
			log.fatal("Expected only 1 coursehistory item to match criteria.  changeId: [" + changeId + "] courseId: [" + course + "]");
			log.fatal("Needs to be fixed");
		}

		return courseHistoryList.get(0);
	}

}
