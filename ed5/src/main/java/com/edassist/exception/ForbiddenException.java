package com.edassist.exception;

import com.edassist.constants.RestConstants;

public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = -5135453953778839666L;

	public ForbiddenException() {
		super(RestConstants.REST_FORBIDDEN);
	}

	public ForbiddenException(String message) {
		super(message);
	}

}
