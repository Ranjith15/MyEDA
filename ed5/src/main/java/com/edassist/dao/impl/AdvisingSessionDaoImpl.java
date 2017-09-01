package com.edassist.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edassist.dao.AdvisingSessionDao;
import com.edassist.models.contracts.crm.advising.AdvisingSession;

@Repository
@Transactional
public class AdvisingSessionDaoImpl extends GenericDaoImpl<AdvisingSession> implements AdvisingSessionDao {

	private static Logger log = Logger.getLogger(AdvisingSessionDaoImpl.class);

	@Override
	public List<AdvisingSession> findSessionsByParticipant(Long participantId) {

		Session session = this.getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AdvisingSession> criteria = builder.createQuery(AdvisingSession.class);
		Root<AdvisingSession> root = criteria.from(AdvisingSession.class);

		List<Predicate> predicatesList = new ArrayList<Predicate>();
		ParameterExpression<Long> parameter = builder.parameter(Long.class, "participantId");
		predicatesList.add(builder.equal(root.get("participantId"), parameter));

		criteria.select(root);
		criteria.where(predicatesList.toArray(new Predicate[0])).distinct(true);
		Query typedQuery = session.createQuery(criteria);
		typedQuery.setParameter("participantId", participantId);

		List<AdvisingSession> advisingSessions = typedQuery.getResultList();
		return advisingSessions;

	}

}
