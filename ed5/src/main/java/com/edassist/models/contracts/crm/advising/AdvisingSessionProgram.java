package com.edassist.models.contracts.crm.advising;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.edassist.models.domain.crm.CrmJsonObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "AdvisingSessionProgram")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdvisingSessionProgram extends CrmJsonObject implements Serializable {

	private static final long serialVersionUID = 6976150141853375981L;

	private Long advisingSessionProgramId;
	private AdvisingSession advisingSessionId;

	@JsonProperty("ProgramId")
	private Integer programId;
	@JsonProperty("Id")
	private String id;
	@JsonProperty("ProgramName")
	private String programName;

	@Id
	@Column(name = "AdvisingSessionProgramID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getAdvisingSessionProgramId() {
		return advisingSessionProgramId;
	}

	public void setAdvisingSessionProgramId(Long advisingSessionProgramId) {
		this.advisingSessionProgramId = advisingSessionProgramId;
	}

	@ManyToOne
	@JoinColumn(name = "AdvisingSessionId", referencedColumnName = "AdvisingSessionID")
	public AdvisingSession getAdvisingSessionId() {
		return advisingSessionId;
	}

	public void setAdvisingSessionId(AdvisingSession advisingSessionId) {
		this.advisingSessionId = advisingSessionId;
	}

	@Column(name = "ProgramID")
	public Integer getProgramId() {
		return this.programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	@Transient
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Transient
	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

}
