package com.edassist.controller;

import com.edassist.exception.ForbiddenException;
import com.edassist.models.contracts.EducationalProvidersSearch;
import com.edassist.models.domain.*;
import com.edassist.models.dto.AccreditingBodyDTO;
import com.edassist.models.dto.EducationalProviderDTO;
import com.edassist.models.dto.EducationalProviderDetailDTO;
import com.edassist.models.mappers.AccreditingBodyMapper;
import com.edassist.models.mappers.EducationalProviderMapper;
import com.edassist.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class EducationalProviderController {

	private final AccessService accessService;
	private final ApplicationService applicationService;
	private final EducationalProvidersService educationalProvidersService;
	private final GenericService<CourseOfStudy> courseOfStudyService;
	private final BenefitPeriodService benefitPeriodService;
	private final ParticipantService participantService;
	private final GenericService<AccreditingBody> accreditationService;
	private final GenericService<DegreeObjectives> degreeObjectivesService;
	private final EducationalProviderMapper educationalProviderMapper;
	private final AccreditingBodyMapper accreditingBodyMapper;

	@Autowired
	public EducationalProviderController(AccessService accessService, ApplicationService applicationService, EducationalProvidersService educationalProvidersService,
			GenericService<CourseOfStudy> courseOfStudyService, BenefitPeriodService benefitPeriodService, ParticipantService participantService, GenericService<AccreditingBody> accreditationService,
			GenericService<DegreeObjectives> degreeObjectivesService, EducationalProviderMapper educationalProviderMapper, AccreditingBodyMapper accreditingBodyMapper) {
		this.accessService = accessService;
		this.applicationService = applicationService;
		this.educationalProvidersService = educationalProvidersService;
		this.courseOfStudyService = courseOfStudyService;
		this.benefitPeriodService = benefitPeriodService;
		this.participantService = participantService;
		this.accreditationService = accreditationService;
		this.degreeObjectivesService = degreeObjectivesService;
		this.educationalProviderMapper = educationalProviderMapper;
		this.accreditingBodyMapper = accreditingBodyMapper;
	}

	@RequestMapping(value = "/v1/educationProviders/{participantId}/{clientId}", method = RequestMethod.GET)
	public ResponseEntity<List<EducationalProviderDTO>> getEducationProviders(@PathVariable("participantId") Long participantId, @PathVariable("clientId") Long clientId,
			@RequestParam String providerName, @RequestParam(required = false) String providerCity, @RequestParam(required = false) String providerState,
			@RequestParam(required = false) String accreditationBodyId, @RequestParam(required = false) String accreditationType,
			@RequestParam(required = false, defaultValue = "false") Boolean featuredProvider, @RequestParam(required = false) Boolean onlineProvider) {

		accessService.compareParticipantToSession(participantId);

		List<EducationalProviderDTO> educationalProviderDTOList = new ArrayList<>();
		EducationalProvidersSearch searchCriteria = educationalProvidersService
				.setEducationProviderSearchCriteria(participantId, clientId, providerName, providerCity, providerState, featuredProvider, onlineProvider, accreditationBodyId, null, null, null, true,
						false);
		List<EducationalProviders> educationalProviderList = educationalProvidersService.search(searchCriteria);

		educationalProviderList = educationalProvidersService.getEducationalProvidersList(educationalProviderList, searchCriteria, clientId, participantId);

		if (educationalProviderList != null && educationalProviderList.size() > 0) {

			educationalProviderDTOList = educationalProviderMapper.toDTOList(educationalProviderList);

			Collections.sort(educationalProviderDTOList);
		}

		return new ResponseEntity<>(educationalProviderDTOList, HttpStatus.OK);
	}

	// TODO Returns the entire list of accreditations right now. Will make it
	// client specific if needed.
	@RequestMapping(value = "/v1/educationProviders/accreditations", method = RequestMethod.GET)
	public ResponseEntity<List<AccreditingBodyDTO>> getAccreditations() {

		List<AccreditingBody> accreditationList = accreditationService.findByParam("active", Boolean.TRUE, "accreditingBodyDisplayName");
		List<AccreditingBodyDTO> accreditingBodyDTOList = accreditingBodyMapper.toDTOList(accreditationList);

		return new ResponseEntity<>(accreditingBodyDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/educationProviders/{applicationId}", method = RequestMethod.POST)
	public ResponseEntity<EducationalProviderDetailDTO> updateEducationProvider(@PathVariable("applicationId") Long applicationId,
			@RequestBody EducationalProviderDetailDTO educationalProviderDetailDTO) {

		Application application = applicationService.findById(applicationId);
		accessService.verifyUserToSession(application);

		if (!applicationService.isApplicationEditable(application)) {
			throw new ForbiddenException();
		}

		if (educationalProviderDetailDTO.getEducationalProvider() != null) {
			EducationalProviders newProvider = educationalProvidersService.findById(educationalProviderDetailDTO.getEducationalProvider().getEducationalProviderId());
			application.setEducationalProvider(newProvider);
		}

		if (educationalProviderDetailDTO.getCourseOfStudyID() != null) {
			CourseOfStudy newCourseOfStudy = courseOfStudyService.findById(educationalProviderDetailDTO.getCourseOfStudyID().getCourseOfStudyID());
			application.setCourseOfStudyID(newCourseOfStudy);
		}

		if (educationalProviderDetailDTO.getDegreeObjectiveID() != null) {
			DegreeObjectives newDegreeObjective = degreeObjectivesService.findById(educationalProviderDetailDTO.getDegreeObjectiveID().getDegreeObjectiveID());
			application.setDegreeObjectiveID(newDegreeObjective);
		}

		if (educationalProviderDetailDTO.getBenefitPeriodID() != null) {
			BenefitPeriod newBenefitPeriod = benefitPeriodService.findById(educationalProviderDetailDTO.getBenefitPeriodID().getbenefitPeriodID());
			application.setBenefitPeriodID(newBenefitPeriod);
		}

		if (educationalProviderDetailDTO.getStudentID() != null) {
			application.setStudentID(educationalProviderDetailDTO.getStudentID());
		}

		applicationService.saveOrUpdate(application);

		return new ResponseEntity<>(educationalProviderDetailDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/educationProviders/{participantId}", method = RequestMethod.GET)
	public ResponseEntity<EducationalProviderDTO> getCurrentEducationProvider(@PathVariable("participantId") Long participantId) {
		Participant participant = participantService.findById(participantId);
		accessService.verifyParticipantOrHigherAccess(participant);
		EducationalProviders currentprovider = participant.getCurrentEducationProfile().getProviderID();
		return new ResponseEntity<>(educationalProviderMapper.toDTO(currentprovider), HttpStatus.OK);
	}

}
