package com.edassist.models.dto;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class RestResponse {
	@JsonCreator
	public RestResponse() {

	}

	@JsonIgnore
	public String status = null;
	public HashMap<String, Object> content = new HashMap<String, Object>();

	@JsonCreator
	public RestResponse(HashMap<String, Object> content) {
		this.content = content;
	}

	private String code = "";

	public RestResponse(String code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the respData
	 */
	public HashMap<String, Object> getContent() {
		return content;
	}

	/**
	 * @param respData the respData to set
	 */
	public void setContent(HashMap<String, Object> respData) {
		this.content = respData;
	}

}
