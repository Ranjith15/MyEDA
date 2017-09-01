package com.edassist.service.impl.crm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchTicketRequest extends CrmRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2536643044629367254L;

	@JsonProperty("TicketNumber")
	private String ticketNumber;
	@JsonProperty("ParticipantID")
	private String participantId;
	@JsonProperty("EmployeeID")
	private String employeeId;
	@JsonProperty("ClientName")
	private String clientName;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("CreatedFrom")
	private String createdFrom;
	@JsonProperty("CreatedTo")
	private String createdTo;
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("LastName")
	private String lastName;
	@JsonProperty("ClientId")
	private String clientId;

	public SearchTicketRequest(String ticketNumber, String participantId, String employeeId, String clientName, String status, String createdFrom, String createdTo, String firstName, String lastName,
			 String clientId) {
		this.setTicketNumber(ticketNumber);
		this.setParticipantId(participantId);
		this.setEmployeeId(employeeId);
		this.setClientName(clientName);
		this.setStatus(status);
		this.setCreatedFrom(createdFrom);
		this.setCreatedTo(createdTo);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setClientId(clientId);
	}

	/**
	 * @return the ticketNumber
	 */
	public String getTicketNumber() {
		return ticketNumber;
	}

	/**
	 * @return the participantId
	 */
	public String getParticipantId() {
		return participantId;
	}

	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the createdFrom
	 */
	public String getCreatedFrom() {
		return createdFrom;
	}

	/**
	 * @return the createdTo
	 */
	public String getCreatedTo() {
		return createdTo;
	}

	/**
	 * @param ticketNumber the ticketNumber to set
	 */
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	/**
	 * @param participantId the participantId to set
	 */
	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param createdFrom the createdFrom to set
	 */
	public void setCreatedFrom(String createdFrom) {
		this.createdFrom = createdFrom;
	}

	/**
	 * @param createdTo the createdTo to set
	 */
	public void setCreatedTo(String createdTo) {
		this.createdTo = createdTo;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
