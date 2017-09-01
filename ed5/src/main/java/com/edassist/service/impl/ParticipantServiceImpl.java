package com.edassist.service.impl;

import com.edassist.constants.RestConstants;
import com.edassist.dao.GenericDao;
import com.edassist.dao.ParticipantDao;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.*;
import com.edassist.models.sp.AccountSnapshot;
import com.edassist.models.sp.AccountSnapshotForParticipant;
import com.edassist.models.sp.IvrParticipant;
import com.edassist.models.sp.ThinSupervisor;
import com.edassist.service.AccessService;
import com.edassist.service.ApplicationApprovalService;
import com.edassist.service.ParticipantService;
import com.edassist.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class ParticipantServiceImpl extends GenericServiceImpl<Participant> implements ParticipantService {

	private ParticipantDao participantDao;

	@Autowired
	private AccessService accessService;

	@Autowired
	private ApplicationApprovalService applicationApprovalService;

	@Autowired
	public ParticipantServiceImpl(@Qualifier("participantDaoImpl") GenericDao<Participant> genericDao) {
		super(genericDao);
		this.participantDao = (ParticipantDao) genericDao;
	}

	static Logger log = Logger.getLogger(ParticipantServiceImpl.class);

	@Override
	@Transactional
	public void saveOrUpdate(Participant participant) {

		Participant tmpParticipant = (com.edassist.models.domain.Participant) participant;

		if (tmpParticipant == null) {
			return;
		}

		if (tmpParticipant.getClient() == null) {
			throw new BadRequestException("Participant must be associated with a client.");
		}

		try {
			CommonUtil.modifySupervisors(tmpParticipant);
		} catch (LazyInitializationException e) {
			// This try/catch block is in here to catch password resets, where
			// the supervisor object is unattached, and
			// doesn't need to be.
			System.out.println(tmpParticipant.getParticipantId() + " PARTICIPANT GENERATED A LAZY INITIALIZATION ERROR.");
			e.printStackTrace();
		}

		participantDao.saveOrUpdate(tmpParticipant);

	}

	@Override
	public Participant findParticipantSupervisor(Participant participant, int level) {
		populateSupervisorLevels(participant);
		if (level == 1) {
			return participant.getLevelOneSupervisor();
		} else if (level == 2) {
			return participant.getLevelTwoSupervisor();
		} else {
			throw new BadRequestException("Unsupported level: [" + level + "]");
		}
	}

	@Override
	public boolean isSupervisor(Participant participant, Participant supervisor) {
		populateSupervisorLevels(participant);
		if (participant.getLevelOneSupervisor() != null && participant.getLevelOneSupervisor().equals(supervisor)) {
			return true;
		} else if (participant.getLevelTwoSupervisor() != null && participant.getLevelTwoSupervisor().equals(supervisor)) {
			return true;
		}

		return false;
	}

	@Override
	public List<ParticipantSupervisor> findSupervisoredParticipantList(Participant participant) {
		return participantDao.findSupervisoredParticipantList(participant);
	}

	@Override
	public List<Participant> search(Participant participant) throws ExceededMaxResultsException {
		return participantDao.search(participant);
	}

	@Override
	public List<AccountSnapshot> callAccountSnapshotProc(Long participantID, String benefitPeriodName) {
		return participantDao.callAccountSnapshotProc(participantID, benefitPeriodName);
	}

	@Override
	public void populateSupervisorLevels(Participant participant) {
		List<ThinSupervisor> myApprovers = getSupervisors(participant);

		for (ThinSupervisor approver : myApprovers) {
			Participant addMe = participantDao.findById(approver.getSupervisor());

			if (approver.isLevelOne()) {
				participant.setLevelOneSupervisor(addMe);
			}

			if (approver.isLevelTwo()) {
				participant.setLevelTwoSupervisor(addMe);
			}

		}

	}

	@Override
	public List<Participant> search(Participant participant, List<Client> clientList) throws ExceededMaxResultsException {
		List<Participant> participantList = new LinkedList<Participant>();

		if (clientList == null) {
			return null;
		} else {
			Iterator it = clientList.iterator();
			while (it.hasNext()) {
				Client client = (Client) it.next();
				participant.setClient(client);

				List<Participant> subList = participantDao.search(participant);
				participantList.addAll(subList);
			}
			return participantList;
		}
	}

	@Override
	public List<AccountSnapshotForParticipant> callAccountSnapshotForParticipantProc(Long participantID) {
		return participantDao.callAccountSnapshotForParticipantProc(participantID);

	}

	@Override
	public PaginationResult<ThinApp> searchThinAppActivityForMyTeam(Participant supervisorParticipant, int index, int recordsPerPage) throws ExceededMaxResultsException {
		return participantDao.searchThinAppActivityForMyTeam(supervisorParticipant, index, recordsPerPage);
	}

	@Override
	public List<ThinSupervisor> getSupervisors(Participant participant) {
		return participantDao.getSupervisors(participant);
	}

	@Override
	public String retrieveUrlByPhone(String phoneNumber, String employeeId) {
		Long participantId;
		if (!StringUtils.isEmpty(phoneNumber)) {
			List<IvrParticipant> ivrParticipant = participantDao.getIvrParticipant(phoneNumber);
			if (ivrParticipant.size() == 1) {
				participantId = ivrParticipant.get(0).getParticipantID();
			} else if (ivrParticipant.size() == 0) {
				throw new BadRequestException(RestConstants.NO_ENTRIES_FOUND);
			} else {
				throw new BadRequestException(RestConstants.NOT_UNIQUE);
			}
		} else if (!StringUtils.isEmpty(employeeId)) {
			List<Participant> participants = participantDao.findByParam("employeeId", employeeId);
			if (participants.size() == 1) {
				participantId = participants.get(0).getParticipantId();
			} else if (participants.size() == 0) {
				throw new BadRequestException(RestConstants.NO_ENTRIES_FOUND);
			} else {
				throw new BadRequestException(RestConstants.NOT_UNIQUE);
			}
		} else {
			throw new BadRequestException();
		}
		return constructTams4Url(participantId);
	}

	private String constructTams4Url(Long participantId) {
		return CommonUtil.getAdminUrl(CommonUtil.TAMS_ENVIRONMENT) + "?participantId=" + participantId.toString();
	}

}
