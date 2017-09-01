package com.edassist.service;

import com.edassist.models.domain.ApprovalHistory;

public interface ApprovalHistoryService extends GenericService<ApprovalHistory> {

	String getByPassedMessage(ApprovalHistory approvalHistory);
}
