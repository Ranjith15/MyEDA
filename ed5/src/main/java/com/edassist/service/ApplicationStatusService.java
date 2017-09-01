package com.edassist.service;

import com.edassist.models.domain.ApplicationStatus;

public interface ApplicationStatusService extends GenericService<ApplicationStatus> {

	ApplicationStatus findByCode(Long statusCode);
}
