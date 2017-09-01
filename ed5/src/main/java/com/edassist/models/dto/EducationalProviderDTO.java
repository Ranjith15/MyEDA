package com.edassist.models.dto;

import java.util.Collection;

public class EducationalProviderDTO implements Comparable<EducationalProviderDTO> {

	private Long educationalProviderId;
	private String providerName;
	private String providerCode;
	private String providerAddress1;
	private String providerAddress2;
	private String providerCity;
	private String providerState;
	private String providerZip;
	private boolean featuredProvider;
	private boolean enhancedEdAssistNetwork;
	private Collection<ProviderAccreditationDTO> providerAccreditationCollection;

	public void setEducationalProviderId(Long educationalProviderId) {
		this.educationalProviderId = educationalProviderId;
	}

	public Long getEducationalProviderId() {
		return educationalProviderId;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderAddress1(String providerAddress1) {
		this.providerAddress1 = providerAddress1;
	}

	public String getProviderAddress1() {
		return providerAddress1;
	}

	public void setProviderAddress2(String providerAddress2) {
		this.providerAddress2 = providerAddress2;
	}

	public String getProviderAddress2() {
		return providerAddress2;
	}

	public void setProviderCity(String providerCity) {
		this.providerCity = providerCity;
	}

	public String getProviderCity() {
		return providerCity;
	}

	public void setProviderState(String providerState) {
		this.providerState = providerState;
	}

	public String getProviderState() {
		return providerState;
	}

	public void setProviderZip(String providerZip) {
		this.providerZip = providerZip;
	}

	public String getProviderZip() {
		return providerZip;
	}

	public void setFeaturedProvider(boolean featuredProvider) {
		this.featuredProvider = featuredProvider;
	}

	public boolean getFeaturedProvider() {
		return featuredProvider;
	}

	public void setEnhancedEdAssistNetwork(boolean enhancedEdAssistNetwork) {
		this.enhancedEdAssistNetwork = enhancedEdAssistNetwork;
	}

	public boolean isEnhancedEdAssistNetwork() {
		return enhancedEdAssistNetwork;
	}

	public void setProviderAccreditationCollection(Collection<ProviderAccreditationDTO> providerAccreditationCollection) {
		this.providerAccreditationCollection = providerAccreditationCollection;
	}

	public Collection<ProviderAccreditationDTO> getProviderAccreditationCollection() {
		return providerAccreditationCollection;
	}

	@Override
	public int compareTo(EducationalProviderDTO educationalProviderDTO) {
		if (educationalProviderDTO != null && educationalProviderDTO.getProviderName() != null) {
			return this.getProviderName().compareTo(educationalProviderDTO.getProviderName());
		}
		return 1;
	}

}
