package com.edassist.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.Program;
import com.edassist.models.domain.ProgramCourseOfStudy;
import com.edassist.models.domain.ProgramDegreeType;
import com.edassist.models.domain.ProgramEligibleDocumentType;
import com.edassist.models.dto.CourseOfStudyDTO;
import com.edassist.models.dto.DegreeObjectivesDTO;
import com.edassist.models.dto.ProgramDTO;
import com.edassist.models.dto.ProgramEligibleDocumentTypeDTO;
import com.edassist.models.mappers.CourseOfStudyMapper;
import com.edassist.models.mappers.DegreeObjectivesMapper;
import com.edassist.models.mappers.ProgramEligibleDocumentTypeMapper;
import com.edassist.models.mappers.ProgramMapper;
import com.edassist.service.GenericService;
import com.edassist.service.ProgramService;

@RestController
public class ProgramController {

	private final ProgramService programService;
	private final GenericService<ProgramEligibleDocumentType> programEligibleDocumentTypeService;
	private final GenericService<ProgramCourseOfStudy> programCourseOfStudyService;
	private final GenericService<ProgramDegreeType> programDegreeTypeService;
	private final CourseOfStudyMapper courseOfStudyMapper;
	private final DegreeObjectivesMapper degreeObjectivesMapper;
	private final ProgramEligibleDocumentTypeMapper programEligibleDocumentTypeMapper;
	private final ProgramMapper programMapper;

	@Autowired
	public ProgramController(ProgramService programService, GenericService<ProgramEligibleDocumentType> programEligibleDocumentTypeService,
			GenericService<ProgramCourseOfStudy> programCourseOfStudyService, GenericService<ProgramDegreeType> programDegreeTypeService, CourseOfStudyMapper courseOfStudyMapper,
			DegreeObjectivesMapper degreeObjectivesMapper, ProgramEligibleDocumentTypeMapper programEligibleDocumentTypeMapper, ProgramMapper programMapper) {
		this.programService = programService;
		this.programEligibleDocumentTypeService = programEligibleDocumentTypeService;
		this.programCourseOfStudyService = programCourseOfStudyService;
		this.programDegreeTypeService = programDegreeTypeService;
		this.courseOfStudyMapper = courseOfStudyMapper;
		this.degreeObjectivesMapper = degreeObjectivesMapper;
		this.programEligibleDocumentTypeMapper = programEligibleDocumentTypeMapper;
		this.programMapper = programMapper;
	}

	@RequestMapping(value = "/v1/programs/{programId}", method = RequestMethod.GET)
	public ResponseEntity<ProgramDTO> getProgram(@PathVariable("programId") Long programId) {

		Program program = programService.findById(programId);
		if (program == null) {
			throw new BadRequestException("Program not found");
		}
		ProgramDTO programDTO = programMapper.toDTO(program);
		return new ResponseEntity<>(programDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/programs/{programID}/courses-of-study", method = RequestMethod.GET)
	public ResponseEntity<List<CourseOfStudyDTO>> getProgramFieldsOfStudy(@PathVariable("programID") Long programID) {
		ArrayList<CourseOfStudyDTO> courseOfStudyList = new ArrayList<>();

		Program program = programService.findById(programID);
		List<ProgramCourseOfStudy> listProgramCourseOfStudy = programCourseOfStudyService.findByParam("programID", program, "courseOfStudy", "courseOfStudyID");

		for (ProgramCourseOfStudy programCourseOfStudy : listProgramCourseOfStudy) {
			CourseOfStudyDTO courseOfStudyDTO = courseOfStudyMapper.toDTO(programCourseOfStudy.getCourseOfStudyID());
			courseOfStudyList.add(courseOfStudyDTO);
		}

		return new ResponseEntity<>(courseOfStudyList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/programs/{programID}/degree-objectives", method = RequestMethod.GET)
	public ResponseEntity<List<DegreeObjectivesDTO>> getProgramDegreeObjectives(@PathVariable("programID") Long programID) {
		ArrayList<DegreeObjectivesDTO> degreeObjectivesList = new ArrayList<>();

		Program program = programService.findById(programID);
		List<ProgramDegreeType> programDegreeTypeList = programDegreeTypeService.findByParam("programID", program, "degree", "degreeObjectiveID");
		for (ProgramDegreeType programDegreeType : programDegreeTypeList) {
			DegreeObjectivesDTO degreeObjectivesDTO = degreeObjectivesMapper.toDTO(programDegreeType.getDegreeObjectiveID());
			degreeObjectivesList.add(degreeObjectivesDTO);
		}

		return new ResponseEntity<>(degreeObjectivesList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/programs/{programID}/documents", method = RequestMethod.GET)
	public ResponseEntity<List<ProgramEligibleDocumentTypeDTO>> getProgramDocuments(@PathVariable("programID") Long programID) {

		Program program = programService.findById(programID);
		List<ProgramEligibleDocumentType> programEligibleDocumentTypeList = programEligibleDocumentTypeService.findByParam("programID", program);
		List<ProgramEligibleDocumentTypeDTO> programEligibleDocumentTypeDTOs = programEligibleDocumentTypeMapper.toDTOList(programEligibleDocumentTypeList);

		return new ResponseEntity<>(programEligibleDocumentTypeDTOs, HttpStatus.OK);
	}

}
