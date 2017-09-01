package com.edassist.service.impl;

import com.edassist.dao.ApprovalHistoryDao;
import com.edassist.dao.GenericDao;
import com.edassist.models.domain.ApprovalHistory;
import com.edassist.service.ApprovalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ApprovalHistoryServiceImpl extends GenericServiceImpl<ApprovalHistory> implements ApprovalHistoryService {

	private ApprovalHistoryDao approvalHistoryDao;

	public ApprovalHistoryServiceImpl() {
	}

	@Autowired
	public ApprovalHistoryServiceImpl(@Qualifier("approvalHistoryDaoImpl") GenericDao<ApprovalHistory> genericDao) {
		super(genericDao);
		this.approvalHistoryDao = (ApprovalHistoryDao) genericDao;
	}

	@Override
	public String getByPassedMessage(ApprovalHistory approvalHistory) {
		StringBuffer messageBuff = new StringBuffer();
		String supervisorName = approvalHistory.getSupervisor().getUser().getFirstName() + " " + approvalHistory.getSupervisor().getUser().getLastName();
		messageBuff.append(supervisorName);

		String levelBlurb = " (Level 1 approver) ";
		if (approvalHistory.getApprovalLevel() == 2) {
			levelBlurb = " (Level 2 approver) ";
		}

		List<ApprovalHistory> approvalHistoryList = approvalHistoryDao.findByParam("applicationID", approvalHistory.getApplicationID());
		if (approvalHistoryList != null && approvalHistoryList.size() == 2) {
			Iterator<ApprovalHistory> ahIter = approvalHistoryList.iterator();
			ApprovalHistory ah1 = ahIter.next();
			ApprovalHistory ah2 = ahIter.next();
			if (ah1.getSupervisor().getParticipantId().equals(ah2.getSupervisor().getParticipantId())) {
				levelBlurb = " (Level 1 and 2 approver) ";
			}
		}

		messageBuff.append(levelBlurb);
		if (approvalHistory.getApprovalTypeID().getApprovalType().equals("Notified") || approvalHistory.getApprovalTypeID().getApprovalType().equals("Bypassed")) {
			messageBuff.append("has been ");
			messageBuff.append(approvalHistory.getApprovalTypeID().getApprovalType());
			messageBuff.append(" for this application by the Program Administrator.");
		} else {
			messageBuff.append(" has ");
			messageBuff.append(approvalHistory.getApprovalTypeID().getApprovalType());
			messageBuff.append(" for this application by the Program Administrator.");

		}
		return messageBuff.toString();
	}

}
