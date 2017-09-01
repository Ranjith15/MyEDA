package com.edassist.models.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

/**
 *
 */
@Entity
@Table(name = "Client")
public class Client implements Serializable {
	public static final String TAMS_UNIQUE_ID = "TAMS Unique ID";
	public static final String EMPLOYEE_ID = "Employee ID";

	private static final long serialVersionUID = 3697782163067448233L;
	public static final String ACTIVE = "1";
	public static final String INACTIVE = "2";

	public Client() {
		super();
	}

	private Long clientId;

	private String clientName;

	private String clientTypeString;

	@Column(name = "ContactEmail")
	private String contactEmail;

	@Column(name = "DefaultEmailAddress")
	private String defaultEmailAddress;

	@Column(name = "DefaultMailingAddress")
	private String defaultMailingAddress;

	@Transient
	public String getClientTypeString() {
		return clientTypeString;
	}

	public void setClientTypeString(String clientTypeString) {
		this.clientTypeString = clientTypeString;
	}

	private ClientType clientType;

	// private Integer clientType;

	private Integer defaultUserName;

	private String alternateClientUrl;

	private String clientBranding;

	private String active;

	/*
	 * @Column(name = "Address1") private String address1;
	 * @Column(name = "Address2") private String address2;
	 * @Column(name = "City") private String city;
	 * @Column(name = "State") private String state;
	 * @Column(name = "Zipcode") private String zipCode;
	 * @Column(name = "Country") private String country;
	 * @Column(name = "Phone") private String phone;
	 * @Column(name = "PhoneExt") private String phoneExt;
	 */
	@Column(name = "FAX")
	private String fax;

	private String contactName;
	/*
	 * @Column(name = "ContactPhone") private String contactPhone;
	 * @Column(name = "ContactExt") private String contactExt;
	 */
	@Column(name = "Email")
	private String email;

	@Column(name = "ContactPhone")
	private String contactPhone;

	private String url;
	/*
	 * @Column(name = "Logo") private byte[] logo;
	 */

	private String genericLabel1;

	private String genericLabel2;

	private String genericLabel3;

	private String genericLabel4;

	private String genericLabel5;

	private String genericLabel6;

	private String genericLabel7;

	private String genericLabel8;

	private String genericLabel9;

	private String genericLabel10;
	// @Column(name = "ReportLogo")
	// private byte[] reportLogo;

	private String accountNumber;

	private boolean showAddressButtonsYN;

	// Valid chars: 'E', 'S', 'U'
	private Character supervisorIDType;

	private String clientCode;

	private boolean TAMS4;

	private boolean ValidateEmailAtAppCreation;

	private boolean enableTaxReversals;

	private String ssoType;


	private String pingOneIdpId;

	private String clientSSORedirectURL;

	private String clientSSOLogOffURL;

	private String clientSSOErrorRedirectURL;

	private String mobileSSORedirectURL;

	private String clientNetworkURL;
	// private boolean resendSupervisorApproval;

	// private Long resendSupervisorApprovalInterval = 0L;

	private boolean viewAllSupportingDocs;

	private boolean featuredProviderNetwork;
	private boolean enhancedEdAssistNetwork;

	private String LW_Passphrase;

	private boolean crmIntegration;
	private boolean mandatoryAdvising;
	private boolean selfScheduledAdvising;
	private boolean cxc;
	// Depenedent TAP

	@Column(name = "AllowAddDependents")
	private Boolean allowAddDependents;

	@Column(name = "AllowDocumentUpload")
	private Boolean allowDocumentUpload;

	@Column(name = "RequiresVerificationDI")
	private Boolean requiresVerificationDI;

	@Column(name = "AllowAddEduProg")
	private Boolean allowAddEduProg;

	private Long approverLevel1;

	private Long approverLevel2;

	private Long approverLevel3;

	private Long approverLevel4;

	private Long approverLevel5;

	@Column(name = "MobileEnabled")
	private boolean mobileEnabled;

	@Column(name = "ClientMobileCode")
	private String clientMobileCode;

	@Column(name = "IDC_Enabled")
	private boolean idc_enabled;

