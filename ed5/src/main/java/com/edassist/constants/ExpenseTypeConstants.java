package com.edassist.constants;

public enum ExpenseTypeConstants {

	BOOKS(3L, "BKS"),
	NON_COURSE_RELATED_FEE(19L, "FEE");

	private final Long id;
	private final String code;

	ExpenseTypeConstants(Long id, String code) {
		this.id = id;
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}
}
