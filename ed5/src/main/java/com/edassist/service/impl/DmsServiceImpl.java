package com.edassist.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edassist.dao.ApplicationDocumentsDao;
import com.edassist.models.domain.Application;
import com.edassist.models.domain.DefaultDocuments;
import com.edassist.service.ApplicationService;
import com.edassist.service.DmsService;
import com.edassist.service.GenericService;
import com.edassist.utils.CommonUtil;
import com.edassist.utils.FileIOUtility;

@Service
public class DmsServiceImpl implements DmsService {

	private Logger log = Logger.getLogger(DmsServiceImpl.class);

	private ArrayList<String> allowedFileTypes;
	private Date today = null;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private GenericService<DefaultDocuments> defaultDocumentsService;

	@Autowired
	private ApplicationDocumentsDao applicationDocumentsDao;

	/**
	 * Saves a file to the DMS
	 */
	public DmsServiceImpl() {
		allowedFileTypes = BuildDefaultAllowedSuffix();
	}

	/**
	 * Builds the allowed suffix ArrayList if one is not defined in the constructor
	 * 
	 * @return list of allowed suffixes suffixes ('.doc', '.jpg', '.pdf')
	 */
	private ArrayList<String> BuildDefaultAllowedSuffix() {
		ArrayList<String> result = new ArrayList();
		/*
		 * 11/5/2009 - DAS - removing Word documents as upload eligible (per today's meeting with EP, CD, KR, HOH and EJH) result.add(".doc");
		 */
		result.add(".xls");
		result.add(".xlsx");
		result.add(".jpg");
		result.add(".jpeg");
		result.add(".bmp");
		result.add(".png");
		result.add(".eml");
		result.add(".pdf");
		return result;
	}

