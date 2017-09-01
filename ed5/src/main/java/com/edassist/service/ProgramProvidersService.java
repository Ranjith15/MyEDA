package com.edassist.service;

import java.util.List;

import com.edassist.models.domain.ProgramProviders;

public interface ProgramProvidersService extends GenericService<ProgramProviders> {

	List<ProgramProviders> findByParamValues(String[] paramNames, Object[] paramValues);

}
