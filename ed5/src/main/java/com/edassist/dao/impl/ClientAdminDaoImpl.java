package com.edassist.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edassist.dao.ClientAdminDao;
import com.edassist.models.domain.PaginationResult;
import com.edassist.models.domain.ThinApp;
import com.edassist.models.domain.ThinParticipantSearch;

@Repository
@Transactional
public class ClientAdminDaoImpl extends GenericDaoImpl<ThinParticipantSearch> implements ClientAdminDao {

	private static Logger log = Logger.getLogger(ClientAdminDaoImpl.class);

	@Override
	public PaginationResult<ThinParticipantSearch> getApplicationsByClient(String firstName, String lastName, String userName, Long applicationNumber, Long applicationStatus, String benefitPeriodName,
			Long clientId, String sortBy, int index, int recordsPerPage) {
		List<Long> arrAppStatusIds = new ArrayList<Long>(Arrays.asList(90L, 100L, 110L, 120L, 125L, 135L, 400L, 425L, 450L, 500L, 510L, 530L, 540L, 545L, 900L, 910L, 930L));

		// Query to fetch count
		String mainQueryString = buildQuery(firstName, lastName, userName, applicationNumber, applicationStatus, benefitPeriodName, null);
		Query countQuery = setQueryParameters("select count(distinct par.participantId) " + mainQueryString, clientId, firstName, lastName, userName, applicationNumber, applicationStatus,
				benefitPeriodName);
		Long totalRecordsCount = (Long) countQuery.getSingleResult();

		// Query to fetch actual results
		mainQueryString = buildQuery(firstName, lastName, userName, applicationNumber, applicationStatus, benefitPeriodName, sortBy);
		Query query = setQueryParameters("select distinct par, usr.firstName, usr.lastName, usr.userName " + mainQueryString, clientId, firstName, lastName, userName, applicationNumber,
				applicationStatus, benefitPeriodName);
		List<ThinParticipantSearch> participantList = new ArrayList<>();
		List<Object[]> tupleList = query.setFirstResult((index - 1) * recordsPerPage).setMaxResults(recordsPerPage).getResultList();

		// Construct participantList
		for (Object[] tuple : tupleList) {
			participantList.add((ThinParticipantSearch) tuple[0]);
		}

		// Filter applications based on user search criteria
		filterApplicationsOnSearchCriteria(participantList, applicationNumber, applicationStatus, arrAppStatusIds, benefitPeriodName);
		return new PaginationResult<ThinParticipantSearch>(participantList, totalRecordsCount);
	}

	private String buildQuery(String firstName, String lastName, String userName, Long applicationNumber, Long applicationStatus, String benefitPeriodName, String sortBy) {
		String mainQueryString = "FROM ThinParticipantSearch par LEFT JOIN par.applicationSet app LEFT JOIN par.user usr LEFT JOIN app.benefitPeriodID bp where par.client = :clientId ";
		if (applicationNumber != null && applicationNumber != 0) {
			mainQueryString += "AND app.applicationNumber = :applicationNumber ";
		} else {
			if (firstName != null && firstName.length() > 0 && !"-1".equals(firstName)) {
				mainQueryString += "AND usr.firstName like :firstName ";
			}

			if (lastName != null && lastName.length() > 0 && !"-1".equals(lastName)) {
				mainQueryString += "AND usr.lastName like :lastName ";
			}

			if (userName != null && userName.length() > 0 && !"-1".equals(userName)) {
				mainQueryString += "AND usr.userName like :userName ";
			}

			if (applicationStatus != null && applicationStatus != 0) {
				mainQueryString += "AND app.applicationStatusID in (:applicationStatusId) ";
			}

			if (benefitPeriodName != null && benefitPeriodName.length() > 0 && !"-1".equals(benefitPeriodName)) {
				mainQueryString += "AND bp.benefitPeriodName = :benefitPeriodName ";
			}
		}

		if (null != sortBy) {
			if (sortBy.equals("firstName")) {
				mainQueryString += "order by usr.firstName";
			} else if (sortBy.equals("lastName")) {
				mainQueryString += "order by usr.lastName";
			} else if (sortBy.equals("userId")) {
				mainQueryString += "order by usr.userName";
			}
		}
		return mainQueryString;
	}

	private Query setQueryParameters(String queryString, Long clientId, String firstName, String lastName, String userName, Long applicationNumber, Long applicationStatus, String benefitPeriodName) {

		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(queryString);
		query.setParameter("clientId", clientId);
		if (query.getParameters().stream().anyMatch((p -> p.getName().equals("firstName")))) {
			query.setParameter("firstName", "%" + firstName + "%");
		}
		if (query.getParameters().stream().anyMatch((p -> p.getName().equals("lastName")))) {
			query.setParameter("lastName", "%" + lastName + "%");
		}
		if (query.getParameters().stream().anyMatch((p -> p.getName().equals("userName")))) {
			query.setParameter("userName", "%" + userName + "%");
		}
		if (query.getParameters().stream().anyMatch((p -> p.getName().equals("applicationNumber")))) {
			query.setParameter("applicationNumber", applicationNumber);
		}
		if (query.getParameters().stream().anyMatch((p -> p.getName().equals("applicationStatusId")))) {
			query.setParameter("applicationStatusId", applicationStatus);
		}
		// only set this query parameter when it's present in the query
		if (query.getParameters().stream().anyMatch((p -> p.getName().equals("benefitPeriodName")))) {
			query.setParameter("benefitPeriodName", benefitPeriodName);
		}

		return query;
	}

	private void filterApplicationsOnSearchCriteria(List<ThinParticipantSearch> participantList, Long applicationNumber, Long applicationStatus, List<Long> appStatusIds, String benefitPeriod) {
		List<ThinApp> thinApps = null;
		for (ThinParticipantSearch participant : participantList) {
			thinApps = new ArrayList<ThinApp>();
			for (ThinApp thinApp : participant.getApplicationSet()) {
				if (applicationNumber != null && applicationNumber != 0) {
					if (thinApp.getApplicationNumber().compareTo(applicationNumber) == 0) {
						thinApps.add(thinApp);
					}
				} else {
					if (applicationStatus != null && applicationStatus != 0 && benefitPeriod != null && !benefitPeriod.isEmpty()) {
						if (applicationStatus.compareTo(thinApp.getApplicationStatusID().getApplicationStatusID()) == 0 && thinApp.getBenefitPeriodID().getBenefitPeriodName().equals(benefitPeriod)) {
							thinApps.add(thinApp);
						}
					} else if (applicationStatus != null && applicationStatus != 0) {
						if (applicationStatus.compareTo(thinApp.getApplicationStatusID().getApplicationStatusID()) == 0) {
							thinApps.add(thinApp);
						}
					} else if (benefitPeriod != null && !benefitPeriod.isEmpty()) {
						if (thinApp.getBenefitPeriodID().getBenefitPeriodName().equals(benefitPeriod) && appStatusIds.contains(thinApp.getApplicationStatusID().getApplicationStatusID())) {
							thinApps.add(thinApp);
						}
					} else if (appStatusIds.contains(thinApp.getApplicationStatusID().getApplicationStatusID())) {
						thinApps.add(thinApp);
					}
				}
			}
			participant.setApplicationSet(thinApps);
		}
	}

}
