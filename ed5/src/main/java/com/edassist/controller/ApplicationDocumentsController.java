package com.edassist.controller;

import com.edassist.exception.BadRequestException;
import com.edassist.exception.ForbiddenException;
import com.edassist.exception.NotFoundException;
import com.edassist.models.domain.Application;
import com.edassist.models.domain.ApplicationDocuments;
import com.edassist.models.domain.Program;
import com.edassist.models.domain.ProgramEligibleDocumentType;
import com.edassist.models.dto.ApplicationDocumentsDTO;
import com.edassist.models.dto.ProgramEligibleDocumentTypeDTO;
import com.edassist.models.mappers.ApplicationDocumentsMapper;
import com.edassist.models.mappers.ProgramEligibleDocumentTypeMapper;
import com.edassist.service.AccessService;
import com.edassist.service.ApplicationDocumentsService;
import com.edassist.service.ApplicationService;
import com.edassist.service.GenericService;
import com.edassist.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.tika.Tika;
import org.apache.tika.io.IOUtils;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
public class ApplicationDocumentsController {

	private final AccessService accessService;
	private final ApplicationDocumentsMapper applicationDocumentsMapper;
	private final ApplicationDocumentsService applicationDocumentsService;
	private final ApplicationService applicationService;
	private final GenericService<ProgramEligibleDocumentType> programEligibleDocumentTypeService;
	private final ProgramEligibleDocumentTypeMapper programEligibleDocumentTypeMapper;

	@Autowired
	public ApplicationDocumentsController(AccessService accessService, ApplicationDocumentsMapper applicationDocumentsMapper, ApplicationDocumentsService applicationDocumentsService,
			ApplicationService applicationService, GenericService<ProgramEligibleDocumentType> programEligibleDocumentTypeService,
			ProgramEligibleDocumentTypeMapper programEligibleDocumentTypeMapper) {
		this.accessService = accessService;
		this.applicationDocumentsMapper = applicationDocumentsMapper;
		this.applicationDocumentsService = applicationDocumentsService;
		this.applicationService = applicationService;
		this.programEligibleDocumentTypeService = programEligibleDocumentTypeService;
		this.programEligibleDocumentTypeMapper = programEligibleDocumentTypeMapper;
	}

	@RequestMapping(value = "/v1/applications/{applicationID}/application-documents", method = RequestMethod.GET)
	public ResponseEntity<List<ApplicationDocumentsDTO>> getApplicationDocuments(@PathVariable("applicationID") Long applicationID) {

		Application application = applicationService.findById(applicationID);
		if (application == null) {
			throw new BadRequestException("Application not found");
		}

		accessService.verifyParticipantOrHigherAccess(application);
		List<ApplicationDocuments> applicationDocumentsList = applicationDocumentsService.findByParam("applicationID", applicationID);

		List<ApplicationDocumentsDTO> applicationDocumentsDTOs = applicationDocumentsMapper.toDTOList(applicationDocumentsList);

		return new ResponseEntity<>(applicationDocumentsDTOs, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationID}/application-documents", method = RequestMethod.POST)
	public ResponseEntity<Boolean> uploadApplicationDocument(@PathVariable("applicationID") Long applicationID, @RequestParam(value = "file") MultipartFile file,
			@RequestParam() String documentType, @RequestParam(required = false) String courseId, @RequestParam(required = false) String gradeId,
			@RequestParam(required = false) String studentID, @RequestParam(required = false) String applicationComment) {

		Application application = applicationService.findById(applicationID);

		if (application == null) {
			throw new BadRequestException("Application not found");
		}

		accessService.verifyParticipantOrClientAdminAccess(application);

		if (!applicationService.isUploadDocumentAllowed(application)) {
			throw new ForbiddenException();
		}

		Long[] courseIds = new Long[] {};
		Long[] gradeIds = new Long[] {};

		if (documentType.toLowerCase().equals("grades")) {
			courseIds = CommonUtil.convertStringToArrayLong(courseId);
			gradeIds = CommonUtil.convertStringToArrayLong(gradeId);
		}

		Map<String, Object> result = applicationDocumentsService.saveUploadedDocument(applicationID, file, documentType, studentID, courseIds, gradeIds, applicationComment);
		if (!result.get("status").toString().equals("success")) {
			throw new BadRequestException(result.get("errorMessage").toString());
		}

		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/application-documents/{documentId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> serveApplicationDocument(@PathVariable("applicationId") Long applicationId, @PathVariable("documentId") Long documentId) throws IOException, MimeTypeException {

		Application application = applicationService.findById(applicationId);
		accessService.verifyParticipantOrHigherAccess(application);

		ApplicationDocuments document = applicationDocumentsService.findApplicationDocumentByIdAndApplication(documentId, applicationId);
		if (document == null) {
			throw new NotFoundException("document cannot be found");
		}

		java.net.URL url = new java.net.URL(StringUtils.replace(document.getDmsLocation(), " ", "%20"));
		java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();

		connection.connect();
		InputStream stream = connection.getInputStream();
		byte[] fileBytes = IOUtils.toByteArray(stream);
		stream.close();

		TikaInputStream tikaStream = TikaInputStream.get(fileBytes);
		Tika tika = new Tika();
		String mimeType = tika.detect(tikaStream);

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(mimeType));

		MimeTypes defaultMimeTypes = MimeTypes.getDefaultMimeTypes();
		String extension = defaultMimeTypes.forName(mimeType).getExtension();
		headers.add("file-ext", extension);

		return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/application-eligible-document-types", method = RequestMethod.GET)
	public ResponseEntity<List<ProgramEligibleDocumentTypeDTO>> getApplicationEligibleDocumentTypes(@PathVariable("applicationId") Long applicationId) {

		Application application = applicationService.findById(applicationId);
		accessService.verifyParticipantOrHigherAccess(application);

		Program program = application.getBenefitPeriodID().getProgramID();
		List<ProgramEligibleDocumentType> programEligibleDocumentTypeList = programEligibleDocumentTypeService.findByParam("programID", program);
		List<ProgramEligibleDocumentTypeDTO> programEligibleDocumentTypeDTOs = programEligibleDocumentTypeMapper.toDTOList(programEligibleDocumentTypeList);

		return new ResponseEntity<>(programEligibleDocumentTypeDTOs, HttpStatus.OK);
	}

}
