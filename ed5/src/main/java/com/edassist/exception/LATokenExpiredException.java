package com.edassist.exception;

import com.edassist.constants.LoanAggregatorConstants;

public class LATokenExpiredException extends LoanAggregatorException {

	private static final long serialVersionUID = 7529153571231640497L;

	public LATokenExpiredException() {
		super(LoanAggregatorConstants.LOANAGGREGATOR_TOKEN_EXPIRED);
	}

	public LATokenExpiredException(String message) {
		super(message);
	}

}
