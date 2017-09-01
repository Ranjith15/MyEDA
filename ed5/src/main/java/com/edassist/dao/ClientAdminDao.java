package com.edassist.dao;

import com.edassist.models.domain.PaginationResult;
import com.edassist.models.domain.ThinParticipantSearch;

public interface ClientAdminDao extends GenericDao<ThinParticipantSearch> {

	PaginationResult<ThinParticipantSearch> getApplicationsByClient(String firstName, String lastName, String employeeId, Long applicationNumber, Long applicationStatus, String benefitPeriod,
			Long clientId, String sortBy, int index, int recordsPerPage);

}
