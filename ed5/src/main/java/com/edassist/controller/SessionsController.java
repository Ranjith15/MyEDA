package com.edassist.controller;

import com.edassist.constants.RestConstants;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ForbiddenException;
import com.edassist.exception.UnauthorizedException;
import com.edassist.models.domain.Client;
import com.edassist.models.domain.Participant;
import com.edassist.models.domain.User;
import com.edassist.models.dto.*;
import com.edassist.models.mappers.ClientFeaturesMapper;
import com.edassist.models.mappers.UserDetailsMapper;
import com.edassist.security.JWTToken;
import com.edassist.security.JWTTokenClaims;
import com.edassist.security.TamsAuthenticationProvider;
import com.edassist.security.UserDetail;
import com.edassist.service.ClientService;
import com.edassist.service.ParticipantService;
import com.edassist.service.SessionService;
import com.edassist.service.UserService;
import com.edassist.utils.CommonUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;

@RestController
public class SessionsController {

	private static Logger log = Logger.getLogger(SessionsController.class);

	private final ClientService clientService;
	private final TamsAuthenticationProvider tamsAuthenticationProvider;
	private final SessionService sessionService;
	private final ParticipantService participantService;
	private final UserService userService;
	private final UserDetailsMapper userDetailsMapper;
	private final ClientFeaturesMapper clientFeaturesMapper;

	@Autowired
	public SessionsController(ClientService clientService, TamsAuthenticationProvider tamsAuthenticationProvider, SessionService sessionService, ParticipantService participantService,
			UserService userService, UserDetailsMapper userDetailsMapper, ClientFeaturesMapper clientFeaturesMapper) {
		this.clientService = clientService;
		this.tamsAuthenticationProvider = tamsAuthenticationProvider;
		this.sessionService = sessionService;
		this.participantService = participantService;
		this.userService = userService;
		this.userDetailsMapper = userDetailsMapper;
		this.clientFeaturesMapper = clientFeaturesMapper;
	}

