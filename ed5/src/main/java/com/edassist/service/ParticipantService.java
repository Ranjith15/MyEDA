package com.edassist.service;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.*;
import com.edassist.models.sp.AccountSnapshot;
import com.edassist.models.sp.AccountSnapshotForParticipant;
import com.edassist.models.sp.ThinSupervisor;

import java.util.List;

public interface ParticipantService extends GenericService<Participant> {

	Participant findParticipantSupervisor(Participant participant, int level);

	List<ParticipantSupervisor> findSupervisoredParticipantList(Participant participant);

	List<Participant> search(Participant participant) throws ExceededMaxResultsException;

	List<Participant> search(Participant participant, List<Client> clientList) throws ExceededMaxResultsException;

	List<AccountSnapshot> callAccountSnapshotProc(Long participantID, String benefitPeriodName);

	List<AccountSnapshotForParticipant> callAccountSnapshotForParticipantProc(final Long participantID);

	boolean isSupervisor(Participant participant, Participant supervisor);

	void populateSupervisorLevels(Participant participant);

	PaginationResult<ThinApp> searchThinAppActivityForMyTeam(Participant supervisorParticipant, int index, int recordsPerPage) throws ExceededMaxResultsException;

	List<ThinSupervisor> getSupervisors(Participant participant);

	String retrieveUrlByPhone(String phoneNumber, String employeeId);
}
