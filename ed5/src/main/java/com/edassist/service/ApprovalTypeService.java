package com.edassist.service;

import com.edassist.models.domain.ApprovalType;

public interface ApprovalTypeService extends GenericService<ApprovalType> {

	ApprovalType fetchDefaultApprovalType();

	ApprovalType fetchPendingApprovalType();

	ApprovalType fetchNotifiedApprovalType();

	ApprovalType fetchApprovedApprovalType();

	ApprovalType fetchDeniedApprovalType();

	ApprovalType fetchBypassedApprovalType();

	ApprovalType fetchNotReviewedApprovalType();

	boolean isPending(ApprovalType approvalType);

	boolean isNotified(ApprovalType approvalType);

	boolean isApproved(ApprovalType approvalType);

	boolean isDenied(ApprovalType approvalType);

	boolean isBypassed(ApprovalType approvalType);

	boolean isNotReviewed(ApprovalType approvalType);
}
