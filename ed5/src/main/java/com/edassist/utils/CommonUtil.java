package com.edassist.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.InitialContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.validator.routines.BigDecimalValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.edassist.constants.ApplicationConstants;
import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.AppStatusChange;
import com.edassist.models.domain.Participant;
import com.edassist.models.domain.ParticipantSupervisor;

public class CommonUtil {

	private static Logger log = Logger.getLogger(CommonUtil.class);

	public static final String RESTRICTION_NOT_NULL = "TAMS_NOT_NULL_PLACEHOLDER";
	public final static String MMDDYYYHHAM = "MM/dd/yyyy hh:mm a";
	public final static String MMDDYYY = "MM/dd/yyyy";
	public final static String YYYYMMDD = "yyyy-MM-dd";
	public final static String YYYY = "yyyy";

	private static Random random = null;
	public static String HOT_TEMPLATES_BASE_DIRECTORY = "templates.base.directory";
	public static String HOT_TEMPLATES_BASE_URL = "templates.base.url";
	public static String HOT_TAMSERRORS = "tamserrors";
	public static String HOT_SERVER_NAME = "server.name";
	public static String HOT_ATTACHMENTS_FOLDER = "attachments.folder";

	public static String HOT_ENABLED = "enabled";
	public static String HOT_PROCESS_BATCH_WORKFLOW = "process.batch.workflow";
	public static String HOT_PROCESS_BATCH_EMAILS = "process.batch.emails";
	public static String HOT_PROCESS_DAILY_EMAILS = "process.daily.emails";
	public static String HOT_PROCESS_DAILY_APPLICATION_CANCEL = "process.daily.application.cancel";
	public static String HOT_PROCESS_DAILY_APPLICATION_MISMATCHED_SUPERS = "process.daily.application.mismatched.supers";
	public static String HOT_DAILY_EMAILS_SERVER = "daily.emails.server";
	public static String HOT_PERIODIC_EMAILS_SERVER = "periodic.emails.server";
	public static String HOT_WORKFLOW_SERVER = "workflow.server";
	public static String HOT_HOURLY_SERVER = "hourly.server";
	public static String HOT_DAILY_CONTENT_MIGRATION_SERVER = "daily.content.migration.server";

	public static String HOT_SSO_STARBUCKS_REDIRECT_URL = "sso.Starbucks.redirectURL";
	public static String HOT_SSO_AMGEN_REDIRECT_URL = "sso.amgen.redirectURL";
	public static String HOT_LOANREPAY_REDIRECT_URL = "loanRepay.redirectURL";
	public static String SSO_URL = "sso.url";

	public static String HOT_LEARNING_CENTER_ROTATING_IMAGE_MAX_FILE_SIZE = "learning.center.rotating.image.max.file.size";
	public static String HOT_LEARNING_CENTER_ROTATING_IMAGE_MAX_NBR_FILES = "learning.center.rotating.image.max.nbr.files";

	public static String HOT_CACHE_MANAGED_CONTENT = "cache.managed.content";
	public static String HOT_CACHE_AGREEMENT_MANAGED_CONTENT = "cache.agreement.managed.content";
	public static String HOT_CACHE_MESSAGE_BOARD_MANAGED_CONTENT = "cache.message.board.managed.content";
	public static String HOT_CACHE_DOCUMENTS_MANAGED_CONTENT = "cache.documents.managed.content";

	public static String HOT_REPORTING_URL = "reportingUrl";
	public static String HOT_REPORTING_URL_WITH_SCHEDULE = "reportingUrlWithSchedule";

	public static String HOT_REGEX_ALPHANUM_1 = "regex.alphanum.1";

	public static String HOT_PRUDENTIAL_MY_ACTIONS_MAILBOX = "prudential.my.actions.mailbox";

