package com.edassist.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edassist.dao.GenericDao;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.ApplicationStatus;
import com.edassist.service.ApplicationStatusService;

@Service
public class ApplicationStatusServiceImpl extends GenericServiceImpl<ApplicationStatus> implements ApplicationStatusService {

	public ApplicationStatusServiceImpl() {
	}

	@Autowired
	public ApplicationStatusServiceImpl(@Qualifier("applicationStatusDao") GenericDao<ApplicationStatus> genericDao) {
		super(genericDao);
	}

	private static Map<Long, ApplicationStatus> statusCache;

	@Override
	public List<ApplicationStatus> findAll() throws ExceededMaxResultsException {
		return findByParam("active", "1", "applicationStatus");
	}

	@Override
	public ApplicationStatus findByCode(Long statusCode) {
		if (statusCache == null) {
			statusCache = new HashMap<Long, ApplicationStatus>();
		}

		if (!statusCache.containsKey(statusCode)) {
			List<ApplicationStatus> statusList;
			try {
				statusList = this.findByParam("applicationStatusCode", statusCode);

				if (statusList == null) {
					throw new BadRequestException("Could not find a status matching status code: [" + statusCode + "]");
				}

				if (statusList.size() > 1) {
					throw new BadRequestException("Expected only 1 ApplicationStatus to have a code of " + statusCode + ", found: [" + statusList.size() + "]");
				}

				statusCache.put(statusCode, statusList.get(0));
			} catch (ExceededMaxResultsException e) {
				throw new BadRequestException("Expected only 1 ApplicationStatus to have a code of " + statusCode + "");
			}
		}

		return statusCache.get(statusCode);
	}
}
