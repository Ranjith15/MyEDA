package com.edassist.service.loanaggregator;

import com.edassist.models.domain.StudentLoanPaymentHistory;

import java.util.List;

public interface StudentLoanPaymentHistoryService {

	void saveLoanPaymentHistory(List<StudentLoanPaymentHistory> studentLoanPaymentHistories) throws Exception;
}
