package com.edassist.service;

import java.text.ParseException;

import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.User;
import com.edassist.security.JWTToken;
import com.nimbusds.jose.JOSEException;

public interface SessionService {

	JWTToken getAuthToken();

	Boolean checkWebEnabled();

	Boolean checkLRPEnabled();

	String getClaimAsString(String claim);

	Long getClaimAsLong(String claim);

	Integer getClaimAsInt(String claim);

	String getJWTSecretKey();

	Boolean validateAuthToken(String stringToken, String tokenType) throws ParseException, JOSEException;

	String getSSOUrl() throws BadRequestException;

	String getServiceUserSessionHeader() throws BadRequestException;

	User getCurrentUser();

}
