package com.edassist.models.dto;

public class ClientLoginDetailsDTO {

	private boolean ssoEnabled;
	private Long clientId;
	private String clientCode;
	private String clientName;
	private String ssoType;
	private String mobileSSORedirectURL;
	private String authCodeEndPoint;
	private String oAuthClientId;
	private String clientSSOErrorRedirectURL;
	private String clientSSOLogOffURL;
	private String clientNetworkURL;
	private boolean rememberMe;
	private boolean showAddressButtonsYN;
	private boolean crmIntegration;
	private boolean selfScheduledAdvising;
	private boolean mandatoryAdvising;
	private boolean enhancedEdAssistNetwork;
	private boolean ssoLogoffEnabled;

	public boolean isSsoEnabled() {
		return ssoEnabled;
	}

	public void setSsoEnabled(boolean ssoEnabled) {
		this.ssoEnabled = ssoEnabled;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getSSOType() {
		return ssoType;
	}

	public void setSSOType(String ssoType) {
		this.ssoType = ssoType;
	}

	public String getMobileSSORedirectURL() {
		return mobileSSORedirectURL;
	}

	public void setMobileSSORedirectURL(String mobileSSORedirectURL) {
		this.mobileSSORedirectURL = mobileSSORedirectURL;
	}

	public String getClientNetworkURL() {
		return clientNetworkURL;
	}

	public void setClientNetworkURL(String clientNetworkURL) {
		this.clientNetworkURL = clientNetworkURL;
	}

	public String getAuthCodeEndPoint() {
		return authCodeEndPoint;
	}

	public void setAuthCodeEndPoint(String authCodeEndPoint) {
		this.authCodeEndPoint = authCodeEndPoint;
	}

	public String getoAuthClientId() {
		return oAuthClientId;
	}

	public void setoAuthClientId(String oAuthClientId) {
		this.oAuthClientId = oAuthClientId;
	}

	public String getClientSSOErrorRedirectURL() {
		return clientSSOErrorRedirectURL;
	}

	public void setClientSSOErrorRedirectURL(String clientSSOErrorRedirectURL) {
		this.clientSSOErrorRedirectURL = clientSSOErrorRedirectURL;
	}

	public String getClientSSOLogOffURL() {
		return clientSSOLogOffURL;
	}

	public void setClientSSOLogOffURL(String clientSSOLogOffURL) {
		this.clientSSOLogOffURL = clientSSOLogOffURL;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public boolean isShowAddressButtonsYN() {
		return showAddressButtonsYN;
	}

	public void setShowAddressButtonsYN(boolean showAddressButtonsYN) {
		this.showAddressButtonsYN = showAddressButtonsYN;
	}

	public boolean isCrmIntegration() {
		return crmIntegration;
	}

	public void setCrmIntegration(boolean crmIntegration) {
		this.crmIntegration = crmIntegration;
	}

	public boolean isSelfScheduledAdvising() {
		return selfScheduledAdvising;
	}

	public void setSelfScheduledAdvising(boolean selfScheduledAdvising) {
		this.selfScheduledAdvising = selfScheduledAdvising;
	}

	public boolean isMandatoryAdvising() {
		return mandatoryAdvising;
	}

	public void setMandatoryAdvising(boolean mandatoryAdvising) {
		this.mandatoryAdvising = mandatoryAdvising;
	}

	public boolean isEnhancedEdAssistNetwork() {
		return enhancedEdAssistNetwork;
	}

	public void setEnhancedEdAssistNetwork(boolean enhancedEdAssistNetwork) {
		this.enhancedEdAssistNetwork = enhancedEdAssistNetwork;
	}
	public boolean isSsoLogoffEnabled() {
		return ssoLogoffEnabled;
	}

	public void setSsoLogoffEnabled(boolean ssoLogoffEnabled) {
		this.ssoLogoffEnabled = ssoLogoffEnabled;
	}
}
