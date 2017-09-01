package com.edassist.dao.impl;

import com.edassist.dao.ParticipantDao;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.*;
import com.edassist.models.sp.AccountSnapshot;
import com.edassist.models.sp.AccountSnapshotForParticipant;
import com.edassist.models.sp.IvrParticipant;
import com.edassist.models.sp.ThinSupervisor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
public class ParticipantDaoImpl extends GenericDaoImpl<Participant> implements ParticipantDao {

	private static final Character SUPERVISOR_ID_TYPE_TAMS_ID = 'S';
	private static final Character SUPERVISOR_ID_TYPE_EMPLOYEE_ID = 'E';

	private List<ParticipantSupervisor> callgetSupervisoredParticipantList(final Participant participant, final String FirstName, final String LastName, final String Employeeid) {
		Session session = this.getSessionFactory().getCurrentSession();

		StringBuilder QueryString = new StringBuilder();

		QueryString.append("FROM ParticipantSupervisor ps where  ps.supervisor = " + participant.getParticipantId().toString());

		if (FirstName != null) {
			QueryString.append(" and ps.participantID.user.firstName like '%" + FirstName + "%'");
		}

		if (LastName != null) {
			QueryString.append(" and ps.participantID.user.lastName like '%" + LastName + "%'");
		}

		if (Employeeid != null) {
			QueryString.append(" and ps.participantID.employeeId like '%" + Employeeid + "%'");
		}

		System.out.println("Here is the new Search Process Result");
		System.out.println("Here is the new Search Process Result : " + QueryString.toString());

		return (List<ParticipantSupervisor>) session.createQuery(QueryString.toString()).setMaxResults(502).list();
	}

	@Override
	@Transactional
	public List<ParticipantSupervisor> findSupervisoredParticipantList(final Participant participant) {

		if (participant == null) {
			throw new BadRequestException("Participant must not be null.");
		}

		if (participant.getClient() == null) {
			throw new BadRequestException("Client must not be null.");
		}

		if (participant.getClient().getSupervisorIDType() == null) {
			throw new BadRequestException("Client's supervisorIdType must not be null.");
		}

		return callgetSupervisoredParticipantList(participant);

	}

	private List<ParticipantSupervisor> callgetSupervisoredParticipantList(final Participant participant) {
		Session session = this.getSessionFactory().getCurrentSession();
		return (List<ParticipantSupervisor>) session.createQuery("FROM ParticipantSupervisor ps where ps.supervisor = " + participant.getParticipantId().toString()).setMaxResults(502).list();
	}

	@Override
	@Transactional
	public List<Participant> search(Participant participant) throws ExceededMaxResultsException {

		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Participant.class);

		boolean haveValue = false;

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		criteria.createAlias("user", "user").createAlias("client", "client");

		criteria.add(Restrictions.eq("client.active", Client.ACTIVE));

		if (participant == null) {
			throw new BadRequestException("Participant must not be null");
		}

		if (participant.getUser() != null && participant.getUser().getFirstName() != null && !(participant.getUser().getFirstName().trim().isEmpty())) {
			criteria.add(Restrictions.like("user.firstName", participant.getUser().getFirstName()));
			haveValue = true;
		}

		if (participant.getUser() != null && participant.getUser().getLastName() != null && !(participant.getUser().getLastName().trim().isEmpty())) {
			criteria.add(Restrictions.like("user.lastName", participant.getUser().getLastName()));
			haveValue = true;
		}
		if (participant.getSsn() != null && !participant.getSsn().trim().isEmpty()) {
			criteria.add(Restrictions.like("ssn", participant.getSsn()));
			haveValue = true;
		}

		if (participant.getEmployeeId() != null && !participant.getEmployeeId().trim().trim().isEmpty()) {
			criteria.add(Restrictions.like("employeeId", participant.getEmployeeId()));
			haveValue = true;
		}
		if (participant.getEmail() != null && !participant.getEmail().trim().isEmpty()) {
			criteria.createAlias("workAddress", "workAddress");
			criteria.add(Restrictions.like("workAddress.email", participant.getEmail()));
			haveValue = true;
		}

		if (participant.getActive() != null && !participant.getActive().toString().trim().isEmpty()) {
			criteria.add(Restrictions.eq("active", participant.getActive()));
			haveValue = true;
		}

		if (participant.getClient() != null && participant.getClient().getClientId() != null && participant.getClient().getClientId() != 0) {
			criteria.add(Restrictions.eq("client.clientId", participant.getClient().getClientId()));
			haveValue = true;
		}

