package com.edassist.models.dto;

import com.edassist.models.domain.crm.CrmJsonObject;
import com.edassist.utils.RestUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketSummaryDTO extends CrmJsonObject implements Comparable<TicketSummaryDTO> {

	@JsonProperty("TicketId")
	private String ticketId;
	@JsonProperty("TicketNumber")
	private String ticketNumber;
	@JsonProperty("SubTopicId")
	private String subTopicId;
	@JsonProperty("State")
	private String state;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("ModifiedBy")
	private String modifiedBy;
	@JsonProperty("ModifiedOn")
	private String modifiedOn;
	@JsonProperty("QueueName")
	private String queueName;
	private String topicName;
	private String subTopicName;

	/**
	 * @return the ticketId
	 */
	public String getTicketId() {
		return ticketId;
	}

	/**
	 * @return the ticketnumber
	 */
	public String getTicketNumber() {
		return ticketNumber;
	}

	/**
	 * @return the subTopicId
	 */
	public String getSubTopicId() {
		return subTopicId;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @return the modifiedOn
	 */
	public String getModifiedOn() {
		return modifiedOn;
	}

	/**
	 * @param ticketId the ticketId to set
	 */
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * @param ticketnumber the ticketnumber to set
	 */
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	/**
	 * @param subTopicId the subTopicId to set
	 */
	public void setSubTopicId(String subTopicId) {
		this.subTopicId = subTopicId;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @param modifiedOn the modifiedOn to set
	 */
	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	/**
	 * @return the queueName
	 */
	public String getQueueName() {
		return queueName;
	}

	/**
	 * @param queueName the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	/**
	 * @return the topicName
	 */
	public String getTopicName() {
		return topicName;
	}

	/**
	 * @return the subTopicName
	 */
	public String getSubTopicName() {
		return subTopicName;
	}

	/**
	 * @param topicName the topicName to set
	 */
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	/**
	 * @param subTopicName the subTopicName to set
	 */
	public void setSubTopicName(String subTopicName) {
		this.subTopicName = subTopicName;
	}

	public String getFormattedModifiedOn() {
		return RestUtils.formatCrmDate(getModifiedOn());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(TicketSummaryDTO o) {
		if (o != null) {
			return RestUtils.formatCRMDate(o.getModifiedOn()).compareTo(RestUtils.formatCRMDate(getModifiedOn()));
		} else {
			return -1;
		}

	}

}
