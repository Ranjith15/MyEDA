package com.edassist.service;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.ApplicationCourses;
import com.edassist.models.domain.ProgramExpenseType;

import java.math.BigDecimal;
import java.util.List;

public interface ProgramExpenseTypeService extends GenericService<ProgramExpenseType> {

	List<ProgramExpenseType> search(Long programId, Long programExpenseTypeId) throws ExceededMaxResultsException;

	ApplicationCourses createExpense(Long applicationId, Long expenseTypeId, Long relatedCourseId, BigDecimal feesAmount, Integer numberOfBooks, String maintainSkillsYN, String meetMinimumQualsYN,
			String newCareerFieldYN) throws Exception;
}
