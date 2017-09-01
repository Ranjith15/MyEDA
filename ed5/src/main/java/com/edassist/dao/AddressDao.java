package com.edassist.dao;

import java.util.List;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.Address;

public interface AddressDao extends GenericDao<Address> {

	List<Long> findEmails(String emailId, Long clientID) throws ExceededMaxResultsException;

}