package com.edassist.service.impl;

import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.Program;
import com.edassist.models.dto.*;
import com.edassist.models.mappers.ProgramTypeMapper;
import com.edassist.service.ProgramService;
import com.edassist.service.RulesService;
import com.edassist.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RulesServiceImpl implements RulesService {

	@Autowired
	private ProgramService programService;

	@Autowired
	private ProgramTypeMapper programTypeMapper;

	private RestTemplate restTemplate = new RestTemplate();

	public String getServiceHost() throws BadRequestException {
		String host = CommonUtil.getHotProperty(CommonUtil.RULES_SERVICE_HOST);
		if (host == null || host.isEmpty()) {
			throw new BadRequestException();
		}
		return "http://" + host + "/TAMS4Web/api/v1/services/rules/";
	}

	@Override
	public RulesOutputDTO buildRuleEngineOutputForNewApplication(Long clientId, Long participantId) {
		String url = this.getServiceHost() + "new-application";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("clientId", clientId).queryParam("participantId", participantId);
		RulesOutputDTO rulesOutput = restTemplate.getForObject(builder.build().encode().toUri(), RulesOutputDTO.class);

		/*
		 * ED-2467 solution (hack) - this really needs to be pushed into caelutility where the list of abbreviated Programs is compiled
		 */
		if (rulesOutput.getPrograms() != null && rulesOutput.getPrograms().size() > 0) {
			for (ProgramDTO programDTO : rulesOutput.getPrograms()) {
				Program program = programService.findById(programDTO.getProgramID());
				ProgramTypeDTO programTypeDTO = programTypeMapper.toDTO(program.getProgramTypeID());
				programDTO.setProgramTypeID(programTypeDTO);
				programDTO.setUnknownCapLimitEnabled(program.isUnknownCapLimitEnabled());
				programDTO.setDefaultBenefitPeriodID(program.getDefaultBenefitPeriodID());
			}
		}
		return rulesOutput;
	}

	@Override
	public RulesOutputDTO buildRuleEngineOutputForResource(Long clientId, Long participantId) {
		String url = this.getServiceHost() + "resource";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("clientId", clientId).queryParam("participantId", participantId);
		RulesOutputDTO rulesOutput = restTemplate.getForObject(builder.build().encode().toUri(), RulesOutputDTO.class);

		return rulesOutput;
	}

	@Override
	public List<ProgramDTO> retrieveEligiblePrograms(Long clientId, Long participantId) throws BadRequestException, Exception {
		String url = this.getServiceHost() + "eligible-programs";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("clientId", clientId).queryParam("participantId", participantId);
		ProgramDTO[] rulesOutput = restTemplate.getForObject(builder.build().encode().toUri(), ProgramDTO[].class);
		List<ProgramDTO> rulesList = new ArrayList<>(Arrays.asList(rulesOutput));

		return rulesList;
	}

	@Override
	public ApplicationStatusDTO getApplicationSubmissionStatus(Long applicationId) {
		String url = this.getServiceHost() + "application-submission-status";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("applicationId", applicationId);
		ApplicationStatusDTO rulesOutput = restTemplate.getForObject(builder.build().encode().toUri(), ApplicationStatusDTO.class);

		return rulesOutput;
	}

	@Override
	public RulesOutputDTO getApplicationSubmissionRuleEngineOutput(Long applicationId) {
		return this.getApplicationSubmissionRuleEngineOutput(applicationId, true);
	}

	@Override
	public RulesOutputDTO getApplicationSubmissionRuleEngineOutput(Long applicationId, boolean createRuleMessages) {
		String url = this.getServiceHost() + "application-submission";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("applicationId", applicationId).queryParam("createRuleMessages", createRuleMessages);
		RulesOutputDTO rulesOutput = restTemplate.getForObject(builder.build().encode().toUri(), RulesOutputDTO.class);

		return rulesOutput;
	}

	@Override
	public Long convertStatusToID(String status, Long programId, Long applicationId) {
		String url = this.getServiceHost() + "convert-status";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("status", status).queryParam("programId", programId).queryParam("applicationId", applicationId);
		Long rulesOutput = restTemplate.getForObject(builder.build().encode().toUri(), Long.class);

		return rulesOutput;
	}

	@Override
	public List<ProgramDTO> retrieveEligibleBookPrograms(Long clientId, Long participantId) {
		String url = this.getServiceHost() + "eligible-book-programs";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("clientId", clientId).queryParam("participantId", participantId);

		ProgramDTO[] rulesOutput = restTemplate.getForObject(builder.build().encode().toUri(), ProgramDTO[].class);
		List<ProgramDTO> rulesList = new ArrayList<>(Arrays.asList(rulesOutput));

		return rulesList;
	}

	@Override
	public String formatRulesComment(RulesOutputDTO ruleEngineOutput, List<RulesDTO> rulesDTO) {

		String formattedComment = "";
		String semiColon = "";

		for (RulesDTO rule : rulesDTO) {
			if (rule.getRuleStatus().equals(ruleEngineOutput.getStatus())) {
				formattedComment += semiColon + rule.getRuleMessage();
				semiColon = "; ";
			}
		}
		return formattedComment;
	}
}
