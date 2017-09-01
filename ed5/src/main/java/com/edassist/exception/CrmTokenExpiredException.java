package com.edassist.exception;

public class CrmTokenExpiredException extends CrmException {

	private static final long serialVersionUID = 7529153571231640497L;
	private static final String defaultMessage = "Security Token Expired. Please reauthenticate";

	public CrmTokenExpiredException() {
		super(defaultMessage);
	}

	public CrmTokenExpiredException(String message) {
		super(message);
	}

	public CrmTokenExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public CrmTokenExpiredException(Throwable cause) {
		super(defaultMessage, cause);
	}

}
