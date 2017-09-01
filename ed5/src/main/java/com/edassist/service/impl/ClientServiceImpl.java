package com.edassist.service.impl;

import com.edassist.dao.ClientDao;
import com.edassist.dao.GenericDao;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.Client;
import com.edassist.models.domain.Program;
import com.edassist.models.domain.ProgramType;
import com.edassist.service.ClientService;
import com.edassist.service.GenericService;
import com.edassist.service.ProgramService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl extends GenericServiceImpl<Client> implements ClientService {

	private static Logger log = Logger.getLogger(ClientServiceImpl.class);

	@Autowired
	private ProgramService programService;

	@Autowired
	private GenericService<ProgramType> programTypeService;

	private ClientDao clientDao;

	public ClientServiceImpl() {
	}

	@Autowired
	public ClientServiceImpl(@Qualifier("clientDaoImpl") GenericDao<Client> genericDao) {
		super(genericDao);
		this.clientDao = (ClientDao) genericDao;
	}

	// TAM-2222
	@Override
	public List<Client> search(String clientName, Long clientTypeID) throws ExceededMaxResultsException {
		return clientDao.search(clientName, clientTypeID);
	}

	@Override
	public List<Client> findEd5Clients() {
		return clientDao.findEd5Clients();
	}

	@Override
	public List<Client> findEd4Clients() {
		return clientDao.findEd4Clients();
	}

	@Override
	public List<Program> getProgramsByClient(Long clientId) {
		return clientDao.getProgramsByClient(clientId);
	}
}
