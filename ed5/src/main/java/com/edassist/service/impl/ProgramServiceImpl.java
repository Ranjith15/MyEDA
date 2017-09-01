package com.edassist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edassist.dao.GenericDao;
import com.edassist.dao.ProgramDao;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.Program;
import com.edassist.service.ProgramService;

@Service
public class ProgramServiceImpl extends GenericServiceImpl<Program> implements ProgramService {

	private ProgramDao programDao;

	public ProgramServiceImpl() {
	}

	@Autowired
	public ProgramServiceImpl(@Qualifier("programDaoImpl") GenericDao<Program> genericDao) {
		super(genericDao);
		this.programDao = (ProgramDao) genericDao;
	}

	@Override
	public List<Program> search(String clientId, String programTypeId, String programId) throws ExceededMaxResultsException {
		return programDao.search(clientId, programTypeId, programId);
	}

	@Override
	public List<Program> search(String clientId, String programTypeId) throws ExceededMaxResultsException {
		return programDao.search(clientId, programTypeId);
	}

}