	@RequestMapping(value = "/v1/sessions/login", method = RequestMethod.POST)
	public ResponseEntity<LoginDTO> login(@Valid @RequestBody UserLoginDTO login) throws JOSEException {

		Client client = null;

		// build an Authentication object with the user's info
		LoginDTO loginDTO = new LoginDTO();

		if (!login.getClientId().equals(RestConstants.ADMIN_CLIENTID)) {
			client = clientService.findById(login.getClientId());

			if (sessionService.checkWebEnabled()) {
				if (!client.isEdassist5Enabled()) {
					throw new ForbiddenException(RestConstants.REST_EDASSIST5_FORBIDDEN);
				}
			} else if (sessionService.checkLRPEnabled()) {
				if (!client.isLoanRepayEnabled()) {
					throw new ForbiddenException(RestConstants.REST_LRP_FORBIDDEN);
				}

			} else {
				if (!client.isMobileEnabled()) {
					throw new ForbiddenException(RestConstants.REST_MOBILE_FORBIDDEN);
				}
			}
		}

		Authentication authentication;
		String userIDClientID = login.getClientId() + ":" + login.getName();
		UserDetail userDetail;

		authentication = new UsernamePasswordAuthenticationToken(userIDClientID, login.getPassword());
		authentication = tamsAuthenticationProvider.authenticate(authentication);
		userDetail = (UserDetail) authentication.getPrincipal();
		if (authentication.isAuthenticated()) {
			String secretKey = sessionService.getJWTSecretKey();
			AuthTokensDTO authTokens = new AuthTokensDTO();
			authTokens.setSessionToken(generateSessionToken(userIDClientID, userDetail.getUser(), secretKey));
			authTokens.setRefreshToken(generateRefreshToken(secretKey));
			loginDTO.setTokens(authTokens);
		} else {
			throw new UnauthorizedException();
		}
		// retrieve the logged in user
		User loggedInUser = userDetail.getUser();
		UserDetailsDTO userDetails = userDetailsMapper.toDTO(loggedInUser);
		loginDTO.setUserDetails(userDetails);

		if (!login.getClientId().equals(RestConstants.ADMIN_CLIENTID)) {
			ClientFeaturesDTO clientFeaturesDTO = clientFeaturesMapper.toDTO(client);
			loginDTO.setClientFeatures(clientFeaturesDTO);
		}

		return new ResponseEntity<>(loginDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/sessions/swapSSOToken", method = RequestMethod.POST)
	public ResponseEntity<LoginDTO> swapSSOToken(@RequestBody SwapTokensDTO swapTokensDTO) throws ParseException, JOSEException {
		log.debug(" INSIDE SWAPTOKEN    " + swapTokensDTO.getAccessToken());
		LoginDTO loginDTO = new LoginDTO();

		JWT jwt = JWTParser.parse(swapTokensDTO.getAccessToken());

		String accessToken = swapTokensDTO.getAccessToken();
		boolean validToken = false;

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.set("Accept", "application/json");
		params.set("Content-Type", "application/x-www-form-urlencoded");

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", "edassistValidate");
		map.add("grant_type", "urn:pingidentity.com:oauth2:grant_type:validate_bearer");
		map.add("token", accessToken);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Accept", "application/json");
		String requestUrl = sessionService.getSSOUrl();

		CommonUtil.setMessageConverters(restTemplate);

		ValidateTokenDTO validatedToken = new ValidateTokenDTO();

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<ValidateTokenDTO> response = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity, ValidateTokenDTO.class);
		log.debug(response.getBody().getAccessToken().getSubject());
		validatedToken = response.getBody();

		Participant participant = new Participant();
		log.debug(validatedToken.getAccessToken().getSubject());
		User user = new User();
		String ssoId = (String) jwt.getJWTClaimsSet().getClaim("SUBJECT");
		if (validatedToken.getAccessToken().getSubject().equals(ssoId)) {
			validToken = true;
			log.debug(" same EmployeeId " + ssoId);
		}
		// TODO: this breaks the mobileapp. We will have to refactor this.
		Client client = (Client) clientService.findByParam("url", swapTokensDTO.getMobileClientCode()).get(0);

		String searchParams[] = new String[] { "client", "ssoId" };
		Object paramsValues[] = new Object[] { client, ssoId };

		participant = (Participant) participantService.findByParams(searchParams, paramsValues, null, null).get(0);
		user = userService.findById(participant.getUser().getUserId());
		if (!validToken || participant == null || !participant.getActiveIndicator() || user.getUserName().equals("~MISSING~") || user.getUserName() == null || user.getUserName() == "") {
			throw new UnauthorizedException();
		}

		String userIDClientID = participant.getClient().getClientId() + ":" + user.getUserName();
		String secretKey = sessionService.getJWTSecretKey();
		log.debug(user.getUserName());

		AuthTokensDTO authTokens = new AuthTokensDTO();
		authTokens.setSessionToken(generateSessionToken(userIDClientID, user, secretKey));
		authTokens.setRefreshToken(generateRefreshToken(secretKey));
		loginDTO.setTokens(authTokens);

		UserDetailsDTO userDetails = userDetailsMapper.toDTO(user);
		loginDTO.setUserDetails(userDetails);
		ClientFeaturesDTO clientFeaturesDTO = clientFeaturesMapper.toDTO(client);
		loginDTO.setClientFeatures(clientFeaturesDTO);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/v1/sessions/refresh-token", method = RequestMethod.POST)
	public ResponseEntity<AuthTokensDTO> refreshToken(@RequestBody AuthTokensDTO authTokensDTO) throws ParseException, JOSEException {

		String secretKey = sessionService.getJWTSecretKey();

		// validate refresh token
		if (sessionService.validateAuthToken(authTokensDTO.getRefreshToken(), RestConstants.REFRESH_TOKEN_NAME) == false) {
			throw new UnauthorizedException();
		}

		// generate session token
		JWTToken token = sessionService.getAuthToken();

		String userIDClientID = token.getName();
		Long userID = token.getClaims().getLongClaim(JWTTokenClaims.USER_ID);
		User user = userService.findById(userID);
		String issuer = token.getClaims().getIssuer();

		if (userIDClientID != null && userIDClientID.contains(":") && issuer.equals(RestConstants.REST_ISSUER_NAME)) {
			authTokensDTO.setSessionToken(generateSessionToken(userIDClientID, user, secretKey));
		} else {
			throw new UnauthorizedException();
		}

		return new ResponseEntity<>(authTokensDTO, HttpStatus.OK);

	}

	private String generateSessionToken(String userIDClientID, User user, String secretKey) throws JOSEException {
		JWSSigner signer = new MACSigner(secretKey);
		JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();

		builder.subject(userIDClientID);
		builder.issuer(RestConstants.REST_ISSUER_NAME);

		StringTokenizer authTokens = new StringTokenizer(userIDClientID, ":");

		String clientId = null;
		String username = null;

		if (authTokens != null && authTokens.countTokens() >= 2) {
			clientId = authTokens.nextToken();
			username = authTokens.nextToken();

			builder.claim(JWTTokenClaims.CLIENT_ID, clientId);
			builder.claim(JWTTokenClaims.USER_NAME, username);
		}
		builder.claim(JWTTokenClaims.USER_ID, user.getUserId());
		builder.claim(JWTTokenClaims.USER_TYPE_ID, user.getUserType().getId());
		builder.claim(JWTTokenClaims.USER_FIRST_NAME, user.getFirstName());
		builder.claim(JWTTokenClaims.USER_LAST_NAME, user.getLastName());
		builder.claim(JWTTokenClaims.USER_MIDDLE_INITIALS, user.getMiddleInitial());
		if (new Long(clientId).equals(RestConstants.ADMIN_CLIENTID)) {
			builder.claim(JWTTokenClaims.CLIENT_CODE, RestConstants.ADMIN_CLIENTCODE);
		} else {
			builder.claim(JWTTokenClaims.PARTICIPANT_ID, user.getParticipantID().getParticipantId());
			builder.claim(JWTTokenClaims.CLIENT_CODE, user.getParticipantID().getClient().getClientCode());
		}
		builder.claim(JWTTokenClaims.TOKEN_NAME, RestConstants.SESSION_TOKEN_NAME);
		builder.expirationTime(new Date(new Date().getTime() + RestConstants.SESSION_TOKEN_LENGTH)).build();

		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), builder.build());
		signedJWT.sign(signer);
		String signedToken = signedJWT.serialize();
		return signedToken;
	}

	private String generateRefreshToken(String secretKey) {
		String signedToken;
		try {
			JWSSigner signer = new MACSigner(secretKey);
			JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
			builder.issuer(RestConstants.REST_ISSUER_NAME);
			builder.claim(JWTTokenClaims.TOKEN_NAME, RestConstants.REFRESH_TOKEN_NAME);
			builder.expirationTime(new Date(new Date().getTime() + RestConstants.REFRESH_TOKEN_LENGTH)).build();

			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), builder.build());
			signedJWT.sign(signer);
			signedToken = signedJWT.serialize();
		} catch (Exception ex) {
			throw new BadRequestException("unexpected error while generating refresh token");
		}

		return signedToken;
	}
}
