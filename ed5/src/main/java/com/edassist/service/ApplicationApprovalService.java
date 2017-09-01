package com.edassist.service;

import com.edassist.models.domain.*;

public interface ApplicationApprovalService {

	ApplicationStatus processApplicationApproval(Application application, ApplicationStatus targetStatus);

	boolean isCurrentUserApprover(Application application);

	boolean isCurrentUserLevelOneApprover(Application application);

	boolean isCurrentUserLevelTwoApprover(Application application);

	boolean isCurrentUserSupervisorApprovalValid(Application application);

	void createApprover(int level, Application application, Participant supervisor);

	PaginationResult<ThinAppActivityForMyTeam> findActionRequiredApplications(Participant supervisor, int index, int recordsPerPage);

	void notifySupervisor(Application application, ApprovalHistory approver, Long emailContentId);

	void resetApprovers(Application application);

	void adjustApproversSet(Application application);

	ApplicationStatus processApplicationApproval(Application application, ApplicationStatus targetStatus, boolean bypassLevelOne, boolean bypassLevelTwo);

	void updateApprovalHistory(ApprovalHistory approver, ApprovalType approvalType);

}
