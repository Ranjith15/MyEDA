package com.edassist.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edassist.dao.EducationalProvidersDao;
import com.edassist.dao.GenericDao;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.contracts.EducationalProvidersSearch;
import com.edassist.models.domain.EducationalProviders;
import com.edassist.models.domain.ProgramProviders;
import com.edassist.models.dto.ProgramDTO;
import com.edassist.service.EducationalProvidersService;
import com.edassist.service.ProgramProvidersService;
import com.edassist.service.RulesService;
import com.edassist.utils.CommonUtil;

@Service
public class EducationalProvidersServiceImpl extends GenericServiceImpl<EducationalProviders> implements EducationalProvidersService {

	@Autowired
	private RulesService rulesService;

	@Autowired
	private ProgramProvidersService programProvidersService;

	private EducationalProvidersDao educationalProvidersDao;

	public EducationalProvidersServiceImpl() {
	}

	@Autowired
	public EducationalProvidersServiceImpl(@Qualifier("educationalProvidersDaoImpl") GenericDao<EducationalProviders> genericDao) {
		super(genericDao);
		this.educationalProvidersDao = (EducationalProvidersDao) genericDao;
	}

	@Override
	public List<EducationalProviders> search(Object object) throws ExceededMaxResultsException {
		if (object == null) {
			throw new BadRequestException("Seach object must not be null");
		}
		return educationalProvidersDao.search(object);
	}

	@Override
	public List<EducationalProviders> search(EducationalProvidersSearch searchObject) throws ExceededMaxResultsException {
		if (searchObject == null) {
			throw new BadRequestException("Seach object must not be null");
		}
		return educationalProvidersDao.search(searchObject);
	}

	@Override
	public List<EducationalProviders> search(String providerName, String providerId, String providerState, boolean isFeatureProvider) throws ExceededMaxResultsException {

		return (List<EducationalProviders>) educationalProvidersDao.search(providerName, providerId, providerState, isFeatureProvider);
	}

	@Override
	public EducationalProvidersSearch setEducationProviderSearchCriteria(Long participantId, Long clientId, String providerName, String providerCity, String stateId, Boolean featuredProvider,
			Boolean onlineProvider, String accreditingBodyId, String accreditingTypeName, String degreeObjectiveId, String courseOfStudyId, boolean een, boolean fpn) {
		EducationalProvidersSearch searchCriteria = new EducationalProvidersSearch();

		String aProvider = null;
		String aproviderCity = null;

		if (StringUtils.isEmpty(providerName)) {
			providerName = null;
		}

		if (StringUtils.isEmpty(providerCity)) {
			providerCity = null;
		}

		if (StringUtils.isEmpty(stateId)) {
			stateId = null;
		}

		if (StringUtils.isEmpty(accreditingBodyId)) {
			accreditingBodyId = null;
		}

		if (StringUtils.isEmpty(accreditingTypeName)) {
			accreditingTypeName = null;
		}

		if (StringUtils.isEmpty(degreeObjectiveId)) {
			degreeObjectiveId = null;
		}

		aProvider = providerName;
		aproviderCity = providerCity;
		if (providerName != null) {

			StringBuffer buff = new StringBuffer();
			buff.append("%");
			buff.append(providerName);
			buff.append("%");
			aProvider = buff.toString();
		}

		if (providerCity != null) {

			StringBuffer buff = new StringBuffer();
			buff.append("%");
			buff.append(providerCity);
			buff.append("%");
			aproviderCity = buff.toString();
		}

		if (StringUtils.isEmpty(courseOfStudyId)) {
			courseOfStudyId = null;
		}

		searchCriteria.setEducationalProviderId(null);
		searchCriteria.setProviderName(aProvider);
		searchCriteria.setProviderCity(aproviderCity);
		searchCriteria.setProviderState(stateId);
		searchCriteria.setFeaturedProvider(featuredProvider);
		if (featuredProvider) {
			searchCriteria.setOnlineProvider(onlineProvider);
		}
		searchCriteria.setAccreditingBodyID(accreditingBodyId);
		searchCriteria.setAccreditingTypeName(accreditingTypeName);
		searchCriteria.setDegreeObjectiveID(degreeObjectiveId);
		searchCriteria.setCourseOfStudyID(courseOfStudyId);

		searchCriteria.setClientEEN(een);
		searchCriteria.setClientFPN(fpn);

		return searchCriteria;
	}

