package com.edassist.models.contracts.crm.advising;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import com.edassist.models.domain.crm.CrmJsonObject;
import com.edassist.utils.RestUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "AdvisingSession")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdvisingSession extends CrmJsonObject implements Serializable, Comparable<AdvisingSession> {

	private static final long serialVersionUID = -1324662926035675405L;

	private Long advisingSessionId;
	private Long participantId;
	private Date sessionDate;

	@JsonProperty("ID")
	private String id;
	@JsonProperty("CaseID")
	private String caseId;
	@JsonProperty("Category")
	private String category;
	@JsonProperty("AdvisorName")
	private String advisorName;
	private String appointment;
	@JsonProperty("Status")
	private Integer status;
	@JsonProperty("StatusReason")
	private String statusReason;
	@JsonProperty("SessionType")
	private String sessionType;
	@JsonProperty("CategoryCode")
	private Integer categoryCode;
	private Collection<AdvisingSessionProgram> advisingSessionPrograms;

	@Id
	@Column(name = "AdvisingSessionID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getAdvisingSessionId() {
		return advisingSessionId;
	}

	public void setAdvisingSessionId(Long advisingSessionId) {
		this.advisingSessionId = advisingSessionId;
	}

	@Column(name = "ParticipantID")
	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	@Column(name = "SessionDate")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSessionDate() {
		return sessionDate;
	}

	public void setSessionDate(Date sessionDate) {
		this.sessionDate = sessionDate;
	}

	@Column(name = "SessionID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Transient
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Transient
	public String getAdvisorName() {
		return advisorName;
	}

	public void setAdvisorName(String advisorName) {
		this.advisorName = advisorName;
	}

	@Transient
	public String getAppointment() {
		return appointment;
	}

	@JsonProperty("Appointment")
	public void setAppointment(String appointment) {
		this.appointment = appointment;
		if (appointment != null && appointment.trim().length() > 0) {
			this.setSessionDate(RestUtils.formatCRMDate(appointment));
		}

	}

	@Column(name = "Status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Transient
	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	@Transient
	public String getSessionType() {
		return sessionType;
	}

	public void setSessionType(String sessionType) {
		this.sessionType = sessionType;
	}

	@OneToMany(mappedBy = "advisingSessionId", cascade = CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public Collection<AdvisingSessionProgram> getAdvisingSessionPrograms() {
		return advisingSessionPrograms;
	}

	@JsonProperty("Programs")
	public void setAdvisingSessionPrograms(Collection<AdvisingSessionProgram> advisingSessionPrograms) {
		if (advisingSessionPrograms != null) {
			for (AdvisingSessionProgram program : advisingSessionPrograms) {
				program.setAdvisingSessionId(this);
			}
		}
		this.advisingSessionPrograms = advisingSessionPrograms;
	}

	public void addProgram(AdvisingSessionProgram program) {
		if (advisingSessionPrograms == null) {
			advisingSessionPrograms = new ArrayList<AdvisingSessionProgram>();
		}
		program.setAdvisingSessionId(this);
		advisingSessionPrograms.add(program);
	}

	@Transient
	public Integer getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(Integer categoryCode) {
		this.categoryCode = categoryCode;
	}

	@Transient
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	@Override
	public int compareTo(AdvisingSession o) {
		return o.getSessionDate().compareTo(getSessionDate());
	}

	public static final int STATUS_SCHEDULED = 3;
	public static final int STATUS_CLOSED = 1;
	public static final int CAT_ACADEMIC = 1;
	public static final int CAT_FINANCE = 2;
	public static final int CAT_LOANREPAY = 3;

}