	public static String HOT_UPLOAD_DOCUMENT_DIRECTORY = "upload.document.directory";
	public static String HOT_UPLOAD_DOCUMENT_FTP_SERVER = "upload.document.ftp.server";
	public static String HOT_UPLOAD_DOCUMENT_FTP_LOGIN = "upload.document.ftp.login";
	public static String HOT_UPLOAD_DOCUMENT_FTP_PASSWORD = "upload.document.ftp.password";
	public static String HOT_UPLOAD_DOCUMENT_MAX_FILE_SIZE = "upload.document.max.file.size";
	public static String HOT_UPLOAD_VHD_DOCUMENT_MAX_FILE_SIZE = "upload.vhd.document.max.file.size";

	public static String HOT_IDC_SHARE_LOCATION = "idc.share.location";
	public static String HOT_IDC_NTLM_DOMAIN_NAME = "idc.ntlm.domain.name";
	public static String HOT_IDC_NTLM_USERNAME = "idc.ntlm.username";
	public static String HOT_IDC_NTLM_PASSWORD = "idc.ntlm.password";

	public static String HOT_PUBLISH_SUBSCRIBE_MODEL = "publish.subscribe.model";

	public static String SUPPRESS_EDLINK_LOGO_STEM = "suppress.edlink.logo.";

	public static String HOT_TRANSIENT_FOLDERS = "transient.folders";
	public static String HOT_WHITE_LISTED_FILES = "white.listed.files";

	public static String HOT_SUPPRESS_SSO = "suppress.sso";

	public static String HOT_DMS_UPLOAD_DUPLICATE_DOCUMENT_INTERVAL = "dms.upload.duplicate.document.interval";

	public static String HOT_SIMULATE_DOCUMENT_UPLOAD = "simulate.document.upload";

	// Hot property for allowed file type to be uploaded to CRM
	public static String CRM_LISTED_FILES = "crm.listed.files";

	// Hot Properties for CRM .net Web Services
	public static final String CRM_USER_NAME = "crm.user.name";
	public static final String CRM_PWD = "crm.pwd";
	public static final String CRM_DOMAIN = "crm.domain";
	public static final String CRM_WS_URL = "crm.ws.url";

	// service hosts
	public static final String RULES_SERVICE_HOST = "services.hosts.rules";
	public static final String CONTENT_SERVICE_HOST = "services.hosts.content";
	public static final String EMAIL_SERVICE_HOST = "services.hosts.email";
	public static final String TAMS_ENVIRONMENT = "tams.environment";

	/**
	 * Constant - Google Analytics Tracking ID
	 */
	public static String HOT_GOOGLE_ANALYTICS_TRACKING_ID = "google.analytics.tracking.id";

	// SWAGGER Documentation
	public static final String SWAGGER_DOCUMENTATION = "swagger.documentation.enabled";

	/**
	 * Constants - Yodlee username and password generation randomly
	 */
	public static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
	public static final String NUM = "0123456789";
	public static final String SPL_CHARS = "!@#$%^&*()";

	public static Properties hotProperties;

	/**
	 * Utility method to ensure start and end dates are proper dates and they are a valid range.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean validateDateRange(String startDate, String endDate) {

		Date formattedStartDate = formatStringToDate(startDate, MMDDYYY);
		Date formattedEndDate = formatStringToDate(endDate, MMDDYYY);

		if (formattedStartDate == null || formattedEndDate == null || formattedStartDate.getTime() > formattedEndDate.getTime()) {
			return false;
		}

		return true;
	}

	public static boolean validateMultipleDateRangeOverlap(Date firstStartDate, Date firstEndDate, Date secondStartDate, Date secondEndDate) {

		if (firstStartDate == null || firstEndDate == null || secondStartDate == null || secondEndDate == null) {
			return false;
		}

		/*
		 * Checks that: 1. First start date is not between the second date's start and end 2. First end date is not between the second date's start and end
		 */
		if ((firstStartDate.getTime() >= secondStartDate.getTime() && firstStartDate.getTime() <= secondEndDate.getTime())
				|| (firstEndDate.getTime() >= secondStartDate.getTime() && firstEndDate.getTime() <= secondEndDate.getTime())) {
			return false;
		}

