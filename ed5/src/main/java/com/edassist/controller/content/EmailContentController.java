package com.edassist.controller.content;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edassist.constants.RestConstants;
import com.edassist.exception.UnauthorizedException;
import com.edassist.models.contracts.content.EmailContent;
import com.edassist.models.dto.content.EmailContentDTO;
import com.edassist.models.mappers.content.EmailContentMapper;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.SessionService;
import com.edassist.service.content.EmailContentService;

@RestController
public class EmailContentController {

	private final EmailContentMapper emailContentMapper;
	private final EmailContentService emailContentService;
	private final SessionService sessionService;

	@Autowired
	public EmailContentController(EmailContentMapper emailContentMapper, EmailContentService emailContentService, SessionService sessionService) {
		this.emailContentMapper = emailContentMapper;
		this.emailContentService = emailContentService;
		this.sessionService = sessionService;
	}

	@RequestMapping(value = "/v1/content/email", method = RequestMethod.GET)
	public ResponseEntity<List<EmailContentDTO>> findByClient(@RequestParam(value = "client") String client) {

		List<EmailContent> emailContentList = emailContentService.getByClient(client);
		List<EmailContentDTO> emailContentDTOList = emailContentMapper.toDTOList(emailContentList);

		return new ResponseEntity<>(emailContentDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email/programs/{program}", method = RequestMethod.GET)
	public ResponseEntity<List<EmailContentDTO>> findByProgram(@PathVariable("program") String program, @RequestParam(value = "client") String client) {

		List<EmailContent> emailContentList = emailContentService.getByProgram(client, program);
		List<EmailContentDTO> emailContentDTOList = emailContentMapper.toDTOList(emailContentList);

		return new ResponseEntity<>(emailContentDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email/components/{component}", method = RequestMethod.GET)
	public ResponseEntity<List<EmailContentDTO>> findByComponent(@PathVariable("component") String component, @RequestParam(value = "client") String client,
			@RequestParam(value = "program", defaultValue = "") String program) {

		List<EmailContent> emailContentList = emailContentService.findByComponent(component, client, program);
		List<EmailContentDTO> emailContentDTOList = emailContentMapper.toDTOList(emailContentList);

		return new ResponseEntity<>(emailContentDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email/components/{component}/names/{name}", method = RequestMethod.GET)
	public ResponseEntity<EmailContentDTO> findByName(@PathVariable("component") String component, @PathVariable("name") String name, @RequestParam(value = "client") String client,
			@RequestParam(value = "program", defaultValue = "") String program, @RequestParam(value = "cascade", required = false, defaultValue = "true") boolean cascade) {

		EmailContent emailContent = emailContentService.findByName(component, name, client, program, cascade);
		EmailContentDTO emailContentDTO = emailContentMapper.toDTO(emailContent);

		return new ResponseEntity<>(emailContentDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email", method = RequestMethod.POST)
	public ResponseEntity<EmailContentDTO> saveContent(@RequestBody EmailContentDTO emailDTO) throws Exception {

		if (!sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID).equals(RestConstants.ADMIN_CLIENTID)) {
			throw new UnauthorizedException(RestConstants.UNAUTHORIZED_FOR_MONGO);
		}

		EmailContent email = emailContentMapper.toDomain(emailDTO);
		EmailContent newEmail = emailContentService.addEmail(email);
		EmailContentDTO newEmailDTO = emailContentMapper.toDTO(newEmail);

		return new ResponseEntity<>(newEmailDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateContent(@RequestBody EmailContentDTO emailDTO, @PathVariable("id") String id) throws Exception {

		if (!sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID).equals(RestConstants.ADMIN_CLIENTID)) {
			throw new UnauthorizedException(RestConstants.UNAUTHORIZED_FOR_MONGO);
		}

		EmailContent email = emailContentMapper.toDomain(emailDTO);
		emailContentService.updateEmail(email);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteEmail(@PathVariable("id") String id) throws Exception {

		if (!sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID).equals(RestConstants.ADMIN_CLIENTID)) {
			throw new UnauthorizedException(RestConstants.UNAUTHORIZED_FOR_MONGO);
		}

		emailContentService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
