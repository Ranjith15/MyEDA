package com.edassist.controller.content;

import com.edassist.models.contracts.content.EmailContent;
import com.edassist.models.contracts.content.FileContent;
import com.edassist.models.contracts.content.GeneralContent;
import com.edassist.models.dto.content.EmailContentDTO;
import com.edassist.models.dto.content.GeneralContentDTO;
import com.edassist.models.mappers.content.EmailContentMapper;
import com.edassist.models.mappers.content.GeneralContentMapper;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.SessionService;
import com.edassist.service.content.ContentService;
import com.edassist.service.content.FileContentService;
import com.edassist.service.content.PropagationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PropagationController {

	private final PropagationService propagationService;
	private final GeneralContentMapper generalContentMapper;
	private final EmailContentMapper emailContentMapper;
	private final FileContentService fileContentService;
	private final SessionService sessionService;
	private final ContentService contentService;

	@Autowired
	public PropagationController(PropagationService propagationService, GeneralContentMapper generalContentMapper, EmailContentMapper emailContentMapper, FileContentService fileContentService, SessionService sessionService, ContentService contentService) {
		this.propagationService = propagationService;
		this.generalContentMapper = generalContentMapper;
		this.emailContentMapper = emailContentMapper;
		this.fileContentService = fileContentService;
		this.sessionService = sessionService;
		this.contentService = contentService;
	}

	@RequestMapping(value = "/v1/content/general/propagate-content", method = RequestMethod.POST)
	public ResponseEntity<Void> propagateGeneralContent(@RequestBody GeneralContentDTO generalContentDto) {
		generalContentDto.setPublishedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
		GeneralContent generalContent = generalContentMapper.toDomain(generalContentDto);
		propagationService.propagateGeneralContent(generalContent);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email/propagate-content", method = RequestMethod.POST)
	public ResponseEntity<Void> propagateEmailContent(@RequestBody EmailContentDTO emailContentDto) {
		emailContentDto.setPublishedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
		EmailContent emailContent = emailContentMapper.toDomain(emailContentDto);
		propagationService.propagateEmailContent(emailContent);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/file/propagate-content", method = RequestMethod.POST)
	public ResponseEntity<Void> propagateFileContent(@RequestBody GeneralContentDTO generalContentDto) {
		ByteArrayResource byteArrayResource = fileContentService.retrieveFileById(generalContentDto.getFileId());
		FileContent fileContent = new FileContent();
		fileContent.setGeneralContentId(generalContentDto.getId());
		fileContent.setFileName(generalContentDto.getFileName());
		fileContent.setFile(byteArrayResource.getByteArray());
		fileContent.setPublishedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
		propagationService.propagateFileContent(fileContent);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/client/propagate-content", method = RequestMethod.POST)
	public ResponseEntity<Void> propagateClientContent(@RequestBody String clientId) {
		contentService.propagateClient(clientId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}