	@Column(name = "Edassist5Enabled")
	private boolean edassist5Enabled;

	@Column(name = "SSOLogoffEnabled")
	private boolean ssoLogoffEnabled;

	@Column(name = "RememberMe")
	private boolean rememberMe;

	@Transient
	public String getIdc_EnabledDesc() {
		if (this.isIdc_enabled()) {
			return "Yes";
		} else {
			return "No";
		}
	}

	private List<Long> dependentApprovalLevels;

	@Transient
	public List<Long> getDependentApprovalLevels() {
		return dependentApprovalLevels;
	}

	public void setDependentApprovalLevels(List<Long> dependentApprovalLevels) {
		this.dependentApprovalLevels = dependentApprovalLevels;
	}

	public Boolean getAllowAddDependents() {
		return allowAddDependents;
	}

	public Boolean getRequiresVerificationDI() {
		return requiresVerificationDI;
	}

	public void setAllowAddDependents(Boolean allowAddDependents) {
		this.allowAddDependents = allowAddDependents;
	}

	public void setRequiresVerificationDI(Boolean requiresVerificationDI) {
		if (requiresVerificationDI == null) {
			requiresVerificationDI = Boolean.FALSE;
		}
		this.requiresVerificationDI = requiresVerificationDI;
	}

	/**************************************************************************
	 *
	 * GETTERS
	 *
	 *************************************************************************/

	@Column(name = "ViewAllSupportingDocs")
	// @Type(type="onezero-boolean")
	public boolean isViewAllSupportingDocs() {
		return viewAllSupportingDocs;
	}

	@Column(name = "ShowAddressButtonsYN")
	// @Type(type="onezero-boolean")
	public boolean isShowAddressButtonsYN() {
		return showAddressButtonsYN;
	}

	@Column(name = "TAMS4")
	public boolean isTAMS4() {
		return TAMS4;
	}

	@Column(name = "ValidateEmailAtAppCreation")
	public boolean isValidateEmailAtAppCreation() {
		return ValidateEmailAtAppCreation;
	}

	@Column(name = "enableTaxReversals")
	public boolean isEnableTaxReversals() {
		return enableTaxReversals;
	}

	/*
	 * @Column(name = "ResendSupervisorApproval") public boolean isResendSupervisorApproval() { return resendSupervisorApproval; }
	 * @Column(name = "ResendSupervisorApprovalInterval") public Long getResendSupervisorApprovalInterval() { return resendSupervisorApprovalInterval; }
	 */

	@Column(name = "SupervisorIDType")
	public Character getSupervisorIDType() {
		return supervisorIDType;
	}

	@Column(name = "ClientName")
	public String getClientName() {
		return clientName;
	}

	@Id
	@Column(name = "ClientID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getClientId() {
		return clientId;
	}

	@Column(name = "ClientBranding")
	public String getClientBranding() {
		return clientBranding;
	}

	@Column(name = "ClientCode")
	public String getClientCode() {
		return clientCode;
	}

	@Column(name = "Active")
	public String getActive() {
		return active;
	}

	@Column(name = "ContactName")
	public String getContactName() {
		return contactName;
	}

	@Column(name = "URL")
	public String getUrl() {
		return url;
	}

	@Column(name = "GenericLabel1")
	public String getGenericLabel1() {
		return genericLabel1;
	}

	@Column(name = "GenericLabel2")
	public String getGenericLabel2() {
		return genericLabel2;
	}

	@Column(name = "GenericLabel3")
	public String getGenericLabel3() {
		return genericLabel3;
	}

	@Column(name = "GenericLabel4")
	public String getGenericLabel4() {
		return genericLabel4;
	}

	@Column(name = "GenericLabel5")
	public String getGenericLabel5() {
		return genericLabel5;
	}

	@Column(name = "GenericLabel6")
	public String getGenericLabel6() {
		return genericLabel6;
	}

	@Column(name = "GenericLabel7")
	public String getGenericLabel7() {
		return genericLabel7;
	}

	/*
	 * public byte[] getLogo() { return logo; }
	 */