		return true;
	}

	public static String formatDate(Date date, String format) {
		try {
			return (new SimpleDateFormat(format)).format(date);
		} catch (Exception e) {
			return "";
		}
	}

	public static Integer formatDateToYear(Date date) {
		try {
			return Integer.valueOf((new SimpleDateFormat(YYYY)).format(date));
		} catch (Exception e) {
			return null;
		}
	}

	public static Date formatStringToDate(String date, String format) {
		try {
			return (new SimpleDateFormat(format)).parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date formatDateToDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(sdf.format(date));
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}

	public static String formatStringToString(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.format(sdf.parse(date));
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}

	public static String formatPhNo(String number) {
		String retVal = number;
		if (number != null && !number.trim().isEmpty() && number.length() >= 6) {
			try {
				retVal = "(" + number.substring(0, 3) + ") " + number.substring(3, 6) + "-" + number.substring(6);
			} catch (StringIndexOutOfBoundsException e) {
				log.error(e);
			}
		}
		return retVal;
	}

	public static String formatPhNumber(String number) {
		String retVal = number;

		if (number != null && !number.trim().isEmpty() && number.length() >= 6) {
			try {
				retVal = retVal.replace("-", "");
				retVal = retVal.replace("(", "");
				retVal = retVal.replace(")", "");
			} catch (StringIndexOutOfBoundsException e) {
				;
			}
		}
		return retVal;

	}

	public static int safeLongToInt(long l) {
		if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
		}
		return (int) l;
	}

	public static String toDisplayStringInitialized(String value)

	{

		if (value == null || value.equalsIgnoreCase("null"))

		{

			return "";

		} else

		{

			String inputString = value.toLowerCase();

			inputString = WordUtils.capitalize(inputString);

			return inputString;

		}

	}

	public static String toDisplayString(String value) {
		if (value == null || value.equalsIgnoreCase("null")) {
			return "";
		}

		return value.trim();
	}

	public static String toDisplayString(Date value) {
		if (value == null) {
			return "";
		}

		return formatDate(value, CommonUtil.MMDDYYY);
	}

	public static String dateToFullDisplayString(Date value) {
		if (value == null) {
			return "";
		}

		return formatDate(value, CommonUtil.MMDDYYYHHAM);
	}

	public static String toCurrencyString(BigDecimal value) {
		// if(value == null || BigDecimal.ZERO.equals(value))
		// {
		// return "$ - ";
		// }
		//
		// value.setScale(2);
		// return "$"+value.toPlainString();
		if (value == null) {
			return "";
		}
		return value.toPlainString();
	}

	public static String getCurrencyString(BigDecimal value) {
		NumberFormat currency = NumberFormat.getCurrencyInstance();

		if (value == null) {
			value = BigDecimal.ZERO;
		}

		String str = currency.format(value);
		return str;
	}

	public static String getNumberString(BigDecimal value) {
		NumberFormat number = NumberFormat.getNumberInstance();

		if (value == null) {
			value = BigDecimal.ZERO;
		}

		String str = number.format(value);
		return str;
	}

	public static String toDisplayString(Double value) {
		if (value == null || value.isNaN()) {
			return "";
		}
		return String.valueOf(value);
	}

	// TODO THIS METHOD NEEDS TO BE IMPLEMENTED AS PART OF THE "RESET PASSWORD BUTTON" ON THE WELCOME SCREEN
	public static String generateDefaultPassword() {
		if (CommonUtil.random == null) {
			String rightNowInMilli = (new Long(new Date().getTime())).toString();
			String bitImInterestedIn = rightNowInMilli.substring(rightNowInMilli.length() - 4, rightNowInMilli.length() - 1);
			long seed = new Long(bitImInterestedIn).longValue();
			CommonUtil.random = new Random(seed);
		}
		int passwordInt = random.nextInt(8999999);
		passwordInt += 1000000;
		String passwordStr = new Integer(passwordInt).toString();
		System.out.println("new password is \"" + passwordStr + "\"");
		return passwordStr;
	}

	/**
	 * before we save/update the participant supervisor information... due to the "special" business logic in the ParticipantSupervisor table we cannot use the JPA annotation to cascade the
	 * save/update
	 * 
	 * @param participant
	 */
	public static void modifySupervisors(Participant participant) {
		// only process the supervisor logic if the user updates/adds a supervisor
		if (participant.getLevelOneSupervisor() != null || participant.getLevelTwoSupervisor() != null) {

			Character supervisorIdType = participant.getClient().getSupervisorIDType();
			if (supervisorIdType == null) {
				throw new BadRequestException("SupervisorIdType must not be null.  Client: " + participant.getClient().getClientName());
			}

			ParticipantSupervisor levelOneParticipantSupervisor = null;
			ParticipantSupervisor levelTwoParticipantSupervisor = null;

			// if the participant has supervisors, then iterate thru them and figure out which one is L1 and which is L2
			if (participant.getSupervisors() != null && participant.getSupervisors().size() > 0) {
				for (ParticipantSupervisor currSupervisor : participant.getSupervisors()) {
					if (currSupervisor != null) {
						if (currSupervisor.getApprovalLevel() == 1) {
							levelOneParticipantSupervisor = currSupervisor;
						} else if (currSupervisor.getApprovalLevel() == 2) {
							levelTwoParticipantSupervisor = currSupervisor;
						} else {
							throw new BadRequestException("Unsupported approvalLevel: [" + currSupervisor.getApprovalLevel() + "]");
						}
					}
				}
			}

			// clear the current list, we will re-populate
			participant.setSupervisors(null);
			Set<ParticipantSupervisor> modifiedSupervisorList = new HashSet<ParticipantSupervisor>();

			// need to update the corresponding supervisor or create a new one
			if (participant.getLevelOneSupervisor() != null) {
				// get the level one supervisor from list
				// if there is no level1 then create one
				// othwerwise just update the SupervisorID field (either SSN or empId)
				// add new/modified supervisor to list so it will be persisted.
				if (levelOneParticipantSupervisor == null) {
					levelOneParticipantSupervisor = buildParticipantSupervisor(participant, participant.getLevelOneSupervisor(), supervisorIdType, 1);
				} else {
					levelOneParticipantSupervisor.setSupervisor(participant.getLevelOneSupervisor().getParticipantId());
					levelOneParticipantSupervisor.setSupervisorID(deriveSupervisorId(supervisorIdType, participant.getLevelOneSupervisor()));
					levelOneParticipantSupervisor.setIdType(supervisorIdType);
				}
				modifiedSupervisorList.add(levelOneParticipantSupervisor);
			}

			if (participant.getLevelTwoSupervisor() != null) {
				// get the level two supervisor from list
				// if there is no level one then create one
				// othwerwise just update the SupervisorID field (either SSN or empId)
				// add new/modified supervisor to list so it will be persisted.
				if (levelTwoParticipantSupervisor == null) {
					levelTwoParticipantSupervisor = buildParticipantSupervisor(participant, participant.getLevelTwoSupervisor(), supervisorIdType, 2);
				} else {
					levelTwoParticipantSupervisor.setSupervisor(participant.getLevelTwoSupervisor().getParticipantId());
					levelTwoParticipantSupervisor.setSupervisorID(deriveSupervisorId(supervisorIdType, participant.getLevelTwoSupervisor()));
					levelTwoParticipantSupervisor.setIdType(supervisorIdType);
				}

				modifiedSupervisorList.add(levelTwoParticipantSupervisor);
			}
			participant.setSupervisors(modifiedSupervisorList);
		}
	}

	private static ParticipantSupervisor buildParticipantSupervisor(Participant participant, Participant originalSupervisor, Character supervisorIdType, int approvalLevel) {
		ParticipantSupervisor newParticipantSupervisor = new ParticipantSupervisor();
		newParticipantSupervisor.setApprovalLevel(approvalLevel);
		newParticipantSupervisor.setIdType(supervisorIdType);

		newParticipantSupervisor.setParticipantID(participant);

		newParticipantSupervisor.setSupervisorID(deriveSupervisorId(supervisorIdType, originalSupervisor));
		newParticipantSupervisor.setSupervisor(originalSupervisor.getParticipantId());
		return newParticipantSupervisor;
	}

	private static String deriveSupervisorId(Character supervisorIdType, Participant supervisor) {
		if (supervisorIdType == null) {
			throw new BadRequestException("SupervisorIdType must not be null.");
		}
		if (supervisorIdType.equals('S') || supervisorIdType.equals('s')) {
			return supervisor.getSsn();
		} else if (supervisorIdType.equals('E') || supervisorIdType.equals('e')) {
			return supervisor.getEmployeeId();
		} else {
			throw new BadRequestException("Unsupported supervisorIdType: [" + supervisorIdType + "]");
		}
	}

	public static Date addDays(Date date, int days) {

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(date.getTime());
		c1.add(Calendar.DATE, days);
		return c1.getTime();
	}

	public static boolean actionEquals(String actionName, String value) {
		return StringUtils.contains(value, actionName);
	}

	public static String parseRequest(String param) {
		if (StringUtils.isNotBlank(param)) {
			return param;
		}

		return null;
	}

	/**
	 * <p>
	 * Returns a normalized, simple client file name. Discards the full path when provided by particular web browsers on particular platforms.
	 * </p>
	 *
	 * @param clientFileName a String, the original client file name
	 * @return the normalized, simple client file name
	 */
	public static String getSimpleFileName(final String clientFileName) {
		// for non-Windows clients, convert path separator to platform
		// convention
		final String updatedPath = clientFileName.replace("/", File.separator);

		// get just the trailing file name, not the path
		final int lastIndexSeparator = updatedPath.lastIndexOf(File.separator);
		final int beginFileName = 1 + lastIndexSeparator;
		final String result = updatedPath.substring(beginFileName);

		return result;
	}

	public static void loadHotProperties() throws Exception {
		File configFile = new File("C:\\TAMSLocalConfig\\TAMS4_HOT_CONFIGURATION.properties");
		hotProperties = new Properties();
		InputStream is = null;
		is = new FileInputStream(configFile);
		hotProperties.load(is);
	}

	public static String getHotProperty(String propertyKey) throws BadRequestException {
		String returnValue = "";
		try {
			returnValue = (hotProperties.getProperty(propertyKey) == null ? "" : hotProperties.getProperty(propertyKey));
		} catch (Throwable t) {
			; // don't care, just wanna return an empty value
		}
		return returnValue;
	}

	public static String getFormattedPhoneNumber(String phone) {
		if (phone == null || phone.trim().length() < 10 || phone.trim().length() > 10 || !isNumeric(phone)) {
			return phone;
		} else {
			String formattedPhone = "(" + phone.substring(0, 3) + ")" + phone.substring(3, 6) + "-" + phone.substring(6);
			return formattedPhone;
		}
	}

	public static boolean isNumeric(String num) {
		// TAM-3281
		return BigDecimalValidator.getInstance().isValid(num);
	}

	public static boolean isEmail(String emailAddressString) {
		// TAM-3281
		return EmailValidator.getInstance().isValid(emailAddressString);
	}

	public static boolean isAlphaNumeric(String alphaNumString) {
		// TAM-3281
		/* "[a-zA-Z0-9\\-#.()/%&\\s!@$*_+'\"\\[\\]{}\\?]+" */
		String alphaNumRegexPattern = CommonUtil.getHotProperty(HOT_REGEX_ALPHANUM_1);
		return alphaNumString.matches(alphaNumRegexPattern);
	}

	public static String getManagedServerName() {
		try {
			InitialContext ctx = new InitialContext();
			MBeanServer mBeanServer = (MBeanServer) ctx.lookup("java:comp/env/jmx/runtime");
			ObjectName runtimeService = new ObjectName("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
			String managedServerName = (String) mBeanServer.getAttribute(runtimeService, "ServerName");
			return managedServerName;
		} catch (Exception e) {
			return "unknown";
		}
	}

	// TAM-3034
	public static String stripHtmlTags(String stripFromThis) {

		String[] tags = { "<p>", "</p>", "&nbsp;" };

		String returnThis = (stripFromThis == null ? "" : stripFromThis);
		int ndx = 0;
		while (ndx < tags.length) {
			returnThis = returnThis.replaceAll(tags[ndx], "");
			ndx++;
		}

		return returnThis.trim();
	}

	public static String cleanseReferrerPortlet(String referrerPortlet) {
		if (referrerPortlet == null) {
			return null;
		}
		if (!CommonUtil.isAlphaNumeric(referrerPortlet)) {
			return "DashboardMessagePortlet";
		} else {
			return referrerPortlet;
		}
	}

	public static String cleanseReferrerAction(String referrerAction) {
		if (referrerAction == null) {
			return null;
		}
		if (!CommonUtil.isAlphaNumeric(referrerAction)) {
			return "DashboardMessagePortlet";
		} else {
			return referrerAction;
		}
	}

	public static String getServerName() {
		Map<String, String> env = System.getenv();
		if (env == null) {
			return "unknown";
		}
		String serverName = env.get("SERVER_NAME");
		if (serverName == null || serverName.length() < 1) {
			return "unknown";
		}

		return serverName;
	}

	/**
	 * Returns an array of Long. This accepts a comma separated string, removes the comma, parse it to Long and return it as an array
	 *
	 * @param ids the comma separated list of ids
	 * @return String[]
	 */
	public static Long[] convertStringToArrayLong(String ids) {
		String[] items = ids.replaceAll("\\[", "").replaceAll("\\]", "").split(",");

		Long[] results = new Long[items.length];

		for (int i = 0; i < items.length; i++) {
			try {
				results[i] = Long.parseLong(items[i]);
			} catch (NumberFormatException nfe) {
				throw new NumberFormatException();
			}
			;
		}
		return results;
	}

	/**
	 * Returns an array of String. This accepts a comma separated string, removes the comma and return it as an array
	 *
	 * @param ids the comma separated list of ids
	 * @return String[]
	 */
	public static String[] convertStringToArrayString(String ids) {
		String[] items = ids.replaceAll("\\[", "").replaceAll("\\]", "").split(",");

		return items;
	}

	public static String getServiceHost(String host, String platform) throws BadRequestException {
		String hostFromProperty = getHotProperty(host);
		if (hostFromProperty == null || hostFromProperty.isEmpty()) {
			throw new BadRequestException();
		}
		return "http://" + hostFromProperty + "/" + platform + "/api/v1/";
	}

	public static String getAdminUrl(String domain) {
		String domainFromProperty = getHotProperty(domain);
		if (domainFromProperty == null || domainFromProperty.isEmpty()) {
			throw new BadRequestException();
		}
		return "https://" + domainFromProperty + "/TAMS4Web/login/admin";
	}

	public static RestTemplate setMessageConverters(RestTemplate restTemplate) throws BadRequestException {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);

		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

		return restTemplate;
	}

	public static String getFullName(String firstName, String middleName, String lastName) {
		StringBuilder fullName = new StringBuilder();
		boolean isBlank = true;
		if (!StringUtils.isEmpty(lastName)) {
			fullName.append(lastName.trim());
			isBlank = false;
		}

		if (!StringUtils.isEmpty(firstName)) {
			if (!isBlank) {
				fullName.append(", ");
			}
			fullName.append(firstName.trim());
			isBlank = false;
		}

		if (!StringUtils.isEmpty(middleName)) {
			if ((!isBlank) && (!middleName.equals(" "))) {
				fullName.append(" ");
				fullName.append(middleName.trim());
			}

		}
		return fullName.toString();
	}

	public static Long getMatchingApplicationStatus(Date date, List<AppStatusChange> appStatusChangeList) {
		AppStatusChange beforeStatus = null;
		for (AppStatusChange statusChange : appStatusChangeList) {
			if (beforeStatus == null) {
				beforeStatus = statusChange;
			}
			if (date.compareTo(statusChange.getDateModified()) >= 0) {
				if (beforeStatus.getDateModified().compareTo(statusChange.getDateModified()) <= 0) {
					beforeStatus = statusChange;
				}
			}

		}
		if (beforeStatus != null) {
			return beforeStatus.getApplicationStatus().getApplicationStatusID();
		}
		return ApplicationConstants.APPLICATION_STATUS_SAVED_NOT_SUBMITTED;
	}

}
