package com.edassist.models.mappers.decorators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edassist.constants.ApplicationConstants;
import com.edassist.constants.StatusChangeSourceConstants;
import com.edassist.models.domain.*;
import com.edassist.models.dto.EligibilityEventHistoryDTO;
import com.edassist.models.dto.OptionDTO;
import com.edassist.models.mappers.EligibilityEventHistoryMapper;
import com.edassist.service.ApprovalHistoryService;
import com.edassist.service.UserTypeService;
import com.edassist.utils.CommonUtil;

public abstract class EligibilityEventHistoryMapperDecorator implements EligibilityEventHistoryMapper {

	@Autowired
	@Qualifier("delegate")
	private EligibilityEventHistoryMapper delegate;

	@Autowired
	private UserTypeService userTypeService;

	@Autowired
	private ApprovalHistoryService approvalHistoryService;

	@Override
	public EligibilityEventHistoryDTO toDTO(RuleMessages ruleMessage) {
		EligibilityEventHistoryDTO eligibilityEventHistoryDTO = delegate.toDTO(ruleMessage);
		OptionDTO messageSource = new OptionDTO(StatusChangeSourceConstants.SOURCE_RULES.getDisplay(), StatusChangeSourceConstants.SOURCE_RULES.getValue(), null);
		eligibilityEventHistoryDTO.setMessageSource(messageSource);
		eligibilityEventHistoryDTO.setCreatedByName("Program Rules"); // used as per the TAMS4 code
		return eligibilityEventHistoryDTO;
	}

	@Override
	public List<EligibilityEventHistoryDTO> toDTOListFromRuleMessages(List<RuleMessages> ruleMessages) {
		List<EligibilityEventHistoryDTO> eligibilityEventHistoryDTOList = new ArrayList<>();
		for (RuleMessages ruleMessage : ruleMessages) {
			EligibilityEventHistoryDTO eligibilityEventHistoryDTO = this.toDTO(ruleMessage);
			if (eligibilityEventHistoryDTO.getStatus() != null && !eligibilityEventHistoryDTO.getStatus().equals("Cap Set")
					&& !eligibilityEventHistoryDTO.getStatus().equals("Submission LOC Not Accepted'")) {
				eligibilityEventHistoryDTOList.add(eligibilityEventHistoryDTO);
			}
		}
		return eligibilityEventHistoryDTOList;
	}

	@Override
	public EligibilityEventHistoryDTO toDTO(EligibilityEventComment eligibilityEventComment) {
		EligibilityEventHistoryDTO eligibilityEventHistoryDTO = delegate.toDTO(eligibilityEventComment);
		OptionDTO messageSource = new OptionDTO(StatusChangeSourceConstants.SOURCE_MANUAL.getDisplay(), StatusChangeSourceConstants.SOURCE_MANUAL.getValue(), null);
		eligibilityEventHistoryDTO.setMessageSource(messageSource);

		setCreatedByConditionally(eligibilityEventHistoryDTO,
				CommonUtil.getFullName(eligibilityEventComment.getCreatedBy().getFirstName(), eligibilityEventComment.getCreatedBy().getmI(), eligibilityEventComment.getCreatedBy().getLastName()),
				eligibilityEventComment.getCreatedBy().getUserType().getId());
		return eligibilityEventHistoryDTO;
	}

	@Override
	public List<EligibilityEventHistoryDTO> toDTOListFromEventComments(List<EligibilityEventComment> eligibilityEventComments) {
		List<EligibilityEventHistoryDTO> eligibilityEventHistoryDTOList = new ArrayList<>();
		for (EligibilityEventComment eligibilityEventComment : eligibilityEventComments) {
			EligibilityEventHistoryDTO eligibilityEventHistoryDTO = this.toDTO(eligibilityEventComment);
			eligibilityEventHistoryDTOList.add(eligibilityEventHistoryDTO);
		}

		return eligibilityEventHistoryDTOList;
	}

	@Override
	public EligibilityEventHistoryDTO toDTO(AppStatusChange appStatusChange) {
		EligibilityEventHistoryDTO eligibilityEventHistoryDTO = delegate.toDTO(appStatusChange);
		setCreatedByConditionally(eligibilityEventHistoryDTO,
				CommonUtil.getFullName(appStatusChange.getModifiedBy().getFirstName(), appStatusChange.getModifiedBy().getmI(), appStatusChange.getModifiedBy().getLastName()),
				appStatusChange.getModifiedBy().getUserType().getId());
		setStatusChangeData(eligibilityEventHistoryDTO);

		return eligibilityEventHistoryDTO;
	}

	@Override
	public EligibilityEventHistoryDTO toDTO(AppStatusChangeLive appStatusChangeLive) {
		EligibilityEventHistoryDTO eligibilityEventHistoryDTO = delegate.toDTO(appStatusChangeLive);
		setCreatedByConditionally(eligibilityEventHistoryDTO,
				CommonUtil.getFullName(appStatusChangeLive.getModifiedBy().getFirstName(), appStatusChangeLive.getModifiedBy().getmI(), appStatusChangeLive.getModifiedBy().getLastName()),
				appStatusChangeLive.getModifiedBy().getUserType().getId());
		setStatusChangeData(eligibilityEventHistoryDTO);

		return eligibilityEventHistoryDTO;
	}

