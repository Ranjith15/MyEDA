package com.edassist.exception;

import com.edassist.constants.RestConstants;

public class UnprocessableEntityException extends RuntimeException {

	private static final long serialVersionUID = -5135453953778839666L;

	public UnprocessableEntityException() {
		super(RestConstants.REST_UNPROCESSABLE_ENTITY);
	}

	public UnprocessableEntityException(String message) {
		super(message);
	}
}
