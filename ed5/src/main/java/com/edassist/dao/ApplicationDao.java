package com.edassist.dao;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.*;
import com.edassist.models.sp.ApplicationCourseCompliancy;
import com.edassist.models.sp.ApplicationNumber;
import com.edassist.models.sp.PercentagePayOut;

import java.util.List;

public interface ApplicationDao extends GenericDao<Application> {

	List<Application> search(Object object) throws ExceededMaxResultsException;

	List<Application> findRelatedBookApplications(Long applicationId, Long[] excludedStatuses) throws ExceededMaxResultsException;

	ApplicationCourseCompliancy callGetApplicationCourseCompliancyProc(final long applicationCoursesID);

	List<ApplicationNumber> callGetNextApplicationNumberProc() throws Exception;

	PaginationResult<ThinApp> getActionNeededTaskList(Participant participant, int index, int recordsPerPage);

	List<PercentagePayOut> callFindPercentagePayoutForAppCourse(final Long applicationCoursesID);

	PaginationResult<ThinAppActivityForMyTeam> findActionRequiredApplicationsBySupervisorId(Participant participant, int index, int recordsPerPage) throws Exception;

	PaginationResult<ThinApp> findSelfAndSuperviseeApplciations(Long supervisorParticipantId, int index, int recordsPerPage, String teamMemberType, String sortingProperty, String benefitPeriods);

	Boolean deleteAppNumber(Application application, String requester);

	Long getNumberOfUnreadComments(Application application);

	List<String> getBenefitPeriodFilterOptions(Long participantId, String teamMemberType, Long defaultBenefitPeriodId);

	App findAppById(Long applicationId);

	ThinApp findThinAppById(Long applicationId);
}
