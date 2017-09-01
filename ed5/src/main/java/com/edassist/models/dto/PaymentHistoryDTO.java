package com.edassist.models.dto;

import java.util.List;

public class PaymentHistoryDTO {

	private List<PaymentDTO> payments;
	private List<RefundDTO> refunds;

	public List<PaymentDTO> getPayments() {
		return payments;
	}

	public void setPayments(List<PaymentDTO> payments) {
		this.payments = payments;
	}

	public List<RefundDTO> getRefunds() {
		return refunds;
	}

	public void setRefunds(List<RefundDTO> refunds) {
		this.refunds = refunds;
	}

}