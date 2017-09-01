package com.edassist.controller;

import com.edassist.constants.RestConstants;
import com.edassist.dao.SessionDao;
import com.edassist.exception.UnauthorizedException;
import com.edassist.models.dto.ServerStatusDTO;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.MonitoringService;
import com.edassist.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MonitoringController {

	private final SessionDao sessionDao;
	private final MonitoringService monitoringService;
	private final SessionService sessionService;

	@Autowired
	public MonitoringController(SessionDao sessionDao, MonitoringService monitoringService, SessionService sessionService) {
		this.sessionDao = sessionDao;
		this.monitoringService = monitoringService;
		this.sessionService = sessionService;
	}

	@RequestMapping(value = "/v1/monitoring/health", method = RequestMethod.GET)
	public ResponseEntity<String> checkHealth() throws Exception {
		sessionDao.testConnection();
		return new ResponseEntity<>("server is up", HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/monitoring/v5-server-status", method = RequestMethod.GET)
	public ResponseEntity<List<ServerStatusDTO>> checkV5ServerStatus() {
		if (!sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID).equals(RestConstants.ADMIN_CLIENTID)) {
			throw new UnauthorizedException(RestConstants.UNAUTHORIZED);
		}
		List<ServerStatusDTO> serverStatusDTOs = monitoringService.checkV5Servers();
		return new ResponseEntity<>(serverStatusDTOs, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/monitoring/v4-server-status", method = RequestMethod.GET)
	public ResponseEntity<List<ServerStatusDTO>> checkV4ServerStatus() {
		if (!sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID).equals(RestConstants.ADMIN_CLIENTID)) {
			throw new UnauthorizedException(RestConstants.UNAUTHORIZED);
		}
		List<ServerStatusDTO> serverStatusDTOs = monitoringService.checkV4Servers();
		return new ResponseEntity<>(serverStatusDTOs, HttpStatus.OK);
	}
}
