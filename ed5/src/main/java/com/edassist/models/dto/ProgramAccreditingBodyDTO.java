package com.edassist.models.dto;

public class ProgramAccreditingBodyDTO {

	private Long programAccreditingBodyID;
	private AccreditingBodyDTO accreditingBody;

	public void setProgramAccreditingBodyID(Long programAccreditingBodyID) {
		this.programAccreditingBodyID = programAccreditingBodyID;
	}

	public Long getProgramAccreditingBodyID() {
		return programAccreditingBodyID;
	}

	public AccreditingBodyDTO getAccreditingBody() {
		return accreditingBody;
	}

	public void setAccreditingBody(AccreditingBodyDTO accreditingBody) {
		this.accreditingBody = accreditingBody;
	}

}