		if (haveValue) {
			int count = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
			checkMaxResultsExceeded(count);

			if (count > 0) {
				criteria.setProjection(null);
				return (List<Participant>) criteria.list();
			}
			return new ArrayList<Participant>();
		} else {
			return new ArrayList<Participant>();
		}

	}

	@Override
	@Transactional
	public List<AccountSnapshot> callAccountSnapshotProc(final Long participantID, final String benefitPeriodName) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query q = session.getNamedQuery("getAccountSnapshot");
		q.setLong("participantID", participantID);
		q.setString("benefitPeriodName", benefitPeriodName);
		q.setLong("currApplicationID", 0);
		return (List<AccountSnapshot>) q.list();
	}

	@Override
	@Transactional
	public List<AccountSnapshotForParticipant> callAccountSnapshotForParticipantProc(final Long participantID) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query q = session.getNamedQuery("CallgetAccountSnapshotForParticipant");
		q.setLong("participantID", participantID);
		return (List<AccountSnapshotForParticipant>) q.list();
	}

	@Override
	public List<IvrParticipant> getIvrParticipant(String phoneNumber) {
		Session session = this.getSessionFactory().getCurrentSession();
		StoredProcedureQuery query = session.createNamedStoredProcedureQuery("findParticipantByPhone");
		query.setParameter("phonenumber", phoneNumber);

		List<IvrParticipant> ivrParticipants = query.getResultList();
		return ivrParticipants;
	}

	@Override
	@Transactional
	public List<ThinSupervisor> getSupervisors(final Participant participant) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		List<ThinSupervisor> supervisors = new ArrayList<>();
		try {
			connection = this.getConnection();

			preparedStatement = connection.prepareCall("{call findThinSupervisor(?)}");

			preparedStatement.setLong(1, participant.getParticipantId());

			ResultSet result;

			result = preparedStatement.executeQuery();

			while (result.next()) {
				ThinSupervisor supervisor = new ThinSupervisor();

				supervisor.setParticipantSupervisorId(result.getLong("ParticipantSupervisorID"));
				supervisor.setAppealDesignee(false);
				supervisor.setApprovalLevel(result.getInt("ApprovalLevel"));
				supervisor.setSupervisor(result.getLong("Supervisor"));
				supervisor.setUserID(result.getLong("UserID"));

				Session session = this.getSessionFactory().getCurrentSession();
				String hql = "FROM " + ThinUser.class.getName() + " user WHERE user.id = :userId";
				Query<ThinUser> query = session.createQuery(hql);
				query.setParameter("userId", supervisor.getUserID());

				List<ThinUser> users = query.getResultList();

				ThinUser u = users.get(0);
				supervisor.getUserName().setFirstName(u.getFirstName());
				supervisor.getUserName().setLastName(u.getLastName());
				supervisor.getUserName().setmI(u.getmI());
				supervisor.getUserName().setId(u.getId());
				supervisors.add(supervisor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeDBResources(preparedStatement, connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return supervisors;

	}

	// TODO remove this method once "History at a Glance" goes live. We will only be using the consolidated history dao from ApplicationDao
	@Override
	public PaginationResult<ThinApp> searchThinAppActivityForMyTeam(final Participant supervisorParticipant, int index, int recordsPerPage) {
		List<ThinApp> thinAppActivityForMyTeamList = null;

		String mainQueryString = "FROM ThinApp app where app.applicationStatusID in (:appStatusIds) AND app.applicationTypeID.applicationTypeID != :applicationTypeId AND app.participantID in (select sup.participantID FROM ParticipantSupervisor sup WHERE sup.supervisor = :supervisorParticipantId)";

		Query countQuery = setQueryParameters("select count(*) " + mainQueryString, supervisorParticipant);
		Query query = setQueryParameters(mainQueryString, supervisorParticipant);

		Long totalRecordsCount = (Long) countQuery.getSingleResult();

		thinAppActivityForMyTeamList = query.setFirstResult((index - 1) * recordsPerPage).setMaxResults(recordsPerPage).getResultList();

		return new PaginationResult<ThinApp>(thinAppActivityForMyTeamList, totalRecordsCount);
	}

	private Query setQueryParameters(String queryString, Participant supervisorParticipant) {
		List<Long> arrAppStatusIds = new ArrayList<Long>(Arrays.asList(110L, 120L, 125L, 135L, 400L, 450L, 500L, 510L, 530L, 545L, 540L, 900L, 930L));
		Session session = this.getSessionFactory().getCurrentSession();
		Query<ThinApp> query = session.createQuery(queryString);
		query.setParameter("appStatusIds", arrAppStatusIds);
		query.setParameter("applicationTypeId", 500L);
		query.setParameter("supervisorParticipantId", supervisorParticipant.getParticipantId());

		return query;
	}

}
