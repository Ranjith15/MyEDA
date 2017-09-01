package com.edassist.controller;

import java.util.*;

import javax.validation.Valid;

import com.edassist.models.sp.AccountSnapshot;
import com.edassist.models.sp.AccountSnapshotForParticipant;
import com.edassist.models.sp.ThinBenefitPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edassist.exception.BadRequestException;
import com.edassist.exception.ForbiddenException;
import com.edassist.models.contracts.Option;
import com.edassist.models.domain.*;
import com.edassist.models.dto.*;
import com.edassist.models.mappers.*;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.*;

@RestController
public class ParticipantController {

	private final ApplicationService applicationService;
	private final AccessService accessService;
	private final ParticipantService participantService;
	private final BenefitPeriodService benefitPeriodService;
	private final RulesService rulesService;
	private final GenericService<ParticipantCurrentEducationProfile> participantCurrentEducationProfileService;
	private final ParticipantSupervisorsMapper participantSupervisorsMapper;
	private final ApplicationMapper applicationMapper;
	private final ParticipantMapper participantMapper;
	private final ThinBenefitPeriodMapper benefitPeriodDropdownMapper;
	private final ParticipantCurrentEducationProfileMapper participantCurrentEducationProfileMapper;
	private final AccountSnapshotMapper accountSnapshotMapper;
	private final OptionMapper filterOptionMapper;
	private final SessionService sessionService;
	private final GenericService<ThinParticipantExceptionRules> participantExceptionRulesService;
	private final ParticipantExceptionRulesMapper participantExceptionRulesMapper;

	@Autowired
	public ParticipantController(GenericService<ParticipantCurrentEducationProfile> participantCurrentEducationProfileService, ApplicationService applicationService, AccessService accessService,
			ParticipantService participantService, BenefitPeriodService benefitPeriodService, RulesService rulesService, ParticipantSupervisorsMapper participantSupervisorsMapper,
			ApplicationMapper applicationMapper, AccountSnapshotMapper accountSnapshotMapper, ParticipantMapper participantMapper,
			GenericService<ThinParticipantExceptionRules> participantExceptionRulesService, ThinBenefitPeriodMapper benefitPeriodDropdownMapper,
			ParticipantExceptionRulesMapper participantExceptionRulesMapper, ParticipantCurrentEducationProfileMapper participantCurrentEducationProfileMapper, OptionMapper filterOptionMapper,
			SessionService sessionService) {
		this.participantCurrentEducationProfileService = participantCurrentEducationProfileService;
		this.applicationService = applicationService;
		this.accessService = accessService;
		this.participantService = participantService;
		this.benefitPeriodService = benefitPeriodService;
		this.rulesService = rulesService;
		this.participantSupervisorsMapper = participantSupervisorsMapper;
		this.applicationMapper = applicationMapper;
		this.accountSnapshotMapper = accountSnapshotMapper;
		this.participantMapper = participantMapper;
		this.participantExceptionRulesService = participantExceptionRulesService;
		this.benefitPeriodDropdownMapper = benefitPeriodDropdownMapper;
		this.participantExceptionRulesMapper = participantExceptionRulesMapper;
		this.participantCurrentEducationProfileMapper = participantCurrentEducationProfileMapper;
		this.filterOptionMapper = filterOptionMapper;
		this.sessionService = sessionService;
	}

	@RequestMapping(value = "/v1/participants/{participantId}/participantEligibilityCheck", method = RequestMethod.GET)
	public ResponseEntity<RulesOutputDTO> participantEligibilityCheck(@PathVariable("participantId") Long participantId) {

		accessService.compareParticipantOrClientAdminToSession(participantId);
		RulesOutputDTO eligibilityruleEngineOutput = rulesService.buildRuleEngineOutputForNewApplication(sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID), participantId);

