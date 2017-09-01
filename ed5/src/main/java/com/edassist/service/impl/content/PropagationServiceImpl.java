package com.edassist.service.impl.content;

import com.edassist.constants.Servers;
import com.edassist.models.contracts.content.EmailContent;
import com.edassist.models.contracts.content.FileContent;
import com.edassist.models.contracts.content.GeneralContent;
import com.edassist.service.content.PropagationService;
import com.edassist.utils.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PropagationServiceImpl implements PropagationService {

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public void propagateGeneralContent(GeneralContent generalContent) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("general").pathSegment("propagate-content");
		restTemplate.postForObject(builder.build().encode().toUri(), generalContent, GeneralContent.class);
	}

	@Override
	public void propagateEmailContent(EmailContent emailContent) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("email").pathSegment("propagate-content");
		restTemplate.postForObject(builder.build().encode().toUri(), emailContent, EmailContent.class);
	}

	@Override
	public void propagateFileContent(FileContent fileContent) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_SERVICE_HOST, Servers.PLATFORM_5_URL);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("file").pathSegment("propagate-content");
		restTemplate.postForObject(builder.build().encode().toUri(), fileContent, FileContent.class);
	}
}
