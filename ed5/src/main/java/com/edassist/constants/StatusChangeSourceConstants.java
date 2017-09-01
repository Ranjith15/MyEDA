package com.edassist.constants;

public enum StatusChangeSourceConstants {

	SOURCE_GENERAL("General", "1"), SOURCE_RULES("Program Rule", "2"), SOURCE_MANUAL("Message", "3"), SOURCE_ADJUSTMENT("Adjustment", "4");

	private final String display;
	private final String value;

	StatusChangeSourceConstants(String display, String value) {
		this.display = display;
		this.value = value;
	}

	public String getDisplay() {
		return display;
	}

	public String getValue() {
		return value;
	}

}
