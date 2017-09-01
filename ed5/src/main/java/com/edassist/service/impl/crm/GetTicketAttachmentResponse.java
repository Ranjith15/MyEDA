package com.edassist.service.impl.crm;

import java.util.List;

import com.edassist.models.dto.AttachmentDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetTicketAttachmentResponse extends CrmResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2001059196710398749L;

	@JsonProperty("CaseAttachments")
	private List<AttachmentDTO> caseVHDAttachment;

	/**
	 * @return the caseVHDAttachment
	 */
	public List<AttachmentDTO> getCaseVHDAttachment() {
		return caseVHDAttachment;
	}

	/**
	 * @param caseVHDAttachment the caseVHDAttachment to set
	 */
	public void setCaseVHDAttachment(List<AttachmentDTO> caseVHDAttachment) {
		this.caseVHDAttachment = caseVHDAttachment;
	}

}
