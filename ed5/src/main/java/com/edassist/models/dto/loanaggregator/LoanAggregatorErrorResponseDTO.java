package com.edassist.models.dto.loanaggregator;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class LoanAggregatorErrorResponseDTO {

	private String errorCode;
	private String errorMessage;
	private String referenceCode;

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}
	
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}

}
