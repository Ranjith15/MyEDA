package com.edassist.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.edassist.dao.ProgramProvidersDao;
import com.edassist.models.domain.ProgramProviders;

@Repository
@Transactional
public class ProgramProvidersDaoImpl extends GenericDaoImpl<ProgramProviders> implements ProgramProvidersDao {

	@Override
	@Transactional
	public List<ProgramProviders> findByParamValues(String[] paramNames, Object[] paramValues) {

		Criteria criteria = null;
		List<ProgramProviders> results = null;

		if (paramNames != null && paramValues != null && paramNames.length == paramValues.length) {
			criteria = this.getSessionFactory().getCurrentSession().createCriteria(ProgramProviders.class);

			for (int i = 0; i < paramNames.length; i++) {
				criteria.add(Restrictions.eq(paramNames[i], paramValues[i]));

			}
		}

		results = criteria.list();
		return results;

	}
}