	@Column(name = "GenericLabel8")
	public String getGenericLabel8() {
		return genericLabel8;
	}

	@Column(name = "GenericLabel9")
	public String getGenericLabel9() {
		return genericLabel9;
	}

	@Column(name = "GenericLabel10")
	public String getGenericLabel10() {
		return genericLabel10;
	}

	public boolean isMobileEnabled() {
		return mobileEnabled;
	}

	public String getClientMobileCode() {
		return clientMobileCode;
	}

	@JoinColumn(name = "ClientType", referencedColumnName = "ClientTypeID")
	@ManyToOne
	public ClientType getClientType() {
		return clientType;
	}

	// @Column(name = "ClientType")
	// public Integer getClientType() {
	// return clientType;
	// }

	@Column(name = "AlternateClientUrl")
	public String getAlternateClientUrl() {
		return alternateClientUrl;
	}

	@Column(name = "AccountNumber")
	public String getAccountNumber() {
		return accountNumber;
	}

	@Column(name = "DefaultUserName")
	public Integer getDefaultUserName() {
		return defaultUserName;
	}

	@Transient
	public String getDefaultUserNameDisc() {
		if (getDefaultUserName() == null) {
			return "";
		}
		// DBJ: Can we use an enum here?
		if (getDefaultUserName().intValue() == 1) {
			return TAMS_UNIQUE_ID;
		}

		return EMPLOYEE_ID;
	}

	@Transient
	public String getActiveDescription() {
		if (this.getActive() != null && this.getActive().equals(Client.ACTIVE)) {
			return "Active";
		} else {
			return "Inactive";
		}
	}

	@Transient
	public String getViewAllSupportingDocsDesc() {
		if (isViewAllSupportingDocs()) {
			return "Yes";
		} else {
			return "No";
		}

	}

	@Transient
	public String getShowAddressButtonsDesc() {
		if (isShowAddressButtonsYN()) {
			return "Yes";
		} else {
			return "No";
		}

	}

	@Transient
	public String getValidateEmailAtAppCreationDesc() {
		if (isValidateEmailAtAppCreation()) {
			return "Yes";
		} else {
			return "No";
		}

	}

	@Transient
	public String getEnableTaxReversalsDesc() {
		if (isEnableTaxReversals()) {
			return "Yes";
		} else {
			return "No";
		}

	}

	/*
	 * @Transient public String getResendSupervisorApprovalDesc() { if(isResendSupervisorApproval()) return "Yes"; else return "No"; }
	 */

	@Transient
	public String getClientTypeDisc() {
		if (clientType != null) {
			return clientType.getClientType();
		}
		return "";
	}

	@Transient
	public boolean isSsoEnabled() {
		if (ssoType == null) {
			return false;
		} else if (ssoType.equalsIgnoreCase("PF")) {
			return true;
		} else {
			return false;
		}
	}

	private Collection<ExceptionRules> exceptionRulesCollection;

	@Column(name = "SSOType")
	public String getSSOType() {
		return ssoType;
	}

	@Column(name = "PingOneIdpId")
	public String getPingOneIdpId() {
		return pingOneIdpId;
	}

	@Column(name = "ClientSSORedirectURL")
	public String getClientSSORedirectURL() {
		return clientSSORedirectURL;
	}

	@Column(name = "ClientSSOLogOffURL")
	public String getClientSSOLogOffURL() {
		return clientSSOLogOffURL;
	}

	@Column(name = "ClientSSOErrorRedirectURL")
	public String getClientSSOErrorRedirectURL() {
		return clientSSOErrorRedirectURL;
	}

	@Column(name = "MobileSSORedirectURL")
	public String getMobileSSORedirectURL() {
		return mobileSSORedirectURL;
	}

	@Column(name = "ClientNetworkURL")
	public String getClientNetworkURL() {
		return clientNetworkURL;
	}
	/*
	 * @Transient public String getClientTypeDisc() { if(getClientType().intValue()==1) return "Full Services"; return "Full Services"; }
	 */

