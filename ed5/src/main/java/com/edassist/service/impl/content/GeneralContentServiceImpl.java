package com.edassist.service.impl.content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.edassist.constants.Servers;
import com.edassist.models.contracts.content.GeneralContent;
import com.edassist.models.contracts.content.ProviderDocument;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.SessionService;
import com.edassist.service.content.GeneralContentService;
import com.edassist.utils.CommonUtil;

@Service
public class GeneralContentServiceImpl implements GeneralContentService {

	private final SessionService sessionService;

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	public GeneralContentServiceImpl(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@Override
	public List<GeneralContent> findByComponent(String component, String client, String program, boolean textOnly, Date signedDate) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder;
		if (program == null) {
			builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("general").pathSegment("components").pathSegment(component).queryParam("client", client)
					.queryParam("textOnly", textOnly).queryParam("signedDate", signedDate);
		} else {
			builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("general").pathSegment("components").pathSegment(component).queryParam("client", client)
					.queryParam("program", program).queryParam("textOnly", textOnly).queryParam("signedDate", signedDate);
		}

		GeneralContent[] contentOutput = restTemplate.getForObject(builder.build().encode().toUri(), GeneralContent[].class);
		List<GeneralContent> contentList = new ArrayList<>(Arrays.asList(contentOutput));

		return contentList;
	}

	@Override
	public List<GeneralContent> findByName(String component, String name, String client, String program, boolean textOnly, boolean cascade) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("general").pathSegment("components").pathSegment(component).pathSegment("names")
				.pathSegment(name).queryParam("client", client).queryParam("program", program).queryParam("textOnly", textOnly).queryParam("cascade", cascade);
		GeneralContent[] contentOutput = restTemplate.getForObject(builder.build().encode().toUri(), GeneralContent[].class);
		List<GeneralContent> contentList = new ArrayList<>(Arrays.asList(contentOutput));

		return contentList;
	}

	@Override
	public List<ProviderDocument> findProviderDocuments(String providerType, Long providerId) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("provider-documents").pathSegment("types").pathSegment(providerType)
				.pathSegment("providers").pathSegment(providerId.toString());

		ProviderDocument[] providerDocuments = restTemplate.getForObject(builder.build().encode().toUri(), ProviderDocument[].class);
		List<ProviderDocument> providerDocumentList = new ArrayList<>(Arrays.asList(providerDocuments));

		return providerDocumentList;
	}

	@Override
	public GeneralContent insertContent(GeneralContent content) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("general");

		content.setCreatedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
		GeneralContent insertedContent = restTemplate.postForObject(builder.build().encode().toUri(), content, GeneralContent.class);
		return insertedContent;
	}

	@Override
	public void updateContent(GeneralContent content) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("general").pathSegment(content.getId());

		content.setModifiedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
		restTemplate.put(builder.build().encode().toUri(), content);
	}

	@Override
	public void deleteById(String id) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("general").pathSegment(id);

		restTemplate.delete(builder.build().encode().toUri());
	}

	@Override
	public List<GeneralContent> getByClient(String client) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("general").pathSegment("clients").pathSegment(client);
		GeneralContent[] result = restTemplate.getForObject(builder.build().encode().toUri(), GeneralContent[].class);

		return new ArrayList<>(Arrays.asList(result));
	}

	@Override
	public List<GeneralContent> getByProgram(String client, String program, boolean textOnly) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("general").pathSegment("programs").pathSegment(program).queryParam("client", client)
				.queryParam("textOnly", textOnly);
		GeneralContent[] result = restTemplate.getForObject(builder.build().encode().toUri(), GeneralContent[].class);

		return new ArrayList<>(Arrays.asList(result));
	}
}
