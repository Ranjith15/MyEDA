package com.edassist.models.dto;

import java.util.List;

// TODO: reference this class as the EntityResponse type, and remove all references to RestResponse, RestContent, and RestError classes, and delete them
public class RestErrors {

	public RestErrors(String message) {
		this.message = message;
	}

	public RestErrors(String message, List<Errors> errors) {
		this.message = message;
		this.errors = errors;
	}

	private String message;
	private List<Errors> errors;

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setErrors(List<Errors> errors) {
		this.errors = errors;
	}

	public List<Errors> getErrors() {
		return errors;
	}

}