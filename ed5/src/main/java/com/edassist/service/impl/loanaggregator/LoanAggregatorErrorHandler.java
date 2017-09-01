package com.edassist.service.impl.loanaggregator;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.edassist.exception.LoanAggregatorException;
import com.edassist.models.dto.loanaggregator.LoanAggregatorErrorResponseDTO;
import com.edassist.utils.RestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoanAggregatorErrorHandler implements ResponseErrorHandler {
	private static Logger log = Logger.getLogger(LoanAggregatorErrorHandler.class);

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		StringBuilder builder = new StringBuilder("Response errorCode: ");
		builder.append(response.getStatusCode()).append(" - ").append(response.getStatusText());
		ObjectMapper objectMapper = new ObjectMapper();
		LoanAggregatorErrorResponseDTO loanAggregatorResponse = objectMapper.readValue(response.getBody(), LoanAggregatorErrorResponseDTO.class);
		builder.append(" Yodlee errorCode : ").append(loanAggregatorResponse.getErrorCode()).append(" - ").append(loanAggregatorResponse.getErrorMessage());
		log.error(builder.toString());
		throw new LoanAggregatorException(loanAggregatorResponse);
	}

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		log.info(response.getStatusCode().toString());
		return RestUtils.isError(response.getStatusCode());
	}

}
