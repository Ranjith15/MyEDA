package com.edassist.constants;

public class ApplicationConstants {

	public static final String APPLICATION_ID = "applicationID";
	public static final String APPLICATION = "application";
	public static final String APPEAL = "appeal";

	public static final String EVENT_HISTORY_INCOMPLETE_STATUS = "INCOMPLETE";
	public static final String EVENT_HISTORY_MANUAL_TYPE = "MANUAL";
	public static final String EVENT_HISTORY_RULES_TYPE = "RULES";
	public static final String EVENT_HISTORY_DENY_STATUS = "DENY";

	public static final Long APPLICATION_STATUS_SAVED_NOT_SUBMITTED = Long.valueOf(90L);
	public static final Long APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW = Long.valueOf(100L);
	public static final Long APPLICATION_STATUS_SUBMITTED_INCOMPLETE = Long.valueOf(110L);
	public static final Long APPLICATION_STATUS_APPROVED = Long.valueOf(120L);
	public static final Long APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW = Long.valueOf(125L);
	public static final Long APPLICATION_STATUS_APPROVED_REIMBURSEMENT_DOCUMENTATION = Long.valueOf(130L);
	public static final Long APPLICATION_STATUS_CARRYOVER_PAYMENT_APPROVED = Long.valueOf(135L);
	public static final Long APPLICATION_STATUS_LOC = Long.valueOf(400L);
	public static final Long APPLICATION_STATUS_PAYMENT_PROCESSING_INCOMPLETE_INFORMATION_NEEDED = Long.valueOf(450L);
	public static final Long APPLICATION_STATUS_PAYMENT_APPROVED_FUND_PROGRESS = Long.valueOf(510L);
	public static final Long APPLICATION_STATUS_PAYMENT_COMPLETE_COMPLETION_DOCUMENTS_REQUIRED = Long.valueOf(530L);
	public static final Long APPLICATION_STATUS_PAYMENT_APPROVED_PROGRESS = Long.valueOf(510L);
	public static final Long APPLICATION_STATUS_PAYMENT_COMPLETE = Long.valueOf(520L);
	public static final Long APPLICATION_STATUS_PAYMENT_INCOMPLETE_COMPLETION_DOCUMENTS_REQUIRED = Long.valueOf(530L);
	public static final Long APPLICATION_STATUS_REPAYMENT_REQUIRED = Long.valueOf(540L);
	public static final Long APPLICATION_STATUS_SENT_COLLECTIONS = Long.valueOf(545L);
	public static final Long APPLICATION_STATUS_PAYMENT_REIMBURSEMENT_PROGRESS = Long.valueOf(500L);
	public static final Long APPLICATION_STATUS_PAYMENT_REVIEW = Long.valueOf(425L);
	public static final Long APPLICATION_STATUS_CLOSED = Long.valueOf(900L);
	public static final Long APPLICATION_STATUS_CANCELLED = Long.valueOf(910L);
	public static final Long APPLICATION_STATUS_VOID = Long.valueOf(920L);
	public static final Long APPLICATION_STATUS_DENIED = Long.valueOf(930L);
	public static final Long APPLICATION_STATUS_TESTING_IN_PROGRESS = Long.valueOf(710L);
	public static final Long APPLICATION_STATUS_TESTING_COMPLETE = Long.valueOf(715L);
	public static final Long APPLICATION_STATUS_FORWARDED_TO_HR_FOR_REVIEW = Long.valueOf(95L);
	public static final Long APPLICATION_STATUS_CERTIFICATE_ISSUED = Long.valueOf(720L);
	public static final Long APPLICATION_STATUS_MATERIAL_SENT = Long.valueOf(300L);
	public static final Long APPLICATION_STATUS_RESEND_MATERIAL = Long.valueOf(301L);

	public static final Long APPLICATION_APPEAL_STATUS_SAVED = Long.valueOf(1L);
	public static final Long APPLICATION_APPEAL_STATUS_SUBMITTED = Long.valueOf(2L);
	public static final Long APPLICATION_APPEAL_STATUS_INCOMPLETE = Long.valueOf(3L);
	public static final Long APPLICATION_APPEAL_STATUS_APPROVED = Long.valueOf(4L);
	public static final Long APPLICATION_APPEAL_STATUS_DENIED = Long.valueOf(5L);
	public static final Long APPLICATION_APPEAL_STATUS_FORWARDED_TO_DESIGNEE = Long.valueOf(6L);
	public static final Long APPLICATION_APPEAL_STATUS_CANCELLED = Long.valueOf(7L);

	public static final Long APPLICATION_TYPE_HOME_STUDY = Long.valueOf(100L);
	public static final Long APPLICATION_TYPE_PREPAY = Long.valueOf(300L);
	public static final Long APPLICATION_TYPE_REIMBURSEMENT = Long.valueOf(400L);
	public static final Long APPLICATION_TYPE_BOOK_REIMBURSEMENT = Long.valueOf(500L);

	public static final Long APPLICATION_DOC_TYPE_APPEAL_TP = Long.valueOf(408L);
	public static final Long APPLICATION_DOC_TYPE_APPEAL_TR = Long.valueOf(508L);

	public static final String SESSION_TAMS_CLIENT_BRANDING = "TAMS_CLIENT_BRANDING";
	public static final String SESSION_TAMS_CLIENT_ID = "TAMS_CLIENT_ID";
	public static final String SESSION_TAMS_USER_ID = "TAMS_USER_ID";

	public static final Long SECURITY_ADMIN_CLIENT_ID = Long.valueOf(-100);
	public static final int DISPLAYED_IN_PROVIDER_SEARCH = 9;

}
