package com.edassist.service;

import com.edassist.models.domain.ThinParticipantSearch;
import com.edassist.models.domain.User;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface UserService extends GenericService<User> {

	List<ThinParticipantSearch> searchUser(String employeeId, String uniqueId, String firstName, String lastName, Long clientId);

	Map<String, Object> authenticate(String username, String password, Long clientId) throws DataAccessException;

	User retrieveUser(String username, Long clientId) throws DataAccessException;
}
