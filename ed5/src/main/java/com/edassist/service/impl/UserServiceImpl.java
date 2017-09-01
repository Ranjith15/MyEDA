package com.edassist.service.impl;

import com.edassist.constants.ApplicationConstants;
import com.edassist.dao.GenericDao;
import com.edassist.dao.UserDao;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.ThinParticipantSearch;
import com.edassist.models.domain.User;
import com.edassist.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

	static Logger log = Logger.getLogger(UserServiceImpl.class);

	private UserDao userDao;

	public UserServiceImpl() {
	}

	@Autowired
	public UserServiceImpl(@Qualifier("userDaoImpl") GenericDao<User> genericDao) {
		super(genericDao);
		this.userDao = (UserDao) genericDao;
	}

	@Override
	public List<ThinParticipantSearch> searchUser(String employeeId, String uniqueId, String firstName, String lastName, Long clientId) {
		return userDao.findThinUserByClient(employeeId, uniqueId, firstName, lastName, clientId);
	}

	@Override
	public Map<String, Object> authenticate(String username, String password, Long clientId) throws DataAccessException {

		Map<String, Object> retMap = new HashMap();

		User user = retrieveUser(username, clientId);

		/*
		 * Return null in case of invalid user status to prevent malicious guessing of usernames
		 */
		if (user == null) {
			retMap.put("user", user);
			return null;
		}

		if (user.getParticipantID() != null && user.getParticipantID().getClient() != null && !user.getParticipantID().getClient().isActiveClient()) {
			// If the user has a Client associated then check status of Client
			retMap.put("user", null);
			return null;
		}

		if (user.getParticipantID() != null && !user.getParticipantID().getActiveIndicator()) {
			// this participant is not allowed to log into the web site
			retMap.put("user", null);
			return null;
		}

		// Do a string comparison to match password credential
		if (StringUtils.equals(user.getPassword(), password)) {
			if (user.isUserLocked() == true) {
				Date currDate = new Date();
				Date lockedDate = user.getUserLockedTimeStamp();
				long dateDiff;
				try {
					dateDiff = (currDate.getTime() - lockedDate.getTime()) / 60000;
				} catch (Exception e) {
					dateDiff = 0;
				}
				// Good password entered after 30 mins from lockout allow user in
				if (dateDiff > 30) {
					clearFailedLoginAttempts(user.getUserId());
					retMap.put("user", user);
					return retMap;
				} else {
					retMap.put("isLocked", Boolean.TRUE);
					retMap.put("user", null);
					return retMap;
				}
			} else {
				if (user.getFailedLoginAttempts() != 0) {
					clearFailedLoginAttempts(user.getUserId());
				}
				retMap.put("user", user);
				return retMap;
			}

		} else {
			if (user.getFailedLoginAttempts() < 4) {
				incrementFailedLoginAttempt(user.getUserId());
			} else {
				if (user.isUserLocked() == true) {
					retMap.put("isLocked", Boolean.TRUE);
				} else {
					lockUserOnFailedLoginAttempt(user.getUserId());
					retMap.put("isLocked", Boolean.TRUE);
				}
			}

		}

		retMap.put("user", null);
		return retMap;
	}

	@Override
	public User retrieveUser(String username, Long clientId) throws DataAccessException {
		List<User> users = null;
		try {
			if (clientId != null && clientId.compareTo(ApplicationConstants.SECURITY_ADMIN_CLIENT_ID) == 0) {
				users = userDao.findInternalUser(username);
			} else {
				users = userDao.findUserByClient(username, clientId);
				// users = userDao.findByParam("userName", username);
			}
		} catch (ExceededMaxResultsException e) {
			log.error(e.getMessage());
			throw new BadRequestException("The list of users is too large for this userName [" + username + "]");
		}

		if (CollectionUtils.isEmpty(users)) {
			// No user found
			return null;
		}

		if (users.size() > 1) {
			// More than 1 user found with this username
			// TODO log this exception
			return null;
		}

		// At this point the list contains only the user we want
		return users.get(0);

	}

	private <Participant> void clearFailedLoginAttempts(Long userId) {
		User user = userDao.findById(userId);
		user.setFailedLoginAttempts(0);
		user.setUserLocked(false);
		user.setUserLockedTimeStamp(null);
		userDao.saveOrUpdate(user);
	}

	private <Participant> void lockUserOnFailedLoginAttempt(Long userId) {
		User user = userDao.findById(userId);
		user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
		user.setUserLocked(true);
		user.setUserLockedTimeStamp(new Date());
		userDao.saveOrUpdate(user);
	}

	private <Participant> void incrementFailedLoginAttempt(Long userId) {
		User user = userDao.findById(userId);
		user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
		userDao.saveOrUpdate(user);
	}
}
