package com.edassist.dao;

import java.util.List;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.*;

public interface UserDao extends GenericDao<User> {

	List<User> findUserByClient(String userName, Long clientId);

	List<ThinParticipantSearch> findThinUserByClient(String employeeId, String uniqueId, String firstName, String lastName, Long clientId);

	List<User> findInternalUser(String userName) throws ExceededMaxResultsException;

}
