package com.edassist.dao;

import java.util.List;

import com.edassist.models.contracts.crm.advising.AdvisingSession;

public interface AdvisingSessionDao extends GenericDao<AdvisingSession> {

	List<AdvisingSession> findSessionsByParticipant(Long participantId);

}
