package com.edassist.service;

import com.edassist.models.domain.ApplicationDocuments;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ApplicationDocumentsService extends GenericService<ApplicationDocuments> {

	ApplicationDocuments findApplicationDocumentByIdAndApplication(Long applicationDocumentId, Long applicationId);

	void saveOrUpdate(ApplicationDocuments entity);

	Map<String, Object> saveUploadedDocument(Long applicationID, MultipartFile multipartFile, String documentType, String studentID, Long[] courseId, Long[] gradeId, String applicationComment);
}
