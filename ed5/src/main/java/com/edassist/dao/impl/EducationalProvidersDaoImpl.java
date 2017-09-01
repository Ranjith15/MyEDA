package com.edassist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edassist.dao.EducationalProvidersDao;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.contracts.EducationalProvidersSearch;
import com.edassist.models.domain.EducationalProviders;
import com.edassist.utils.CommonUtil;

@Repository
@Transactional
public class EducationalProvidersDaoImpl extends GenericDaoImpl<EducationalProviders> implements EducationalProvidersDao {

	@Override
	public List<EducationalProviders> search(Object object) throws ExceededMaxResultsException {
		if (object == null) {
			throw new BadRequestException("Input param must not be null.");
		}

		EducationalProviders educationalProviders = (EducationalProviders) object;

		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(EducationalProviders.class);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		if (educationalProviders.getProviderCode() != null && !educationalProviders.getProviderCode().isEmpty()) {
			criteria.add(Restrictions.like("providerCode", educationalProviders.getProviderCode()));
		} else {
			if (educationalProviders.getProviderName() != null && educationalProviders.getProviderName().length() > 0) {
				criteria.add(Restrictions.like("providerName", educationalProviders.getProviderName()));
			}
			if (educationalProviders.getProviderCity() != null && educationalProviders.getProviderCity().length() > 0) {
				criteria.add(Restrictions.like("providerCity", educationalProviders.getProviderCity()));
			}
			// TODO: The Dao shouldn't know about the "--ALL--" option
			if (educationalProviders.getProviderState() != null && !educationalProviders.getProviderState().equals(" ") && // equates to " " in select list
					!educationalProviders.getProviderState().equals("") && // equates to "" in select list
					!educationalProviders.getProviderState().equals("0")) { // equates to "--ALL--" in select list
				criteria.add(Restrictions.like("providerState", educationalProviders.getProviderState()));
			}
			if (educationalProviders.getProviderZip() != null && educationalProviders.getProviderZip().length() > 0) {
				criteria.add(Restrictions.like("providerZip", educationalProviders.getProviderZip()));
			}
			if (educationalProviders.getParentCode() != null && educationalProviders.getParentCode().length() > 0) {
				criteria.add(Restrictions.like("parentCode", educationalProviders.getParentCode()));
			}
			if (educationalProviders.isRestrictSearchToFeaturedProviders()) {
				criteria.add(Restrictions.eq("featuredProvider", Boolean.TRUE));
			}
		}

		int count = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		checkMaxResultsExceeded(count);

		if (count > 0) {
			criteria.setProjection(null);
			return (List<EducationalProviders>) criteria.list();
		}
		return new ArrayList<EducationalProviders>();
	}

