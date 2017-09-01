package com.edassist.models.dto;

import com.edassist.models.dto.content.EmailContentDTO;
import com.edassist.models.dto.content.GeneralContentDTO;

import java.util.List;

public class ClientContentDTO {

	private List<GeneralContentDTO> generalContentDTOList;
	private List<EmailContentDTO> emailContentDTOList;

	public List<GeneralContentDTO> getGeneralContentDTOList() {
		return generalContentDTOList;
	}

	public void setGeneralContentDTOList(List<GeneralContentDTO> generalContentDTOList) {
		this.generalContentDTOList = generalContentDTOList;
	}

	public List<EmailContentDTO> getEmailContentDTOList() {
		return emailContentDTOList;
	}

	public void setEmailContentDTOList(List<EmailContentDTO> emailContentDTOList) {
		this.emailContentDTOList = emailContentDTOList;
	}
}
