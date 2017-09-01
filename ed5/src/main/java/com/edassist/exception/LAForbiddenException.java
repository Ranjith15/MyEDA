package com.edassist.exception;

import com.edassist.constants.LoanAggregatorConstants;

public class LAForbiddenException extends RuntimeException {

	private static final long serialVersionUID = -5135453953778839666L;

	public LAForbiddenException() {
		super(LoanAggregatorConstants.LOANAGGREGATOR_FORBIDDEN);
	}

	public LAForbiddenException(String message) {
		super(message);
	}

}
