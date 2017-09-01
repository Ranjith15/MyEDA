package com.edassist.dao;

import com.edassist.models.domain.StudentLoanPaymentHistory;

import java.util.List;

public interface StudentLoanPaymentHistoryDao extends GenericDao<StudentLoanPaymentHistory> {

	void saveLoanPaymentHistory(List<StudentLoanPaymentHistory> studentLoanPaymentHistories) throws Exception;
}
