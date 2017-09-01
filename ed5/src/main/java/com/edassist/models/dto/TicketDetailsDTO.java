package com.edassist.models.dto;

import java.util.List;

import com.edassist.models.domain.crm.CrmJsonObject;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketDetailsDTO extends CrmJsonObject {

	private String ticketId;

	private String ticketNumber;

	private String subTopicId;

	private String topic;

	private String applicationNumber;

	private String resolution;

	private String type;

	private String origin;

	private String state;

	private String status;

	private String createdBy;

	private String createdOn;

	private String modifiedBy;

	private String modifiedOn;

	private List<NotesDTO> notes;

	private long statusCode;

	private String subTopic;

	/**
	 * @return the ticketId
	 */
	public String getTicketId() {
		return ticketId;
	}

	/**
	 * @return the ticketNumber
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
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @return the applicationNumber
	 */
	public String getApplicationNumber() {
		return applicationNumber;
	}

	/**
	 * @return the resolution
	 */
	public String getResolution() {
		return resolution;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
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
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @return the createdOn
	 */
	public String getCreatedOn() {
		return createdOn;
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
	@JsonProperty("TicketId")
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * @param ticketNumber the ticketNumber to set
	 */
	@JsonProperty("TicketNumber")
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	/**
	 * @param subTopicId the subTopicId to set
	 */
	@JsonProperty("SubTopicId")
	public void setSubTopicId(String subTopicId) {
		this.subTopicId = subTopicId;
	}

	/**
	 * @param topic the topic to set
	 */
	@JsonProperty("Topic")
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * @param applicationNumber the applicationNumber to set
	 */
	@JsonProperty("ApplicationNumber")
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	/**
	 * @param resolution the resolution to set
	 */
	@JsonProperty("Resolution")
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	/**
	 * @param type the type to set
	 */
	@JsonProperty("Type")
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param origin the origin to set
	 */
	@JsonProperty("Origin")
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @param state the state to set
	 */
	@JsonProperty("State")
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param status the status to set
	 */
	@JsonProperty("Status")
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	@JsonProperty("CreatedBy")
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	@JsonProperty("CreatedOn")
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	@JsonProperty("ModifiedBy")
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @param modifiedOn the modifiedOn to set
	 */
	@JsonProperty("ModifiedOn")
	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	/**
	 * @return the notes
	 */
	public List<NotesDTO> getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(List<NotesDTO> notes) {
		this.notes = notes;
	}

	/**
	 * @return the statusCode
	 */
	public long getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	@JsonProperty("StatusCode")
	public void setStatusCode(long statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the subTopic
	 */
	public String getSubTopic() {
		return subTopic;
	}

	/**
	 * @param subTopic the subTopic to set
	 */
	public void setSubTopic(String subTopic) {
		this.subTopic = subTopic;
	}

}
