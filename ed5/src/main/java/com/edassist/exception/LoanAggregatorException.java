package com.edassist.exception;

import com.edassist.models.dto.loanaggregator.LoanAggregatorErrorResponseDTO;

public class LoanAggregatorException extends RuntimeException {

	private static final long serialVersionUID = -7910240808863308363L;
	private static final String defaultMessage = "Error while communicating with LoanAggregator";
	private LoanAggregatorErrorResponseDTO errorResponse;

	public LoanAggregatorException() {
		super(defaultMessage);
	}

	public LoanAggregatorException(String message) {
		super(message);
	}
	public LoanAggregatorException(Exception ex) {
		super(ex);
	}

	public LoanAggregatorException(LoanAggregatorErrorResponseDTO errorResponseDto) {
		super(errorResponseDto.getErrorMessage());
		this.errorResponse = errorResponseDto;
	}

	public LoanAggregatorErrorResponseDTO getErrorResponse() {
		return errorResponse;
	}

}
