package com.edassist.controller.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

import com.edassist.constants.ClientConstants;
import com.edassist.constants.ContentConstants;
import com.edassist.constants.RestConstants;
import com.edassist.exception.ForbiddenException;
import com.edassist.exception.NotFoundException;
import com.edassist.exception.UnauthorizedException;
import com.edassist.models.contracts.content.GeneralContent;
import com.edassist.models.domain.Client;
import com.edassist.models.domain.Program;
import com.edassist.models.dto.ClientLoginDetailsDTO;
import com.edassist.models.dto.LoginContentDTO;
import com.edassist.models.dto.ProgramDetailsDTO;
import com.edassist.models.dto.content.GeneralContentDTO;
import com.edassist.models.mappers.ClientLoginDetailsMapper;
import com.edassist.models.mappers.content.GeneralContentMapper;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.ClientService;
import com.edassist.service.ProgramService;
import com.edassist.service.SessionService;
import com.edassist.service.content.GeneralContentService;
import com.edassist.utils.CommonUtil;

@RestController
public class GeneralContentController {

	private final ClientLoginDetailsMapper clientLoginDetailsMapper;
	private final GeneralContentMapper generalContentMapper;
	private final GeneralContentService generalContentService;
	private final ClientService clientService;
	private final ProgramService programService;
	private final SessionService sessionService;

	@Autowired
	public GeneralContentController(ClientLoginDetailsMapper clientLoginDetailsMapper, GeneralContentMapper generalContentMapper, GeneralContentService generalContentService,
			ClientService clientService, ProgramService programService, SessionService sessionService) {
		this.clientLoginDetailsMapper = clientLoginDetailsMapper;
		this.generalContentMapper = generalContentMapper;
		this.generalContentService = generalContentService;
		this.clientService = clientService;
		this.programService = programService;
		this.sessionService = sessionService;
	}