	@Override
	public List<EducationalProviders> search(EducationalProvidersSearch providersSearch) throws ExceededMaxResultsException {

		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(EducationalProviders.class);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		boolean haveSearchValue = false;
		if (providersSearch.getEducationalProviderId() != null && !providersSearch.getEducationalProviderId().isEmpty()) {
			criteria.add(Expression.sql("{alias}.educationalProviderId like ?", providersSearch.getEducationalProviderId(), StandardBasicTypes.STRING));
			haveSearchValue = true;
		} else {

			if (providersSearch.getProviderName() != null && !providersSearch.getProviderName().trim().isEmpty()) {
				criteria.add(Restrictions.like("providerName", providersSearch.getProviderName()));
				haveSearchValue = true;
			}

			if (providersSearch.getProviderCity() != null && !providersSearch.getProviderCity().trim().isEmpty()) {
				criteria.add(Restrictions.like("providerCity", providersSearch.getProviderCity()));
				haveSearchValue = true;
			}

			// TODO: The Dao shouldn't know about the "--ALL--" option
			/*
			 * "0" String equates to "--ALL--" in select list
			 */
			if (providersSearch.getProviderState() != null && !providersSearch.getProviderState().trim().isEmpty() && !providersSearch.getProviderState().equals("0")) {
				criteria.add(Restrictions.like("providerState", providersSearch.getProviderState()));
				haveSearchValue = true;
			}

			if (StringUtils.isNotBlank(providersSearch.getAccreditingBodyID()) || StringUtils.isNotBlank(providersSearch.getAccreditingTypeName())) {

				criteria.createAlias("providerAccreditationCollection", "accreditationCollection").createAlias("accreditationCollection.accreditingBodyID", "accredation");

				if (StringUtils.isNotBlank(providersSearch.getAccreditingBodyID())) {
					criteria.add(Restrictions.eq("accredation.accreditingBodyID", new Long(providersSearch.getAccreditingBodyID())));
				}

				if (StringUtils.isNotBlank(providersSearch.getAccreditingTypeName())) {
					criteria.add(Restrictions.eq("accredation.accreditingTypeDisplayName", providersSearch.getAccreditingTypeName()));
				}

				criteria.add(Restrictions.ge("accreditationCollection.effectiveEndDate", new Date()));

				haveSearchValue = true;
			}

			if (providersSearch.getProviderZip() != null && !providersSearch.getProviderZip().trim().isEmpty()) {
				criteria.add(Restrictions.like("providerZip", providersSearch.getProviderZip()));
				haveSearchValue = true;
			}

			if (providersSearch.getParentCode() != null && !providersSearch.getParentCode().trim().isEmpty()) {
				criteria.add(Restrictions.like("parentCode", providersSearch.getParentCode()));
				haveSearchValue = true;
			}

			if (providersSearch.isFeaturedProvider() != null && providersSearch.isFeaturedProvider()) {
				haveSearchValue = true;
				if (providersSearch.isOnlineProvider() != null && providersSearch.isOnlineProvider()) {
					criteria.add(Restrictions.eq("deliveryOnline", providersSearch.isOnlineProvider().booleanValue()));
				}

				if (providersSearch.isClientEEN() && providersSearch.isClientFPN()) {
					criteria.add(Restrictions.or(Restrictions.eq("enhancedEdAssistNetwork", true), Restrictions.eq("featuredProvider", true)));
				} else {
					if (providersSearch.isClientEEN()) {
						String eenIncludesFpn = "";
						try {
							new CommonUtil();
							eenIncludesFpn = CommonUtil.getHotProperty("EEN.includes.FPN");
						} catch (Exception e) {
							;
						}
						if (eenIncludesFpn.equalsIgnoreCase("true")) {
							/* EEN clients have access to both networks */
							criteria.add(Restrictions.or(Restrictions.eq("enhancedEdAssistNetwork", true), Restrictions.eq("featuredProvider", true)));
						} else {
							criteria.add(Restrictions.eq("enhancedEdAssistNetwork", true));
						}
					} else {
						if (providersSearch.isClientFPN()) {
							/* FPN has access to just FPN */
							criteria.add(Restrictions.eq("featuredProvider", true));
						} else {
							/* this client is eligible for no featured schools - queer up the search */
							criteria.add(Restrictions.eq("featuredProvider", true));
							criteria.add(Restrictions.eq("featuredProvider", false));
						}
					}
				}
			}
		}

		if (haveSearchValue) {

			criteria.add(Restrictions.eq("providerStatusID", Byte.valueOf("1")));

			int count = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
			/*
			 * disable this test when searching for featured providers. Further result set pruning happens in the controllers
			 */
			if (!providersSearch.isFeaturedProvider()) {
				checkMaxResultsExceeded(count);
			}

			if (count > 0) {
				criteria.setProjection(null);
				return (List<EducationalProviders>) criteria.list();
			}

			return new ArrayList<EducationalProviders>();
		} else {
			return new ArrayList<EducationalProviders>();
		}
	}

	@Override
	public List<EducationalProviders> search(String providerName, String providerId, String providerState, boolean isFeatureProvider) {
		String query = "from EducationalProviders e WHERE e.providerName like :providerName and e.educationalProviderId like :providerId and e.providerState like :providerState and featuredProvider=:isFeatureProvider";
		List<EducationalProviders> providerList = (List<EducationalProviders>) this.getSessionFactory().getCurrentSession().createNamedQuery(query).setParameter("providerName", providerName)
				.setParameter("providerId", providerId).setParameter("providerState", providerState).setParameter("isFeatureProvider", isFeatureProvider).list();

		return providerList;
	}

}
