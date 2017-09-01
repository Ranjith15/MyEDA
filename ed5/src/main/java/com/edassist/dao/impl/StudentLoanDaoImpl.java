package com.edassist.dao.impl;

import com.edassist.dao.StudentLoanDao;
import com.edassist.models.domain.*;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
public class StudentLoanDaoImpl extends GenericDaoImpl<StudentLoan> implements StudentLoanDao {

	@Override
	@Transactional
	public List<StudentLoan> fetchLoanAggregatorUserAccountNumbers(Long participantId) throws DataAccessException, Exception {

		Session session = this.getSessionFactory().getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<StudentLoan> criteria = builder.createQuery(StudentLoan.class);

		Root<StudentLoan> studentLoanRoot = criteria.from(StudentLoan.class);

		List<Predicate> equalPredicates = new ArrayList<Predicate>();
		equalPredicates.add(builder.equal(studentLoanRoot.get("participantID"), participantId));
		equalPredicates.add(builder.equal(studentLoanRoot.get("loanSourceID"), 2L));
		equalPredicates.add(builder.isNotNull(studentLoanRoot.get("loanAggregatorUserAccountId")));

		criteria.select(studentLoanRoot);
		criteria.where(equalPredicates.toArray(new Predicate[0])).distinct(true);

		criteria.orderBy(builder.asc(studentLoanRoot.get("participantID")));

		return session.createQuery(criteria).getResultList();
	}

	@Override
	@Transactional
	public List<StudentLoan> fetchUserAcctNumbersWithActiveApps() throws Exception {
		Session session = this.getSessionFactory().getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<StudentLoan> criteria = builder.createQuery(StudentLoan.class);

		Root<StudentLoan> studentLoanRoot = criteria.from(StudentLoan.class);
		Root<LoanAggregatorUserDomain> loanAggregatorUserRoot = criteria.from(LoanAggregatorUserDomain.class);
		Root<StudentLoanPaymentRequest> studentLoanPaymentRequestRoot = criteria.from(StudentLoanPaymentRequest.class);
		Root<ReimburseStudentLoanApp> reimburseStudentLoanAppRoot = criteria.from(ReimburseStudentLoanApp.class);
		Root<Application> applicationRoot = criteria.from(Application.class);
		Root<ApplicationStatus> applicationStatusRoot = criteria.from(ApplicationStatus.class);

		List<Predicate> equalPredicates = new ArrayList<Predicate>();
		equalPredicates.add(builder.equal(studentLoanRoot.get("participantID"), loanAggregatorUserRoot.get("participantId")));
		equalPredicates.add(builder.equal(studentLoanRoot.get("studentLoanID"), studentLoanPaymentRequestRoot.get("studentLoanID")));
		equalPredicates.add(builder.equal(studentLoanPaymentRequestRoot.get("studentLoanPaymentRequestID"), reimburseStudentLoanAppRoot.get("studentLoanPaymentRequest")));
		equalPredicates.add(builder.equal(reimburseStudentLoanAppRoot.get("applicationID"), applicationRoot.get("applicationID")));
		equalPredicates.add(builder.equal(applicationRoot.get("applicationStatusID"), applicationStatusRoot.get("applicationStatusID")));
		equalPredicates.add(builder.in(applicationStatusRoot.get("applicationStatusID")).value(new ArrayList<Integer>(Arrays.asList(90, 110, 120, 425, 450, 500))));
		equalPredicates.add(builder.equal(studentLoanRoot.get("loanSourceID"), 2L));
		equalPredicates.add(builder.isNotNull(studentLoanRoot.get("loanAggregatorUserAccountId")));

		criteria.select(builder.construct(StudentLoan.class, studentLoanRoot.get("participantID")));
		criteria.where(equalPredicates.toArray(new Predicate[0])).distinct(true);
		criteria.orderBy(builder.asc(studentLoanRoot.get("participantID")));

		return session.createQuery(criteria).getResultList();
	}

}
