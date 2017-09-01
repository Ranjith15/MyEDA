package com.edassist.service;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.Client;
import com.edassist.models.domain.Program;

import java.util.List;

public interface ClientService extends GenericService<Client> {

	List<Client> search(String clientName, Long clientTypeID) throws ExceededMaxResultsException;

	List<Client> findEd5Clients();

	List<Client> findEd4Clients();

	List<Program> getProgramsByClient(Long clientId);
}
