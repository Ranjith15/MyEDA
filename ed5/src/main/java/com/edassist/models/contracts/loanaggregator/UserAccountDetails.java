package com.edassist.models.contracts.loanaggregator;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAccountDetails {

	@JsonProperty("CONTAINER")
	private String container;

	private Long providerAccountId;
	private String accountStatus;
	private String url;
	private String accountNumber;
	private boolean isAsset;
	private Amount balance;
	private Long id;
	private Date lastUpdated;
	private String providerId;
	private String providerName;
	private String accountType;
	private Amount amountDue;
	private Date dueDate;
	private Long interestRate;
	private boolean isManual;
	private Date createdDate;

	@JsonProperty("refreshinfo")
	private RefreshInfo refreshInfo;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public boolean isAsset() {
		return isAsset;
	}

	public void setAsset(boolean isAsset) {
		this.isAsset = isAsset;
	}

	public Amount getBalance() {
		return balance;
	}

	public void setBalance(Amount balance) {
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

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Amount getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(Amount amountDue) {
		this.amountDue = amountDue;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public RefreshInfo getRefreshInfo() {
		return refreshInfo;
	}

	public void setRefreshInfo(RefreshInfo refreshInfo) {
		this.refreshInfo = refreshInfo;
	}

}
