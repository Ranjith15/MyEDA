package com.edassist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edassist.exception.BadRequestException;
import com.edassist.exception.ForbiddenException;
import com.edassist.models.domain.Application;
import com.edassist.models.domain.FinancialAidSource;
import com.edassist.models.dto.FinancialAidSourceDTO;
import com.edassist.models.dto.GrantScholarshipDTO;
import com.edassist.models.mappers.FinancialAidSourceMapper;
import com.edassist.models.mappers.GrantScholarshipMapper;
import com.edassist.service.AccessService;
import com.edassist.service.ApplicationService;
import com.edassist.service.GenericService;

@RestController
public class GrantScholarshipController {

	private final AccessService accessService;
	private final ApplicationService applicationService;
	private final GenericService<FinancialAidSource> financialAidSourceService;
	private final FinancialAidSourceMapper financialAidSourceMapper;
	private final GrantScholarshipMapper grantScholarshipMapper;

	@Autowired
	public GrantScholarshipController(AccessService accessService, ApplicationService applicationService, GenericService<FinancialAidSource> financialAidSourceService,
			FinancialAidSourceMapper financialAidSourceMapper, GrantScholarshipMapper grantScholarshipMapper) {
		this.accessService = accessService;
		this.applicationService = applicationService;
		this.financialAidSourceService = financialAidSourceService;
		this.financialAidSourceMapper = financialAidSourceMapper;
		this.grantScholarshipMapper = grantScholarshipMapper;
	}

	@RequestMapping(value = "/v1/applications/grants-scholarship/types", method = RequestMethod.GET)
	public ResponseEntity<List<FinancialAidSourceDTO>> getGrants() {

		List<FinancialAidSource> financialAidSourceList = financialAidSourceService.findAll();
		List<FinancialAidSourceDTO> financialAidSourceDTOs = financialAidSourceMapper.toDTOList(financialAidSourceList);

		return new ResponseEntity<>(financialAidSourceDTOs, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationID}/grants-scholarship", method = RequestMethod.GET)
	public ResponseEntity<GrantScholarshipDTO> getGrants(@PathVariable("applicationID") Long applicationID) {

		Application application = applicationService.findById(applicationID);

		if (application == null) {
			throw new BadRequestException("Application not found");
		}

		accessService.compareParticipantOrClientAdminToSession(application.getParticipantID().getParticipantId());
		FinancialAidSourceDTO financialAidSourceDTO = financialAidSourceMapper.toDTO(application.getFinancialAidSourceId());
		GrantScholarshipDTO grantScholorshipDTO = grantScholarshipMapper.toDTO(application, financialAidSourceDTO);

		return new ResponseEntity<>(grantScholorshipDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationID}/grants-scholarship", method = RequestMethod.POST)
	public ResponseEntity<GrantScholarshipDTO> createGrant(@PathVariable("applicationID") Long applicationID, @RequestBody GrantScholarshipDTO grantScholarshipDTO) {

		if (grantScholarshipDTO == null || grantScholarshipDTO.getFinancialAidSourceId() == null || grantScholarshipDTO.getFinancialAidAmount() == null) {
			throw new BadRequestException();
		}

		Application application = applicationService.findById(applicationID);

		if (application == null) {
			throw new BadRequestException("Application not found");
		}

		accessService.compareParticipantToSession(application.getParticipantID().getParticipantId());

		if (!applicationService.isApplicationEditable(application)) {
			throw new ForbiddenException();
		}

		FinancialAidSource financialAidSource = financialAidSourceService.findById(grantScholarshipDTO.getFinancialAidSourceId().getFinancialAidSourceId());
		Application applicationAfterUpdate = grantScholarshipMapper.toDomain(grantScholarshipDTO, financialAidSource, application);

		applicationService.saveOrUpdate(applicationAfterUpdate);

		return new ResponseEntity<>(grantScholarshipDTO, HttpStatus.OK);
	}
}