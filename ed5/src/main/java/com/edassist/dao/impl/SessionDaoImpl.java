package com.edassist.dao.impl;

import com.edassist.dao.SessionDao;
import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.User;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
@Transactional
public class SessionDaoImpl extends GenericDaoImpl<User> implements SessionDao {

	@Override
	@Transactional
	public void testConnection() throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = this.getConnection();
			preparedStatement = connection.prepareCall(" SELECT 1 ");
			ResultSet result = preparedStatement.executeQuery();

			if (result.wasNull()) {
				throw new BadRequestException();
			}
		} catch (Exception ex) {
			throw new BadRequestException("health check failed to connect to the DB");
		} finally {
			this.closeDBResources(preparedStatement, connection);
		}

	}

}
