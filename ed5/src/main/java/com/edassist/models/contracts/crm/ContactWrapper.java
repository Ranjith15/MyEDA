package com.edassist.models.contracts.crm;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edassist.models.domain.crm.Contact;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactWrapper {
	private Contact contact;

	@JsonProperty("Contact")
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}

}
