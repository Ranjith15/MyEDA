package com.edassist.service.impl.crm;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class CrmRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -741286183966214314L;

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}

}
