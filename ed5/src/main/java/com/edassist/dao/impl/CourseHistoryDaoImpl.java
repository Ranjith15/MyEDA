package com.edassist.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edassist.dao.CourseHistoryDao;
import com.edassist.models.domain.CourseHistory;

@Repository
@Transactional
public class CourseHistoryDaoImpl extends GenericDaoImpl<CourseHistory> implements CourseHistoryDao {

	@Override
	public Long findLatestChangeByStatus(Long applicationId, Long primaryStatus, Long secondaryStatus) {
		return findLatestChangeByStatus(applicationId, "applicationStatus", primaryStatus, secondaryStatus);
	}

	private Long findLatestChangeByStatus(Long applicationId, String statusField, Long primaryStatus, Long secondaryStatus) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(CourseHistory.class);

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("application.applicationID", applicationId));
		criteria.createAlias(statusField, statusField);
		if (primaryStatus != null) {
			if (secondaryStatus == null) {
				criteria.add(Restrictions.eq(statusField + ".applicationStatusCode", primaryStatus));
			} else {
				criteria.add(Restrictions.or(Restrictions.eq(statusField + ".applicationStatusCode", primaryStatus), Restrictions.eq(statusField + ".applicationStatusCode", secondaryStatus)));
			}
		}
		criteria.setProjection(Projections.max("changeId"));

		List<Long> results = (List<Long>) criteria.list();
		if (CollectionUtils.isNotEmpty(results)) {
			return results.get(0);
		}
		return null;
	}
}
