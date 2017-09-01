package com.edassist.service.crm.advising;

import java.util.List;

import com.edassist.models.contracts.crm.advising.AdvisingSession;
import com.edassist.models.domain.Participant;
import com.edassist.service.GenericService;

public interface AdvisingService extends GenericService<AdvisingSession> {

	List<AdvisingSession> findSessionsByParticipant(Long participantId);

	List<AdvisingSession> updateMandatoryAdvising(Participant participant);

}
