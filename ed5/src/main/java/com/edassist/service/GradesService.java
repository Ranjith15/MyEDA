package com.edassist.service;

import java.util.List;

import com.edassist.models.domain.Grades;

public interface GradesService extends GenericService<Grades> {

	List<Grades> findActive();

}
