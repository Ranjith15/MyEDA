package com.edassist.models.mappers.decorators;

import com.edassist.constants.UserTypeConstants;
import com.edassist.models.domain.ApplicationComment;
import com.edassist.models.dto.ApplicationCommentsDTO;
import com.edassist.models.mappers.ApplicationCommentsMapper;
import com.edassist.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

public abstract class ApplicationCommentsMapperDecorator implements ApplicationCommentsMapper {

	@Autowired
	@Qualifier("delegate")
	private ApplicationCommentsMapper delegate;

	@Autowired
	private UserTypeService userTypeService;

	@Override
	public ApplicationCommentsDTO toDTO(ApplicationComment applicationComments) {
		ApplicationCommentsDTO applicationCommentsDTO = delegate.toDTO(applicationComments);
		boolean isEdAssistComment = userTypeService.isUserInternalAdmin(applicationComments.getCreatedBy().getUserType().getId());
		applicationCommentsDTO.setEdassistComment(isEdAssistComment);
		if (isEdAssistComment && !userTypeService.verfiyLoggedInUserType(UserTypeConstants.INTERNAL_ADMIN)) {
			applicationCommentsDTO.getCreatedBy().setFirstName("Program");
			applicationCommentsDTO.getCreatedBy().setLastName("Admin");
		}

		return applicationCommentsDTO;
	}

	@Override
	public List<ApplicationCommentsDTO> toDTOList(List<ApplicationComment> applicationCommentsList) {
		List<ApplicationCommentsDTO> ApplicationCommentsDtoList = new ArrayList<ApplicationCommentsDTO>();
		for (ApplicationComment applicationIssue : applicationCommentsList) {
			ApplicationCommentsDTO applicationCommentsDTO = this.toDTO(applicationIssue);
			ApplicationCommentsDtoList.add(applicationCommentsDTO);
		}
		return ApplicationCommentsDtoList;
	}

}
