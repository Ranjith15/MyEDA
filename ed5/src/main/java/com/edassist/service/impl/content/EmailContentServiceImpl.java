package com.edassist.service.impl.content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.edassist.constants.Servers;
import com.edassist.models.contracts.content.EmailContent;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.SessionService;
import com.edassist.service.content.EmailContentService;
import com.edassist.utils.CommonUtil;

@Service
public class EmailContentServiceImpl implements EmailContentService {

	private final SessionService sessionService;

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	public EmailContentServiceImpl(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@Override
	public List<EmailContent> findByComponent(String component, String client, String program) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("email").pathSegment("components").pathSegment(component).queryParam("client", client)
				.queryParam("program", program);
		EmailContent[] result = restTemplate.getForObject(builder.build().encode().toUri(), EmailContent[].class);

		return new ArrayList<>(Arrays.asList(result));
	}

	@Override
	public EmailContent findByName(String component, String name, String client, String program, boolean cascade) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("email").pathSegment("components").pathSegment(component).pathSegment("names")
				.pathSegment(name).queryParam("client", client).queryParam("program", program).queryParam("cascade", cascade);

		return restTemplate.getForObject(builder.build().encode().toUri(), EmailContent.class);
	}

	@Override
	public EmailContent addEmail(EmailContent email) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("email");

		email.setCreatedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
		return restTemplate.postForObject(builder.build().encode().toUri(), email, EmailContent.class);
	}

	@Override
	public void updateEmail(EmailContent email) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("email").pathSegment(email.getId());
		email.setModifiedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
		restTemplate.put(builder.build().encode().toUri(), email);
	}

	@Override
	public void deleteById(String id) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("email").pathSegment(id);
		restTemplate.delete(builder.build().encode().toUri());
	}

	@Override
	public List<EmailContent> getByClient(String client) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("email").pathSegment("clients").pathSegment(client);
		EmailContent[] result = restTemplate.getForObject(builder.build().encode().toUri(), EmailContent[].class);

		return new ArrayList<>(Arrays.asList(result));
	}

	@Override
	public List<EmailContent> getByProgram(String client, String program) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("email").pathSegment("programs").pathSegment(program).queryParam("client", client);
		EmailContent[] result = restTemplate.getForObject(builder.build().encode().toUri(), EmailContent[].class);

		return new ArrayList<>(Arrays.asList(result));
	}

}