	@Override
	public List<EligibilityEventHistoryDTO> toDTOListFromAppStatusChange(List<AppStatusChange> appStatusChangeList) {
		List<EligibilityEventHistoryDTO> eligibilityEventHistoryDTOList = new ArrayList<>();
		for (AppStatusChange appStatusChange : appStatusChangeList) {
			eligibilityEventHistoryDTOList.add(toDTO(appStatusChange));
		}
		return eligibilityEventHistoryDTOList;
	}

	@Override
	public List<EligibilityEventHistoryDTO> toDTOListFromAppStatusChangeLive(List<AppStatusChangeLive> appStatusChangeLiveList) {
		List<EligibilityEventHistoryDTO> eligibilityEventHistoryDTOList = new ArrayList<>();
		for (AppStatusChangeLive appStatusChangeLive : appStatusChangeLiveList) {
			eligibilityEventHistoryDTOList.add(toDTO(appStatusChangeLive));
		}
		return eligibilityEventHistoryDTOList;
	}

	@Override
	public EligibilityEventHistoryDTO toDTO(ApprovalHistory approvalHistory) {
		EligibilityEventHistoryDTO eligibilityEventHistoryDTO = delegate.toDTO(approvalHistory);
		setCreatedByConditionally(eligibilityEventHistoryDTO,
				CommonUtil.getFullName(approvalHistory.getCreatedBy().getFirstName(), approvalHistory.getCreatedBy().getmI(), approvalHistory.getCreatedBy().getLastName()),
				approvalHistory.getCreatedBy().getUserType().getId());
		setApprovalHistoryData(approvalHistory, eligibilityEventHistoryDTO);

		return eligibilityEventHistoryDTO;
	}

	@Override
	public List<EligibilityEventHistoryDTO> toDTOListFromApprovalHistory(List<ApprovalHistory> approvalHistoryList) {
		List<EligibilityEventHistoryDTO> eligibilityEventHistoryDTOList = new ArrayList<>();
		for (ApprovalHistory approvalHistory : approvalHistoryList) {
			eligibilityEventHistoryDTOList.add(toDTO(approvalHistory));
		}
		return eligibilityEventHistoryDTOList;
	}

	@Override
	public EligibilityEventHistoryDTO toDTO(Refunds refund) {
		EligibilityEventHistoryDTO eligibilityEventHistoryDTO = delegate.toDTO(refund);
		setCreatedByConditionally(eligibilityEventHistoryDTO, CommonUtil.getFullName(refund.getCreatedBy().getFirstName(), refund.getCreatedBy().getmI(), refund.getCreatedBy().getLastName()),
				refund.getCreatedBy().getUserType().getId());
		eligibilityEventHistoryDTO.setMessageSource(new OptionDTO(StatusChangeSourceConstants.SOURCE_ADJUSTMENT.getDisplay(), StatusChangeSourceConstants.SOURCE_ADJUSTMENT.getValue(), null));
		return eligibilityEventHistoryDTO;
	}

	@Override
	public List<EligibilityEventHistoryDTO> toDTOListFromRefunds(List<Refunds> refundsList) {
		List<EligibilityEventHistoryDTO> eligibilityEventHistoryDTOList = new ArrayList<EligibilityEventHistoryDTO>();
		for (Refunds refund : refundsList) {
			eligibilityEventHistoryDTOList.add(toDTO(refund));
		}
		return eligibilityEventHistoryDTOList;
	}

	private void setStatusChangeData(EligibilityEventHistoryDTO eligibilityEventHistoryDTO) {
		eligibilityEventHistoryDTO.setMessage("Status Change");

		OptionDTO messageSource = new OptionDTO(StatusChangeSourceConstants.SOURCE_GENERAL.getDisplay(), StatusChangeSourceConstants.SOURCE_GENERAL.getValue(), null);
		eligibilityEventHistoryDTO.setMessageSource(messageSource);
	}

	private void setCreatedByConditionally(EligibilityEventHistoryDTO eligibilityEventHistoryDTO, String name, int userTypeId) {
		// if the status changes was created by an EdAssist admin, and the end user is an external client user, use a generic name
		if (userTypeService.isUserInternalAdmin(userTypeId) && !userTypeService.isUserInternalAdmin()) {
			eligibilityEventHistoryDTO.setCreatedByName("EdAssist Admin");
		} else {
			eligibilityEventHistoryDTO.setCreatedByName(name);
		}
	}

	private void setApprovalHistoryData(ApprovalHistory approvalHistory, EligibilityEventHistoryDTO eligibilityEventHistoryDTO) {
		eligibilityEventHistoryDTO.setMessage(approvalHistoryService.getByPassedMessage(approvalHistory));
		eligibilityEventHistoryDTO.setStatusId(ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW);
		OptionDTO messageSource = new OptionDTO(StatusChangeSourceConstants.SOURCE_GENERAL.getDisplay(), StatusChangeSourceConstants.SOURCE_GENERAL.getValue(), null);
		eligibilityEventHistoryDTO.setMessageSource(messageSource);
	}

}
