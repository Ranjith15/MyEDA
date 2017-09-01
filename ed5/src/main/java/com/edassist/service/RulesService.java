package com.edassist.service;

import com.edassist.models.dto.ApplicationStatusDTO;
import com.edassist.models.dto.ProgramDTO;
import com.edassist.models.dto.RulesDTO;
import com.edassist.models.dto.RulesOutputDTO;

import java.util.List;

public interface RulesService {

	RulesOutputDTO buildRuleEngineOutputForNewApplication(Long clientId, Long participantId);

	RulesOutputDTO buildRuleEngineOutputForResource(Long clientId, Long participantId);

	List<ProgramDTO> retrieveEligiblePrograms(Long clientId, Long participantId) throws Exception;

	ApplicationStatusDTO getApplicationSubmissionStatus(Long applicationId);

	RulesOutputDTO getApplicationSubmissionRuleEngineOutput(Long applicationId);

	RulesOutputDTO getApplicationSubmissionRuleEngineOutput(Long applicationId, boolean createRuleMessages) throws Exception;

	Long convertStatusToID(String status, Long programId, Long applicationId);

	List<ProgramDTO> retrieveEligibleBookPrograms(Long clientId, Long participantId);

	String formatRulesComment(RulesOutputDTO ruleEngineOutput, List<RulesDTO> rulesDTO);
}