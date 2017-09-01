package com.edassist.dao.impl;

import com.edassist.dao.BenefitPeriodDao;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.BenefitPeriod;
import com.edassist.models.sp.CapInfo;
import com.edassist.models.sp.ThinBenefitPeriod;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
public class BenefitPeriodDaoImpl extends GenericDaoImpl<BenefitPeriod> implements BenefitPeriodDao {

	@Override
	public List<BenefitPeriod> search(String programId) throws ExceededMaxResultsException {
		String query = "from BenefitPeriod b WHERE b.programID.programID =:programId";
		List benefitPeriodList = this.getSessionFactory().getCurrentSession().createNamedQuery(query).setParameter("programId", programId).list();

		if (benefitPeriodList == null) {
			benefitPeriodList = new ArrayList<BenefitPeriod>();
		}

		return benefitPeriodList;
	}

	@Override
	public List<ThinBenefitPeriod> callBPList3(final Long participantID) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query q = session.getNamedQuery("getBPList3");
		q.setLong("participantID", participantID);

		return (List<ThinBenefitPeriod>) q.list();
	}

	@Override
	public List<String> getBPListByClient(Long clientId) throws ExceededMaxResultsException {
		Session session = this.getSessionFactory().getCurrentSession();
		String queryString = "";
		queryString += "SELECT DISTINCT bp.benefitPeriodName ";
		queryString += "FROM " + BenefitPeriod.class.getName() + " bp ";
		queryString += "WHERE bp.programID.clientID = :clientId AND bp.programID.programTypeID.programTypeID in :programTypeIDs AND bp.programID.active = 1";
		Query query = session.createQuery(queryString);
		query.setParameter("clientId", clientId);
		query.setParameter("programTypeIDs", new ArrayList<>(Arrays.asList(4, 5, 8, 9)));
		return (List<String>) query.list();
	}

	@Override
	public CapInfo getParticipantCapInfo(Long participantId, Long programId, Long degreeObjectiveId, String benefitPeriod) {
		Session session = this.getSessionFactory().getCurrentSession();
		StoredProcedureQuery query = session.createNamedStoredProcedureQuery("getCapLimitForParticipant");
		query.setParameter("participantId", participantId);
		query.setParameter("programId", programId);
		query.setParameter("degreeObjectiveId", degreeObjectiveId);
		query.setParameter("BenefitPeriodName", benefitPeriod);

		CapInfo capInfo = (CapInfo) query.getSingleResult();
		return capInfo;
	}

}
