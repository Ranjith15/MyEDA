package com.edassist.controller;

import com.edassist.models.domain.Grades;
import com.edassist.models.dto.GradesDTO;
import com.edassist.models.mappers.GradesMapper;
import com.edassist.service.GradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GradesController {

	private final GradesService gradesService;
	private final GradesMapper gradesMapper;

	@Autowired
	public GradesController(GradesService gradesService, GradesMapper gradesMapper) {
		this.gradesService = gradesService;
		this.gradesMapper = gradesMapper;
	}

	@RequestMapping(value = "/v1/grades", method = RequestMethod.GET)
	public ResponseEntity<List<GradesDTO>> getGrades() {
		List<GradesDTO> gradesDTOs;
		List<Grades> grades = gradesService.findActive();
		gradesDTOs = gradesMapper.toDTOList(grades);

		return new ResponseEntity<>(gradesDTOs, HttpStatus.OK);
	}
}
