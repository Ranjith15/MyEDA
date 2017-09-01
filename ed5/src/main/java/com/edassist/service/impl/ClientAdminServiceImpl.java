package com.edassist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edassist.dao.ClientAdminDao;
import com.edassist.dao.GenericDao;
import com.edassist.models.domain.PaginationResult;
import com.edassist.models.domain.ThinParticipantSearch;
import com.edassist.service.ClientAdminService;

@Service
public class ClientAdminServiceImpl extends GenericServiceImpl<ThinParticipantSearch> implements ClientAdminService {

	private ClientAdminDao clientAdminDao;

	public ClientAdminServiceImpl() {
	}

	@Autowired
	public ClientAdminServiceImpl(@Qualifier("clientAdminDaoImpl") GenericDao<ThinParticipantSearch> genericDao) {
		super(genericDao);
		this.clientAdminDao = (ClientAdminDao) genericDao;
	}

	@Override
	public PaginationResult<ThinParticipantSearch> getApplicationsByClient(String firstName, String lastName, String employeeId, Long applicationNumber, Long applicationStatus, String benefitPeriod,
			Long clientId, String sortBy, int index, int recordsPerPage) {
		PaginationResult<ThinParticipantSearch> applications = clientAdminDao.getApplicationsByClient(firstName, lastName, employeeId, applicationNumber, applicationStatus, benefitPeriod, clientId,
				sortBy, index, recordsPerPage);
		return applications;
	}

}
