package com.edassist.controller.content;

import com.edassist.models.contracts.content.ProviderDocument;
import com.edassist.models.domain.Client;
import com.edassist.models.domain.EducationalProviders;
import com.edassist.models.dto.content.ProviderDocumentDTO;
import com.edassist.models.mappers.content.ProviderDocumentMapper;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.ClientService;
import com.edassist.service.EducationalProvidersService;
import com.edassist.service.SessionService;
import com.edassist.service.content.GeneralContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProviderDocumentsContentController {

	private final GeneralContentService generalContentService;
	private final ClientService clientService;
	private final SessionService sessionService;
	private final EducationalProvidersService educationalProvidersService;
	private final ProviderDocumentMapper providerDocumentMapper;

	@Autowired
	public ProviderDocumentsContentController(GeneralContentService generalContentService, ClientService clientService, SessionService sessionService,
			EducationalProvidersService educationalProvidersService, ProviderDocumentMapper providerDocumentMapper) {
		this.generalContentService = generalContentService;
		this.clientService = clientService;
		this.sessionService = sessionService;
		this.educationalProvidersService = educationalProvidersService;
		this.providerDocumentMapper = providerDocumentMapper;
	}

	@RequestMapping(value = "/v1/content/provider-documents", method = RequestMethod.GET)
	public ResponseEntity<List<ProviderDocumentDTO>> getProviderDocumentsFromCMS(@RequestParam(value = "educationalProviderId", defaultValue = "") Long educationalProviderId) {

		Client client = clientService.findById(sessionService.getClaimAsLong(JWTTokenClaims.CLIENT_ID));
		EducationalProviders primaryProvider = this.educationalProvidersService.findById(educationalProviderId);
		String providerDocumentType = "non-EEN";
		if (client.isEnhancedEdAssistNetwork() && primaryProvider.isEnhancedEdAssistNetwork()) {
			providerDocumentType = "EEN";
		}

		List<ProviderDocument> providerDocumentsList = generalContentService.findProviderDocuments(providerDocumentType, educationalProviderId);
		if (providerDocumentsList != null && providerDocumentsList.size() < 1) {
			if ("EEN".equals(providerDocumentType)) {
				providerDocumentsList = generalContentService.findProviderDocuments("non-EEN", educationalProviderId);
			}
			if (providerDocumentsList != null && providerDocumentsList.size() < 1) {
				if (!primaryProvider.getParentCode().equals(primaryProvider.getProviderCode())) {
					List<EducationalProviders> parentProviders = this.educationalProvidersService.findByParam("providerCode", primaryProvider.getParentCode());
					primaryProvider = parentProviders.get(0);
					providerDocumentsList = generalContentService.findProviderDocuments(providerDocumentType, primaryProvider.getEducationalProviderId());
				}
				if (providerDocumentsList != null && providerDocumentsList.size() < 1) {
					if ("EEN".equals(providerDocumentType)) {
						providerDocumentsList = generalContentService.findProviderDocuments("non-EEN", primaryProvider.getEducationalProviderId());
					}
				}
			}
		}
		List<ProviderDocumentDTO> providerDocumentDtoList = providerDocumentMapper.toDTOList(providerDocumentsList);

		return new ResponseEntity<>(providerDocumentDtoList, HttpStatus.OK);
	}
}