	@Override
	public List<EducationalProviders> getEducationalProvidersList(List<EducationalProviders> providersList, EducationalProvidersSearch searchCriteria, Long clientId, Long participantId) {

		List<EducationalProviders> educationalProviderList = new ArrayList<EducationalProviders>();
		Set<EducationalProviders> inEligibleProvidersList = fetchInEligibleProviders(clientId, participantId);

		if (CollectionUtils.isNotEmpty(inEligibleProvidersList)) {
			if (CollectionUtils.isNotEmpty(providersList)) {

				for (EducationalProviders currProvider : providersList) {
					if (currProvider != null) {
						/* include the non-parent provider IF AND ONLY IF location criteria were also specified */
						if (currProvider.getProviderCode().equals(currProvider.getParentCode())
								|| (StringUtils.isNotBlank(searchCriteria.getProviderCity()) || StringUtils.isNotBlank(searchCriteria.getProviderState()))) {
							Map<String, Object> jsonItem = new HashMap<String, Object>();

							String eenIncludesFpn = "";
							try {
								new CommonUtil();
								eenIncludesFpn = CommonUtil.getHotProperty("EEN.includes.FPN");
							} catch (Exception e) {
								;
							}

							if (!inEligibleProvidersList.contains(currProvider)) {

								EducationalProviders educationalProviders = new EducationalProviders();

								educationalProviders.setEducationalProviderId(currProvider.getEducationalProviderId());
								educationalProviders.setProviderName(CommonUtil.toDisplayString(currProvider.getProviderName()));
								educationalProviders.setProviderCode(CommonUtil.toDisplayString(currProvider.getProviderCode()));
								educationalProviders.setProviderAddress1(CommonUtil.toDisplayString(currProvider.getProviderAddress1()));
								educationalProviders.setProviderAddress2(CommonUtil.toDisplayString(currProvider.getProviderAddress2()));
								educationalProviders.setProviderCity(CommonUtil.toDisplayString(currProvider.getProviderCity()));
								educationalProviders.setProviderState(CommonUtil.toDisplayString(currProvider.getProviderState()));
								educationalProviders.setProviderZip(CommonUtil.toDisplayString(currProvider.getProviderZip()));

								educationalProviders.setFeaturedProvider(CommonUtil.toDisplayString("No").equalsIgnoreCase("No") ? false : true);
								if (currProvider.isEnhancedEdAssistNetwork() && searchCriteria.isClientEEN()) {
									jsonItem.put("featuredProvider", CommonUtil.toDisplayString("Yes"));
								}
								educationalProviders.setFeaturedProvider(CommonUtil.toDisplayString("Yes").equalsIgnoreCase("Yes") ? true : false);
								if (currProvider.isFeaturedProvider() && searchCriteria.isClientFPN()) {
									educationalProviders.setFeaturedProvider(CommonUtil.toDisplayString("Yes").equalsIgnoreCase("Yes") ? true : false);
								}
								if (eenIncludesFpn.equalsIgnoreCase("true")) {
									if (currProvider.isFeaturedProvider() && searchCriteria.isClientEEN()) {
										educationalProviders.setFeaturedProvider(CommonUtil.toDisplayString("Yes").equalsIgnoreCase("Yes") ? true : false);
									}
								}

								educationalProviders.setEnhancedEdAssistNetwork(currProvider.isEnhancedEdAssistNetwork());
								educationalProviders.setProviderAccreditationCollection(currProvider.getProviderAccreditationCollection());

								educationalProviderList.add(educationalProviders);

							}

						}
					}
				}

			}
		} else {

			if (CollectionUtils.isNotEmpty(providersList)) {
				for (EducationalProviders currProvider : providersList) {
					if (currProvider != null) {
						if (currProvider.getProviderCode().equals(currProvider.getParentCode()) || ( /*
																										 * include the non-parent provider IF AND ONLY IF location criteria were also specified
																										 */
						StringUtils.isNotBlank(searchCriteria.getProviderCity()) || StringUtils.isNotBlank(searchCriteria.getProviderState()))) {
							Map<String, Object> jsonItem = new HashMap<String, Object>();

							String eenIncludesFpn = "";
							try {
								new CommonUtil();
								eenIncludesFpn = CommonUtil.getHotProperty("EEN.includes.FPN");
							} catch (Exception e) {
								;
							}

							EducationalProviders educationalProviders = new EducationalProviders();

							educationalProviders.setEducationalProviderId(currProvider.getEducationalProviderId());
							educationalProviders.setProviderName(CommonUtil.toDisplayString(currProvider.getProviderName()));
							educationalProviders.setProviderCode(CommonUtil.toDisplayString(currProvider.getProviderCode()));
							educationalProviders.setProviderAddress1(CommonUtil.toDisplayString(currProvider.getProviderAddress1()));
							educationalProviders.setProviderAddress2(CommonUtil.toDisplayString(currProvider.getProviderAddress2()));
							educationalProviders.setProviderCity(CommonUtil.toDisplayString(currProvider.getProviderCity()));
							educationalProviders.setProviderState(CommonUtil.toDisplayString(currProvider.getProviderState()));
							educationalProviders.setProviderZip(CommonUtil.toDisplayString(currProvider.getProviderZip()));

							educationalProviders.setFeaturedProvider(CommonUtil.toDisplayString("No").equalsIgnoreCase("No") ? false : true);
							if (currProvider.isEnhancedEdAssistNetwork() && searchCriteria.isClientEEN()) {
								jsonItem.put("featuredProvider", CommonUtil.toDisplayString("Yes"));
							}
							educationalProviders.setFeaturedProvider(CommonUtil.toDisplayString("Yes").equalsIgnoreCase("Yes") ? true : false);
							if (currProvider.isFeaturedProvider() && searchCriteria.isClientFPN()) {
								educationalProviders.setFeaturedProvider(CommonUtil.toDisplayString("Yes").equalsIgnoreCase("Yes") ? true : false);
							}
							if (eenIncludesFpn.equalsIgnoreCase("true")) {
								if (currProvider.isFeaturedProvider() && searchCriteria.isClientEEN()) {
									educationalProviders.setFeaturedProvider(CommonUtil.toDisplayString("Yes").equalsIgnoreCase("Yes") ? true : false);
								}
							}

							educationalProviders.setEnhancedEdAssistNetwork(currProvider.isEnhancedEdAssistNetwork());
							educationalProviders.setProviderAccreditationCollection(currProvider.getProviderAccreditationCollection());

							educationalProviderList.add(educationalProviders);

						}
					}
				}
			}
		}

		return educationalProviderList;
	}

	private Set<EducationalProviders> fetchInEligibleProviders(long clientId, long participantId) {
		Set<EducationalProviders> inEligibleProvidersList = new HashSet<EducationalProviders>();

		try {

			List<ProgramDTO> eligiblePrograms = rulesService.retrieveEligiblePrograms(clientId, participantId);

			if (CollectionUtils.isNotEmpty(eligiblePrograms)) {

				for (ProgramDTO currProg : eligiblePrograms) {
					if (currProg != null) {

						String[] paramNames = { "programID.programID", "providerEligibility" };
						Object[] paramValues = { currProg.getProgramID(), 0 };

						List<ProgramProviders> currProgProviders = programProvidersService.findByParamValues(paramNames, paramValues);
						if (CollectionUtils.isNotEmpty(currProgProviders)) {
							for (ProgramProviders currProgProv : currProgProviders) {
								if (currProgProv != null) {

									inEligibleProvidersList.add(currProgProv.getEducationalProviderID());

								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return inEligibleProvidersList;
	}
}
