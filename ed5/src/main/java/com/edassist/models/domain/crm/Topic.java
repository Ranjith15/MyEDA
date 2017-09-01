package com.edassist.models.domain.crm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Topic extends CrmJsonObject {
	@JsonProperty("TopicId")
	private String topicId;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("SubTopics")
	private Topic[] subTopics;
	@JsonProperty("ProgramTypes")
	private ProgramType[] programTypes;

	/**
	 * @return the topicId
	 */

	public String getTopicId() {
		return topicId;
	}

	/**
	 * @return the name
	 */

	public String getName() {
		return name;
	}

	/**
	 * @return the subTopics
	 */
	public Topic[] getSubTopics() {
		return subTopics;
	}

	/**
	 * @param topicId the topicId to set
	 */
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param subTopics the subTopics to set
	 */
	public void setSubTopics(Topic[] subTopics) {
		this.subTopics = subTopics;
	}

	/**
	 * @return
	 */
	public ProgramType[] getProgramTypes() {
		return programTypes;
	}

	/**
	 * @param programTypes
	 */
	public void setProgramTypes(ProgramType[] programTypes) {
		this.programTypes = programTypes;
	}

}
