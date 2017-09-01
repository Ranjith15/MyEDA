package com.edassist.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edassist.constants.RestConstants;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ForbiddenException;
import com.edassist.exception.UnprocessableEntityException;
import com.edassist.models.domain.Application;
import com.edassist.models.domain.ApplicationCourses;
import com.edassist.models.domain.CourseMethod;
import com.edassist.models.dto.ApplicationCoursesDTO;
import com.edassist.models.dto.CourseMethodDTO;
import com.edassist.models.mappers.ApplicationCoursesMapper;
import com.edassist.models.mappers.CourseMethodMapper;
import com.edassist.service.AccessService;
import com.edassist.service.ApplicationCoursesService;
import com.edassist.service.ApplicationService;
import com.edassist.service.GenericService;

@RestController
public class ApplicationCourseController {

	private final AccessService accessService;
	private final ApplicationService applicationService;
	private final ApplicationCoursesService applicationCoursesService;
	private final GenericService<CourseMethod> courseMethodService;
	private final ApplicationCoursesMapper applicationCoursesMapper;
	private final CourseMethodMapper courseMethodMapper;

	@Autowired
	public ApplicationCourseController(AccessService accessService, ApplicationService applicationService, ApplicationCoursesService applicationCoursesService,
			GenericService<CourseMethod> courseMethodService, ApplicationCoursesMapper applicationCoursesMapper, CourseMethodMapper courseMethodMapper) {
		this.accessService = accessService;
		this.applicationService = applicationService;
		this.applicationCoursesService = applicationCoursesService;
		this.courseMethodService = courseMethodService;
		this.applicationCoursesMapper = applicationCoursesMapper;
		this.courseMethodMapper = courseMethodMapper;
	}

	@RequestMapping(value = "/v1/applications/courses/methods", method = RequestMethod.GET)
	public ResponseEntity<List<CourseMethodDTO>> getCourseMethods() {

		List<CourseMethod> courseMethods = courseMethodService.findAll();
		List<CourseMethodDTO> courseMethodDTOs = courseMethodMapper.toDTOList(courseMethods);
		Collections.sort(courseMethodDTOs);

		return new ResponseEntity<>(courseMethodDTOs, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/courses", method = RequestMethod.GET)
	public ResponseEntity<List<ApplicationCoursesDTO>> getApplicationCourses(@PathVariable("applicationId") Long applicationId) {

		Application application = applicationService.findById(applicationId);
		accessService.compareParticipantOrClientAdminToSession(application.getParticipantID().getParticipantId());
		List<ApplicationCourses> ApplicationCoursesList = applicationCoursesService.getApplicationCoursesWithGradeCompliance(application);
		List<ApplicationCoursesDTO> applicationCoursesDTOList = applicationCoursesMapper.toDTOList(ApplicationCoursesList);

		return new ResponseEntity<>(applicationCoursesDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/courses", method = RequestMethod.POST)
	public ResponseEntity<List<ApplicationCoursesDTO>> setApplicationCourse(@PathVariable("applicationId") Long applicationId, @RequestBody List<ApplicationCoursesDTO> applicationCoursesDTOList) {

		// Participant does not enter a grade while creating a course.
		Long gradeId = null;

		Application application = applicationService.findById(applicationId);

		if (application == null) {
			throw new BadRequestException("Application not found");
		}

		accessService.compareParticipantToSession(application.getParticipantID().getParticipantId());

		if (!applicationService.isApplicationEditable(application)) {
			throw new ForbiddenException();
		}

		Integer maxCourses = application.getBenefitPeriodID().getProgramID().getMaxNumOfCourses();
		if (maxCourses != null && applicationCoursesService.getApplicationCoursesWithGradeCompliance(application).size() >= maxCourses) {
			throw new BadRequestException(RestConstants.MAX_COURSES_EXCEEDED);
		}
		// validate the course list
		for (ApplicationCoursesDTO applicationCoursesDTO : applicationCoursesDTOList) {
			if (applicationId == null || applicationCoursesDTO.getCourseNumber() == null || applicationCoursesDTO.getCourseMethod() == null || applicationCoursesDTO.getCourseName() == null
					|| applicationCoursesDTO.getTuitionAmount() == null) {
				throw new UnprocessableEntityException(RestConstants.REST_MISSING_REQUIRED_FIELD);
			}
		}

		List<ApplicationCourses> createdCourses = new ArrayList<>();

		for (ApplicationCoursesDTO applicationCoursesDTO : applicationCoursesDTOList) {
			createdCourses.add(applicationCoursesService.createCourse(applicationId, applicationCoursesDTO.getCourseNumber(), applicationCoursesDTO.getCourseName(),
					applicationCoursesDTO.getTuitionAmount(), applicationCoursesDTO.getRefundAmount(), applicationCoursesDTO.getCreditHours(),
					applicationCoursesDTO.getCourseMethod().getCourseMethodId(), applicationCoursesDTO.getMaintainSkillsYN(), applicationCoursesDTO.getMeetMinimumQualsYN(),
					applicationCoursesDTO.getNewCareerFieldYN(), gradeId, applicationCoursesDTO.getCourseDescriptionURL(), applicationCoursesDTO.getCourseSchedule()));
		}

		List<ApplicationCoursesDTO> createdCoursesDTO = applicationCoursesMapper.toDTOList(createdCourses);

		return new ResponseEntity<>(createdCoursesDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/courses/{applicationCoursesId}", method = RequestMethod.PUT)
	public ResponseEntity<ApplicationCoursesDTO> updateApplicationCourses(@PathVariable("applicationId") Long applicationId, @PathVariable("applicationCoursesId") Long applicationCourseId,
			@RequestBody ApplicationCoursesDTO applicationCoursesDTO) {

		Application application = applicationService.findById(applicationId);
		ApplicationCourses updatedCourse;

		if (application == null) {
			throw new BadRequestException("Application not found");
		}

		if (!accessService.verifyCourseAndExpenseInApplication(application, applicationCourseId)) {
			throw new BadRequestException();
		}

		accessService.compareParticipantToSession(application.getParticipantID().getParticipantId());

		if (!applicationService.isApplicationEditable(application)) {
			throw new ForbiddenException();
		}

		if (applicationCoursesDTO == null || !applicationCoursesDTO.getApplicationCoursesID().equals(applicationCourseId)) {
			throw new BadRequestException();
		} else {
			ApplicationCourses applicationCourse = applicationCoursesService.findById(applicationCoursesDTO.getApplicationCoursesID());
			updatedCourse = applicationCoursesMapper.toDomain(applicationCoursesDTO, applicationCourse);
		}

		if (updatedCourse != null) {
			applicationCoursesService.persistApplicationCourseOrExpense(updatedCourse);
		}

		ApplicationCoursesDTO updatedCourseDTO = applicationCoursesMapper.toDTO(updatedCourse);

		return new ResponseEntity<>(updatedCourseDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/courses/{applicationCoursesId}", method = RequestMethod.DELETE)
	public ResponseEntity<ApplicationCoursesDTO> deleteApplicationCourses(@PathVariable("applicationId") Long applicationId, @PathVariable("applicationCoursesId") Long applicationCourseId) {

		ApplicationCourses applicationCourse = applicationCoursesService.deleteCourse(applicationCourseId);
		ApplicationCoursesDTO applicationCourseDTO = applicationCoursesMapper.toDTO(applicationCourse);

		return new ResponseEntity<>(applicationCourseDTO, HttpStatus.OK);
	}
}
