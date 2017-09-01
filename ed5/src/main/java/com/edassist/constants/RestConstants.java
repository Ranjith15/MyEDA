package com.edassist.constants;

public class RestConstants {

	public static final String REST_ISSUER_NAME = "edAssist.com";
	public static final int SESSION_TOKEN_LENGTH = 30 * 60000; // 30 minutes
	public static final int REFRESH_TOKEN_LENGTH = 8 * 30 * 60000; // 8 hours
	public static final String REFRESH_TOKEN_NAME = "refresh";
	public static final String SESSION_TOKEN_NAME = "session";
	public static final String OAUTH_END_POINT = "https://sso-dev.edassist.com/as/token.oauth2?";
	public static final String OAUTH_CLIENT_ID = "edassistMobileApp";

	public static final String ADMIN_CLIENTCODE = "admin";
	public static final Long ADMIN_CLIENTID = -100L;
	public static final String ADMIN_CLIENTNAME = "Admin";

	// messages
	public static final String REST_RESET_PASSWORD_EMAIL = "Email has been sent to: ";
	public static final String REST_PASSWORD_CHANGED = "Password successfully changed";

	// error messages
	public static final String REST_MISSING_REQUIRED_FIELD = "Missing required fields.";
	public static final String BAD_REQUEST_MESSAGE = "Invalid Request.";
	public static final String INVALID_LOGIN_MESSAGE = "Invalid Login Information";
	public static final String NOT_FOUND_MESSAGE = "There is no resource behind the URI";
	public static final String CONTENT_NOT_FOUND_MESSAGE = "No Content found";
	public static final String REST_UNPROCESSABLE_ENTITY = "The server cannot process the entity";
	public static final String REST_UNAUTHORIZED = "Authentication credentials were missing or incorrect";
	public static final String REST_FORBIDDEN = "The server understood the request, but no permissions for that user";
	public static final String REST_MOBILE_FORBIDDEN = "Client is not mobile enabled";
	public static final String REST_INTERNAL_SERVER_ERROR = "Internal Server Error";
	public static final String REST_EXCEEDED_MAX_RESULT = "Exceeded Max Results. Please modify search and try again";
	public static final String REST_UNEXPECTED_ERROR = "Unexpected error.";
	public static final String REST_NOT_CURRENT_USER = "User details does not match current user";
	public static final String REST_EDASSIST5_FORBIDDEN = "Client is not EdAssist 5 enabled";
	public static final String REST_LRP_FORBIDDEN = "Client is not Loan Repay enabled";
	public static final String CRM_ERROR_MESSAGE = "CRM Communication error";
	public static final String INVALID_APPLICATION_STATUS = "Invalid Application Status";
	public static final String INCOMPLETE_APPLICATION = "INCOMPLETE_APPLICATION";
	public static final String INCOMPLETE_APPLICATION_COURSES = "INCOMPLETE_APPLICATION_COURSES";
	public static final String INCOMPLETE_APPLICATION_AGREEMENTS = "INCOMPLETE_APPLICATION_AGREEMENTS";
	public static final String UNAUTHORIZED_FOR_MONGO = "User is not allowed to edit content";
	public static final String UNAUTHORIZED = "User is not authorized to view this page";
	public static final String NOT_UNIQUE = "Multiple entries found. Please refine request";
	public static final String NO_ENTRIES_FOUND = "No entries found. Please refine request";
	public static final int CRM_TIMEZONE = 85;

	// Expensetype relation
	public static final String EXPENSETYPE_COURSE = "CRS";
	public static final String EXPENSETYPE_NON_COURSE = "NONCRS";

	// Application Errors
	public static final String MAX_COURSES_EXCEEDED = "Maximum number of courses limit exceeded";
}
