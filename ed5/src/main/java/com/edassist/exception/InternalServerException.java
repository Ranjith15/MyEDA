package com.edassist.exception;

import com.edassist.constants.RestConstants;

public class InternalServerException extends RuntimeException {
	private static final long serialVersionUID = 8942052157893623357L;

	public InternalServerException() {
		super(RestConstants.REST_INTERNAL_SERVER_ERROR);
	}

	public InternalServerException(String message) {
		super(message);
	}
}
