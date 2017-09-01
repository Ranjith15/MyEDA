package com.edassist.controller.content;

import com.edassist.models.contracts.content.EmailContent;
import com.edassist.models.contracts.content.GeneralContent;
import com.edassist.models.dto.ClientContentDTO;
import com.edassist.models.mappers.content.EmailContentMapper;
import com.edassist.models.mappers.content.GeneralContentMapper;
import com.edassist.service.content.ContentService;
import com.edassist.service.content.EmailContentService;
import com.edassist.service.content.GeneralContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContentController {

	private final ContentService contentService;
	private final EmailContentMapper emailContentMapper;
	private final EmailContentService emailContentService;
	private final GeneralContentMapper generalContentMapper;
	private final GeneralContentService generalContentService;

	@Autowired
	public ContentController(ContentService contentService, EmailContentMapper emailContentMapper, EmailContentService emailContentService, GeneralContentMapper generalContentMapper,
			GeneralContentService generalContentService) {
		this.contentService = contentService;
		this.emailContentMapper = emailContentMapper;
		this.emailContentService = emailContentService;
		this.generalContentMapper = generalContentMapper;
		this.generalContentService = generalContentService;
	}

	@RequestMapping(value = "/v1/content/clients/{client}", method = RequestMethod.GET)
	public ResponseEntity<ClientContentDTO> getClientContent(@PathVariable(value = "client") String client) {

		ClientContentDTO clientContentDTO = new ClientContentDTO();
		List<EmailContent> emails = emailContentService.getByClient(client);
		List<GeneralContent> contents = generalContentService.getByClient(client);
		clientContentDTO.setEmailContentDTOList(emailContentMapper.toDTOList(emails));
		clientContentDTO.setGeneralContentDTOList(generalContentMapper.toDTOList(contents));
		return new ResponseEntity<>(clientContentDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/programs/{program}", method = RequestMethod.GET)
	public ResponseEntity<ClientContentDTO> getProgramContent(@PathVariable(value = "program") String program, @RequestParam(value = "client", defaultValue = "global") String client,
			@RequestParam(value = "textOnly", defaultValue = "true") boolean textOnly) {

		ClientContentDTO clientContentDTO = new ClientContentDTO();
		List<EmailContent> emails = emailContentService.getByProgram(client, program);
		List<GeneralContent> contents = generalContentService.getByProgram(client, program, textOnly);
		clientContentDTO.setEmailContentDTOList(emailContentMapper.toDTOList(emails));
		clientContentDTO.setGeneralContentDTOList(generalContentMapper.toDTOList(contents));
		return new ResponseEntity<>(clientContentDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/components/keys", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getComponentKeys(@RequestParam(value = "type") String type) {

		List<String> keys = contentService.getComponentKeys(type);
		return new ResponseEntity<>(keys, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/components/{component}/names/keys", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getNamesKeys(@PathVariable("component") String component, @RequestParam(value = "type") String type) {
		List<String> keys = contentService.getNamesKeys(type, component);
		keys.removeIf(c -> c.equals("fs.chunks"));
		keys.removeIf(c -> c.equals("fs.files"));
		keys.removeIf(c -> c.equals("health"));
		keys.removeIf(c -> c.equals("providerDocument"));

		return new ResponseEntity<>(keys, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/collection/keys", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getCollectionKeys(@RequestParam(value = "type") String type) {
		List<String> keys = contentService.getCollectionKeys(type);
		keys.removeIf(c -> c.equals("fs.chunks"));
		keys.removeIf(c -> c.equals("fs.files"));
		keys.removeIf(c -> c.equals("health"));
		keys.removeIf(c -> c.equals("providerDocument"));
		return new ResponseEntity<>(keys, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/migrate", method = RequestMethod.POST)
	public ResponseEntity<Void> migrateClient(@RequestBody String clientId) {
		contentService.migrateClient(clientId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/clients/{client}/propagation-client-content-size", method = RequestMethod.GET)
	public ResponseEntity<Long> getNeedsPropagationContentSize(@PathVariable(value = "client") String client) {
		Long contentCount = contentService.needsPropagatedContentSize(client);
		return new ResponseEntity<>(contentCount, HttpStatus.OK);
	}
}
