package com.edassist.service.impl.content;

import com.edassist.constants.Servers;
import com.edassist.models.contracts.content.FileContent;
import com.edassist.service.content.FileContentService;
import com.edassist.utils.CommonUtil;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FileContentServiceImpl implements FileContentService {

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public ByteArrayResource retrieveFileById(String id) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("file").pathSegment(id);
		return restTemplate.getForObject(builder.build().encode().toUri(), ByteArrayResource.class);
	}

	@Override
	public Boolean saveOrUpdateFileById(FileContent content) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("file");
		return restTemplate.postForObject(builder.build().encode().toUri(), content, Boolean.class);
	}

}