		return new ResponseEntity<>(eligibilityruleEngineOutput, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/participants/{participantId}/programEligibilityCheck", method = RequestMethod.GET)
	public ResponseEntity<RulesOutputDTO> programEligibilityCheck(@PathVariable("participantId") Long participantId) {

		accessService.compareParticipantOrClientAdminToSession(participantId);
		RulesOutputDTO eligibilityruleEngineOutput = rulesService.buildRuleEngineOutputForResource(sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID), participantId);

		return new ResponseEntity<>(eligibilityruleEngineOutput, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/participants/{participantId}/participant-exceptions", method = RequestMethod.GET)
	public ResponseEntity<List<ParticipantExceptionRulesDTO>> participantExceptions(@PathVariable("participantId") Long participantId) {

		Participant participant = accessService.compareParticipantOrClientAdminToSession(participantId);
		List<ThinParticipantExceptionRules> exceptionList = participantExceptionRulesService.findByParam("participantID", participant);
		List<ParticipantExceptionRulesDTO> exceptionDtoList = participantExceptionRulesMapper.toDTOList(exceptionList);
		return new ResponseEntity<>(exceptionDtoList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/participants/{participantId}", method = RequestMethod.GET)
	public ResponseEntity<ParticipantDTO> getParticipantDetails(@PathVariable("participantId") Long participantId) {

		Participant participant = participantService.findById(participantId);
		accessService.verifyParticipantOrHigherAccess(participant);
		ParticipantDTO participantResult;

		if (participant != null && participant.getParticipantId() != null) {
			participantResult = participantMapper.toDTO(participant);
			if (participantResult.getPaymentPreference() == null) {
				PaymentPreferenceDTO newPmtPreference = new PaymentPreferenceDTO();
				newPmtPreference.setPaymentPreference("Unknown");
				participantResult.setPaymentPreference(newPmtPreference);
			}
		} else {
			throw new BadRequestException("User not found");
		}

		return new ResponseEntity<>(participantResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/participants/{participantId}/tasklist", method = RequestMethod.GET)
	public ResponseEntity<ThinApplicationResultSetDTO> getTasklist(@PathVariable("participantId") Long participantId, @RequestParam(value = "index", required = false, defaultValue = "1") int index,
			@RequestParam(value = "recordsPerPage", required = false, defaultValue = "200") int recordsPerPage) {

		ThinApplicationResultSetDTO thinApplicationResultSetDTO = new ThinApplicationResultSetDTO();
		PaginationDTO paginationDTO = new PaginationDTO();

		Participant participant = accessService.retrieveAndCompareParticipantToSession(participantId);
		PaginationResult<ThinApp> actionNeededTaskList = applicationService.getActionNeededTaskList(participant, index, recordsPerPage);
		List<ThinAppDTO> thinAppDTOList = applicationMapper.toDTOList(actionNeededTaskList.getResult());

		paginationDTO.setTotal(actionNeededTaskList.getTotalRecordsCount());
		paginationDTO.setIndex(index);

		thinApplicationResultSetDTO.setApplications(thinAppDTOList);
		thinApplicationResultSetDTO.setPagination(paginationDTO);

		return new ResponseEntity<>(thinApplicationResultSetDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/participants/{participantId}", method = RequestMethod.PUT)
	public ResponseEntity<ParticipantDTO> updateParticipantDetails(@PathVariable("participantId") Long participantId, @Valid @RequestBody ParticipantDTO participantDTO) {

		Participant participant = accessService.compareParticipantOrClientAdminToSession(participantId);

		// Verifying that the preferredAddress we are updating is valid
		Long updatedAddress = participantDTO.getPreferredAddress();
		if (!participant.getPreferredAddress().equals(updatedAddress)) {
			if (!participant.getHomeAddress().getAddressId().equals(updatedAddress) && !participant.getWorkAddress().getAddressId().equals(updatedAddress)) {
				throw new ForbiddenException();
			}
		}

		ParticipantDTO participantDTOAfterUpdate;
		Participant participantAfterUpdate;

		if (participant.getParticipantId() != null) {
			participantAfterUpdate = participantMapper.toDomain(participantDTO, participant);
			// TODO: Should be removed, should automatically be done in the
			// domain objects
			participantAfterUpdate.setModifiedBy(participant.getUser());
			participantAfterUpdate.setModifiedDate(new Date());
			if (participantAfterUpdate.getPaymentPreference() != null) {
				if (participantAfterUpdate.getPaymentPreference().getParticipantID() == null) {
					participantAfterUpdate.getPaymentPreference().setParticipantID(participantAfterUpdate);
				}
				participantAfterUpdate.getPaymentPreference().setModifiedBy(participant.getUser());
				participantAfterUpdate.getPaymentPreference().setModifiedDate(new Date());
				if (participantAfterUpdate.getPaymentPreference().getDateCreated() == null || participantAfterUpdate.getPaymentPreference().getCreatedBy() == null) {
					participantAfterUpdate.getPaymentPreference().setCreatedBy(participant.getUser());
					participantAfterUpdate.getPaymentPreference().setDateCreated(new Date());
				}
			}
			if (participantAfterUpdate.getOtherPhone() != null && participantAfterUpdate.getOtherPhone().isEmpty()) {
				participantAfterUpdate.setOtherPhone(null);
			}
			participantService.saveOrUpdate(participantAfterUpdate);
			participantDTOAfterUpdate = participantMapper.toDTO(participantAfterUpdate);
		} else {
			throw new BadRequestException("User not found");
		}

		return new ResponseEntity<>(participantDTOAfterUpdate, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/participants/{participantId}/supervisors", method = RequestMethod.GET)
	public ResponseEntity<ParticipantSupervisorsDTO> getParticipantApprovers(@PathVariable("participantId") Long participantId) {

		Participant participant = accessService.compareParticipantOrClientAdminToSession(participantId);
		participant.setLevelOneSupervisor(participantService.findParticipantSupervisor(participant, 1));
		participant.setLevelTwoSupervisor(participantService.findParticipantSupervisor(participant, 2));
		ParticipantSupervisorsDTO participantSupervisorsDTO = participantSupervisorsMapper.toDTO(participant);

		return new ResponseEntity<>(participantSupervisorsDTO, HttpStatus.OK);
	}

	// TODO- We want to change this in the future ThinBenefitPeriodDTO to FilterOptionDTO
	@RequestMapping(value = "/v1/participants/{participantId}/benefitperiod-years", method = RequestMethod.GET)
	public ResponseEntity<List<ThinBenefitPeriodDTO>> getBenefitPeriodDropdown(@PathVariable("participantId") Long participantId) {

		accessService.compareParticipantToSession(participantId);

		List<ThinBenefitPeriod> bpDropdown = benefitPeriodService.callBPList3(participantId);
		List<ThinBenefitPeriodDTO> benefitPeriodDropdownDTOs = benefitPeriodDropdownMapper.toDTOList(bpDropdown);

		return new ResponseEntity<>(benefitPeriodDropdownDTOs, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/participants/{participantId}/educational-profiles", method = RequestMethod.GET)
	public ResponseEntity<ParticipantCurrentEducationProfileDTO> getParticipantEducationalProfile(@PathVariable("participantId") Long participantId) {

		Participant participant = accessService.compareParticipantOrClientAdminToSession(participantId);

		if (participant.getCurrentEducationProfile() == null) {
			ParticipantCurrentEducationProfile newProfile = new ParticipantCurrentEducationProfile();
			participant.setCurrentEducationProfile(newProfile);
			participantService.saveOrUpdate(participant);
		}

		ParticipantCurrentEducationProfileDTO educationProfileDTO = participantCurrentEducationProfileMapper.toDTO(participant.getCurrentEducationProfile());

		return new ResponseEntity<>(educationProfileDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/participants/{participantId}/educational-profiles", method = RequestMethod.POST)
	public ResponseEntity<ParticipantCurrentEducationProfileDTO> AddParticipantEducationalProfile(@PathVariable("participantId") Long participantId,
			@RequestBody ParticipantCurrentEducationProfileDTO participantCurrentEducationProfileDTO) {

		Participant participant = accessService.retrieveAndCompareParticipantToSession(participantId);

		ParticipantCurrentEducationProfile educationProfile = participantCurrentEducationProfileMapper.toDomain(participantCurrentEducationProfileDTO, new ParticipantCurrentEducationProfile());
		participant.setCurrentEducationProfile(educationProfile);

		participantService.saveOrUpdate(participant);

		Participant updatedParticipant = participantService.findById(participantId);
		ParticipantCurrentEducationProfileDTO updatedEducationProfileDTO = participantCurrentEducationProfileMapper.toDTO(updatedParticipant.getCurrentEducationProfile());

		return new ResponseEntity<>(updatedEducationProfileDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/participants/{participantId}/educational-profiles/{educationProfileId}", method = RequestMethod.PUT)
	public ResponseEntity<ParticipantCurrentEducationProfileDTO> updateParticipantEducationalProfile(@PathVariable("participantId") Long participantId,
			@PathVariable("educationProfileId") Long educationProfileId, @RequestBody ParticipantCurrentEducationProfileDTO participantCurrentEducationProfileDTO) {

		Participant participant = accessService.compareParticipantOrClientAdminToSession(participantId);
		if (!participant.getCurrentEducationProfile().getCurrentEducationProfileId().equals(educationProfileId)) {
			throw new ForbiddenException();
		}

		ParticipantCurrentEducationProfile educationProfile = participantCurrentEducationProfileMapper.toDomain(participantCurrentEducationProfileDTO, participant.getCurrentEducationProfile());

		participantCurrentEducationProfileService.saveOrUpdate(educationProfile);

		ParticipantCurrentEducationProfile updatedEducationProfile = participantCurrentEducationProfileService.findById(educationProfileId);
		ParticipantCurrentEducationProfileDTO updatedEducationProfileDTO = participantCurrentEducationProfileMapper.toDTO(updatedEducationProfile);

		return new ResponseEntity<>(updatedEducationProfileDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/participants/{participantID}/applications", method = RequestMethod.GET)
	public ResponseEntity<ThinApplicationResultSetDTO> getParticipantApplications(@PathVariable("participantID") Long participantID,
			@RequestParam(value = "index", required = false, defaultValue = "1") int index, @RequestParam(value = "recordsPerPage", required = false, defaultValue = "200") int recordsPerPage,
			@RequestParam(value = "teamMemberType", required = false, defaultValue = "me") String teamMemberType,
			@RequestParam(value = "sortBy", required = false, defaultValue = "applicationNumber DESC") String sortingProperty,
			@RequestParam(value = "benefitPeriod", required = false, defaultValue = "-1") String benefitPeriods) {

		ThinApplicationResultSetDTO thinApplicationResultSetDTO = new ThinApplicationResultSetDTO();
		PaginationDTO paginationDTO = new PaginationDTO();
		List<ThinAppDTO> applicationList;

		Participant participant = participantService.findById(participantID);
		accessService.verifyParticipantOrHigherAccess(participant);
		PaginationResult<ThinApp> applications;

		applications = applicationService.findSelfAndSuperviseeApplciations(participant, index, recordsPerPage, teamMemberType, sortingProperty, benefitPeriods);

		paginationDTO.setTotal(applications.getTotalRecordsCount());
		paginationDTO.setIndex(index);

		applicationList = applicationMapper.toDTOList(applications.getResult());

		thinApplicationResultSetDTO.setApplications(applicationList);
		thinApplicationResultSetDTO.setPagination(paginationDTO);

		return new ResponseEntity<>(thinApplicationResultSetDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/participants/{participantId}/applications/team-filter-options", method = RequestMethod.GET)
	public ResponseEntity<List<OptionDTO>> getTeamFilterOptions(@PathVariable("participantId") Long participantId) {
		Participant participant = participantService.findById(participantId);
		accessService.verifyParticipantOrHigherAccess(participant);
		List<Option> filterOptionContractList = applicationService.getTeamFilterOptions(participant);
		List<OptionDTO> filterOptionDTOList = filterOptionMapper.toDTOList(filterOptionContractList);

		return new ResponseEntity<>(filterOptionDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/participants/{participantId}/applications/benefit-period-filter-options", method = RequestMethod.GET)
	public ResponseEntity<List<OptionDTO>> getBenefitPeriodOptions(@PathVariable("participantId") Long participantId,
			@RequestParam(value = "teamMemberType", required = false, defaultValue = "me") String teamMemberType,
			@RequestParam(value = "defaultBenefitPeriodId", required = false) Long defaultBenefitPeriodId) {

		accessService.compareParticipantOrClientAdminToSession(participantId);
		List<Option> filterOptionContractList = applicationService.getBenefitPeriodFilterOptions(participantId, teamMemberType, defaultBenefitPeriodId);
		List<OptionDTO> filterOptionDTOList = filterOptionMapper.toDTOList(filterOptionContractList);

		return new ResponseEntity<>(filterOptionDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/participants/{participantId}/applications/sort-options", method = RequestMethod.GET)
	public ResponseEntity<List<OptionDTO>> getApplicationSortOptions(@PathVariable("participantId") Long participantId) {
		accessService.compareParticipantOrClientAdminToSession(participantId);
		List<Option> sortOptionContractList = applicationService.getAppSortOptions();
		List<OptionDTO> sortOptionDTOList = filterOptionMapper.toDTOList(sortOptionContractList);

		return new ResponseEntity<>(sortOptionDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/participants/{participantID}/accountSnapshot", method = RequestMethod.GET)
	public ResponseEntity<List<AccountSnapshotDTO>> getParticipantApplicationsAccountSnapshot(@PathVariable("participantID") Long participantID,
			@RequestParam(value = "benefitPeriod", required = false, defaultValue = "ALL") String benefitPeriod) {
		List<AccountSnapshotDTO> accountSnapshotDTOList = new ArrayList<>();

		accessService.compareParticipantToSession(participantID);
		if (benefitPeriod != null) {
			if (benefitPeriod.equalsIgnoreCase("All")) {
				List<AccountSnapshotForParticipant> allAccountSnapshotList = participantService.callAccountSnapshotForParticipantProc(participantID);
				for (AccountSnapshotForParticipant accountSnapshotForParticipant : allAccountSnapshotList) {
					AccountSnapshotDTO accountSnapshotForParticipantDTO = accountSnapshotMapper.toDTO(accountSnapshotForParticipant);
					accountSnapshotDTOList.add(accountSnapshotForParticipantDTO);
				}
			} else {
				List<AccountSnapshot> accountSnapshotList = participantService.callAccountSnapshotProc(participantID, benefitPeriod);
				for (AccountSnapshot accountSnapshot : accountSnapshotList) {
					if (!accountSnapshot.getCategory().equals("No Programs Assigned")) {
						AccountSnapshotDTO accountSnapshotDTO = accountSnapshotMapper.toDTO(accountSnapshot);
						accountSnapshotDTOList.add(accountSnapshotDTO);
					}
				}
			}
		}

		return new ResponseEntity<>(accountSnapshotDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/participants/search", method = RequestMethod.GET)
	public ResponseEntity<Map<String,String>> getParticipantUrl(@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			@RequestParam(value = "employeeId", required = false) String employeeId) {

		Map<String,String> result = new HashMap<>();
		result.put("adminURL", participantService.retrieveUrlByPhone(phoneNumber, employeeId));
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
