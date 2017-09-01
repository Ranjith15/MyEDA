package com.edassist.constants;

public enum AppTeamFilterConstants {

	TEAM_MEMBER_ME("fixed options", "You", "me"), TEAM_MEMBER_TEAM("fixed options", "Your Team", "team"), TEAM_MEMBER_COMPANY("fixed options", "Your Company", "all");

	private final String group;
	private final String display;
	private final String value;

	AppTeamFilterConstants(String group, String display, String value) {
		this.group = group;
		this.display = display;
		this.value = value;
	}

	public String getGroup() {
		return group;
	}

	public String getDisplay() {
		return display;
	}

	public String getValue() {
		return value;
	}

}