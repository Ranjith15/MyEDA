package com.edassist.service.impl.crm;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class CrmResponse implements Serializable {

	@JsonProperty("ResponseCode")
	private long responseCode;

	/**
	 * @return the responseCode
	 */
	public long getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(long responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}
}
