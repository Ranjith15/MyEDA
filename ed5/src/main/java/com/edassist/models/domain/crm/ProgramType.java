package com.edassist.models.domain.crm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProgramType extends CrmJsonObject {
	@JsonProperty("ProgramTypeID")
	private String programTypeID;
	@JsonProperty("ProgramType")
	private String programType;
	@JsonProperty("ProgramTypeCode")
	private String programTypeCode;

	/**
	 * @return the programTypeID
	 */
	public String getProgramTypeID() {
		return programTypeID;
	}

	/**
	 * @return the programType
	 */
	public String getProgramType() {
		return programType;
	}

	/**
	 * @return the programTypeCode
	 */
	public String getProgramTypeCode() {
		return programTypeCode;
	}

	/**
	 * @param programTypeID the programTypeID to set
	 */
	public void setProgramTypeID(String programTypeID) {
		this.programTypeID = programTypeID;
	}

	/**
	 * @param programType the programType to set
	 */
	public void setProgramType(String programType) {
		this.programType = programType;
	}

	/**
	 * @param programTypeCode the programTypeCode to set
	 */
	public void setProgramTypeCode(String programTypeCode) {
		this.programTypeCode = programTypeCode;
	}
}
