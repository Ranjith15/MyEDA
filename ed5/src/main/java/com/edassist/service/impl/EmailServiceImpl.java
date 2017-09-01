package com.edassist.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.edassist.exception.BadRequestException;
import com.edassist.service.EmailService;
import com.edassist.utils.CommonUtil;

@Service
public class EmailServiceImpl implements EmailService {

	private RestTemplate restTemplate = new RestTemplate();

	public String getServiceHost() throws BadRequestException {
		String host = CommonUtil.getHotProperty(CommonUtil.EMAIL_SERVICE_HOST);
		if (host == null || host.isEmpty()) {
			throw new BadRequestException();
		}
		return "http://" + host + "/ed5/api/v1/services/email/";
	}

	@Override
	public void sendApplicationEventNotificationEmail(Long applicationId, Long emailContentId, String comment) {
		String url = this.getServiceHost() + "application-event-notification";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("applicationId", applicationId).queryParam("emailContentId", emailContentId).queryParam("comment", comment);

		restTemplate.postForEntity(builder.build().encode().toUri(), null, Void.class);
	}

	@Override
	public void sendPasswordResetEmail(Long userId, String newPassword, String email) {
		String url = this.getServiceHost() + "password-reset-email";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("userId", userId).queryParam("newPassword", newPassword).queryParam("email", email);

		restTemplate.postForEntity(builder.build().encode().toUri(), null, Void.class);
	}
}