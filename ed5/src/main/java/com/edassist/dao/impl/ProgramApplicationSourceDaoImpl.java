package com.edassist.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.edassist.dao.ProgramApplicationSourceDao;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.ProgramApplicationSource;

@Repository
@Transactional
public class ProgramApplicationSourceDaoImpl extends GenericDaoImpl<ProgramApplicationSource> implements ProgramApplicationSourceDao {

	@Override
	@Transactional
	public List<ProgramApplicationSource> search(Long programId, Long applicationSourceId) throws ExceededMaxResultsException {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ProgramApplicationSource.class);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		boolean haveValue = false;
		if (programId != null) {
			criteria.createAlias("programID", "program");
			criteria.add(Restrictions.eq("program.programID", programId));
		}
		if (applicationSourceId != null) {
			criteria.createAlias("applicationSourceID", "applicationSource");
			criteria.add(Restrictions.eq("applicationSource.applicationSourceID", applicationSourceId));
		}

		haveValue = true;

		if (haveValue) {
			int count = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
			checkMaxResultsExceeded(count);

			if (count > 0) {
				criteria.setProjection(null);
				return (List<ProgramApplicationSource>) criteria.list();
			}

			return new ArrayList<ProgramApplicationSource>();
		} else {
			return new ArrayList<ProgramApplicationSource>();
		}
	}

}
