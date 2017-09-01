package com.edassist.models.dto.crm.advising;

public class AdvisingSessionProgramDTO {

	private Long advisingSessionProgramId;
	private AdvisingSessionDTO advisingSessionIdDTO;
	private Integer programId;
	private String id;
	private String programName;

	public Long getAdvisingSessionProgramId() {
		return advisingSessionProgramId;
	}

	public AdvisingSessionDTO getAdvisingSessionIdDTO() {
		return advisingSessionIdDTO;
	}

	public Integer getProgramId() {
		return programId;
	}

	public String getId() {
		return id;
	}

	public String getProgramName() {
		return programName;
	}

	public void setAdvisingSessionProgramId(Long advisingSessionProgramId) {
		this.advisingSessionProgramId = advisingSessionProgramId;
	}

	public void setAdvisingSessionIdDTO(AdvisingSessionDTO advisingSessionIdDTO) {
		this.advisingSessionIdDTO = advisingSessionIdDTO;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

}
