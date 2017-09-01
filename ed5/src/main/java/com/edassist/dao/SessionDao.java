package com.edassist.dao;

import com.edassist.models.domain.User;

public interface SessionDao extends GenericDao<User> {

	void testConnection() throws Exception;
}
