package com.edassist.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.edassist.validator.ValidatingHttpRequest;

public class ValidationFilter implements Filter {

	Logger log = Logger.getLogger(ValidationFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		// HttpServletResponse response = (HttpServletResponse) res;

		// String sessionId = request.getSession().getId();
		String requestedSessionId = request.getRequestedSessionId();
		String requestedURL = request.getRequestURL().toString();

		// System.load("C:/BlazeLib/SSOEnabledClients.txt");
		// FileUtilities fileUtil = new FileUtilities();
		// fileUtil.fileContainsString(file, aString);

		if (requestedSessionId != null) {
			if (requestedURL.contains("/login/sanofi-aventis") || requestedURL.contains("/login/LMEducationAssistance") || requestedURL.contains("/login/LibertyMutual")
					|| requestedURL.contains("/login/UnionBank") || requestedURL.contains("/login/TRowePrice") || requestedURL.contains("/login/enterprise")
					|| requestedURL.contains("/login/Enterprise") || requestedURL.contains("/login/Pru") || requestedURL.contains("/login/amgen") || requestedURL.contains("/login/Amgen")
					|| requestedURL.contains("/login/Starbucks") || requestedURL.contains("/login/bcbsnc") || requestedURL.contains("/login/icf") || requestedURL.contains("/login/clevelandclinic")
					|| requestedURL.contains("/login/Stanford") || requestedURL.contains("/login/ketteringhealthnetwork") || requestedURL.contains("/login/Allstate")
					|| requestedURL.contains("/login/prime") || requestedURL.contains("/login/halliburton") || requestedURL.contains("/login/ufhealthshands")
					|| requestedURL.contains("/login/childrens") || requestedURL.contains("/login/MH") || requestedURL.contains("/login/spe") || requestedURL.contains("/login/UCM")

			) {
				request.getSession().invalidate();
				request.getSession(true);
			}
		}
		chain.doFilter(new ValidatingHttpRequest((HttpServletRequest) req), res);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

}
