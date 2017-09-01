
package com.edassist.dao;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.*;
import com.edassist.models.sp.AccountSnapshot;
import com.edassist.models.sp.AccountSnapshotForParticipant;
import com.edassist.models.sp.IvrParticipant;
import com.edassist.models.sp.ThinSupervisor;

import java.util.List;

public interface ParticipantDao extends GenericDao<Participant> {

	List<ParticipantSupervisor> findSupervisoredParticipantList(Participant participant);

	List<Participant> search(Participant participant) throws ExceededMaxResultsException;

	List<AccountSnapshot> callAccountSnapshotProc(Long participantID, String benefitPeriodName);

	List<AccountSnapshotForParticipant> callAccountSnapshotForParticipantProc(final Long participantID);

	List<IvrParticipant> getIvrParticipant(String phoneNumber);

	PaginationResult<ThinApp> searchThinAppActivityForMyTeam(Participant supervisorParticipant, int index, int recordsPerPage);

	List<ThinSupervisor> getSupervisors(Participant participant);
}
