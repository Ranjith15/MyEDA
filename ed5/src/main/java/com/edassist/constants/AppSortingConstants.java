package com.edassist.constants;

public enum AppSortingConstants {

	SUBMITTED_DATE ("Date Submitted", "submittedDate", "DESC"),
	TEAM_MEMBER("Team Member", "participantID.user.firstName", "ASC"),
	APP_STATUS("Application Status", "applicationStatusID.applicationStatus", "ASC"),
	APP_NUMBER("Application Number", "applicationNumber", "DESC"),
	EDUCATIONAL_PROVIDER("Education Provider", "educationalProvider.providerName", "ASC");
	
	private final String display;
	private final String value;
	private final String direction;

	AppSortingConstants(String display, String value, String direction) {
		this.display = display;
		this.value = value;
		this.direction = direction;
	}

	public String getDisplay() {
		return display;
	}

	public String getValue() {
		return value;
	}

	public String getDirection() {
		return direction;
	}
}
