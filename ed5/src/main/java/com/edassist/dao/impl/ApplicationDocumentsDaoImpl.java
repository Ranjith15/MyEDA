package com.edassist.dao.impl;

import com.edassist.dao.ApplicationDocumentsDao;
import com.edassist.models.domain.ApplicationDocuments;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ApplicationDocumentsDaoImpl extends GenericDaoImpl<ApplicationDocuments> implements ApplicationDocumentsDao {

	static Logger logger = Logger.getLogger(ApplicationDocumentsDaoImpl.class);

	@Override
	public ApplicationDocuments findApplicationDocumentByIdAndApplication(Long applicationDocumentId, Long applicationId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ApplicationDocuments.class);

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).add(Restrictions.eq("applicationDocumentID", applicationDocumentId))
				.add(Restrictions.eq("applicationID", applicationId));

		List<ApplicationDocuments> documents = criteria.list();

		/*
		 * If the documents list is empty or has more than 1 result then we can't determine the proper application document
		 */
		if (documents.size() != 1) {
			return null;
		}

		return documents.get(0);
	}

	@Override
	public void driveAddApplicationDocumentProc(Long applicationNumber, Long documentTypeId, String documentName, boolean autoIndexed, boolean multipleParticipant, String url, String DMSDocumentID,
			Date dateReceived) throws Exception {
		Connection connection = this.getConnection();
		PreparedStatement preparedStatement = connection.prepareCall("{call AddApplicationDocument(?,?,?,?,?,?,?,?)}");

		preparedStatement.setLong(1, applicationNumber);
		preparedStatement.setLong(2, documentTypeId);
		preparedStatement.setString(3, documentName);
		preparedStatement.setBoolean(4, autoIndexed);
		preparedStatement.setBoolean(5, multipleParticipant);
		preparedStatement.setString(6, url);
		preparedStatement.setString(7, DMSDocumentID);
		preparedStatement.setDate(8, new java.sql.Date(dateReceived.getTime()));

		try {
			preparedStatement.executeUpdate();
		} finally {
			this.closeDBResources(preparedStatement, connection);
		}
		return;
	}
}
