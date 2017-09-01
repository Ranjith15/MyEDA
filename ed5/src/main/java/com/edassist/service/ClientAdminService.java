package com.edassist.service;

import com.edassist.models.domain.PaginationResult;
import com.edassist.models.domain.ThinParticipantSearch;

public interface ClientAdminService extends GenericService<ThinParticipantSearch> {

	PaginationResult<ThinParticipantSearch> getApplicationsByClient(String firstName, String lastName, String employeeId, Long applicationNumber, Long applicationStatus, String benefitPeriod,
			Long clientId, String sortBy, int index, int recordsPerPage);

}
