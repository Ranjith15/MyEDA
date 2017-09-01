package com.edassist.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edassist.dao.GenericDao;
import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.ApprovalType;
import com.edassist.service.ApprovalTypeService;

@Service
public class ApprovalTypeServiceImpl extends GenericServiceImpl<ApprovalType> implements ApprovalTypeService {

	private GenericDao<ApprovalType> approvalTypeDao;

	public ApprovalTypeServiceImpl() {
	}

	@Autowired
	public ApprovalTypeServiceImpl(@Qualifier("approvalTypeDao") GenericDao<ApprovalType> genericDao) {
		super(genericDao);
		this.approvalTypeDao = genericDao;
	}

	public final String DEFAULT_APPROVAL_TYPE = "Pending";
	public final String APPROVAL_TYPE_PENDING = "Pending";
	public final String APPROVAL_TYPE_NOTIFIED = "Notified";
	public final String APPROVAL_TYPE_APPROVED = "Approved";
	public final String APPROVAL_TYPE_DENIED = "Denied";
	public final String APPROVAL_TYPE_BYPASSED = "Bypassed";
	public final String APPROVAL_TYPE_NOT_REVIEWED = "Not Reviewed";

	private Map<String, ApprovalType> approvalTypes = new HashMap<String, ApprovalType>();

	@Override
	public ApprovalType fetchDefaultApprovalType() {
		return fetchApprovalType(DEFAULT_APPROVAL_TYPE);
	}

	@Override
	public ApprovalType fetchPendingApprovalType() {
		return fetchApprovalType(APPROVAL_TYPE_PENDING);
	}

	@Override
	public ApprovalType fetchNotifiedApprovalType() {
		return fetchApprovalType(APPROVAL_TYPE_NOTIFIED);
	}

	@Override
	public ApprovalType fetchApprovedApprovalType() {
		return fetchApprovalType(APPROVAL_TYPE_APPROVED);
	}

	@Override
	public ApprovalType fetchDeniedApprovalType() {
		return fetchApprovalType(APPROVAL_TYPE_DENIED);
	}

	@Override
	public ApprovalType fetchBypassedApprovalType() {
		return fetchApprovalType(APPROVAL_TYPE_BYPASSED);
	}

	@Override
	public ApprovalType fetchNotReviewedApprovalType() {
		return fetchApprovalType(APPROVAL_TYPE_NOT_REVIEWED);
	}

	@Override
	public boolean isPending(ApprovalType approvalType) {
		if (approvalType == null) {
			return false;
		}

		return (fetchPendingApprovalType().equals(approvalType));
	}

	@Override
	public boolean isNotified(ApprovalType approvalType) {
		if (approvalType == null) {
			return false;
		}

		return (fetchNotifiedApprovalType().equals(approvalType));
	}

	@Override
	public boolean isApproved(ApprovalType approvalType) {
		if (approvalType == null) {
			return false;
		}

		return (fetchApprovedApprovalType().equals(approvalType));
	}

	@Override
	public boolean isDenied(ApprovalType approvalType) {
		if (approvalType == null) {
			return false;
		}

		return (fetchDeniedApprovalType().equals(approvalType));
	}

	@Override
	public boolean isBypassed(ApprovalType approvalType) {
		if (approvalType == null) {
			return false;
		}

		return (fetchBypassedApprovalType().equals(approvalType));
	}

	@Override
	public boolean isNotReviewed(ApprovalType approvalType) {
		if (approvalType == null) {
			return false;
		}

		return (fetchNotReviewedApprovalType().equals(approvalType));
	}

	private ApprovalType fetchApprovalType(String approvalTypeName) {

		if (!approvalTypes.containsKey(approvalTypeName)) {
			ApprovalType approvalType = approvalTypeDao.findByUniqueParam("approvalType", approvalTypeName);

			if (approvalType == null) {
				throw new BadRequestException("Could not find a approval type: " + approvalTypeName);
			}
			approvalTypes.put(approvalTypeName, approvalType);
		}
		return approvalTypes.get(approvalTypeName);
	}
}
