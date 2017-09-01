package com.edassist.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.edassist.constants.Servers;
import com.edassist.models.dto.ServerStatusDTO;
import com.edassist.service.MonitoringService;
import com.edassist.utils.CommonUtil;
import com.google.common.base.Stopwatch;

@Service
public class MonitoringServiceImpl implements MonitoringService {

	@Override
	public List<ServerStatusDTO> checkV5Servers() {
		List<ServerStatusDTO> serverStatusDTOs = new ArrayList<>();
		serverStatusDTOs.addAll(this.retrieveServerStatus(Servers.SERVERS_5_WSS, Servers.PLATFORM_5_URL, "https", true));
		serverStatusDTOs.addAll(this.retrieveServerStatus(Servers.SERVERS_5_EMAIL, Servers.PLATFORM_5_URL, "http", true));
		serverStatusDTOs.addAll(this.retrieveServerStatus(Servers.SERVERS_5_RULES, Servers.PLATFORM_4_URL, "http", true));
		serverStatusDTOs.addAll(this.retrieveServerStatus(Servers.SERVERS_5_CONTENT_SVC, Servers.PLATFORM_5_URL, "http", true));
		serverStatusDTOs.addAll(this.retrieveServerStatus(Servers.SERVERS_5_WEB, Servers.PLATFORM_5_URL, "http", false));

		return serverStatusDTOs;
	}

	@Override
	public List<ServerStatusDTO> checkV4Servers() {
		List<ServerStatusDTO> serverStatusDTOs = new ArrayList<>();
		serverStatusDTOs.addAll(this.retrieveServerStatus(Servers.SERVERS_4_WEB, Servers.PLATFORM_4_URL, "https", false));
		serverStatusDTOs.addAll(this.retrieveServerStatus(Servers.SERVERS_4_CONTENT_SVC, Servers.PLATFORM_4_URL, "http", true));
		serverStatusDTOs.addAll(this.retrieveServerStatus(Servers.SERVERS_4_CDN, Servers.PLATFORM_4_URL, "https", false));

		return serverStatusDTOs;
	}

	private List<ServerStatusDTO> retrieveServerStatus(String purpose, String platform, String protocol, boolean isRestEndpoint) {
		List<ServerStatusDTO> serverStatusDTOs = new ArrayList<>();

		String servers = CommonUtil.getHotProperty(purpose);
		List<String> serverList = Arrays.asList(CommonUtil.convertStringToArrayString(servers));

		for (String server : serverList) {
			String url = this.buildHealthPageUrl(server, platform, protocol, isRestEndpoint);
			serverStatusDTOs.add(this.getServerStatus(url, server, purpose, isRestEndpoint));
		}

		return serverStatusDTOs;
	}

	private String buildHealthPageUrl(String server, String platform, String protocol, boolean isRestEndpoint) {
		String url = protocol + "://" + server + "/";

		if (isRestEndpoint) {
			url += platform + "/api/v1/monitoring/health";
		} else {
			url += platform.equals(Servers.PLATFORM_5_URL) ? "monitoring/health.html" : "TAMS4Web/public/health.html";
		}

		return url;
	}

	private ServerStatusDTO getServerStatus(String url, String server, String purpose, boolean isRestEndpoint) {
		RestTemplate restTemplate = new RestTemplate();
		ServerStatusDTO serverStatusDTO = new ServerStatusDTO();
		serverStatusDTO.setName(server);
		serverStatusDTO.setPurpose(purpose);
		serverStatusDTO.setRestEndpoint(isRestEndpoint);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		Stopwatch stopwatch = Stopwatch.createUnstarted();
		try {
			stopwatch.start();
			String status = restTemplate.getForObject(builder.build().encode().toUri(), String.class);
			stopwatch.stop();
			serverStatusDTO.setResponseTime(stopwatch.elapsed(TimeUnit.MILLISECONDS));
			serverStatusDTO.setStatus(status.contains("server is up") ? true : false);
		} catch (RestClientException e) {
			stopwatch.stop();
			serverStatusDTO.setResponseTime(stopwatch.elapsed(TimeUnit.MILLISECONDS));
			serverStatusDTO.setStatus(false);
		}

		return serverStatusDTO;
	}

}
