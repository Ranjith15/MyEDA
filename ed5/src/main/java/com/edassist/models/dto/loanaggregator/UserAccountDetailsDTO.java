package com.edassist.models.dto.loanaggregator;

import java.util.Date;

public class UserAccountDetailsDTO {

	private String container;
	private Long providerAccountId;
	private String accountStatus;
	private String accountNumber;
	private AmountDTO balance;
	private Long id;
	private Date lastUpdated;
	private String providerId;
	private String servicerName;
	private String accountType;
	private AmountDTO amountDue;
	private Date dueDate;
	private Date createdDate;
	private Long interestRate;
	private boolean isManual;
	private RefreshInfoDTO refreshInfo;

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public Long getProviderAccountId() {
		return providerAccountId;
	}

	public void setProviderAccountId(Long providerAccountId) {
		this.providerAccountId = providerAccountId;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public AmountDTO getBalance() {
		return balance;
	}

	public void setBalance(AmountDTO balance) {
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public AmountDTO getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(AmountDTO amountDue) {
		this.amountDue = amountDue;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Long interestRate) {
		this.interestRate = interestRate;
	}

	public boolean isManual() {
		return isManual;
	}

	public void setManual(boolean isManual) {
		this.isManual = isManual;
	}

	public RefreshInfoDTO getRefreshInfo() {
		return refreshInfo;
	}

	public void setRefreshInfo(RefreshInfoDTO refreshInfo) {
		this.refreshInfo = refreshInfo;
	}

	public String getServicerName() {
		return servicerName;
	}

	public void setServicerName(String servicerName) {
		this.servicerName = servicerName;
	}
}
