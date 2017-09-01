package com.edassist.service;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.contracts.Option;
import com.edassist.models.domain.*;
import com.edassist.models.dto.ApplicationSubmissionDTO;
import com.edassist.models.dto.ExpenseSnapshotDTO;
import com.edassist.models.sp.ApplicationCourseCompliancy;
import com.edassist.models.sp.ApplicationNumber;
import com.edassist.models.sp.PercentagePayOut;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ApplicationService extends GenericService<Application> {

	List<Application> search(Object object) throws ExceededMaxResultsException;

	List<Application> findRelatedBookApplications(Long applicationID) throws ExceededMaxResultsException;

	void saveOrUpdate(Application entity);

	void saveOrUpdate(Application entity, String newStatus);

	PaginationResult<ThinApp> findSelfAndSuperviseeApplciations(Participant supervisorParticipant, int index, int recordsPerPage, String teamMemberType, String sortingProperty, String benefitPeriodName);

	Application merge(Application entity);

	List<ApplicationNumber> getNextApplicationNumber() throws Exception;

	ApplicationCourseCompliancy getApplicationCourseCompliancyProc(final long applicationCoursesID);

	List<Application> search(Application application, List<Client> clientList) throws ExceededMaxResultsException;

	ExpenseSnapshotDTO requestedTotals(Long applicationId) throws DataAccessException, ExceededMaxResultsException;

	ExpenseSnapshotDTO paidTotals(Long applicationId) throws DataAccessException, ExceededMaxResultsException;

	ExpenseSnapshotDTO approvedTotals(Long applicationId) throws ExceededMaxResultsException;

	Map<String, Object> getRequestedTotalsAsBigDecimal(List<ApplicationCourses> courseAndExpenseList, Long applicationStatusCode, List<ProgramExpenseType> ProgramExpenseTypeNotApplyToCapList);

	List<PercentagePayOut> callFindPercentagePayoutForAppCourse(final Long applicationCoursesID);

	void processNewApplication(Application application, Program program, EducationalProviders currentProvider) throws Exception;

	void captureApplicationHistory(Application entity) throws Exception;

	Boolean isApplicationEditable(Application application);

	Boolean isUploadDocumentAllowed(Application application);

	void sendEmailNotificationOnStatusChange(Application application, Long originalAppStatusCode, String comment);

	void saveApplication(Application application);

	void changeApplicationStatus(Application application, ApplicationStatus status, String reason, boolean viewableByParticipant, String eventType, boolean appStatusChange);

	boolean isStatusChangeValid(Long fromStatus, Long toStatus, boolean allowNoChange);

	ApplicationSubmissionDTO applicationSubmission(Application tuitionApplication, Long userId);

	Boolean deleteAppNumber(Application application);

	List<Payments> getPaymentHistory(ThinApp application);

	List<Refunds> getRefundsHistory(Long applicationId);

	Long getNumberOfUnreadComments(Application application);

	PaginationResult<ThinApp> getActionNeededTaskList(Participant participant, int index, int recordsPerPage);

	List<Option> getTeamFilterOptions(Participant participant);

	List<Option> getAppSortOptions();

	List<Option> getBenefitPeriodFilterOptions(Long participantId, String teamMemberType, Long defaultBenefitPeriodId);

	byte[] exportAppsToCsv(String teamMemberType, String sortingProperty, String benefitPeriods);

	BigDecimal getTotalRefunds(List<ThinCourse> courses, List<ThinExpense> nonCourseExpenses);

	BigDecimal getTotalRequestedAmount(List<ThinCourse> courses, List<ThinExpense> nonCourseExpenses);

	App findAppById(Long applicationId);

	ThinApp findThinAppById(Long applicationId);

	Application getRelatedBookApplication(Long applicationId);
}
