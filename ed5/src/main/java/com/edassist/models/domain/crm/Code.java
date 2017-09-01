package com.edassist.models.domain.crm;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Code extends CrmJsonObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6858780717939855746L;

	@JsonProperty("ID")
	private String ID;

	@JsonProperty("Description")
	private String description;

	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
