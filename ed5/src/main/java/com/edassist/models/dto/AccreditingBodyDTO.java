package com.edassist.models.dto;

public class AccreditingBodyDTO {

	private Long accreditingBodyID;
	private String accreditingBodyName;
	private String accreditingBodyDisplayName;

	public void setAccreditingBodyID(Long accreditingBodyID) {
		this.accreditingBodyID = accreditingBodyID;
	}

	public Long getAccreditingBodyID() {
		return accreditingBodyID;
	}

	public void setAccreditingBodyName(String accreditingBodyName) {
		this.accreditingBodyName = accreditingBodyName;
	}

	public String getAccreditingBodyName() {
		return accreditingBodyName;
	}

	public void setAccreditingBodyDisplayName(String accreditingBodyDisplayName) {
		this.accreditingBodyDisplayName = accreditingBodyDisplayName;
	}

	public String getAccreditingBodyDisplayName() {
		return accreditingBodyDisplayName;
	}

}
