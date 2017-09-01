package com.edassist.models.contracts;

public class Option {

	private String group;
	private String display;
	private String value;

	public Option(String group, String display, String value) {
		this.group = group;
		this.display = display;
		this.value = value;
	}
	public Option(String display, String value) {
		this.display = display;
		this.value = value;
	}


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