	@RequestMapping(value = "/v1/content/general", method = RequestMethod.GET)
	public ResponseEntity<List<GeneralContentDTO>> findByClient(@RequestParam(value = "client", defaultValue = "global") String client) {

		List<GeneralContent> generalContentList = generalContentService.getByClient(client);

		List<GeneralContentDTO> generalContentDTOList = generalContentMapper.toDTOList(generalContentList);

		return new ResponseEntity<>(generalContentDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general/programs/{program}", method = RequestMethod.GET)
	public ResponseEntity<List<GeneralContentDTO>> findByProgram(@PathVariable("program") String program, @RequestParam(value = "client", defaultValue = "global") String client,
			@RequestParam(value = "textOnly", defaultValue = "true") boolean textOnly) {

		List<GeneralContent> generalContentList = generalContentService.getByProgram(client, program, textOnly);

		List<GeneralContentDTO> generalContentDTOList = generalContentMapper.toDTOList(generalContentList);

		return new ResponseEntity<>(generalContentDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general/components/{component}", method = RequestMethod.GET)
	public ResponseEntity<List<GeneralContentDTO>> findByComponent(@PathVariable("component") String component, @RequestParam(value = "client", defaultValue = "global") String client,
			@RequestParam(value = "program", defaultValue = "") String program, @RequestParam(value = "textOnly", defaultValue = "true") boolean textOnly,
			@RequestParam(value = "signedDate", required = false) Long signedDateAsLong) {

		List<GeneralContent> generalContentList = generalContentService.findByComponent(component, client, program, textOnly, signedDateAsLong != null ? new Date(signedDateAsLong) : new Date());

		List<GeneralContentDTO> generalContentDTOList = generalContentMapper.toDTOList(generalContentList);

		return new ResponseEntity<>(generalContentDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general/components/{component}/names/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<GeneralContentDTO>> findByName(@PathVariable("component") String component, @PathVariable("name") String name, @RequestParam(value = "client") String client,
			@RequestParam(value = "program", defaultValue = "") String program, @RequestParam(value = "textOnly", defaultValue = "true") boolean textOnly,
			@RequestParam(value = "cascade", required = false, defaultValue = "true") boolean cascade) {

		List<GeneralContent> generalContentList = generalContentService.findByName(component, name, client, program, textOnly, cascade);
		List<GeneralContentDTO> generalContentDTOList = generalContentMapper.toDTOList(generalContentList);

		return new ResponseEntity<>(generalContentDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general/login/{clientIdentifier}", method = RequestMethod.GET)
	public ResponseEntity<LoginContentDTO> getLoginContent(@PathVariable(ClientConstants.CLIENT_IDENTIFIER) String clientIdentifier,
			@RequestParam(value = "textOnly", defaultValue = "false") boolean textOnly) {

		Boolean clientWebEnabled = sessionService.checkWebEnabled();
		List<Client> clientResult;
		List<GeneralContent> generalContentList;
		LoginContentDTO loginContentDTO = new LoginContentDTO();
		ClientLoginDetailsDTO clientLoginDetailsDTO = new ClientLoginDetailsDTO();

		if (clientWebEnabled) {
			clientResult = clientService.findByParam(ClientConstants.CLIENT_IDENTIFIER_WEB, clientIdentifier);
		} else {
			clientResult = clientService.findByParam(ClientConstants.CLIENT_IDENTIFIER_MOBILE, clientIdentifier);
		}

		if (clientIdentifier.equals("admin")) {
			generalContentList = generalContentService.findByComponent(ContentConstants.COMPONENT_LOGIN, ContentConstants.PROGRAM_CLIENT_GLOBAL, "", textOnly,
					new Date());
			// Manually setting client info for admin
			clientLoginDetailsDTO.setClientCode(RestConstants.ADMIN_CLIENTCODE);
			clientLoginDetailsDTO.setClientId(RestConstants.ADMIN_CLIENTID);
			clientLoginDetailsDTO.setClientName(RestConstants.ADMIN_CLIENTNAME);
			clientLoginDetailsDTO.setAuthCodeEndPoint(RestConstants.OAUTH_END_POINT);
			clientLoginDetailsDTO.setoAuthClientId(RestConstants.OAUTH_CLIENT_ID);
			loginContentDTO.setClientLoginDetails(clientLoginDetailsDTO);
		} else if (clientResult != null && clientResult.size() > 0) {
			Client client = clientResult.get(0);
			if (client.isMobileEnabled() || clientWebEnabled) {
				generalContentList = generalContentService.findByComponent(ContentConstants.COMPONENT_LOGIN, client.getClientId().toString(), ContentConstants.PROGRAM_CLIENT_DEFAULT, textOnly, new Date());
				if (clientWebEnabled) {
					generalContentList.removeIf(c -> c.getName().equals(ContentConstants.NAME_CLIENT_LOGO_MOBILE));
				} else {
					generalContentList.removeIf(c -> c.getName().equals(ContentConstants.NAME_CLIENT_LOGO_WEB));
				}
				clientLoginDetailsDTO = clientLoginDetailsMapper.toDTO(client);
				clientLoginDetailsDTO.setAuthCodeEndPoint(RestConstants.OAUTH_END_POINT);
				clientLoginDetailsDTO.setoAuthClientId(RestConstants.OAUTH_CLIENT_ID);
			} else {
				throw new ForbiddenException(RestConstants.REST_MOBILE_FORBIDDEN);
			}
		} else {
			throw new NotFoundException("Client Code not found");
		}

		List<GeneralContentDTO> generalContentDTOList = generalContentMapper.toDTOList(generalContentList);
		loginContentDTO.setGeneralContentList(generalContentDTOList);
		loginContentDTO.setClientLoginDetails(clientLoginDetailsDTO);

		return new ResponseEntity<>(loginContentDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general/search/program-details", method = RequestMethod.GET)
	public ResponseEntity<List<ProgramDetailsDTO>> getProgramDetailsFromCMS(@RequestParam() String commaSeparatedProgramIds) {

		List<ProgramDetailsDTO> programDetailsListDTO = new ArrayList<>();
		String clientId = sessionService.getClaimAsString(JWTTokenClaims.CLIENT_ID);
		String[] programIds = new String[] {};

		if (commaSeparatedProgramIds != null) {
			programIds = CommonUtil.convertStringToArrayString(commaSeparatedProgramIds);
		}

		for (String programId : programIds) {
			ProgramDetailsDTO programDetailsDTO = new ProgramDetailsDTO();
			Program program = programService.findById(new Long(programId));

			List<GeneralContent> programDescription = generalContentService.findByComponent(ContentConstants.COMPONENT_PROGRAM_DESCRIPTION, clientId, programId, false, new Date());
			List<GeneralContent> policyDocuments = generalContentService.findByComponent(ContentConstants.COMPONENT_CLIENT_DOCUMENTS, clientId, programId, true, new Date());
			programDetailsDTO.setProgramId(new Long(programId));
			programDetailsDTO.setProgramName(program.getProgramName());

			if (programDescription != null && (!programDescription.isEmpty()) && programDescription.size() > 0) {
				GeneralContentDTO programDescriptionDTO = generalContentMapper.toDTO(programDescription.get(0));
				programDetailsDTO.setProgramDescription(programDescriptionDTO);
			} else {
				programDetailsDTO.setProgramDescription(new GeneralContentDTO());
			}

			if (policyDocuments != null && (!policyDocuments.isEmpty())) {
				List<GeneralContentDTO> policyDocumentsDTO = generalContentMapper.toDTOList(policyDocuments);
				programDetailsDTO.setPolicyDocuments(policyDocumentsDTO);
			} else {
				programDetailsDTO.setPolicyDocuments(new ArrayList<>());
			}

			programDetailsListDTO.add(programDetailsDTO);
		}
		Collections.sort(programDetailsListDTO);
		return new ResponseEntity<>(programDetailsListDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general", method = RequestMethod.POST)
	public ResponseEntity<GeneralContentDTO> saveContent(@RequestBody GeneralContentDTO generalContentDTO) {

		if (!sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID).equals(RestConstants.ADMIN_CLIENTID)) {
			throw new UnauthorizedException(RestConstants.UNAUTHORIZED_FOR_MONGO);
		}

		GeneralContent generalContent = generalContentMapper.toDomain(generalContentDTO);
		GeneralContent insertedContent = generalContentService.insertContent(generalContent);
		GeneralContentDTO updatedContentDTO = generalContentMapper.toDTO(insertedContent);

		return new ResponseEntity<>(updatedContentDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateContent(@RequestBody GeneralContentDTO generalContentDTO, @PathVariable("id") String id) {

		if (!sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID).equals(RestConstants.ADMIN_CLIENTID)) {
			throw new UnauthorizedException(RestConstants.UNAUTHORIZED_FOR_MONGO);
		}

		GeneralContent content = generalContentMapper.toDomain(generalContentDTO);
		generalContentService.updateContent(content);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteContent(@PathVariable("id") String id) {

		if (!sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID).equals(RestConstants.ADMIN_CLIENTID)) {
			throw new UnauthorizedException(RestConstants.UNAUTHORIZED_FOR_MONGO);
		}

		generalContentService.deleteById(id);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
