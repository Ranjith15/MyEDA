package com.edassist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edassist.dao.GenericDao;
import com.edassist.dao.ProgramProvidersDao;
import com.edassist.models.domain.ProgramProviders;
import com.edassist.service.ProgramProvidersService;

@Service
public class ProgramProvidersServiceImpl extends GenericServiceImpl<ProgramProviders> implements ProgramProvidersService {

	private ProgramProvidersDao programProvidersDao;

	public ProgramProvidersServiceImpl() {
	}

	@Autowired
	public ProgramProvidersServiceImpl(@Qualifier("programProvidersDaoImpl") GenericDao<ProgramProviders> genericDao) {
		super(genericDao);
		this.programProvidersDao = (ProgramProvidersDao) genericDao;
	}

	@Override
	public List<ProgramProviders> findByParamValues(String[] paramNames, Object[] paramValues) {
		return programProvidersDao.findByParamValues(paramNames, paramValues);
	}
}
