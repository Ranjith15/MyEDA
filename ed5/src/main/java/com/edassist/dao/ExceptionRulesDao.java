package com.edassist.dao;

import java.util.List;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.ExceptionRules;

public interface ExceptionRulesDao extends GenericDao<ExceptionRules> {

	List<ExceptionRules> search(String clientName, String ruleName, String friendlyName) throws ExceededMaxResultsException;

}
