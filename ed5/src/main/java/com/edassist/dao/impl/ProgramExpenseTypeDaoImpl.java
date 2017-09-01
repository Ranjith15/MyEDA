package com.edassist.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.edassist.dao.ProgramExpenseTypeDao;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.ProgramExpenseType;

@Repository
@Transactional
public class ProgramExpenseTypeDaoImpl extends GenericDaoImpl<ProgramExpenseType> implements ProgramExpenseTypeDao {

	@Override
	@Transactional
	public List<ProgramExpenseType> search(Long programId, Long programExpenseTypeId) throws ExceededMaxResultsException {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ProgramExpenseType.class);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		boolean haveValue = false;
		if (programId != null) {
			criteria.createAlias("programID", "programID");
			criteria.add(Restrictions.eq("programID.programID", programId));
		}
		if (programExpenseTypeId != null) {
			criteria.createAlias("expenseTypeID", "expenseTypeID");
			criteria.add(Restrictions.eq("expenseTypeID.expenseTypeID", programExpenseTypeId));
		}

		haveValue = true;

		if (haveValue) {
			int count = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
			checkMaxResultsExceeded(count);

			if (count > 0) {
				criteria.setProjection(null);
				return (List<ProgramExpenseType>) criteria.list();
			}

			return new ArrayList<ProgramExpenseType>();
		} else {
			return new ArrayList<ProgramExpenseType>();
		}
	}

}
