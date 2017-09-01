package com.edassist.models.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ProgramProviders")
public class ProgramProviders {

	@Id
	@Column(name = "ProgramProviderID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long programProviderID;

	@Column(name = "ProviderCode")
	private String providerCode;

	@Column(name = "ProviderStatusID")
	private Integer providerStatusID;

	@Column(name = "ProviderEligibility")
	private Integer providerEligibility;

	@Column(name = "ProgramDiscountPercent")
	private BigDecimal programDiscountPercent;

	@JoinColumn(name = "ClientID", referencedColumnName = "ClientID")
	@ManyToOne(optional = false)
	private Client clientID;

	@JoinColumn(name = "ProgramID", referencedColumnName = "ProgramID")
	@ManyToOne(optional = false)
	private Program programID;

	@JoinColumn(name = "EducationalProviderID", referencedColumnName = "EducationalProviderID")
	@ManyToOne
	private EducationalProviders educationalProviderID;

	public ProgramProviders() {
	}

	public ProgramProviders(Long programProviderID) {
		this.programProviderID = programProviderID;
	}

	public ProgramProviders(Long programProviderID, Integer providerStatusID, BigDecimal programDiscountPercent) {
		this.programProviderID = programProviderID;
		this.providerStatusID = providerStatusID;
		this.programDiscountPercent = programDiscountPercent;
	}

	public Long getProgramProviderID() {
		return programProviderID;
	}

	public void setProgramProviderID(Long programProviderID) {
		this.programProviderID = programProviderID;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public Integer getProviderStatusID() {
		return providerStatusID;
	}

	public void setProviderStatusID(Integer providerStatusID) {
		this.providerStatusID = providerStatusID;
	}

	public Integer getProviderEligibility() {
		return providerEligibility;
	}

	public void setProviderEligibility(Integer providerEligibility) {
		this.providerEligibility = providerEligibility;
	}

	public BigDecimal getProgramDiscountPercent() {
		return programDiscountPercent;
	}

	public void setProgramDiscountPercent(BigDecimal programDiscountPercent) {
		this.programDiscountPercent = programDiscountPercent;
	}

	public Client getClientID() {
		return clientID;
	}

	public void setClientID(Client clientID) {
		this.clientID = clientID;
	}

	public Program getProgramID() {
		return programID;
	}

	public void setProgramID(Program programID) {
		this.programID = programID;
	}

	public EducationalProviders getEducationalProviderID() {
		return educationalProviderID;
	}

	public void setEducationalProviderID(EducationalProviders educationalProviderID) {
		this.educationalProviderID = educationalProviderID;
	}

}
