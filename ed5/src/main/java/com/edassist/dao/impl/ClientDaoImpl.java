package com.edassist.dao.impl;

import com.edassist.dao.ClientDao;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.Client;
import com.edassist.models.domain.Program;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ClientDaoImpl extends GenericDaoImpl<Client> implements ClientDao {
	private static Logger log = Logger.getLogger(ClientDaoImpl.class);

	@Override
	public List<Client> search(String clientName, Long clientTypeID) throws ExceededMaxResultsException {
		List<Client> results = null;
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Client.class);
		criteria.createAlias("clientType", "clientType");

		// Client Name Restriction
		if (clientName != null && clientName.isEmpty() == false) {
			criteria.add(Restrictions.like("clientName", clientName));
		}

		// Client type Restriction
		if (clientTypeID != 0) {
			criteria.add(Restrictions.eq("clientType.clientTypeID", clientTypeID));
		}

		// TAM-2882-Pull only TAMS4 clients
		criteria.add(Restrictions.eq("TAMS4", true));

		// Query
		results = criteria.list();
		return results;
	}

	@Override
	public List<Client> findEd5Clients() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Client.class).add(Restrictions.eq("edassist5Enabled", true));
		List<Client> results = criteria.list();

		return results;
	}

	@Override
	public List<Client> findEd4Clients() {
		String mainQueryString = "";
		mainQueryString += "FROM Client cli where cli.active = 1 and cli.TAMS4 = 1";
		Session session = this.getSession();
		Query<Client> query = session.createQuery(mainQueryString);

		return query.getResultList();
	}

	private String buildQuery(int programIds, int bpendDates) {
		StringBuffer buffer = new StringBuffer(PS1);
		for (int i = 0; i < programIds; i++) {
			if (i == 0) {
				buffer.append(QMARK);
			} else {
				buffer.append(QCOMMA);
			}
		}
		buffer.append(PS2);
		for (int i = 0; i < bpendDates; i++) {
			if (i == 0) {
				buffer.append(QMARK);
			} else {
				buffer.append(QCOMMA);
			}
		}
		buffer.append(PS3);

		return buffer.toString();
	}

	private class BenefitperiodWork implements Work {
		private Long[] arrProgramIds;
		private String[] arrBpNames;
		private long result;

		BenefitperiodWork(Long[] arrProgramIds, String[] arrBpNames) {
			this.arrBpNames = arrBpNames;
			this.arrProgramIds = arrProgramIds;
		}

		@Override
		public void execute(Connection connection) throws SQLException {
			PreparedStatement ps = null;
			try {
				ps = connection.prepareStatement(buildQuery(arrProgramIds.length, arrBpNames.length));
				int index = 1;
				for (Long programId : arrProgramIds) {
					ps.setLong(index++, programId.longValue());
				}
				for (String bpName : arrBpNames) {
					ps.setString(index++, bpName);
				}

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					result = rs.getLong(1);
				}
				log.debug("Result :" + result);
			} catch (SQLException ex) {
				log.error(ex);
				throw ex;
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (Exception e) {
						log.error(e);
					}
				}
			}

		}

		public long getResult() {
			return result;
		}

	}

	private static final String PS1 = "select count(*) from (select distinct a.ParticipantID from Application a where a.BenefitPeriodID in "
			+ "(select BenefitPeriodID from BenefitPeriod bp where bp.ProgramID in(";
	private static final String QMARK = "?";
	private static final String QCOMMA = ",?";
	private static final String PS2 = ")and bp.BenefitPeriodName in(";
	private static final String PS3 = "))and a.ApplicationStatusID not in ( 90,910,920))x";

	@Override
	@Transactional
	public List<Program> getProgramsByClient(Long clientId) {

		Session session = this.getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Program> criteria = builder.createQuery(Program.class);
		Root<Program> root = criteria.from(Program.class);

		List<Predicate> predicatesList = new ArrayList<Predicate>();

		ParameterExpression<Long> clientParam = builder.parameter(Long.class, "clientID");
		predicatesList.add(builder.equal(root.get("clientID"), clientParam));

		predicatesList.add(builder.equal(root.get("active"), "1"));

		criteria.select(root);
		criteria.where(predicatesList.toArray(new Predicate[0]));

		Query typedQuery = session.createQuery(criteria);

		typedQuery.setParameter("clientID", clientId);

		List<Program> results = typedQuery.getResultList();

		return results;
	}

}
