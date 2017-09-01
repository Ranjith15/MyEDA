package com.edassist.dao;

import java.util.List;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.ProgramApplicationSource;

public interface ProgramApplicationSourceDao extends GenericDao<ProgramApplicationSource> {

	List<ProgramApplicationSource> search(Long programId, Long applicationId) throws ExceededMaxResultsException;

}
