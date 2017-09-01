package com.edassist.service;

import java.util.List;

import com.edassist.models.dto.ServerStatusDTO;

public interface MonitoringService {

	List<ServerStatusDTO> checkV5Servers();

	List<ServerStatusDTO> checkV4Servers();

}
