
package com.edassist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.edassist.models.domain.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.edassist.dao.UserDao;
import com.edassist.exception.ExceededMaxResultsException;

@Repository
@Transactional
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

	@Override
	@Transactional
	public List<User> findUserByClient(String userName, Long clientId) {

		return this.getSessionFactory().getCurrentSession().createCriteria(User.class).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).add(Restrictions.eq("userName", userName))
				.createAlias("participantID", "participantID").createAlias("participantID.client", "client").add(Restrictions.eq("client.clientId", clientId)).list();
	}

	@Override
	public List<ThinParticipantSearch> findThinUserByClient(String employeeId, String uniqueId, String firstName, String lastName, Long clientId) {
		String mainQueryString = "FROM ThinParticipantSearch par JOIN par.user usr where par.client = :clientId ";
		if (firstName != null && firstName.length() > 0 && !"-1".equals(firstName)) {
			mainQueryString += "AND usr.firstName = :firstName ";
		}
		if (lastName != null && lastName.length() > 0 && !"-1".equals(lastName)) {
			mainQueryString += "AND usr.lastName = :lastName ";
		}
		if (employeeId != null && employeeId.length() > 0 && !"-1".equals(employeeId)) {
			mainQueryString += "AND par.employeeId = :employeeId ";
		}
		if (uniqueId != null && uniqueId.length() > 0 && !"-1".equals(uniqueId)) {
			mainQueryString += "AND par.uniqueId = :uniqueId ";
		}
		javax.persistence.Query query = setQueryParameters(mainQueryString, employeeId, uniqueId, firstName, lastName, clientId);
		List<ThinParticipantSearch> participantList = new ArrayList<>();
		List<Object[]> tupleList = query.getResultList();

		for (Object[] tuple : tupleList) {
			participantList.add((ThinParticipantSearch) tuple[0]);
		}

		return participantList;
	}

	private javax.persistence.Query setQueryParameters(String queryString, String employeeId, String uniqueId, String firstName, String lastName, Long clientId) {
		Session session = this.getSessionFactory().getCurrentSession();
		javax.persistence.Query query = session.createQuery(queryString);
		query.setParameter("clientId", clientId);
		if (query.getParameters().stream().anyMatch((p -> p.getName().equals("employeeId")))) {
			query.setParameter("employeeId", employeeId);
		}
		if (query.getParameters().stream().anyMatch((p -> p.getName().equals("uniqueId")))) {
			query.setParameter("uniqueId", uniqueId);
		}
		if (query.getParameters().stream().anyMatch((p -> p.getName().equals("firstName")))) {
			query.setParameter("firstName", firstName);
		}
		if (query.getParameters().stream().anyMatch((p -> p.getName().equals("lastName")))) {
			query.setParameter("lastName", lastName);
		}

		return query;
	}

	@Override
	@Transactional
	public List<User> findInternalUser(String userName) throws ExceededMaxResultsException {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);

		return this.getSessionFactory().getCurrentSession().createCriteria(User.class).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).add(Restrictions.eq("userName", userName))
				.createAlias("participantID", "participantID", CriteriaSpecification.LEFT_JOIN).add(Restrictions.isNull("participantID.user")).list();
	}

}
