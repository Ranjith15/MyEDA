package com.edassist.service;

import java.util.List;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.contracts.EducationalProvidersSearch;
import com.edassist.models.domain.EducationalProviders;

public interface EducationalProvidersService extends GenericService<EducationalProviders> {

	List<EducationalProviders> search(Object object) throws ExceededMaxResultsException;

	List<EducationalProviders> search(EducationalProvidersSearch searchObject) throws ExceededMaxResultsException;

	List<EducationalProviders> search(String providerName, String providerId, String providerState, boolean isFeatureProvider) throws ExceededMaxResultsException;

	EducationalProvidersSearch setEducationProviderSearchCriteria(Long participantId, Long clientId, String providerName, String providerCity, String stateId, Boolean featuredProvider,
			Boolean onlineProvider, String accreditingBodyId, String accreditingTypeName, String degreeObjectiveId, String courseOfStudyId, boolean een, boolean fpn);

	List<EducationalProviders> getEducationalProvidersList(List<EducationalProviders> providersList, EducationalProvidersSearch searchCriteria, Long clientId, Long participantId);
}
