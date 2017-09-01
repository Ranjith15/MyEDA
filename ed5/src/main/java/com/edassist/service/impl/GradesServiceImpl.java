package com.edassist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edassist.dao.GenericDao;
import com.edassist.dao.GradesDao;
import com.edassist.models.domain.Grades;
import com.edassist.service.GradesService;

@Service
public class GradesServiceImpl extends GenericServiceImpl<Grades> implements GradesService {

	private GradesDao gradesDao;

	public GradesServiceImpl() {
	}

	@Autowired
	public GradesServiceImpl(@Qualifier("gradesDaoImpl") GenericDao<Grades> genericDao) {
		super(genericDao);
		this.gradesDao = (GradesDao) genericDao;
	}

	@Override
	public List<Grades> findActive() {
		return gradesDao.findActive();
	}
}
