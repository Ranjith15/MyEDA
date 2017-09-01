package com.edassist.service;

import com.edassist.models.domain.Application;
import com.edassist.models.domain.Participant;
import com.edassist.models.domain.ThinApp;
import com.edassist.models.domain.User;

public interface AccessService {

	void compareParticipantToSession(Long participantId);

	Participant retrieveAndCompareParticipantToSession(Long participantId);

	void compareUserToSession(Long userId);

	User verifyUserToSession(Application application);

	void verifyUserOrClientAdminToSession(Long participantId, Long clientId);

	boolean verifyCourseAndExpenseInApplication(Application app, Long applicationCoursesId);

	void verifyParticipantOrHigherAccess(Application application);

	void verifyParticipantOrHigherAccess(ThinApp application);

	void verifyParticipantOrHigherAccess(Participant participant);

	void verifyParticipantOrClientAdminAccess(Application application);

	Participant compareParticipantOrClientAdminToSession(Long participantId);

	void verifyUserOrClientAdminAccess(Long userId);

}
