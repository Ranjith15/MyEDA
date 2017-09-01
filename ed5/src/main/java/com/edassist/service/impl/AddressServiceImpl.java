package com.edassist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edassist.dao.AddressDao;
import com.edassist.dao.GenericDao;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.Address;
import com.edassist.service.AddressService;

@Service
public class AddressServiceImpl extends GenericServiceImpl<Address> implements AddressService {

	private AddressDao addressDao;

	public AddressServiceImpl() {
	}

	@Autowired
	public AddressServiceImpl(@Qualifier("addressDaoImpl") GenericDao<Address> genericDao) {
		super(genericDao);
		this.addressDao = (AddressDao) genericDao;
	}

	@Override
	public List<Long> findEmails(String emailId, Long clientId) throws ExceededMaxResultsException {
		return addressDao.findEmails(emailId, clientId);
	}

}