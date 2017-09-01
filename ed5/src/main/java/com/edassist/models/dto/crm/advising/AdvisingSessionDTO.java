package com.edassist.models.dto.crm.advising;

import java.util.Collection;
import java.util.Date;

public class AdvisingSessionDTO {

	private Long advisingSessionId;
	private Long participantId;
	private Date sessionDate;
	private String id;
	private String caseId;
	private String category;
	private String advisorName;
	private String appointment;
	private Integer status;
	private String statusReason;
	private String sessionType;
	private Integer categoryCode;
	private Collection<AdvisingSessionProgramDTO> advisingSessionProgramsDTO;

	public Long getAdvisingSessionId() {
		return advisingSessionId;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public Date getSessionDate() {
		return sessionDate;
	}

	public String getId() {
		return id;
	}

	public String getCaseId() {
		return caseId;
	}

	public String getCategory() {
		return category;
	}

	public String getAdvisorName() {
		return advisorName;
	}

	public String getAppointment() {
		return appointment;
	}

	public Integer getStatus() {
		return status;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public String getSessionType() {
		return sessionType;
	}

	public Integer getCategoryCode() {
		return categoryCode;
	}

	public Collection<AdvisingSessionProgramDTO> getAdvisingSessionProgramsDTO() {
		return advisingSessionProgramsDTO;
	}

	public void setAdvisingSessionId(Long advisingSessionId) {
		this.advisingSessionId = advisingSessionId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public void setSessionDate(Date sessionDate) {
		this.sessionDate = sessionDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setAdvisorName(String advisorName) {
		this.advisorName = advisorName;
	}

	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	public void setSessionType(String sessionType) {
		this.sessionType = sessionType;
	}

	public void setCategoryCode(Integer categoryCode) {
		this.categoryCode = categoryCode;
	}

	public void setAdvisingSessionProgramsDTO(Collection<AdvisingSessionProgramDTO> advisingSessionProgramsDTO) {
		this.advisingSessionProgramsDTO = advisingSessionProgramsDTO;
	}

}
