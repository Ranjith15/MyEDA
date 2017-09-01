package com.edassist.dao;

import com.edassist.models.domain.ApplicationDocuments;

import java.util.Date;

public interface ApplicationDocumentsDao extends GenericDao<ApplicationDocuments> {

	ApplicationDocuments findApplicationDocumentByIdAndApplication(Long applicationDocumentId, Long applicationId);

	void driveAddApplicationDocumentProc(Long applicationNumber, Long documentTypeId, String documentName, boolean autoInxesed, boolean multipleParticipant, String url, String DMSDocumentID,
			Date dateReceived) throws Exception;
}