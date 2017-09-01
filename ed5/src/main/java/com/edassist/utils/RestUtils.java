package com.edassist.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import com.edassist.exception.BadRequestException;
import com.edassist.exception.CrmException;
import com.edassist.models.contracts.crm.advising.AdvisingSession;
import com.edassist.service.impl.loanaggregator.LoanAggregatorErrorHandler;

public class RestUtils {
	private static Logger log = Logger.getLogger(RestUtils.class);

	/**
	 * @param status
	 * @return
	 */
	public static boolean isError(HttpStatus status) {
		HttpStatus.Series series = status.series();
		return (HttpStatus.Series.CLIENT_ERROR.equals(series) || HttpStatus.Series.SERVER_ERROR.equals(series));
	}

	/**
	 * @param status
	 * @return
	 */
	public static boolean isClientError(HttpStatus status) {
		HttpStatus.Series series = status.series();
		return (HttpStatus.Series.CLIENT_ERROR.equals(series));
	}

	/**
	 * @param status
	 * @return
	 */
	public static boolean isServerError(HttpStatus status) {
		HttpStatus.Series series = status.series();
		return (HttpStatus.Series.SERVER_ERROR.equals(series));
	}

	/**
	 * @param responseCode
	 * @return
	 */
	public static boolean isError(long responseCode) {
		return RestUtils.CRM_SUCCESS != responseCode;
	}

	/**
	 * @param responseCode
	 * @return
	 */
	public static boolean isSecurityTokenExpired(long responseCode) {
		return RestUtils.CRM_TOKEN_EXPIRED == responseCode;
	}

	/**
	 * @param dtStr
	 * @return
	 * @throws CrmException
	 */
	public static String formatCrmDate(String dtStr) throws CrmException {
		return formatCrmDate(dtStr, "MM/dd/yyyy hh:mm a");
	}

	/**
	 * @param dtStr
	 * @return
	 * @throws CrmException
	 */
	public static String formatCrmDate(String dtStr, String format) throws CrmException {
		SimpleDateFormat strFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat dtFmt = new SimpleDateFormat(format);
		strFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
		Locale userLocale = LocaleContextHolder.getLocale();
		Calendar cal = Calendar.getInstance(userLocale);
		String ret = null;
		try {
			cal.setTime(strFmt.parse(dtStr));
			ret = dtFmt.format(cal.getTime());
		} catch (ParseException ex) {
			log.error("Error parsing DataTime from CRM : " + dtStr);
			throw new CrmException(dtStr, ex);
		}
		return ret;
	}

	/**
	 * @param dtStr
	 * @return
	 * @throws CrmException
	 */
	public static Date formatCRMDate(String dtStr) throws CrmException {
		SimpleDateFormat strFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		strFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = null;
		if (dtStr != null) {
			try {
				date = strFmt.parse(dtStr);

			} catch (ParseException ex) {
				log.error("Error parsing DataTime from CRM : " + dtStr);
				throw new CrmException(dtStr, ex);
			}
		}
		return date;
	}

	public static String formatDateToString(String date) {
		SimpleDateFormat dtFmt = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat strFmt = new SimpleDateFormat("yyyy-MM-dd");
		String ret = null;
		try {
			Date dateObj = dtFmt.parse(date);
			ret = strFmt.format(dateObj);

		} catch (ParseException ex) {
			log.error("Error parsing DataTime from UI : " + date);
			throw new CrmException(date, ex);
		}
		return ret;
	}

	public static String formatAdvisingSlotDate(String dtStr, String format, String offSet) throws CrmException {
		String ret = null;
		String strTimeZone = getTimeZoneString(offSet);
		TimeZone tz = TimeZone.getTimeZone(strTimeZone.toString());

		SimpleDateFormat strFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		strFmt.setTimeZone(TimeZone.getTimeZone("UTC"));

		SimpleDateFormat dtFmt = new SimpleDateFormat(format);
		dtFmt.setTimeZone(tz);

		try {
			Date utcDate = strFmt.parse(dtStr);
			ret = dtFmt.format(utcDate);
		} catch (ParseException ex) {
			log.error("Error parsing DataTime from CRM : " + dtStr);
			throw new CrmException(dtStr, ex);
		}

		return ret;
	}

	public static boolean checkAdvisingLeadTime(int advisingCategory, String offSet, String utcTime) {

		Calendar current = new GregorianCalendar(TimeZone.getTimeZone(getTimeZoneString(offSet)));
		Calendar slot = new GregorianCalendar(TimeZone.getTimeZone(getTimeZoneString(offSet)));
		slot.setTime(formatCRMDate(utcTime));
		if (advisingCategory == AdvisingSession.CAT_ACADEMIC) {
			return ((slot.getTimeInMillis() - current.getTimeInMillis()) > LEAD_TIME_ACADEMIC);
		} else if (advisingCategory == AdvisingSession.CAT_FINANCE) {
			return ((slot.getTimeInMillis() - current.getTimeInMillis()) > LEAD_TIME_FINANCIAL);
		} else if (advisingCategory == AdvisingSession.CAT_LOANREPAY) {
			return ((slot.getTimeInMillis() - current.getTimeInMillis()) > LEAD_TIME_FINANCIAL);
		}

		return false;
	}

