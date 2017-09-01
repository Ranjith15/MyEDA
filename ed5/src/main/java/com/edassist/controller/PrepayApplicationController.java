package com.edassist.controller;

import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.*;
import com.edassist.models.dto.PrepayApplicationDTO;
import com.edassist.models.dto.ThinBookApplicationDTO;
import com.edassist.models.mappers.PrepayApplicationMapper;
import com.edassist.models.mappers.ThinBookApplicationMapper;
import com.edassist.service.AccessService;
import com.edassist.service.ApplicationService;
import com.edassist.service.EducationalProvidersService;
import com.edassist.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PrepayApplicationController {

	private final AccessService accessService;
	private final ApplicationService applicationService;
	private final ProgramService programService;
	private final EducationalProvidersService educationalProvidersService;
	private final PrepayApplicationMapper prepayApplicationMapper;
	private final ThinBookApplicationMapper thinBookApplicationMapper;

	@Autowired
	public PrepayApplicationController(AccessService accessService, ApplicationService applicationService, ProgramService programService, EducationalProvidersService educationalProvidersService,
			PrepayApplicationMapper prepayApplicationMapper, ThinBookApplicationMapper thinBookApplicationMapper) {
		this.accessService = accessService;
		this.applicationService = applicationService;
		this.programService = programService;
		this.educationalProvidersService = educationalProvidersService;
		this.prepayApplicationMapper = prepayApplicationMapper;
		this.thinBookApplicationMapper = thinBookApplicationMapper;
	}

	@RequestMapping(value = "/v1/applications/prepay-applications/{applicationId}", method = RequestMethod.GET)
	public ResponseEntity<PrepayApplicationDTO> getPrepayApplicationDetails(@PathVariable("applicationId") Long applicationId) {
		Application application = applicationService.findById(applicationId);
		accessService.verifyParticipantOrHigherAccess(application);
		PrepayApplicationDTO prepayApplicationDTO = prepayApplicationMapper.toDTO(application);
		ThinBookApplicationDTO bookApplication = thinBookApplicationMapper.toDTO(applicationService.getRelatedBookApplication(applicationId));
		prepayApplicationDTO.setBookApplication(bookApplication);
		prepayApplicationDTO.setUnreadMessages(applicationService.getNumberOfUnreadComments(application));

		return new ResponseEntity<>(prepayApplicationDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/prepay-applications/{applicationId}", method = RequestMethod.PUT)
	public ResponseEntity<PrepayApplicationDTO> updatePrepayApplication(@PathVariable("applicationId") Long applicationId, @RequestBody PrepayApplicationDTO prepayApplicationDTO) {
		Application application = applicationService.findById(applicationId);
		application = prepayApplicationMapper.toDomain(prepayApplicationDTO, application);
		applicationService.saveApplication(application);
		Application updatedTuitionApplication = applicationService.findById(applicationId);
		PrepayApplicationDTO updatedPrepayApplicationDTO = prepayApplicationMapper.toDTO(updatedTuitionApplication);

		return new ResponseEntity<>(updatedPrepayApplicationDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/prepay-applications", method = RequestMethod.POST)
	public ResponseEntity<PrepayApplicationDTO> createPrepayApplication(@RequestBody PrepayApplicationDTO prepayApplicationDTO) throws Exception {
		if (prepayApplicationDTO == null) {
			throw new BadRequestException();
		}

		Application tuitionApplication = new Application();
		Participant participant = accessService.retrieveAndCompareParticipantToSession(prepayApplicationDTO.getParticipant().getParticipantId());
		tuitionApplication = prepayApplicationMapper.toDomain(prepayApplicationDTO, tuitionApplication);
		tuitionApplication.setParticipantID(participant);
		accessService.verifyUserToSession(tuitionApplication);

		if (tuitionApplication.getParticipantID().getCurrentEducationProfile() == null) {
			tuitionApplication.getParticipantID().setCurrentEducationProfile(new ParticipantCurrentEducationProfile());
		}

		EducationalProviders currentProvider = educationalProvidersService.findById(tuitionApplication.getEducationalProvider().getEducationalProviderId());
		tuitionApplication.getParticipantID().getCurrentEducationProfile().setProviderID(currentProvider);
		Program program = programService.findById(prepayApplicationDTO.getBenefitPeriod().getProgramID().getProgramID());
		applicationService.processNewApplication(tuitionApplication, program, currentProvider);
		PrepayApplicationDTO applicationDTO = prepayApplicationMapper.toDTO(tuitionApplication);

		return new ResponseEntity<>(applicationDTO, HttpStatus.OK);
	}
}
