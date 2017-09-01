package com.edassist.service.impl.loanaggregator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.edassist.dao.GenericDao;
import com.edassist.dao.StudentLoanPaymentHistoryDao;
import com.edassist.models.domain.StudentLoan;
import com.edassist.models.domain.StudentLoanPaymentHistory;
import com.edassist.service.impl.GenericServiceImpl;
import com.edassist.service.loanaggregator.StudentLoanPaymentHistoryService;

@Service
public class StudentLoanPaymentHistoryServiceImpl extends GenericServiceImpl<StudentLoanPaymentHistory> implements StudentLoanPaymentHistoryService {

	private StudentLoanPaymentHistoryDao studentLoanPaymentHistoryDao;

	public StudentLoanPaymentHistoryServiceImpl() {
	}

	@Autowired
	public StudentLoanPaymentHistoryServiceImpl(@Qualifier("studentLoanPaymentHistoryDaoImpl") GenericDao<StudentLoanPaymentHistory> genericDao) {
		super(genericDao);
		this.studentLoanPaymentHistoryDao = (StudentLoanPaymentHistoryDao) genericDao;
	}

	@Override
	public void saveLoanPaymentHistory(List<StudentLoanPaymentHistory> studentLoanPaymentHistories) throws DataAccessException, Exception {
		studentLoanPaymentHistoryDao.saveLoanPaymentHistory(studentLoanPaymentHistories);
	}
}
