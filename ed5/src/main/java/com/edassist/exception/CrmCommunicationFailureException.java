package com.edassist.exception;

public class CrmCommunicationFailureException extends CrmException {

	private static final long serialVersionUID = -7046896049803922048L;
	private static final String defaultMessage = "An Error occurred while Communicating with CRM";

	public CrmCommunicationFailureException() {
		super(defaultMessage);
	}

	public CrmCommunicationFailureException(String message) {
		super(message);
	}

	public CrmCommunicationFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public CrmCommunicationFailureException(Throwable cause) {
		super(defaultMessage, cause);
	}

}