	public static long getCrmTimeInMillies(String dtStr) {
		SimpleDateFormat strFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		strFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date;
		try {
			date = strFmt.parse(dtStr);

		} catch (ParseException ex) {
			log.error("Error parsing DataTime from CRM : " + dtStr);
			throw new CrmException(dtStr, ex);
		}
		return date.getTime();

	}

	public static String convertToBase64(byte[] fileContent) {
		byte[] encodedContent = Base64.encodeBase64(fileContent);
		return new String(encodedContent);
	}

	public static byte[] convertFromBase64(String enContent) {
		byte[] base64Data = enContent.getBytes();
		byte[] decodedContent = Base64.decodeBase64(base64Data);
		return decodedContent;
	}

	public static boolean isValidCrmFileType(String fileName) {
		String crmListedFileExtensions = CommonUtil.getHotProperty(CommonUtil.CRM_LISTED_FILES);
		String[] crmListedFileExtensionsArr = crmListedFileExtensions.split("\\s*,\\s*");

		return FilenameUtils.isExtension(fileName, crmListedFileExtensionsArr);
	}

	private static String getTimeZoneString(String offset) {
		double timeZone = Double.parseDouble(offset);
		double absTz = Math.abs(timeZone);
		int hours = (int) (absTz);
		int minutes = (int) ((absTz * 60) % 60);
		StringBuffer bufer = new StringBuffer(GMT);
		if (timeZone >= 0) {
			bufer.append(PLUS);
		} else {
			bufer.append(MINUS);
		}
		bufer.append(hours);
		if (minutes > 0) {
			bufer.append(TIME_DIV).append(minutes);
		}
		return bufer.toString();
	}

	public static RestTemplate setErrorHandler(RestTemplate restTemplate) throws BadRequestException {
		restTemplate.setErrorHandler(new LoanAggregatorErrorHandler());
		return restTemplate;
	}

	private static final long LEAD_TIME_ACADEMIC = 3 * 60 * 60 * 1000; // 3 Hours
	private static final long LEAD_TIME_FINANCIAL = 18 * 60 * 60 * 1000; // 18 Hours
	private static final String GMT = "GMT";
	private static final String TIME_DIV = ":";
	private static final String PLUS = "+";
	private static final String MINUS = "-";

	public static final long CRM_SUCCESS = 200;
	public static final long CRM_CONNECTION_FAILURE = 1001;
	public static final long CRM_AUTENTICATION_FAILURE = 1004;
	public static final long CRM_TOKEN_EXPIRED = 1005;
	public static final long CRM_TOKEN_INVALID = 1012;
	public static final long CRM_NARROW_SEARCH = 1013;
	public static final long CRM_DATA_NOTFOUND = 1016;
	public static final String CRM_TOKEN_EXPIRY_HEADER = "TokenExpiry";
	public static final long CRM_SLOT_UNAVAILABLE = 1029;

	public static final String CRM_AUTHENTICATE = "AuthenticateUser/AuthenticateUser";
	public static final String CRM_GET_REFERENCEDATA = "ReferenceData/GetReferenceData";
	public static final String CRM_SUBMIT_TICKET = "SubmitTicket/SubmitTicket";
	public static final String CRM_GET_TICKETHISTORY = "TicketHistory/GetTicketHistory";
	public static final String CRM_GET_TICKETDETAILS = "TicketDetail/GetTicketDetails";
	public static final String CRM_UPDATE_TICKET = "UpdateTicket/UpdateTicket";
	public static final String CRM_SEARCH_TICKET = "SearchTicket/SearchTicket";

	public static final String CRM_GET_ADVISINGHISTORY = "Advising/GetAdvisingHistory";
	public static final String CRM_GET_AVAILABLESLOTS = "Advising/GetAvailableSlots";
	public static final String CRM_GET_AVAILABLEDATES = "Advising/GetAvailableBusinessDates";
	public static final String CRM_GET_ADVREFDATA = "Advising/GetReferenceData";
	public static final String CRM_CANCEL_EVENT = "Advising/CancelEvent";
	public static final String CRM_REGISTER_EVENT = "Advising/RegisterEvent";
	public static final String CRM_RESCEDULE_EVENT = "Advising/RescheduleEvent";
	public static final String CRM_GET_ADVISEEINFO = "Advising/GetAdviseeInformation";

	public static final String CRM_SUBMIT_LOAN_TICKET = "SubmitTicketHelpDesk/SubmitTicketHelpDesk";
	public static final String CRM_GET_LOAN_TICKETHISTORY = "TicketHistoryHelpDesk/GetTicketHistoryHelpDesk";
	public static final String CRM_GET_LOAN_TICKETDETAILS = "TicketDetailHelpDesk/GetTicketDetailHelpDesk";
	public static final String CRM_UPDATE_LOAN_TICKET = "UpdateTicketHelpDesk/UpdateTicketHelpDesk";
	public static final String CRM_GET_TICKET_ATTACHMENT = "Attachment/GetAttachmentData";
}
