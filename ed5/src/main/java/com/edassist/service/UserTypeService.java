package com.edassist.service;

import com.edassist.models.domain.UserType;

public interface UserTypeService extends GenericService<UserType> {

	boolean verfiyLoggedInUserType(int userTypeId);

	boolean isUserInternalAdmin();

	boolean isUserInternalAdmin(int userTypeId);

}