	/**************************************************************************
	 *
	 * SETTERS
	 *
	 *************************************************************************/

	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "clientId")
	public Collection<ExceptionRules> getExceptionRulesCollection() {
		return exceptionRulesCollection;
	}

	public void setExceptionRulesCollection(Collection<ExceptionRules> exceptionRulesCollection) {
		this.exceptionRulesCollection = exceptionRulesCollection;
	}

	public void setShowAddressButtonsYN(boolean showAddressButtonsYN) {
		this.showAddressButtonsYN = showAddressButtonsYN;
	}

	public void setValidateEmailAtAppCreation(boolean ValidateEmailAtAppCreation) {
		this.ValidateEmailAtAppCreation = ValidateEmailAtAppCreation;
	}

	public void setEnableTaxReversals(boolean enableTaxReversals) {
		this.enableTaxReversals = enableTaxReversals;
	}

	public void setTAMS4(boolean TAMS4) {
		this.TAMS4 = TAMS4;
	}

	public void setViewAllSupportingDocs(boolean viewAllSupportingDocs) {
		this.viewAllSupportingDocs = viewAllSupportingDocs;
	}

	/*
	 * public void setResendSupervisorApproval(boolean resendSupervisorApproval) { this.resendSupervisorApproval = resendSupervisorApproval; } public void setResendSupervisorApprovalInterval(Long
	 * resendSupervisorApprovalInterval) { this.resendSupervisorApprovalInterval = resendSupervisorApprovalInterval; }
	 */

	public void setSupervisorIDType(Character supervisorIDType) {
		this.supervisorIDType = supervisorIDType;
	}

	public Client(long clientId, String clientName) {
		super();
		this.clientId = clientId;
		this.clientName = clientName;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public void setClientBranding(String clientBranding) {
		this.clientBranding = clientBranding;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/*
	 * public void setLogo(byte[] logo) { this.logo = logo; }
	 */

	public void setGenericLabel1(String genericLabel1) {
		this.genericLabel1 = genericLabel1;
	}

	public void setGenericLabel2(String genericLabel2) {
		this.genericLabel2 = genericLabel2;
	}

	public void setGenericLabel3(String genericLabel3) {
		this.genericLabel3 = genericLabel3;
	}

	public void setGenericLabel4(String genericLabel4) {
		this.genericLabel4 = genericLabel4;
	}

	public void setGenericLabel5(String genericLabel5) {
		this.genericLabel5 = genericLabel5;
	}

	public void setGenericLabel6(String genericLabel6) {
		this.genericLabel6 = genericLabel6;
	}

	public void setGenericLabel7(String genericLabel7) {
		this.genericLabel7 = genericLabel7;
	}

	public void setGenericLabel8(String genericLabel8) {
		this.genericLabel8 = genericLabel8;
	}

	public void setGenericLabel9(String genericLabel9) {
		this.genericLabel9 = genericLabel9;
	}

	public void setGenericLabel10(String genericLabel10) {
		this.genericLabel10 = genericLabel10;
	}

	public void setDefaultUserName(Integer defaultUserName) {
		this.defaultUserName = defaultUserName;
	}

	public void setAlternateClientUrl(String alternateClientUrl) {
		this.alternateClientUrl = alternateClientUrl;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setMobileEnabled(boolean mobileEnabled) {
		this.mobileEnabled = mobileEnabled;
	}

	public void setClientMobileCode(String clientMobileCode) {
		this.clientMobileCode = clientMobileCode;
	}

	/*
	 * public boolean isShowAddressButtonsYN() { return showAddressButtonsYN; } public void setShowAddressButtonsYN(boolean showAddressButtonsYN) { this.showAddressButtonsYN = showAddressButtonsYN; }
	 */

	/**************************************************************************
	 *
	 * METHODS
	 *
	 *************************************************************************/

	@Transient
	public boolean isActiveClient() {

		if (StringUtils.equals(active, ACTIVE)) {
			return true;
		}

		return false;
	}

	@Transient
	public String getMobileEnabledDesc() {
		if (this.isMobileEnabled()) {
			return "Yes";
		} else {
			return "No";
		}
	}

	@Override
	public String toString() {
		StringBuffer clientStr = new StringBuffer("Client =[ Client Name = ");
		clientStr.append(clientName);
		clientStr.append(", Client Id = ");
		clientStr.append(clientId);
		clientStr.append(" ]");
		return clientStr.toString();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setSSOType(String ssoType) {
		this.ssoType = ssoType;
	}

	public void setPingOneIdpId(String pingOneIdpId) {
		this.pingOneIdpId = pingOneIdpId;
	}

	public void setClientSSORedirectURL(String clientSSORedirectURL) {
		this.clientSSORedirectURL = clientSSORedirectURL;
	}

	public void setClientSSOErrorRedirectURL(String clientSSOErrorRedirectURL) {
		this.clientSSOErrorRedirectURL = clientSSOErrorRedirectURL;
	}

	public void setClientSSOLogOffURL(String clientSSOLogOffURL) {
		this.clientSSOLogOffURL = clientSSOLogOffURL;
	}

	public void setMobileSSORedirectURL(String mobileSSORedirectURL) {
		this.mobileSSORedirectURL = mobileSSORedirectURL;
	}

	public void setClientNetworkURL(String clientNetworkURL) {
		this.clientNetworkURL = clientNetworkURL;
	}

	@Column(name = "FeaturedProviderNetwork")
	public boolean isFeaturedProviderNetwork() {
		return featuredProviderNetwork;
	}

	public void setFeaturedProviderNetwork(boolean featuredProviderNetwork) {
		this.featuredProviderNetwork = featuredProviderNetwork;
	}

	@Transient
	public String getFeaturedProviderNetworkDesc() {
		if (isFeaturedProviderNetwork()) {
			return "Yes";
		} else {
			return "No";
		}

	}

	@Column(name = "EnhancedEdAssistNetwork")
	public boolean isEnhancedEdAssistNetwork() {
		return enhancedEdAssistNetwork;
	}

	public void setEnhancedEdAssistNetwork(boolean enhancedEdAssistNetwork) {
		this.enhancedEdAssistNetwork = enhancedEdAssistNetwork;
	}

	@Transient
	public String getEnhancedEdAssistNetworkDesc() {
		if (isEnhancedEdAssistNetwork()) {
			return "Yes";
		} else {
			return "No";
		}

	}

	public String getLW_Passphrase() {
		return LW_Passphrase;
	}

	public void setLW_Passphrase(String lWPassphrase) {
		LW_Passphrase = lWPassphrase;
	}

	@Transient
	public Long getApproverLevel1() {
		return approverLevel1;
	}

	@Transient
	public Long getApproverLevel2() {
		return approverLevel2;
	}

	@Transient
	public Long getApproverLevel3() {
		return approverLevel3;
	}

	@Transient
	public Long getApproverLevel4() {
		return approverLevel4;
	}

	@Transient
	public Long getApproverLevel5() {
		return approverLevel5;
	}

	public void setApproverLevel1(Long approverLevel1) {
		this.approverLevel1 = approverLevel1;
	}

	public void setApproverLevel2(Long approverLevel2) {
		this.approverLevel2 = approverLevel2;
	}

	public void setApproverLevel3(Long approverLevel3) {
		this.approverLevel3 = approverLevel3;
	}

	public void setApproverLevel4(Long approverLevel4) {
		this.approverLevel4 = approverLevel4;
	}

	public void setApproverLevel5(Long approverLevel5) {
		this.approverLevel5 = approverLevel5;
	}

	public Boolean getAllowAddEduProg() {
		return allowAddEduProg;
	}

	public void setAllowAddEduProg(Boolean allowAddEduProg) {
		this.allowAddEduProg = allowAddEduProg;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getDefaultEmailAddress() {
		return defaultEmailAddress;
	}

	public String getDefaultMailingAddress() {
		return defaultMailingAddress;
	}

	public void setDefaultEmailAddress(String defaultEmailAddress) {
		this.defaultEmailAddress = defaultEmailAddress;
	}

	public void setDefaultMailingAddress(String defaultMailingAddress) {
		this.defaultMailingAddress = defaultMailingAddress;
	}

	public Boolean getAllowDocumentUpload() {
		if (allowDocumentUpload == null) {
			allowDocumentUpload = false;
		}
		return allowDocumentUpload;
	}

	public void setAllowDocumentUpload(Boolean allowDocumentUpload) {
		if (allowDocumentUpload == null) {
			allowDocumentUpload = false;
		}
		this.allowDocumentUpload = allowDocumentUpload;
	}

	@Column(name = "CRMIntegration")
	public boolean isCrmIntegration() {
		return crmIntegration;
	}

	public void setCrmIntegration(boolean crmIntegration) {
		this.crmIntegration = crmIntegration;
	}

	@Transient
	public String getCrmIntegrationDesc() {
		if (isCrmIntegration()) {
			return "Yes";
		} else {
			return "No";
		}
	}

	@Column(name = "EnableMandatoryAdvising")
	public boolean isMandatoryAdvising() {
		return mandatoryAdvising;
	}

	public void setMandatoryAdvising(boolean mandatoryAdvising) {
		this.mandatoryAdvising = mandatoryAdvising;
	}

	@Transient
	public String getMandatoryAdvisingDesc() {
		if (isMandatoryAdvising()) {
			return "Yes";
		} else {
			return "No";
		}
	}

	@Column(name = "EnableSelfScheduledAdvising")
	public boolean isSelfScheduledAdvising() {
		return selfScheduledAdvising;
	}

	public void setSelfScheduledAdvising(boolean selfScheduledAdvising) {
		this.selfScheduledAdvising = selfScheduledAdvising;
	}

	@Transient
	public String getSelfScheduledAdvisingDesc() {
		if (isSelfScheduledAdvising()) {
			return "Yes";
		} else {
			return "No";
		}
	}

	public boolean isIdc_enabled() {
		return idc_enabled;
	}

	public void setIdc_enabled(boolean idcEnabled) {
		idc_enabled = idcEnabled;
	}

	@Column(name = "isCXC")
	public boolean isCxc() {
		return cxc;
	}

	public void setCxc(boolean cxc) {
		this.cxc = cxc;
	}

	@Transient
	public String getCxcDesc() {
		if (isCxc()) {
			return "Yes";
		} else {
			return "No";
		}
	}

	private boolean dependentProgram;

	@Column(name = "EnableDependentProgram")
	public boolean isDependentProgram() {
		return dependentProgram;
	}

	public void setDependentProgram(boolean dependentProgram) {
		this.dependentProgram = dependentProgram;
	}

	@Transient
	public String getDependentProgramDesc() {
		if (isDependentProgram()) {
			return "Yes";
		} else {
			return "No";
		}
	}

	private boolean loanRepayEnabled;

	@Column(name = "EnableLoanRepay")
	public boolean isLoanRepayEnabled() {
		return loanRepayEnabled;
	}

	public void setLoanRepayEnabled(boolean loanRepayEnabled) {
		this.loanRepayEnabled = loanRepayEnabled;
	}

	@Transient
	public String getLoanRepayEnabledDesc() {
		if (isLoanRepayEnabled()) {
			return "Yes";
		} else {
			return "No";
		}
	}

	public boolean isEdassist5Enabled() {
		return edassist5Enabled;
	}

	public void setEdassist5Enabled(boolean edassist5Enabled) {
		this.edassist5Enabled = edassist5Enabled;
	}

	@Transient
	public String getEdassist5EnabledDesc() {
		if (this.isEdassist5Enabled()) {
			return "Yes";
		} else {
			return "No";
		}
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	@Transient
	public String getRememberMeDesc() {
		if (this.isRememberMe()) {
			return "Yes";
		} else {
			return "No";
		}
	}

	public boolean isSsoLogoffEnabled() {
		return ssoLogoffEnabled;
	}

	public void setSsoLogoffEnabled(boolean ssoLogoffEnabled) {
		this.ssoLogoffEnabled = ssoLogoffEnabled;
	}

	@Transient
	public String getSsoLogoffEnabledDesc() {
		if (this.isSsoLogoffEnabled()) {
			return "Yes";
		} else {
			return "No";
		}
	}
}
