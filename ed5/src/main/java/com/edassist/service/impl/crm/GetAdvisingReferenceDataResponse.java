package com.edassist.service.impl.crm;

import java.util.List;

import com.edassist.models.domain.crm.Code;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAdvisingReferenceDataResponse extends CrmResponse {
	@JsonProperty("ReferenceData")
	private List<Code> referenceData;
	@JsonProperty("Message")
	private String message;

	/**
	 * @return the referenceData
	 */
	public List<Code> getReferenceData() {
		return referenceData;
	}

	/**
	 * @param referenceData the referenceData to set
	 */
	public void setReferenceData(List<Code> referenceData) {
		this.referenceData = referenceData;
	}

}
