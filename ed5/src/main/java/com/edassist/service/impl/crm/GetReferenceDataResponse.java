package com.edassist.service.impl.crm;

import com.edassist.models.domain.crm.TicketState;
import com.edassist.models.domain.crm.Topic;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetReferenceDataResponse extends CrmResponse {

	@JsonProperty("Topics")
	private Topic[] listTopic;

	@JsonProperty("TicketState")
	private TicketState[] ticketStates;

	@JsonProperty("HelpDeskTopic")
	private Topic[] helpDeskTopics;

	/**
	 * @return the listTopic
	 */
	public Topic[] getListTopic() {
		return listTopic;
	}

	/**
	 * @return the ticketStates
	 */
	public TicketState[] getTicketStates() {
		return ticketStates;
	}

	/**
	 * @param listTopic the listTopic to set
	 */
	public void setListTopic(Topic[] listTopic) {
		this.listTopic = listTopic;
	}

	/**
	 * @param ticketStates the ticketStates to set
	 */
	public void setTicketStates(TicketState[] ticketStates) {
		this.ticketStates = ticketStates;
	}

	/**
	 * @return the helpDeskTopics
	 */
	public Topic[] getHelpDeskTopics() {
		return helpDeskTopics;
	}

	/**
	 * @param helpDeskTopics the helpDeskTopics to set
	 */
	public void setHelpDeskTopics(Topic[] helpDeskTopics) {
		this.helpDeskTopics = helpDeskTopics;
	}

}
