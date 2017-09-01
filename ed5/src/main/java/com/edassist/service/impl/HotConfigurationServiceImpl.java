package com.edassist.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.edassist.service.HotConfigurationService;
import com.edassist.utils.CommonUtil;

@Service
public class HotConfigurationServiceImpl implements HotConfigurationService {

	@Override
	@PostConstruct
	public void reloadHotConfiguration() throws Exception {
		CommonUtil.loadHotProperties();
	}
}
