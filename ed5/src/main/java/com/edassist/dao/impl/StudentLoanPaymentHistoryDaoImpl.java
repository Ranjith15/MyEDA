package com.edassist.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edassist.dao.StudentLoanPaymentHistoryDao;
import com.edassist.models.domain.StudentLoan;
import com.edassist.models.domain.StudentLoanPaymentHistory;

@Repository
@Transactional
public class StudentLoanPaymentHistoryDaoImpl extends GenericDaoImpl<StudentLoanPaymentHistory> implements StudentLoanPaymentHistoryDao {

	@Override
	public void saveLoanPaymentHistory(List<StudentLoanPaymentHistory> studentLoanPaymentHistories) throws DataAccessException, Exception {
		List<StudentLoanPaymentHistory> tempLoanPaymentList = null;
		for (StudentLoanPaymentHistory studentLoanTransaction : studentLoanPaymentHistories) {
			tempLoanPaymentList = findByParam("loanAggregatorTransactionId", studentLoanTransaction.getLoanAggregatorTransactionId());
			if (tempLoanPaymentList == null || tempLoanPaymentList.isEmpty()) {
				saveOrUpdate(studentLoanTransaction);
			}
		}
	}

}
