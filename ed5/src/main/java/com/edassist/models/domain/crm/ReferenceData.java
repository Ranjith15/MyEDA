package com.edassist.models.domain.crm;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ReferenceData {
	private List<Topic> topics;
	private List<TicketState> states;
	private List<Topic> helpDeskTopics;

	/**
	 * @return the topics
	 */
	public List<Topic> getTopics() {
		return topics;
	}

	/**
	 * @return the states
	 */
	public List<TicketState> getStates() {
		return states;
	}

	/**
	 * @param topics the topics to set
	 */
	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	/**
	 * @param states the states to set
	 */
	public void setStates(List<TicketState> states) {
		this.states = states;
	}

	public void setStates(TicketState[] arrStates) {
		this.setStates(Arrays.asList(arrStates));
	}

	public void setTopics(Topic[] arrTopics) {
		this.setTopics(Arrays.asList(arrTopics));
	}

	/**
	 * @return the helpDeskTopics
	 */
	public List<Topic> getHelpDeskTopics() {
		return helpDeskTopics;
	}

	/**
	 * @param helpDeskTopics the helpDeskTopics to set
	 */
	public void setHelpDeskTopics(List<Topic> helpDeskTopics) {
		this.helpDeskTopics = helpDeskTopics;
	}

	public void setHelpDeskTopics(Topic[] arrTopics) {
		this.setHelpDeskTopics(Arrays.asList(arrTopics));
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).toString();
	}
}
