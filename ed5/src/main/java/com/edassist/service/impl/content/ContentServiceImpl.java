package com.edassist.service.impl.content;

import com.edassist.constants.Servers;
import com.edassist.service.content.ContentService;
import com.edassist.utils.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public List<String> getComponentKeys(String type) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment(type).pathSegment("components").pathSegment("keys");
		String[] result = restTemplate.getForObject(builder.build().encode().toUri(), String[].class);
		return new ArrayList<>(Arrays.asList(result));
	}

	@Override
	public List<String> getNamesKeys(String type, String component) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment(type).pathSegment("components").pathSegment(component).pathSegment("names")
				.pathSegment("keys");
		String[] result = restTemplate.getForObject(builder.build().encode().toUri(), String[].class);
		return new ArrayList<>(Arrays.asList(result));
	}

	@Override
	public List<String> getCollectionKeys(String type) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment(type).pathSegment("keys");
		String[] result = restTemplate.getForObject(builder.build().encode().toUri(), String[].class);
		return new ArrayList<>(Arrays.asList(result));
	}

	@Override
	public void migrateClient(String clientId) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content-migration").pathSegment("migrate-client").queryParam("clientId", clientId);
		restTemplate.postForObject(builder.build().encode().toUri(), null, Void.class);
	}

	@Override
	public void propagateClient(String clientId) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content-propagation").pathSegment("propagate-client").queryParam("clientId", clientId);
		restTemplate.postForObject(builder.build().encode().toUri(), null, Void.class);
	}

	@Override
	public Long needsPropagatedContentSize(String clientId) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("clients").pathSegment(clientId).pathSegment("propagation-client-content-size");
		Long needsPropagationContentSize = restTemplate.getForObject(builder.build().encode().toUri(), Long.class);

		return needsPropagationContentSize;
	}
}
