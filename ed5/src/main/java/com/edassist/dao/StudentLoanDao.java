package com.edassist.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.edassist.models.domain.StudentLoan;

public interface StudentLoanDao extends GenericDao<StudentLoan> {

	List<StudentLoan> fetchLoanAggregatorUserAccountNumbers(Long participantId) throws Exception;

	List<StudentLoan> fetchUserAcctNumbersWithActiveApps() throws Exception;

}
