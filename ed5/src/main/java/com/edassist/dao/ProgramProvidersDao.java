package com.edassist.dao;

import java.util.List;

import com.edassist.models.domain.ProgramProviders;

public interface ProgramProvidersDao extends GenericDao<ProgramProviders> {

	List<ProgramProviders> findByParamValues(String[] paramNames, Object[] paramValues);

}
