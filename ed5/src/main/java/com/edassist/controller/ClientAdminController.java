package com.edassist.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edassist.models.domain.ApplicationStatus;
import com.edassist.models.domain.PaginationResult;
import com.edassist.models.domain.ThinParticipantSearch;
import com.edassist.models.dto.ApplicationStatusDTO;
import com.edassist.models.dto.OptionDTO;
import com.edassist.models.dto.PaginationDTO;
import com.edassist.models.dto.ParticipantDTO;
import com.edassist.models.dto.ParticipantResultSetDTO;
import com.edassist.models.mappers.ApplicationStatusMapper;
import com.edassist.models.mappers.OptionMapper;
import com.edassist.models.mappers.ParticipantMapper;
import com.edassist.models.mappers.ApplicationMapper;
import com.edassist.service.ApplicationStatusService;
import com.edassist.service.BenefitPeriodService;
import com.edassist.service.ClientAdminService;

@RestController
public class ClientAdminController {

	private final ClientAdminService clientAdminService;
	private final ApplicationStatusService applicationStatusService;
	private final BenefitPeriodService benefitPeriodService;
	private final ApplicationMapper applicationMapper;
	private final ParticipantMapper participantMapper;
	private final ApplicationStatusMapper applicationStatusMapper;
	private final OptionMapper filterOptionMapper;

	@Autowired
	public ClientAdminController(ClientAdminService clientAdminService, ApplicationStatusService applicationStatusService, BenefitPeriodService benefitPeriodService,
			ApplicationMapper applicationMapper, ParticipantMapper participantMapper, ApplicationStatusMapper applicationStatusMapper, OptionMapper filterOptionMapper) {
		this.clientAdminService = clientAdminService;
		this.applicationStatusService = applicationStatusService;
		this.benefitPeriodService = benefitPeriodService;
		this.applicationMapper = applicationMapper;
		this.participantMapper = participantMapper;
		this.applicationStatusMapper = applicationStatusMapper;
		this.filterOptionMapper = filterOptionMapper;
	}

	@RequestMapping(value = "/v1/client-admins/applications-by-client", method = RequestMethod.GET)
	public ResponseEntity<ParticipantResultSetDTO> getApplicationsByClient(@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName, @RequestParam(value = "employeeId", required = false) String employeeId,
			@RequestParam(value = "applicationNumber", required = false) Long applicationNumber, @RequestParam(value = "applicationStatus", required = false) Long applicationStatus,
			@RequestParam(value = "benefitPeriod", required = false) String benefitPeriod, @RequestParam(value = "clientId") Long clientId,
			@RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "index", required = false, defaultValue = "1") int index,
			@RequestParam(value = "recordsPerPage", required = false, defaultValue = "50") int recordsPerPage) {

		ParticipantResultSetDTO participantResultSetDTO = new ParticipantResultSetDTO();
		PaginationDTO paginationDTO = new PaginationDTO();
		List<ParticipantDTO> participantList = new ArrayList<>();
		PaginationResult<ThinParticipantSearch> participants = clientAdminService.getApplicationsByClient(firstName, lastName, employeeId, applicationNumber, applicationStatus, benefitPeriod,
				clientId, sortBy, index, recordsPerPage);

		paginationDTO.setTotal(participants.getTotalRecordsCount());
		paginationDTO.setIndex(index);

		for (ThinParticipantSearch participant : participants.getResult()) {
			ParticipantDTO participantDTO = participantMapper.toDTO(participant);
			participantDTO.setApplications(applicationMapper.toDTOList(participant.getApplicationSet()));
			participantList.add(participantDTO);
		}

		participantResultSetDTO.setParticipants(participantList);
		participantResultSetDTO.setPagination(paginationDTO);
		return new ResponseEntity<>(participantResultSetDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/client-admins/application-status-dropdown", method = RequestMethod.GET)
	public ResponseEntity<List<ApplicationStatusDTO>> getApplicationStatusDropdown() throws Exception {
		List<ApplicationStatus> appStatusDropdown = applicationStatusService.findAll();
		List<ApplicationStatusDTO> applicationStatusDTOs = applicationStatusMapper.toDTOList(appStatusDropdown);
		return new ResponseEntity<>(applicationStatusDTOs, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/client-admins/benefit-period-dropdown/{clientId}", method = RequestMethod.GET)
	public ResponseEntity<List<OptionDTO>> getBenefitPeriodDropdown(@PathVariable("clientId") Long clientId) throws Exception {
		List<String> benefitPeriodNames = benefitPeriodService.getBPListByClient(clientId);
		List<OptionDTO> filterOptionDTOs = filterOptionMapper.toFilterOptionDTOList(benefitPeriodNames);

		return new ResponseEntity<>(filterOptionDTOs, HttpStatus.OK);
	}

}
