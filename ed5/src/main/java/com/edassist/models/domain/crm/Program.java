package com.edassist.models.domain.crm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Program extends CrmJsonObject {

	@JsonProperty("Id")
	private String Id;

	@JsonProperty("ProgramId")
	private String programId;

	@JsonProperty("ProgramName")
	private String programName;

	/**
	 * @return the id
	 */
	public String getId() {
		return Id;
	}

	/**
	 * @return the programId
	 */
	public String getProgramId() {
		return programId;
	}

	/**
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}
}
