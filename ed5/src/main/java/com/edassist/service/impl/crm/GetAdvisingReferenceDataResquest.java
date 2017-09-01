package com.edassist.service.impl.crm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAdvisingReferenceDataResquest extends CrmRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 104569322260196111L;

	private String category;

	/**
	 * @return the category
	 */
	@JsonProperty("Category")
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

}
