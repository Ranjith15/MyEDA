package com.edassist.dao;

import java.util.List;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.contracts.EducationalProvidersSearch;
import com.edassist.models.domain.EducationalProviders;

public interface EducationalProvidersDao extends GenericDao<EducationalProviders> {

	List<EducationalProviders> search(Object object) throws ExceededMaxResultsException;

	List<EducationalProviders> search(EducationalProvidersSearch provider) throws ExceededMaxResultsException;

	List<EducationalProviders> search(String providerName, String providerId, String providerState, boolean isFeatureProvider) throws ExceededMaxResultsException;

}
