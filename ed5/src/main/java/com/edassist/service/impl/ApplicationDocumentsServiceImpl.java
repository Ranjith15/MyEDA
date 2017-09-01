package com.edassist.service.impl;

import com.edassist.dao.ApplicationDocumentsDao;
import com.edassist.dao.GenericDao;
import com.edassist.models.domain.*;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.*;
import com.edassist.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ApplicationDocumentsServiceImpl extends GenericServiceImpl<ApplicationDocuments> implements ApplicationDocumentsService {

	static Logger logger = Logger.getLogger(ApplicationDocumentsServiceImpl.class);

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ApplicationCoursesService applicationCoursesService;

	@Autowired
	private GradesService gradesService;

	@Autowired
	private GenericService<DefaultDocuments> defaultDocumentsService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private GenericService<ApplicationComment> applicationCommentService;

	@Autowired
	private UserService userService;

	@Autowired
	private DmsService dmsService;

	private ApplicationDocumentsDao applicationDocumentsDao;

	public ApplicationDocumentsServiceImpl() {
	}

	@Autowired
	public ApplicationDocumentsServiceImpl(@Qualifier("applicationDocumentsDaoImpl") GenericDao<ApplicationDocuments> genericDao) {
		super(genericDao);
		this.applicationDocumentsDao = (ApplicationDocumentsDao) genericDao;
	}

	@Override
	public ApplicationDocuments findApplicationDocumentByIdAndApplication(Long applicationDocumentId, Long applicationId) {
		return applicationDocumentsDao.findApplicationDocumentByIdAndApplication(applicationDocumentId, applicationId);
	}

	@Override
	public void saveOrUpdate(ApplicationDocuments entity) {
		super.saveOrUpdate(entity);
	}

	static HashMap uploadedDocuments = new HashMap();

	@Override
	public Map<String, Object> saveUploadedDocument(Long applicationID, MultipartFile multipartFile, String documentType, String studentID, Long[] courseId, Long[] gradeId,
			String applicationComment) {
		int returnCode = 1;
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "failed");
		try {
			int clientId;
			String clientName;
			ArrayList<String> documentTypeIds = new ArrayList<String>();

			Application application = applicationService.findById(applicationID);
			Long applicationNumber = application.getApplicationNumber();
			if (studentID != null && !"".equals(studentID)) {
				application.setStudentID(studentID);
				applicationService.saveOrUpdate(application);

			}
			clientId = application.getBenefitPeriodID().getProgramID().getClientID().getClientId().intValue();
			clientName = application.getBenefitPeriodID().getProgramID().getClientID().getClientName();
			Long programTypeId = application.getBenefitPeriodID().getProgramID().getProgramTypeID().getProgramTypeID();
			String documentTypeName = "";

			if (StringUtils.isNotBlank(documentType) && documentType.equals("Grades")) {
				documentTypeName = "Grades";
				if (courseId != null && gradeId != null) {
					for (int i = 0; i < courseId.length; i++) {
						if (gradeId[i] != null && gradeId[i] != 0) {
							ApplicationCourses applicationCourses = applicationCoursesService.findById(courseId[i]);
							Grades grades = gradesService.findById(gradeId[i]);
							applicationCourses.setGradeID(grades);
							applicationCoursesService.saveOrUpdate(applicationCourses);
						}
					}
				}
			} else {
				documentTypeName = documentType;
			}

			// Find document Id
			String query = "from DefaultDocuments d where d.programTypeID.programTypeID = :programTypeId and d.documentName = :documentName";
			String[] paramNames = { "programTypeId", "documentName" };
			Object[] values = { programTypeId, documentTypeName };
			List<DefaultDocuments> documentsList = defaultDocumentsService.findByNamedParam(query, paramNames, values);
			for (DefaultDocuments document : documentsList) {
				documentTypeIds.add(document.getDefaultDocumentsID().toString());
			}

			// start saving the file

			// note: this may be a full path, depending on the web
			// client
			final String clientFileName = multipartFile.getOriginalFilename();
			// ensure simple file name is used
			final String simpleFileName = CommonUtil.getSimpleFileName(clientFileName);

			Date rightNow = new Date();
			boolean carryOnWithUpload = true;
			synchronized (uploadedDocuments) {
				if (uploadedDocuments == null) {
					uploadedDocuments = new HashMap();
				} else {
					Date mostRecentUploadDate = (Date) uploadedDocuments.get(applicationNumber.toString() + "-" + documentTypeIds.get(0));
					if (mostRecentUploadDate != null) {
						String interval = CommonUtil.getHotProperty(CommonUtil.HOT_DMS_UPLOAD_DUPLICATE_DOCUMENT_INTERVAL);
						if (interval == null || "".equals(interval)) {
							interval = "60";
						}
						int intervalInt = Integer.parseInt(interval) * 1000;
						if (mostRecentUploadDate.getTime() + intervalInt > rightNow.getTime()) {
							carryOnWithUpload = false;
						}
					}
				}
				uploadedDocuments.put(applicationNumber.toString() + "-" + documentTypeIds.get(0), rightNow);
			}

			if (carryOnWithUpload) {
				returnCode = dmsService.saveToDms(simpleFileName, new Long(multipartFile.getSize()).intValue(), multipartFile.getBytes(), applicationNumber.toString(), clientName, clientId,
						documentTypeIds);
				result.put("code", returnCode);
				if (returnCode == 0) {
					result.put("status", "success");
					result.put("errorMessage", " Successful Upload ");
					// TAM-2866 : Inserting dummy row so that participant does
					// not get confused
					try {
						ApplicationDocuments entity = new ApplicationDocuments();
						entity.setApplicationID(application.getApplicationID());
						entity.setDefaultDocumentsID(documentsList.get(0));
						entity.setReceived(false);
						entity.setIncomplete(false);
						entity.setDateCreated(new Timestamp(new Date().getTime()));
						entity.setDmsLocation("File en route");
						Long userId = sessionService.getClaimAsLong(JWTTokenClaims.USER_ID);
						entity.setCreatedBy(userId);
						// Needs some tweaking
						entity.setVisibleToParticipant(true);
						if (!CommonUtil.getHotProperty(CommonUtil.HOT_SIMULATE_DOCUMENT_UPLOAD).equals("enabled")) {
							this.saveOrUpdate(entity);
						}

						// TAM-3383 : Move this to Application Issues Also need
						// to persist the application comment if it was passed
						// in
						if (applicationComment != null && applicationComment.isEmpty() == false) {
							ApplicationComment issue = new ApplicationComment();
							issue.setApplicationID(application.getApplicationID());
							User currentUser = userService.findById(userId);
							issue.setCreatedBy(currentUser);
							issue.setDateCreated(new Date());
							issue.setViewableToParticipant(true);
							issue.setApplicationStatusID(application.getApplicationStatusID());
							issue.setComment(applicationComment);
							issue.setDefaultDocumentsID(entity.getDefaultDocumentsID().getDefaultDocumentsID());

							// If application belongs to the user uploading the file
							if (Long.valueOf(currentUser.getUserId()).equals(application.getParticipantID().getUser().getUserId())) {
								issue.setEmailSent(false);
								issue.setReviewed(false);
								issue.setReviewedDate(null);
								issue.setReviewedBy(null);
							} else {
								issue.setEmailSent(true);
								issue.setReviewed(true);
								issue.setReviewedBy(currentUser);
								issue.setReviewedDate(new Date());
							}
							applicationCommentService.saveOrUpdate(issue);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if (returnCode == 102) {
					result.put("errorMessage", "The file must have a name");
					result.put("action", "uploadError");
				} else if (returnCode == 103) {
					result.put("errorMessage", "File cannot be empty");
					result.put("action", "uploadError");
				} else if (returnCode == 104) {
					result.put("errorMessage", "Invalid File Type, only files of type pdf, jpg, xls, xlsx, bmp, tif, png are supported. ");
					result.put("action", "uploadError");
				} else if (returnCode == 105) {
					result.put("errorMessage", "Invalid File Size - please limit your file size to " + CommonUtil.getHotProperty(CommonUtil.HOT_UPLOAD_DOCUMENT_MAX_FILE_SIZE) + " bytes or less.");
					result.put("action", "uploadError");
				} else {
					result.put("errorMessage", "An unexpected error happened while attempting to upload a file.  ");
					result.put("action", "uploadError");
				}
			}
		} catch (Exception e) {

			logger.error("An error occurred while uploading a document: ", e);
			final String message = "An unexpected error happened while attempting to upload a file.  " + e.getLocalizedMessage();
			result.put("errorMessage", message);

			// show error view
			result.put("action", "uploadError");
		}
		return result;
	}
}
