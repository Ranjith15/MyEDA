package com.edassist.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.edassist.constants.RestConstants;
import com.edassist.exception.UnauthorizedException;
import com.edassist.service.SessionService;

public class JWTAuthenticationFilter extends HandlerInterceptorAdapter {

	@Autowired
	private SessionService sessionService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws UnauthorizedException, Exception {

		if (request.getRequestURI().equals("/ed5/api/v1/test-autowired") || request.getRequestURI().equals("/ed5/api/v1/sessions/login")
				|| request.getRequestURI().equals("/ed5/api/v1/sessions/swapSSOToken") || request.getRequestURI().startsWith("/ed5/api/v1/content/general/login/")
				|| request.getRequestURI().equals("/ed5/api/v1/users/reset-password") || request.getRequestURI().equals("/ed5/api/swagger-ui.html")
				|| request.getRequestURI().equals("/ed5/api/v2/api-docs") || request.getRequestURI().equals("/ed5/api/webjars/") || request.getRequestURI().equals("/ed5/api/swagger-resources")
				|| request.getRequestURI().startsWith("/ed5/api/images/") || request.getRequestURI().startsWith("/ed5/api/configuration/")
				|| request.getRequestURI().equals("/ed5/api/v1/monitoring/health")) {
			return true;
		}
		String stringToken = request.getHeader("X-AUTH-TOKEN");
		if (stringToken == null) {
			stringToken = request.getParameter("X-AUTH-TOKEN");
		}
		if (stringToken == null || stringToken.isEmpty()) {
			stringToken = request.getHeader("api_key");
			if (stringToken == null || stringToken.isEmpty()) {
				throw new UnauthorizedException("auth token not found");
			}
		}

		if (sessionService.validateAuthToken(stringToken, RestConstants.SESSION_TOKEN_NAME) == false) {
			throw new UnauthorizedException("Invalid token");
		}
		return true;
	}

}
