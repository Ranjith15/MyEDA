package com.edassist.dao;

import java.util.List;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.ProgramExpenseType;

public interface ProgramExpenseTypeDao extends GenericDao<ProgramExpenseType> {

	List<ProgramExpenseType> search(Long programId, Long programExpenseTypeId) throws ExceededMaxResultsException;
}
