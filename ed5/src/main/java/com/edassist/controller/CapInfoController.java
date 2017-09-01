package com.edassist.controller;

import com.edassist.models.sp.CapInfo;
import com.edassist.models.domain.Participant;
import com.edassist.models.dto.CapInfoDTO;
import com.edassist.models.mappers.CapInfoMapper;
import com.edassist.service.AccessService;
import com.edassist.service.BenefitPeriodService;
import com.edassist.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CapInfoController {

	private final AccessService accessService;
	private final BenefitPeriodService benefitPeriodService;
	private final CapInfoMapper capInfoMapper;
	private final ParticipantService participantService;

	@Autowired
	public CapInfoController(AccessService accessService, BenefitPeriodService benefitPeriodService, CapInfoMapper capInfoMapper, ParticipantService participantService) {
		this.accessService = accessService;
		this.benefitPeriodService = benefitPeriodService;
		this.capInfoMapper = capInfoMapper;
		this.participantService = participantService;
	}

	@RequestMapping(value = "/v1/cap-info/participants/{participantId}", method = RequestMethod.GET)
	public ResponseEntity<CapInfoDTO> getParticipantCapInfo(@PathVariable("participantId") Long participantId, @RequestParam("programId") Long programId,
			@RequestParam("degreeObjectiveId") Long degreeObjectiveId, @RequestParam("benefitPeriod") String benefitPeriod) {

		Participant participant = participantService.findById(participantId);
		accessService.verifyParticipantOrHigherAccess(participant);

		CapInfo capInfo = benefitPeriodService.getParticipantCapInfo(participantId, programId, degreeObjectiveId, benefitPeriod);
		CapInfoDTO capInfoDTO = capInfoMapper.toDTO(capInfo);

		return new ResponseEntity<>(capInfoDTO, HttpStatus.OK);

	}
}
