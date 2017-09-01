package com.edassist.controller;

import com.edassist.models.domain.Client;
import com.edassist.models.domain.Program;
import com.edassist.models.dto.ClientDTO;
import com.edassist.models.dto.ProgramDTO;
import com.edassist.models.mappers.ClientMapper;
import com.edassist.models.mappers.ProgramMapper;
import com.edassist.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {

	private final ClientService clientService;
	private final ClientMapper clientMapper;

	@Autowired
	public ClientController(ClientService clientService, ClientMapper clientMapper, ProgramMapper programMapper) {
		this.clientService = clientService;
		this.clientMapper = clientMapper;
		this.programMapper = programMapper;
	}

	@Autowired
	private ProgramMapper programMapper;

	@RequestMapping(value = "/v1/clients", method = RequestMethod.GET)
	public ResponseEntity<List<ClientDTO>> getClients() {

		List<Client> clients = clientService.findEd5Clients();
		List<ClientDTO> clientDTOs = clientMapper.toDTOList(clients);

		return new ResponseEntity<>(clientDTOs, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/clients/v4", method = RequestMethod.GET)
	public ResponseEntity<List<ClientDTO>> getEd4Clients() {

		List<Client> clients = clientService.findEd4Clients();
		List<ClientDTO> clientDTOs = clientMapper.toDTOList(clients);

		return new ResponseEntity<>(clientDTOs, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/clients/{clientId}/programs", method = RequestMethod.GET)
	public ResponseEntity<List<ProgramDTO>> getProgramsByClient(@PathVariable("clientId") Long clientId) {

		List<Program> programs = clientService.getProgramsByClient(clientId);
		List<ProgramDTO> programDTOs = programMapper.toDTOList(programs);

		return new ResponseEntity<>(programDTOs, HttpStatus.OK);
	}
}
