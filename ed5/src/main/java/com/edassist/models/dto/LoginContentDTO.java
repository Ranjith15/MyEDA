package com.edassist.models.dto;

import com.edassist.models.dto.content.GeneralContentDTO;

import java.util.List;

public class LoginContentDTO {

	private List<GeneralContentDTO> generalContentList;
	private ClientLoginDetailsDTO clientLoginDetails;

	public List<GeneralContentDTO> getGeneralContentList() {
		return generalContentList;
	}

	public void setGeneralContentList(List<GeneralContentDTO> generalContentList) {
		this.generalContentList = generalContentList;
	}

	public ClientLoginDetailsDTO getClientLoginDetails() {
		return clientLoginDetails;
	}

	public void setClientLoginDetails(ClientLoginDetailsDTO clientLoginDetails) {
		this.clientLoginDetails = clientLoginDetails;
	}

}
