package com.edassist.dao.impl;

import com.edassist.constants.AppTeamFilterConstants;
import com.edassist.constants.ApplicationConstants;
import com.edassist.constants.UserTypeConstants;
import com.edassist.dao.ApplicationDao;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.*;
import com.edassist.models.sp.ApplicationCourseCompliancy;
import com.edassist.models.sp.ApplicationNumber;
import com.edassist.models.sp.PercentagePayOut;
import com.edassist.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
public class ApplicationDaoImpl extends GenericDaoImpl<Application> implements ApplicationDao {

	private static Logger log = Logger.getLogger(ApplicationDaoImpl.class);

	@Override
	public List<Application> search(Object object) throws ExceededMaxResultsException {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Application.class);
		Application application = (Application) object;

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).createAlias("participantID", "participant").createAlias("participant.client", "client").createAlias("benefitPeriodID",
				"benefitPeriod");

		criteria.add(Restrictions.eq("client.active", Client.ACTIVE));
		boolean isCriteriaEmpty = true;
		if (application.getApplicationNumber() != null) {
			criteria.add(Restrictions.eq("applicationNumber", application.getApplicationNumber()));

			if (application.getParticipantID() != null && application.getParticipantID().getClient() != null && application.getParticipantID().getClient().getClientId() != null
					&& application.getParticipantID().getClient().getClientId() != 0) {
				criteria.add(Restrictions.eq("participant.client.clientId", application.getParticipantID().getClient().getClientId()));
			}

			isCriteriaEmpty = false;
		} else {
			if (application.getParticipantID() != null) {
				if (application.getParticipantID().getUser() != null) {
					criteria.createAlias("participant.user", "user");
					if (application.getParticipantID().getUser().getFirstName() != null && !(application.getParticipantID().getUser().getFirstName().isEmpty())) {
						criteria.add(Restrictions.like("user.firstName", application.getParticipantID().getUser().getFirstName()));
						isCriteriaEmpty = false;
					}

					if (application.getParticipantID().getUser().getLastName() != null && !(application.getParticipantID().getUser().getLastName().isEmpty())) {
						criteria.add(Restrictions.like("user.lastName", application.getParticipantID().getUser().getLastName()));
						isCriteriaEmpty = false;
					}
				}

				// Added TAMS Unique ID criteria
				if (application.getParticipantID().getSsn() != null && !application.getParticipantID().getSsn().trim().isEmpty()) {
					criteria.add(Restrictions.like("participant.ssn", application.getParticipantID().getSsn()));
					isCriteriaEmpty = false;
				}

				if (application.getParticipantID().getEmployeeId() != null && !application.getParticipantID().getEmployeeId().isEmpty()) {
					criteria.add(Restrictions.like("participant.employeeId", application.getParticipantID().getEmployeeId()));
					isCriteriaEmpty = false;
				}

				if (application.getParticipantID().getClient() != null && application.getParticipantID().getClient().getClientId() != 0) {
					criteria.add(Restrictions.eq("participant.client.clientId", application.getParticipantID().getClient().getClientId()));
					isCriteriaEmpty = false;
				}
			}

			if (application.isAppealsOnly()) {
				criteria.createAlias("appealCollection", "appeal");
				isCriteriaEmpty = false;
			}

			if (application.isNonCompliantApplications()) {
				Object[] objects = { ApplicationConstants.APPLICATION_STATUS_REPAYMENT_REQUIRED, ApplicationConstants.APPLICATION_STATUS_SENT_COLLECTIONS };
				criteria.add(Restrictions.in("applicationStatusID.applicationStatusID", objects));
				isCriteriaEmpty = false;
			} else if (application.getApplicationStatusID() != null && application.getApplicationStatusID().getApplicationStatusID() != 0) {
				criteria.add(Restrictions.eq("applicationStatusID.applicationStatusID", application.getApplicationStatusID().getApplicationStatusID()));
				isCriteriaEmpty = false;
			}

			if (application.getEducationalProvider() != null && StringUtils.isNotBlank(application.getEducationalProvider().getProviderName())) {
				criteria.createAlias("educationalProvider", "educationalProvider");
				criteria.add(Restrictions.like("educationalProvider.providerName", application.getEducationalProvider().getProviderName()));
				isCriteriaEmpty = false;
			}

			if (application.getBenefitPeriodID() != null && application.getBenefitPeriodID().getBenefitPeriodID() != 0) {
				criteria.add(Restrictions.eq("benefitPeriodID.benefitPeriodID", application.getBenefitPeriodID().getBenefitPeriodID()));
				isCriteriaEmpty = false;
			}

			if (application.getBenefitPeriodID() != null && application.getBenefitPeriodID().getProgramID() != null && application.getBenefitPeriodID().getProgramID().getProgramID() != 0) {
				criteria.add(Restrictions.eq("benefitPeriod.programID.programID", application.getBenefitPeriodID().getProgramID().getProgramID()));
			}
		}

		if (isCriteriaEmpty) {
			return new ArrayList<Application>();
		} else {
			Session session = this.getSessionFactory().getCurrentSession();
			int count = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();

			checkMaxResultsExceeded(count);

			if (count > 0) {
				criteria.setProjection(null);
				return criteria.list();
			}
			return new ArrayList<Application>();
		}
	}

	@Override
	public List<Application> findRelatedBookApplications(Long applicationId, Long[] excludedStatuses) throws ExceededMaxResultsException {
		if (applicationId == null) {
			return null;
		}

		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Application.class);
		criteria.add(Restrictions.eq("relatedAppId", applicationId));
		List<Application> results = criteria.list();

		return results;
	}

	@Override
	public PaginationResult<ThinApp> findSelfAndSuperviseeApplciations(Long supervisorParticipantId, int index, int recordsPerPage, String teamMemberType, String sortingProperty,
			String benefitPeriods) {

		String mainQueryString = buildApplicationHistoryQuery(teamMemberType);

		List<String> benefitPeriodNames = new ArrayList<>(Arrays.asList(CommonUtil.convertStringToArrayString(benefitPeriods)));

		if (!benefitPeriods.contains("-1")) {
			mainQueryString += "AND app.benefitPeriodID.benefitPeriodName in (:benefitPeriodNames) ";
		}

		Query countQuery = setQueryParameters("select count(*) " + mainQueryString, supervisorParticipantId, benefitPeriodNames);

		mainQueryString += "order by app." + sortingProperty;

		Query query = setQueryParameters(mainQueryString, supervisorParticipantId, benefitPeriodNames);

		Long totalRecordsCount = (Long) countQuery.getSingleResult();
		List<ThinApp> thinAppActivityForMyTeamList = query.setFirstResult((index - 1) * recordsPerPage).setMaxResults(recordsPerPage).getResultList();

		return new PaginationResult<>(thinAppActivityForMyTeamList, totalRecordsCount);
	}

	@Override
	public List<String> getBenefitPeriodFilterOptions(Long participantId, String teamMemberType, Long defaultBenefitPeriodId) {
		String queryString = buildApplicationHistoryQuery(teamMemberType);
		Query query = setQueryParameters("SELECT DISTINCT app.benefitPeriodID.benefitPeriodName " + queryString, participantId, null);

		List<String> benefitPeriodNames = query.getResultList();

		// return default benefit period name if the query above returned no benefit periods.
		if (defaultBenefitPeriodId != null && (benefitPeriodNames == null || benefitPeriodNames.isEmpty())) {
			queryString = "SELECT bp.benefitPeriodName ";
			queryString += " FROM " + BenefitPeriod.class.getName() + " bp";
			queryString += " WHERE bp.benefitPeriodID = :defaultBenefitPeriodId";

			query = this.getSession().createQuery(queryString);
			query.setParameter("defaultBenefitPeriodId", defaultBenefitPeriodId);
			benefitPeriodNames = query.getResultList();
		}

		return benefitPeriodNames;
	}

	@Override
	public App findAppById(Long applicationId) {
		return this.getSession().get(App.class, applicationId);
	}

	@Override
	public ThinApp findThinAppById(Long applicationId) {
		return this.getSession().get(ThinApp.class, applicationId);
	}

	private String buildApplicationHistoryQuery(String teamMemberType) {
		String mainQueryString = "";
		mainQueryString += "FROM ThinApp app where app.applicationTypeID.applicationTypeID != :applicationTypeId ";
		mainQueryString += "AND app.applicationStatusID != :voidStatusAppId ";
		if (teamMemberType.equals(AppTeamFilterConstants.TEAM_MEMBER_ME.getValue())) {
			mainQueryString += "AND (app.participantID = :supervisorParticipantId) ";
		} else if (teamMemberType.equals(AppTeamFilterConstants.TEAM_MEMBER_TEAM.getValue())) {
			mainQueryString += "AND app.applicationStatusID in (:appStatusIds) AND (app.participantID in (select sup.participantID FROM ParticipantSupervisor sup WHERE sup.supervisor = :supervisorParticipantId)) ";
		} else if (teamMemberType.equals(AppTeamFilterConstants.TEAM_MEMBER_COMPANY.getValue())) {
			mainQueryString += "AND app.applicationStatusID in (:appStatusIds) AND (app.participantID.client = (select part.client FROM Participant part WHERE part.participantId = :supervisorParticipantId)) ";
		} else {
			// it is for a specific participant
			mainQueryString += "AND app.applicationStatusID in (:appStatusIds) AND app.participantID.participantId = " + Long.valueOf(teamMemberType) + " ";
		}
		return mainQueryString;
	}

	private Query setQueryParameters(String queryString, Long participantId, List<String> benefitPeriodNames) {
		List<Long> arrAppStatusIds = new ArrayList<>(Arrays.asList(110L, 120L, 125L, 135L, 400L, 450L, 500L, 510L, 530L, 545L, 540L, 900L, 930L));
		Session session = this.getSessionFactory().getCurrentSession();
		Query<ThinApp> query = session.createQuery(queryString);
		query.setParameter("applicationTypeId", 500L);
		query.setParameter("voidStatusAppId", 920L);
		if (query.getParameters().stream().anyMatch((p -> p.getName().equals("appStatusIds")))) {
			query.setParameter("appStatusIds", arrAppStatusIds);
		}
		// only set this query parameter when it's present in the query
		if (query.getParameters().stream().anyMatch((p -> p.getName().equals("supervisorParticipantId")))) {
			query.setParameter("supervisorParticipantId", participantId);
		}
		// only set this query parameter when it's present in the query
		if (query.getParameters().stream().anyMatch((p -> p.getName().equals("benefitPeriodNames")))) {
			query.setParameter("benefitPeriodNames", benefitPeriodNames);
		}

		return query;
	}

	@Override
	public PaginationResult<ThinAppActivityForMyTeam> findActionRequiredApplicationsBySupervisorId(final Participant supervisorParticipant, int index, int recordsPerPage) throws Exception {

		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ThinAppActivityForMyTeam.class).add(Restrictions.eq("supervisor", supervisorParticipant))
				.add(Restrictions.eq("approvalTypeID.approvalTypeID", 1)).createCriteria("applicationID", "app").createCriteria("applicationStatusID")
				.add(Restrictions.eq("applicationStatusID", 125L));
		long totalRecordsCount = 0L;
		if (recordsPerPage != -1) {
			criteria.setProjection(Projections.rowCount());
			totalRecordsCount = (Long) criteria.uniqueResult();
			criteria.setProjection(null);
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).addOrder(Order.desc("app.dateModified"));
			criteria.setFirstResult((index - 1) * recordsPerPage);
			criteria.setMaxResults(recordsPerPage);

		}
		List<ThinAppActivityForMyTeam> results = criteria.list();

		return new PaginationResult<ThinAppActivityForMyTeam>(results, totalRecordsCount);
	}

	@Override
	public PaginationResult<ThinApp> getActionNeededTaskList(Participant participant, int index, int recordsPerPage) {
		String mainQueryString = "";
		mainQueryString += "FROM ThinApp app where app.applicationID in (";
		mainQueryString += "select app2.applicationID from ThinApp app2 where app2.participantID = :participant and app2.applicationStatusID in (:appStatusIds) ) ";

		mainQueryString += "OR app.applicationID in (";
		mainQueryString += "select app3.applicationID from ThinApp app3, ApplicationComment ac ";
		mainQueryString += "where ac.applicationID = app3.applicationID and ac.reviewed = 0 and app3.participantID = :participant and ac.createdBy != :user and ac.viewableToParticipant = 1 )";

		if (participant.getUser().getUserType().getId() == UserTypeConstants.SUPERVISOR) {
			mainQueryString += "OR app.applicationID in (";
			mainQueryString += "select app4.applicationID from ThinApp app4, ApprovalHistory ah ";
			mainQueryString += "where ah.applicationID = app4.applicationID and ah.supervisor = :participant and ah.approvalTypeID = 1 and app4.applicationStatusID = 125 )";
		}

		Query<ThinApp> query = setActionNeededParameters(mainQueryString, participant);
		Query<Long> countQuery = setActionNeededParameters("select COUNT(*) " + mainQueryString, participant);

		Long totalRecordsCount = countQuery.getSingleResult();
		List<ThinApp> thinAppList = query.setFirstResult((index - 1) * recordsPerPage).setMaxResults(recordsPerPage).getResultList();

		return new PaginationResult<>(thinAppList, totalRecordsCount);
	}

	private Query setActionNeededParameters(String queryString, Participant participant) {
		List<Long> arrAppStatusIds = new ArrayList<>(Arrays.asList(90L, 110L, 120L, 125L, 400L, 450L, 530L, 540L, 545L));
		Session session = this.getSession();
		Query<ThinApp> query = session.createQuery(queryString);
		query.setParameter("appStatusIds", arrAppStatusIds);
		query.setParameter("participant", participant);
		query.setParameter("user", participant.getUser());

		return query;
	}

	@Override
	public Long getNumberOfUnreadComments(Application application) {
		Session session = null;
		Long totalUnreadMessages = 0L;

		session = this.getSessionFactory().getCurrentSession();

		Criteria criteria = session.createCriteria(ApplicationComment.class, "applicationComment");
		criteria.add(Restrictions.eq("applicationComment.applicationID", application.getApplicationID()));
		criteria.add(Restrictions.eq("applicationComment.reviewed", false));
		criteria.add(Restrictions.ne("applicationComment.createdBy.userId", application.getParticipantID().getUser().getUserId()));
		criteria.add(Restrictions.eq("applicationComment.viewableToParticipant", true));

		criteria.setProjection(Projections.rowCount());
		totalUnreadMessages = (Long) criteria.uniqueResult();

		return totalUnreadMessages;
	}

	@Override
	public ApplicationCourseCompliancy callGetApplicationCourseCompliancyProc(final long applicationCoursesID) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query q = session.getNamedQuery("callGetApplicationCourseCompliancy");
		q.setLong("applicationCoursesId", applicationCoursesID);
		return (ApplicationCourseCompliancy) q.list().get(0);
	}

	@Override
	public List<ApplicationNumber> callGetNextApplicationNumberProc() throws Exception {
		Connection connection = this.getConnection();
		CallableStatement callableStatement = connection.prepareCall("{call getNextApplicationNumber(?)}");
		callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);
		callableStatement.execute();
		int newAppNbr = callableStatement.getInt(1);

		ApplicationNumber appNbr = new ApplicationNumber();
		appNbr.setApplicationNumber(new Long(newAppNbr));
		ArrayList<ApplicationNumber> rtnList = new ArrayList<ApplicationNumber>();
		rtnList.add(appNbr);
		return rtnList;

		/*
		 * return (List<ApplicationNumber>) getHibernateTemplate().execute(new HibernateCallback() { public Object doInHibernate(Session session) throws HibernateException, SQLException { Query q =
		 * session.getNamedQuery("getNextApplicationNumber"); // DAS 5/8/2011 - I believe this is used to indicate the returned value, but....???? q.setInteger("applicationNumber", new
		 * Integer(0).intValue()); return q.list(); } });
		 */
	}

	@Override
	public List<PercentagePayOut> callFindPercentagePayoutForAppCourse(final Long applicationCoursesID) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query q = session.getNamedQuery("findPercentagePayoutForAppCourse");
		q.setLong("applicationCoursesID", applicationCoursesID);
		List<PercentagePayOut> result = q.list();

		return result;
	}

	@Override
	public Boolean deleteAppNumber(Application application, String requester) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query q = session.getNamedQuery("deleteAppNumber");
		q.setLong("clientID", application.getBenefitPeriodID().getProgramID().getClientID().getClientId());
		q.setLong("appnumber", application.getApplicationNumber());
		q.setString("Requestor", requester);
		int update = q.executeUpdate();

		return Boolean.TRUE;
	}

}
