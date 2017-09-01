package com.edassist.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.edassist.dao.ProgramDao;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.Program;

@Repository
@Transactional
public class ProgramDaoImpl extends GenericDaoImpl<Program> implements ProgramDao {

	@Override
	@Transactional
	public List<Program> search(String clientId, String programTypeId, String programId) throws ExceededMaxResultsException {

		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Program.class);

		if (clientId != null && !clientId.equals("0") && !clientId.equals("%")) {
			criteria.add(Restrictions.eq("clientID.clientId", Long.parseLong(clientId)));
		}
		if (programTypeId != null && !programTypeId.equals("0") && !programTypeId.equals("%")) {
			criteria.add(Restrictions.eq("programTypeID.programTypeID", Long.parseLong(programTypeId)));
		}
		if (programId != null && !programId.equals("0") && !programId.equals("%")) {
			criteria.add(Restrictions.eq("programID", Long.parseLong(programId)));
		}
		List<Program> programList = (List<Program>) criteria.list();

		return programList;
	}

	@Override
	@Transactional
	public List<Program> search(String clientId, String programTypeId) throws ExceededMaxResultsException {
		String query = "from Program p WHERE p.programTypeID.programTypeID =:lngProgramTypeId and p.clientID.clientId =:lngClientId";
		List<Program> programList = (List<Program>) this.getSessionFactory().getCurrentSession().createNamedQuery(query).setParameter("lngProgramTypeId", programTypeId)
				.setParameter("lngClientId", clientId).list();

		if (programList == null) {
			programList = new ArrayList<Program>();
		}

		return programList;
	}
}
