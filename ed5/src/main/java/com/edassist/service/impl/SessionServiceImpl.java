package com.edassist.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edassist.constants.RestConstants;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.UnauthorizedException;
import com.edassist.models.domain.Property;
import com.edassist.models.domain.User;
import com.edassist.security.JWTToken;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.GenericService;
import com.edassist.service.SessionService;
import com.edassist.service.UserService;
import com.edassist.utils.CommonUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

@Service
public class SessionServiceImpl implements SessionService {

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private GenericService<Property> propertyService;

	@Autowired
	private UserService userService;

	@Override
	public JWTToken getAuthToken() {
		JWTToken token = null;
		try {
			String stringToken = httpServletRequest.getHeader("X-AUTH-TOKEN");
			if (stringToken == null) {
				stringToken = httpServletRequest.getParameter("X-AUTH-TOKEN");
			}
			JWT jwt = JWTParser.parse(stringToken);
			token = new JWTToken(jwt);
		} catch (Exception ex) {
			throw new BadRequestException();
		}
		return token;
	}

	@Override
	public Boolean checkWebEnabled() {
		Boolean status = false;
		try {
			String stringToken = httpServletRequest.getHeader("source");
			if (StringUtils.isNotEmpty(stringToken)) {
				if (stringToken.equals("web")) {
					status = true;
				}
			}
		} catch (Exception ex) {
			throw new BadRequestException();
		}
		return status;
	}

	@Override
	public Boolean checkLRPEnabled() {
		Boolean status = false;
		try {
			String stringToken = httpServletRequest.getHeader("source");
			if (StringUtils.isNotEmpty(stringToken)) {
				if (stringToken.equals("LRP")) {
					status = true;
				}
			}
		} catch (Exception ex) {
			throw new BadRequestException();
		}
		return status;
	}

	@Override
	public String getClaimAsString(String claim) {
		String claimValue = null;
		try {
			JWTToken token = getAuthToken();
			if (token != null && token.getClaims().getClaim(claim) != null) {
				claimValue = token.getClaims().getClaim(claim).toString();
			}

		} catch (Exception ex) {
			throw new BadRequestException();
		}
		return claimValue;
	}

	@Override
	public Long getClaimAsLong(String claim) {
		Long claimValue = null;
		try {
			JWTToken token = getAuthToken();
			if (token != null && token.getClaims().getClaim(claim) != null) {
				claimValue = Long.parseLong(token.getClaims().getClaim(claim).toString());
			}
		} catch (Exception ex) {
			throw new BadRequestException();
		}
		return claimValue;
	}

	@Override
	public Integer getClaimAsInt(String claim) {
		Integer claimValue = null;
		try {
			JWTToken token = getAuthToken();
			if (token != null && token.getClaims().getClaim(claim) != null) {
				claimValue = Integer.parseInt(token.getClaims().getClaim(claim).toString());
			}
		} catch (Exception ex) {
			throw new BadRequestException();
		}
		return claimValue;
	}

	@Override
	public String getJWTSecretKey() {
		String restSecretKey = null;
		Property property;
		List<Property> propertyList = propertyService.findByParam("propertyKey", "WEB_SERVICE_SECRET_KEY");
		if (propertyList != null && propertyList.size() > 0) {
			property = propertyList.get(0);
			restSecretKey = property.getPropertyValue();
		}
		return restSecretKey;
	}

	@Override
	public Boolean validateAuthToken(String stringToken, String tokenType) throws ParseException, JOSEException {
		JWSObject jwsObject = JWSObject.parse(stringToken);
		JWT jwt = JWTParser.parse(stringToken);
		JWTToken token = new JWTToken(jwt);

		JWSVerifier verifier = new MACVerifier(getJWTSecretKey());

		String issuer = token.getClaims().getIssuer();

		Date expTime = token.getClaims().getExpirationTime();
		String tokenName = token.getClaims().getStringClaim(JWTTokenClaims.TOKEN_NAME);
		// verify the signature, token name and issuer.
		if (jwsObject.verify(verifier) == false || !(issuer != null && issuer.equalsIgnoreCase(RestConstants.REST_ISSUER_NAME)) || !(tokenName != null && tokenName.equalsIgnoreCase(tokenType))) {
			throw new UnauthorizedException("Invalid Token.");
		}

		Date currTime = new Date();

		// if the token has expired
		if (expTime != null && currTime.after(expTime)) {
			throw new UnauthorizedException("Token Expired..Please login again.");
		}

		return true;
	}

	@Override
	public String getSSOUrl() throws BadRequestException {
		String host = CommonUtil.getHotProperty(CommonUtil.SSO_URL);
		if (host == null || host.isEmpty()) {
			throw new BadRequestException("SSO url is not specified");
		}
		return host;
	}

	@Override
	public String getServiceUserSessionHeader() {
		String userSession = null;
		try {
			userSession = httpServletRequest.getHeader("LOAN-AGGREGATOR-USER-TOKEN");
			if (userSession == null) {
				userSession = httpServletRequest.getParameter("LOAN-AGGREGATOR-USER-TOKEN");
			}

		} catch (Exception ex) {
			throw new BadRequestException();
		}
		if (userSession == null) {
			throw new BadRequestException();
		}
		return userSession;
	}

	@Override
	public User getCurrentUser() {
		Long jwtUserId = getClaimAsLong(JWTTokenClaims.USER_ID);
		return userService.findById(jwtUserId);

	}

}
