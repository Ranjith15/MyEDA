package com.edassist.service;

import java.util.List;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.Address;

public interface AddressService extends GenericService<Address> {

	List<Long> findEmails(String emailId, Long clientId) throws ExceededMaxResultsException;
}