package com.edassist.exception;

public class CrmException extends RuntimeException {

	private static final long serialVersionUID = -7910240808863308363L;
	private static final String defaultMessage = "Error while communication with CRM";

	public CrmException() {
		super(defaultMessage);
	}

	public CrmException(String message) {
		super(message);
	}

	public CrmException(String message, Throwable cause) {
		super(message, cause);
	}

	public CrmException(Throwable cause) {
		super(defaultMessage, cause);
	}

}
