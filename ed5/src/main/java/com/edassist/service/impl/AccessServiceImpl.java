package com.edassist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edassist.constants.RestConstants;
import com.edassist.constants.UserTypeConstants;
import com.edassist.exception.ForbiddenException;
import com.edassist.models.domain.*;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.AccessService;
import com.edassist.service.ParticipantService;
import com.edassist.service.SessionService;
import com.edassist.service.UserTypeService;

@Service
public class AccessServiceImpl implements AccessService {

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private UserTypeService userTypeService;

	@Override
	public void compareParticipantToSession(Long participantId) {
		Long jwtParticipantId = sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID);
		if (!jwtParticipantId.equals(participantId)) {
			throw new ForbiddenException(RestConstants.REST_NOT_CURRENT_USER);
		}
	}

	@Override
	public Participant retrieveAndCompareParticipantToSession(Long participantId) {
		Long jwtParticipantId = sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID);
		if (!jwtParticipantId.equals(participantId)) {
			throw new ForbiddenException(RestConstants.REST_NOT_CURRENT_USER);
		}
		return participantService.findById(participantId);

	}

	@Override
	public void compareUserToSession(Long userId) {
		Long jwtUserId = sessionService.getClaimAsLong(JWTTokenClaims.USER_ID);
		if (!userId.equals(jwtUserId)) {
			throw new ForbiddenException(RestConstants.REST_NOT_CURRENT_USER);
		}
	}

	@Override
	public User verifyUserToSession(Application application) {
		Long jwtParticipantId = sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID);
		if (!jwtParticipantId.equals(application.getParticipantID().getParticipantId())) {
			throw new ForbiddenException(RestConstants.REST_NOT_CURRENT_USER);
		}
		return application.getParticipantID().getUser();
	}

	@Override
	public void verifyUserOrClientAdminToSession(Long appParticipantId, Long appClientId) {
		Long jwtParticipantId = sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID);
		boolean hasSelfAccess = jwtParticipantId.equals(appParticipantId);
		boolean hasClientAdminAccess = userTypeService.verfiyLoggedInUserType(UserTypeConstants.CLIENT_ADMIN) && sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID).equals(appClientId);
		if (!hasSelfAccess && !hasClientAdminAccess) {
			throw new ForbiddenException(RestConstants.REST_FORBIDDEN);
		}
	}

	@Override
	public boolean verifyCourseAndExpenseInApplication(Application app, Long applicationCoursesId) {
		for (ApplicationCourses appC : app.getApplicationCoursesCollection()) {
			if (appC.getApplicationCoursesID().equals(applicationCoursesId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void verifyParticipantOrHigherAccess(Application application) {
		verifyParticipantOrHigherAccess(application.getParticipantID());
	}

	@Override
	public void verifyParticipantOrHigherAccess(ThinApp application) {
		verifyParticipantOrHigherAccess(application.getParticipantID());
	}

	@Override
	public void verifyParticipantOrHigherAccess(Participant participant) {
		Long jwtParticipantId = sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID);
		boolean hasSelfAccess = jwtParticipantId.equals(participant.getParticipantId());
		boolean hasSupervisorAccess = userTypeService.verfiyLoggedInUserType(UserTypeConstants.SUPERVISOR)
				&& participantService.isSupervisor(participant, participantService.findById(jwtParticipantId));
		boolean hasClientAdminAccess = userTypeService.verfiyLoggedInUserType(UserTypeConstants.CLIENT_ADMIN)
				&& participant.getClient().getClientId().equals(sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID));

		if (!hasSelfAccess && !hasSupervisorAccess && !hasClientAdminAccess) {
			throw new ForbiddenException(RestConstants.REST_FORBIDDEN);
		}
	}

	@Override
	public void verifyParticipantOrClientAdminAccess(Application application) {
		Long jwtParticipantId = sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID);
		boolean hasSelfAccess = jwtParticipantId.equals(application.getParticipantID().getParticipantId());
		boolean hasClientAdminAccess = userTypeService.verfiyLoggedInUserType(UserTypeConstants.CLIENT_ADMIN)
				&& application.getParticipantID().getClient().getClientId().equals(sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID));

		if (!hasSelfAccess && !hasClientAdminAccess) {
			throw new ForbiddenException(RestConstants.REST_FORBIDDEN);
		}
	}

	@Override
	public Participant compareParticipantOrClientAdminToSession(Long participantId) throws ForbiddenException {
		Long jwtParticipantId = sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID);
		Participant originalParticipant = participantService.findById(participantId);
		boolean hasSelfAccess = jwtParticipantId.equals(originalParticipant.getParticipantId());
		boolean hasClientAdminAccess = userTypeService.verfiyLoggedInUserType(UserTypeConstants.CLIENT_ADMIN)
				&& originalParticipant.getClient().getClientId().equals(sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID));

		if (!hasSelfAccess && !hasClientAdminAccess) {
			throw new ForbiddenException(RestConstants.REST_FORBIDDEN);
		}
		return originalParticipant;
	}

	@Override
	public void verifyUserOrClientAdminAccess(Long userId) {
		Long jwtParticipantId = sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID);
		List<Participant> originalParticipant = participantService.findByParam("user.userId", userId);
		boolean hasSelfAccess = jwtParticipantId.equals(originalParticipant.get(0).getParticipantId());
		boolean hasClientAdminAccess = userTypeService.verfiyLoggedInUserType(UserTypeConstants.CLIENT_ADMIN)
				&& originalParticipant.get(0).getClient().getClientId().equals(sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID));

		if (!hasSelfAccess && !hasClientAdminAccess) {
			throw new ForbiddenException(RestConstants.REST_FORBIDDEN);
		}

	}

}
