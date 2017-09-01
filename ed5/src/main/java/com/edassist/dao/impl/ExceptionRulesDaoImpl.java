package com.edassist.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edassist.dao.ExceptionRulesDao;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.ExceptionRules;

@Repository
@Transactional
public class ExceptionRulesDaoImpl extends GenericDaoImpl<ExceptionRules> implements ExceptionRulesDao {

	@Override
	public List<ExceptionRules> search(String clientName, String ruleName, String friendlyName) throws ExceededMaxResultsException {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ExceptionRules.class);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		if (StringUtils.isNotBlank(clientName)) {
			criteria.createAlias("clientId", "client");
			criteria.add(Restrictions.like("client.clientName", clientName));
		}

		if (StringUtils.isNotBlank(ruleName)) {
			criteria.add(Restrictions.like("ruleName", ruleName));
		}

		if (StringUtils.isNotBlank(friendlyName)) {
			criteria.add(Restrictions.like("friendlyName", friendlyName));// }
		}

		int count = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		checkMaxResultsExceeded(count);

		if (count > 0) {
			criteria.setProjection(null);
			return (List<ExceptionRules>) criteria.list();
		}

		return new ArrayList<ExceptionRules>();
	}
}
