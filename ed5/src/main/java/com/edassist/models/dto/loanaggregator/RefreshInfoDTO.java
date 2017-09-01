package com.edassist.models.dto.loanaggregator;

import java.util.Date;

public class RefreshInfoDTO {

	private Long statusCode;
	private String statusMessage;
	private Date lastRefreshed;
	private Date lastRefreshAttempt;
	private Date nextRefreshScheduled;

	public Long getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Long statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Date getLastRefreshed() {
		return lastRefreshed;
	}

	public void setLastRefreshed(Date lastRefreshed) {
		this.lastRefreshed = lastRefreshed;
	}

	public Date getLastRefreshAttempt() {
		return lastRefreshAttempt;
	}

	public void setLastRefreshAttempt(Date lastRefreshAttempt) {
		this.lastRefreshAttempt = lastRefreshAttempt;
	}

	public Date getNextRefreshScheduled() {
		return nextRefreshScheduled;
	}

	public void setNextRefreshScheduled(Date nextRefreshScheduled) {
		this.nextRefreshScheduled = nextRefreshScheduled;
	}

}
