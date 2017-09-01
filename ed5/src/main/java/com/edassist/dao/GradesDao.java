package com.edassist.dao;

import com.edassist.models.domain.Grades;

import java.util.List;

public interface GradesDao extends GenericDao<Grades> {

	List<Grades> findActive();
}
