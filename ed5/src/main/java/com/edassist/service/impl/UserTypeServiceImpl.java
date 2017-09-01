package com.edassist.service.impl;

import com.edassist.constants.UserTypeConstants;
import com.edassist.dao.GenericDao;
import com.edassist.models.domain.UserType;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.SessionService;
import com.edassist.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserTypeServiceImpl extends GenericServiceImpl<UserType> implements UserTypeService {

	@Autowired
	SessionService sessionService;

	public UserTypeServiceImpl() {
	}

	@Autowired
	public UserTypeServiceImpl(@Qualifier("userTypeDao") GenericDao<UserType> genericDao) {
		super(genericDao);
	}

	private List<Integer> externalUserTypeIds = new ArrayList<>(Arrays.asList(UserTypeConstants.PARTICIPANT, UserTypeConstants.SUPERVISOR, UserTypeConstants.CLIENT_ADMIN));

	@Override
	public boolean verfiyLoggedInUserType(int userTypeId) {
		return userTypeId == sessionService.getClaimAsInt(JWTTokenClaims.USER_TYPE_ID);
	}

	@Override
	public boolean isUserInternalAdmin() {
		int userTypeId = sessionService.getClaimAsInt(JWTTokenClaims.USER_TYPE_ID);
		return isUserInternalAdmin(userTypeId);
	}

	@Override
	public boolean isUserInternalAdmin(int userTypeId) {
		return !externalUserTypeIds.contains(userTypeId);
	}

}
