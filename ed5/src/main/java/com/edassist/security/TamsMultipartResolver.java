package com.edassist.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

public class TamsMultipartResolver extends CommonsMultipartResolver {
	private boolean resolveLazily = false;

	public TamsMultipartResolver() {
		super();
	}

	public TamsMultipartResolver(ServletContext servletContext) {
		this();
		setServletContext(servletContext);
	}

	@Override
	public void setResolveLazily(boolean resolveLazily) {
		this.resolveLazily = resolveLazily;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.multipart.commons.CommonsMultipartResolver#resolveMultipart(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public MultipartHttpServletRequest resolveMultipart(final HttpServletRequest request) throws MultipartException {
		Assert.notNull(request, "Request must not be null");
		if (this.resolveLazily) {
			return new DefaultMultipartHttpServletRequest(request) {
				@Override
				protected void initializeMultipart() {
					MultipartParsingResult parsingResult = parseRequest(request);
					setMultipartFiles(parsingResult.getMultipartFiles());
					setMultipartParameters(getSanitizedMultipartparameters(parsingResult));
				}
			};
		} else {
			MultipartParsingResult parsingResult = parseRequest(request);
			return new DefaultMultipartHttpServletRequest(request, parsingResult.getMultipartFiles(), getSanitizedMultipartparameters(parsingResult),
					parsingResult.getMultipartParameterContentTypes());
		}

	}

	private Map<String, String[]> getSanitizedMultipartparameters(MultipartParsingResult parsingResult) {
		Map<String, String[]> mpParams = parsingResult.getMultipartParameters();
		if (mpParams != null) {
			HashMap<String, String[]> encodedParams = new HashMap<String, String[]>();
			for (String key : mpParams.keySet()) {
				encodedParams.put(key, AntiSamyHelper.sanitizeXSS(mpParams.get(key)));
			}
			return encodedParams;
		}
		return mpParams;
	}
}
