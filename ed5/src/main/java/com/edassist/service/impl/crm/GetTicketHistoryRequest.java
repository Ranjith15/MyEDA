package com.edassist.service.impl.crm;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetTicketHistoryRequest extends CrmRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3482331542767125319L;

	private String key;

	private String[] values;

	private String clientid;

	/**
	 * @return the key
	 */
	@JsonProperty("KeyType")
	public String getKey() {
		return key;
	}

	/**
	 * @return the values
	 */
	@JsonProperty("Values")
	public String[] getValues() {
		return values;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(String[] values) {
		this.values = values;
	}

	/**
	 * @return the clientid
	 */
	@JsonProperty("ClientId")
	public String getClientid() {
		return clientid;
	}

	/**
	 * @param clientid the clientid to set
	 */
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).toString();
	}

}
