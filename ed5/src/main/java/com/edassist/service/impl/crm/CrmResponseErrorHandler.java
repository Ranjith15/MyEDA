package com.edassist.service.impl.crm;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.edassist.exception.CrmException;
import com.edassist.exception.CrmTokenExpiredException;
import com.edassist.utils.RestUtils;

public class CrmResponseErrorHandler implements ResponseErrorHandler {
	private static Logger log = Logger.getLogger(CrmResponseErrorHandler.class);

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		StringBuilder builder = new StringBuilder("Response error: ");
		builder.append(response.getStatusCode()).append(" ").append(response.getStatusText());
		List<String> tokens = response.getHeaders().get(RestUtils.CRM_TOKEN_EXPIRY_HEADER);
		builder.append(tokens);
		log.error(builder.toString());
		if (tokens != null && tokens.size() > 0 && tokens.get(0) != null && tokens.get(0).trim().length() > 0) {
			if (String.valueOf(RestUtils.CRM_TOKEN_EXPIRED).equals(tokens.get(0).trim()) || String.valueOf(RestUtils.CRM_TOKEN_INVALID).equals(tokens.get(0).trim())) {
				throw new CrmTokenExpiredException();
			}
		}
		throw new CrmException(builder.toString());
	}

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		log.info(response.getStatusCode().toString());
		return RestUtils.isError(response.getStatusCode());
	}

}
