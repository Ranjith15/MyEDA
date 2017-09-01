package com.edassist.models.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RestContent {
	@JsonInclude(value = Include.NON_DEFAULT)
	private String message = "";

	@JsonCreator
	public RestContent() {

	}

	public RestContent(String message) {
		this.message = message;
	}

	@JsonInclude(value = Include.NON_NULL)
	private RestError errors[] = new RestError[1];

	public RestContent(RestError errors[]) {
		this.errors = errors;
	}

	/**
	 * @return the errors
	 */
	public RestError[] getErrors() {
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(RestError[] errors) {
		this.errors = errors;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
