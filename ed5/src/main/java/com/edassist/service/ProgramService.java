package com.edassist.service;

import java.util.List;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.Program;

public interface ProgramService extends GenericService<Program> {

	List<Program> search(String clientId, String programTypeId, String programId) throws ExceededMaxResultsException;

	List<Program> search(String clientId, String programTypeId) throws ExceededMaxResultsException;

}
