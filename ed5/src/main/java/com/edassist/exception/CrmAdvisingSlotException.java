package com.edassist.exception;

public class CrmAdvisingSlotException extends CrmException {

	private static final long serialVersionUID = 5908789410810879470L;
	private static final String defaultMessage = "Resource is not available for selected time slot";

	public CrmAdvisingSlotException() {
		super(defaultMessage);
	}

	public CrmAdvisingSlotException(String message) {
		super(message);
	}

	public CrmAdvisingSlotException(String message, Throwable cause) {
		super(message, cause);
	}

	public CrmAdvisingSlotException(Throwable cause) {
		super(defaultMessage, cause);
	}

}
