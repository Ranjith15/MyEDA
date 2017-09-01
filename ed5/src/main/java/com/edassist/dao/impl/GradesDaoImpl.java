package com.edassist.dao.impl;

import com.edassist.dao.GradesDao;
import com.edassist.models.domain.Grades;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class GradesDaoImpl extends GenericDaoImpl<Grades> implements GradesDao {

	@Override
	@Transactional
	public List<Grades> findActive() {
		Session session = this.getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Grades> criteria = builder.createQuery(Grades.class);
		Root<Grades> root = criteria.from(Grades.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get("active"), true));
		List<Grades> grades = session.createQuery(criteria).getResultList();

		return grades;
	}

}
