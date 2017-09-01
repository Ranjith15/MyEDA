package com.edassist.models.dto;

public class PaymentPreferenceDTO {

	private Long paymentPreferenceID;
	private String paymentPreference;
	private String cxcID;

	public Long getPaymentPreferenceID() {
		return paymentPreferenceID;
	}

	public void setPaymentPreferenceID(Long paymentPreferenceID) {
		this.paymentPreferenceID = paymentPreferenceID;
	}

	public String getPaymentPreference() {
		return paymentPreference;
	}

	public void setPaymentPreference(String paymentPreference) {
		this.paymentPreference = paymentPreference;
	}

	public String getCXCID() {
		return cxcID;
	}

	public void setCXCID(String cxcID) {
		this.cxcID = cxcID;
	}

}
