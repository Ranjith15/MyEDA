package com.edassist.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RestError {
	public RestError() {

	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String code;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the detailedMessage
	 */
	public String getDetailedMessage() {
		return detailedMessage;
	}

	/**
	 * @return the businessMessage
	 */
	public String getBusinessMessage() {
		return businessMessage;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @param detailedMessage the detailedMessage to set
	 */
	public void setDetailedMessage(String detailedMessage) {
		this.detailedMessage = detailedMessage;
	}

	/**
	 * @param businessMessage the businessMessage to set
	 */
	public void setBusinessMessage(String businessMessage) {
		this.businessMessage = businessMessage;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String detailedMessage;
	@JsonInclude(value = Include.NON_DEFAULT)
	public String businessMessage;
}