	/**
	 * Saves a file to the DMS
	 *
	 * @param name the name of the file
	 * @param size the size of the file
	 * @param data the data of the file
	 * @param applicationnum the application number
	 * @param clientname the client name
	 * @param clientid the cleint ID
	 * @param documentTypeIds collection of document type IDs
	 * @return the return code
	 * @throws FileNotFoundException
	 * @throws SecurityException
	 * @throws IOException
	 */
	@Override
	public int saveToDms(String name, int size, byte[] data, String applicationnum, String clientname, int clientid, Collection documentTypeIds)
			throws FileNotFoundException, SecurityException, IOException, Exception {

		log.debug("Entering  .saveToDms() - b");

		// File does not have a name
		if (name.equals("")) {
			return 102;
		}

		// File is empty
		if (data.length == 0) {
			return 103;
		}

		// File type is not allowed
		if (!IsAllowedType(name)) {
			return 104;
		}

		// File is too large
		if (size > new Integer(CommonUtil.getHotProperty(CommonUtil.HOT_UPLOAD_DOCUMENT_MAX_FILE_SIZE)).intValue()) {
			return 105;
		}

		if (!CommonUtil.getHotProperty(CommonUtil.HOT_SIMULATE_DOCUMENT_UPLOAD).equals("enabled")) {
			FTPClient client = new FTPClient();
			client.connect(CommonUtil.getHotProperty(CommonUtil.HOT_UPLOAD_DOCUMENT_FTP_SERVER));
			boolean loggedIn = client.login(CommonUtil.getHotProperty(CommonUtil.HOT_UPLOAD_DOCUMENT_FTP_LOGIN), CommonUtil.getHotProperty(CommonUtil.HOT_UPLOAD_DOCUMENT_FTP_PASSWORD));

			if (!loggedIn) {
				log.debug("Unable to connect to document upload FTP server at " + CommonUtil.getHotProperty(CommonUtil.HOT_UPLOAD_DOCUMENT_FTP_SERVER) + ".");
				return 101;
			}

			// build a unique name here to keep the DMS from hurting itself too badly...
			String uploadFileName = "App" + applicationnum + "-" + (new Long(new java.util.Date().getTime())).toString() + name.substring(name.lastIndexOf("."));

			uploadFileName = uploadFileName.toLowerCase();
			FileIOUtility.writeFile(uploadFileName, CommonUtil.getHotProperty(CommonUtil.HOT_UPLOAD_DOCUMENT_DIRECTORY), data);

			if (loggedIn) {
				FileInputStream fis = new FileInputStream(CommonUtil.getHotProperty(CommonUtil.HOT_UPLOAD_DOCUMENT_DIRECTORY) + "/" + uploadFileName);
				client.setFileType(FTP.BINARY_FILE_TYPE);
				client.storeFile(uploadFileName, fis);
			}

			String xmlName = uploadFileName.substring(0, uploadFileName.lastIndexOf('.')) + ".xml";
			String xml = buildXml(uploadFileName, applicationnum, clientname, clientid, documentTypeIds);
			FileIOUtility.writeFile(xmlName, CommonUtil.getHotProperty(CommonUtil.HOT_UPLOAD_DOCUMENT_DIRECTORY), xml);

			if (loggedIn) {
				FileInputStream fis = new FileInputStream(CommonUtil.getHotProperty(CommonUtil.HOT_UPLOAD_DOCUMENT_DIRECTORY) + "/" + xmlName);
				client.setFileType(FTP.BINARY_FILE_TYPE);
				client.storeFile(xmlName, fis);
			}

			if (loggedIn) {
				client.logout();
			}

			client.disconnect();

		} else {
			List<Application> appList = applicationService.findByParam("applicationNumber", new Long(applicationnum));
			if (appList != null && appList.size() > 0) {
				Application app = appList.get(0);
				Iterator<String> iter = documentTypeIds.iterator();
				String defaultDocumentsID = iter.next();
				Long defaultDocumentsId = new Long(defaultDocumentsID);
				DefaultDocuments defaultDocument = defaultDocumentsService.findById(defaultDocumentsId);
				try {
					/*
					 * idc.ntlm.domain.name=brighthorizons idc.ntlm.username=30003475 idc.ntlm.password=GoodGirls2014* idc.share.location=smb://coauwebstg503/UploadedFiles/
					 * fake.dms.folder=C:\\inetpubLocalDMS fake.dms.url=http://dstrouse-dt:82/
					 */
					/*
					 * if ( !"".equals(CommonUtil.getHotProperty(CommonUtil.HOT_IDC_NTLM_DOMAIN_NAME)) && !"".equals(CommonUtil.getHotProperty(CommonUtil.HOT_IDC_NTLM_USERNAME)) &&
					 * !"".equals(CommonUtil.getHotProperty(CommonUtil.HOT_IDC_NTLM_PASSWORD)) && !"".equals(CommonUtil.getHotProperty(CommonUtil.HOT_IDC_SHARE_LOCATION)) ) { String domain =
					 * CommonUtil.getHotProperty(CommonUtil.HOT_IDC_NTLM_DOMAIN_NAME); String username = CommonUtil.getHotProperty(CommonUtil.HOT_IDC_NTLM_USERNAME); String password =
					 * CommonUtil.getHotProperty(CommonUtil.HOT_IDC_NTLM_PASSWORD); NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain,username,password); String path =
					 * CommonUtil.getHotProperty(CommonUtil.HOT_IDC_SHARE_LOCATION) + fileName; SmbFile sFile = new SmbFile(path, auth); SmbFileOutputStream sfos = new SmbFileOutputStream(sFile);
					 * sfos.write(data); sfos.flush(); sfos.close(); }
					 */
					if (CommonUtil.getHotProperty("fake.dms.folder") != null && CommonUtil.getHotProperty("fake.dms.folder").length() > 0) {
						String randomfileName = new Long(new java.util.Date().getTime()).toString() + name.substring(name.lastIndexOf("."));
						FileIOUtility.writeFile(randomfileName, CommonUtil.getHotProperty("fake.dms.folder"), data);
						applicationDocumentsDao.driveAddApplicationDocumentProc(new Long(applicationnum), defaultDocument.getDefaultDocumentsID(), defaultDocument.getDocumentName(), true, false,
								CommonUtil.getHotProperty("fake.dms.url"), randomfileName, new java.util.Date());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		log.debug("Leaving  .saveToDms() - b");
		return 0;
	}

	/**
	 * Compares the file name to the collection off allowable suffixes
	 *
	 * @param name the name of the file
	 */
	private boolean IsAllowedType(String name) {
		log.debug("Entering .IsAllowedType(), name=" + name);
		if (name == null) {
			return false;
		}

		String suffix = name.substring(name.lastIndexOf('.'));

		if (suffix == null) {
			return false;
		}
		suffix = suffix.toLowerCase();

		log.debug("Leaving .IsAllowedType()");
		return (allowedFileTypes.contains(suffix));
	}

	/**
	 * Builds the XML file used to pass file data to the DMS
	 *
	 * @param name the name of the file
	 * @param applicationnum the application number
	 * @param clientname the client name
	 * @param clientid the cleint ID
	 * @param documentTypeIds the document type ID
	 * @return xml
	 */
	private String buildXml(String name, String applicationnum, String clientname, int clientid, Collection documentTypeIds) {

		String whereAmI = this.getClass().toString() + ".buildXml()";
		log.debug("Entering " + whereAmI);

		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version='1.0' encoding='utf-8'?>\r\n");
		builder.append("<FileMetaData xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema'>\r\n");

		if (clientid != -1) {
			builder.append("    <Client>\r\n");
			builder.append("        <Id>");
			builder.append(clientid);
			builder.append("</Id>\r\n");
			builder.append("        <Name>");
			builder.append(clientname);
			builder.append("</Name>\r\n");
			builder.append("    </Client>\r\n");
		}

		builder.append("    <DocumentSource>Upload</DocumentSource>\r\n");

		if (applicationnum != null) {
			builder.append("    <ApplicationNumber>");
			builder.append(applicationnum);
			builder.append("</ApplicationNumber>\r\n");
		} else {
			builder.append("    <ApplicationNumber/>\r\n");
		}

		if (documentTypeIds != null && documentTypeIds.size() > 0) {
			builder.append("    <DocumentTypeIDs>\r\n");
			Iterator iterator = documentTypeIds.iterator();

			for (Object docTypeId : documentTypeIds) {
				builder.append("        <string>");
				builder.append(docTypeId);
				builder.append("</string>\r\n");

			}
			builder.append("    </DocumentTypeIDs>\r\n");
		} else {
			builder.append("    <DocumentTypeIDs/>\r\n");
		}

		if (name != null) {
			builder.append("    <FileName>");
			builder.append(name);
			builder.append("</FileName>\r\n");
		}

		this.today = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		builder.append("    <DateReceived>");
		builder.append(formatter.format(this.today));
		builder.append("</DateReceived>\r\n");

		builder.append("</FileMetaData>");

		log.debug("Leaving " + whereAmI);
		return builder.toString();
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}
}
