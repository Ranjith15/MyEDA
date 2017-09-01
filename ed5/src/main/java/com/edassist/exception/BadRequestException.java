package com.edassist.exception;

import com.edassist.constants.RestConstants;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 8942052157893623356L;

	public BadRequestException() {
		super(RestConstants.BAD_REQUEST_MESSAGE);
	}

	public BadRequestException(String message) {
		super(message);
	}

}