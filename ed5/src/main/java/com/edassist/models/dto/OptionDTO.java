package com.edassist.models.dto;

public class OptionDTO {

	public OptionDTO() {}

	public OptionDTO(String display, String value, String group) {
		this.display = display;
		this.value = value;
		this.group = group;
	}

	private String group;
	private String display;
	private String value;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
