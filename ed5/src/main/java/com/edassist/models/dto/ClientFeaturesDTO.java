package com.edassist.models.dto;

public class ClientFeaturesDTO {

	private boolean loanRepayEnabled;
	private boolean cxc;
	private boolean enableTaxReversals;
	private boolean showAddressButtonsYN;
	private boolean viewAllSupportingDocs;
	private boolean ValidateEmailAtAppCreation;
	private String defaultMailingAddress;
	private String defaultEmailAddress;

	public boolean isLoanRepayEnabled() {
		return loanRepayEnabled;
	}

	public void setLoanRepayEnabled(boolean loanRepayEnabled) {
		this.loanRepayEnabled = loanRepayEnabled;
	}

	public boolean isCxc() {
		return cxc;
	}

	public void setCxc(boolean cxc) {
		this.cxc = cxc;
	}

	public boolean isEnableTaxReversals() {
		return enableTaxReversals;
	}

	public void setEnableTaxReversals(boolean enableTaxReversals) {
		this.enableTaxReversals = enableTaxReversals;
	}

	public boolean isShowAddressButtonsYN() {
		return showAddressButtonsYN;
	}

	public void setShowAddressButtonsYN(boolean showAddressButtonsYN) {
		this.showAddressButtonsYN = showAddressButtonsYN;
	}

	public boolean isViewAllSupportingDocs() {
		return viewAllSupportingDocs;
	}

	public void setViewAllSupportingDocs(boolean viewAllSupportingDocs) {
		this.viewAllSupportingDocs = viewAllSupportingDocs;
	}

	public boolean isValidateEmailAtAppCreation() {
		return ValidateEmailAtAppCreation;
	}

	public void setValidateEmailAtAppCreation(boolean validateEmailAtAppCreation) {
		ValidateEmailAtAppCreation = validateEmailAtAppCreation;
	}

	public String getDefaultMailingAddress() {
		return defaultMailingAddress;
	}

	public void setDefaultMailingAddress(String defaultMailingAddress) {
		this.defaultMailingAddress = defaultMailingAddress;
	}

	public String getDefaultEmailAddress() {
		return defaultEmailAddress;
	}

	public void setDefaultEmailAddress(String defaultEmailAddress) {
		this.defaultEmailAddress = defaultEmailAddress;
	}

}
