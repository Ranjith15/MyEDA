package com.edassist.models.sp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.NamedNativeQuery(name = "getNextApplicationNumber", query = "{call getNextApplicationNumberTAMS4(:applicationNumber OUTPUT)}", resultClass = ApplicationNumber.class)
public class ApplicationNumber {

	@Id
	private Long applicationNumber;

	public Long getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
}
