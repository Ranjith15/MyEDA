package com.edassist.service.impl.crm.advising;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edassist.dao.AdvisingSessionDao;
import com.edassist.dao.GenericDao;
import com.edassist.models.contracts.crm.ContactWrapper;
import com.edassist.models.contracts.crm.advising.AdvisingSession;
import com.edassist.models.contracts.crm.advising.AdvisingSessionProgram;
import com.edassist.models.domain.Participant;
import com.edassist.models.domain.User;
import com.edassist.models.domain.crm.Contact;
import com.edassist.service.CRMService;
import com.edassist.service.crm.advising.AdvisingService;
import com.edassist.service.impl.GenericServiceImpl;

@Service
public class AdvisingServiceImpl extends GenericServiceImpl<AdvisingSession> implements AdvisingService {

	private static Logger log = Logger.getLogger(AdvisingServiceImpl.class);

	@Autowired
	private CRMService crmService;

	private AdvisingSessionDao advisingSessionDao;

	public AdvisingServiceImpl() {
	}

	@Autowired
	public AdvisingServiceImpl(@Qualifier("advisingSessionDaoImpl") GenericDao<AdvisingSession> genericDao) {
		super(genericDao);
		this.advisingSessionDao = (AdvisingSessionDao) genericDao;
	}

	@Override
	public List<AdvisingSession> findSessionsByParticipant(Long participantId) {
		return advisingSessionDao.findSessionsByParticipant(participantId);
	}

	@Override
	public List<AdvisingSession> updateMandatoryAdvising(Participant participant) {

		log.debug("updateMandatoryAdvising ----->>");

		Contact contact = new Contact();
		contact.setParticipantId(participant.getParticipantId().toString());
		contact.setEmployeeId(participant.getEmployeeId());
		contact.setFirstName(participant.getUser().getFirstName());
		contact.setLastName(participant.getUser().getLastName());
		contact.setEmail(participant.getPreferEmail());
		contact.setPhone(participant.getHomeAddress().getPhone());
		contact.setClientName(participant.getClient().getClientName());
		User user = new User();
		user.setParticipantID(participant);
		contact.setClientId(user.getParticipantID().getClient().getClientId());
		contact.setDepartment(participant.getCompanyName());
		ContactWrapper contactWrapper = new ContactWrapper();
		contactWrapper.setContact(contact);
		List<AdvisingSession> advisingSessionCRMList = crmService.getAdvisingHistory(contactWrapper).getAdvisingSessions();
		List<AdvisingSession> advisingSessionDBList = findSessionsByParticipant(participant.getParticipantId());

		Map<String, AdvisingSession> advisingSessionCrmMap = new HashMap<String, AdvisingSession>();
		// creating HashMap of DBList
		Map<String, AdvisingSession> advisingSessionDBHashMap = new HashMap<String, AdvisingSession>();
		// populating the HashMap
		AdvisingSession dbAdvisingSession = null;

		if (advisingSessionCRMList != null && advisingSessionCRMList.size() > 0) {
			for (AdvisingSession crmAdvSession : advisingSessionCRMList) {
				advisingSessionCrmMap.put(crmAdvSession.getId(), crmAdvSession);
			}
		}

		if (advisingSessionDBList != null && advisingSessionDBList.size() > 0) {
			for (AdvisingSession dbAdvSession : advisingSessionDBList) {
				if (advisingSessionCrmMap.get(dbAdvSession.getId()) == null) {
					super.remove(dbAdvSession);
				} else {
					advisingSessionDBHashMap.put(dbAdvSession.getId(), dbAdvSession);
				}

			}
		}

		// Iterating the CRM List to save or update
		if (advisingSessionCRMList != null && advisingSessionCRMList.size() > 0) {
			for (AdvisingSession crmAdvisingSession : advisingSessionCRMList) {
				if (AdvisingSession.CAT_ACADEMIC == crmAdvisingSession.getCategoryCode()) {
					dbAdvisingSession = advisingSessionDBHashMap.get(crmAdvisingSession.getId());

					if (dbAdvisingSession != null) {
						// remove programs and add
						setAdvisingSessionPrograms(dbAdvisingSession, crmAdvisingSession.getAdvisingSessionPrograms());
						dbAdvisingSession.setStatus(crmAdvisingSession.getStatus());
						dbAdvisingSession.setSessionDate(crmAdvisingSession.getSessionDate());

						this.saveOrUpdate(dbAdvisingSession);
					} else {
						// insert
						crmAdvisingSession.setParticipantId(participant.getParticipantId());
						this.saveOrUpdate(crmAdvisingSession);

					}

				}
			}
		}
		log.debug("updateMandatoryAdvising <<-----");
		return advisingSessionCRMList;
	}

	private void setAdvisingSessionPrograms(AdvisingSession session, Collection<AdvisingSessionProgram> crmPrograms) {
		Collection<AdvisingSessionProgram> dbPrograms = session.getAdvisingSessionPrograms();
		List<AdvisingSessionProgram> toDelete = new ArrayList<AdvisingSessionProgram>();
		boolean found = false;
		if (session.getAdvisingSessionPrograms() != null && session.getAdvisingSessionPrograms().size() > 0) {
			if (crmPrograms != null) {
				for (AdvisingSessionProgram program : session.getAdvisingSessionPrograms()) {
					found = false;
					if (crmPrograms.stream().anyMatch((crmProgram -> crmProgram.getProgramId().intValue() == program.getProgramId().intValue()))) {
						found = true;
						break;
					}
					if (!found) {
						toDelete.add(program);
					}
				}
				session.getAdvisingSessionPrograms().removeAll(toDelete);
			} else {
				session.getAdvisingSessionPrograms().clear();
			}
		}

		if (crmPrograms != null && crmPrograms.size() > 0) {
			if (session.getAdvisingSessionPrograms() != null) {
				for (AdvisingSessionProgram program : crmPrograms) {
					found = false;
					if (session.getAdvisingSessionPrograms().stream().anyMatch((dbProgram -> dbProgram.getProgramId().intValue() == program.getProgramId().intValue()))) {
						found = true;
						break;
					}

					if (!found) {
						session.addProgram(program);
					}

				}
			} else {
				session.setAdvisingSessionPrograms(crmPrograms);
			}
		}

	}
}
