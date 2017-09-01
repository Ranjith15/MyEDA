package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.domain.*;
import com.edassist.models.dto.EligibilityEventHistoryDTO;
import com.edassist.models.mappers.decorators.EligibilityEventHistoryMapperDecorator;

@Mapper(uses = { ApplicationStatusMapper.class })
@DecoratedWith(EligibilityEventHistoryMapperDecorator.class)
public interface EligibilityEventHistoryMapper {

	@Mappings({ @Mapping(source = "ruleStatus", target = "status"), @Mapping(source = "ruleStatusId", target = "statusId"), @Mapping(source = "ruleMessage", target = "message") })
	EligibilityEventHistoryDTO toDTO(RuleMessages ruleMessage);

	@Mappings({ @Mapping(source = "eligibilityEventComment.applicationStatus.applicationStatus", target = "status"),
			@Mapping(source = "eligibilityEventComment.applicationStatus.applicationStatusID", target = "statusId"), @Mapping(source = "comment", target = "message") })
	EligibilityEventHistoryDTO toDTO(EligibilityEventComment eligibilityEventComment);

	@Mappings({ @Mapping(source = "appStatusChange.applicationStatus.applicationStatusID", target = "statusId"),
			@Mapping(source = "appStatusChange.applicationStatus.applicationStatus", target = "status"), @Mapping(source = "appStatusChange.dateModified", target = "dateCreated") })
	EligibilityEventHistoryDTO toDTO(AppStatusChange appStatusChange);

	@Mappings({ @Mapping(source = "appStatusChangeLive.applicationStatus.applicationStatusID", target = "statusId"),
			@Mapping(source = "appStatusChangeLive.applicationStatus.applicationStatus", target = "status"), @Mapping(source = "appStatusChangeLive.dateModified", target = "dateCreated") })
	EligibilityEventHistoryDTO toDTO(AppStatusChangeLive appStatusChangeLive);

	@Mappings({ @Mapping(source = "approvalHistory.approvalTypeID.approvalTypeID", target = "statusId"), @Mapping(source = "approvalHistory.approvalTypeID.approvalType", target = "status"),
			@Mapping(source = "approvalHistory.dateCreated", target = "dateCreated") })
	EligibilityEventHistoryDTO toDTO(ApprovalHistory approvalHistory);

	@Mapping(source = "comments", target = "message")
	EligibilityEventHistoryDTO toDTO(Refunds refund);

	List<EligibilityEventHistoryDTO> toDTOListFromRuleMessages(List<RuleMessages> ruleMessages);

	List<EligibilityEventHistoryDTO> toDTOListFromEventComments(List<EligibilityEventComment> eligibilityEventComments);

	List<EligibilityEventHistoryDTO> toDTOListFromAppStatusChange(List<AppStatusChange> appStatusChangeList);

	List<EligibilityEventHistoryDTO> toDTOListFromAppStatusChangeLive(List<AppStatusChangeLive> appStatusChangeLiveList);

	List<EligibilityEventHistoryDTO> toDTOListFromApprovalHistory(List<ApprovalHistory> approvalHistoryList);

	List<EligibilityEventHistoryDTO> toDTOListFromRefunds(List<Refunds> refundsList);
}
