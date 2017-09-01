package com.edassist.models.dto;

import com.edassist.models.domain.crm.CrmJsonObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentDTO extends CrmJsonObject {

	private String content;

	private String fileName;

	private String annotationId;

	/**
	 * @return the content
	 */
	@JsonProperty("AttachmentData")
	public String getContent() {
		return content;
	}

	/**
	 * @return the fileName
	 */
	@JsonProperty("FileName")
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param content the content to set
	 */
	@JsonProperty("AttachmentData")
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @param fileName the fileName to set
	 */
	@JsonProperty("FileName")
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the annotationId
	 */
	@JsonProperty("AnnotationId")
	public String getAnnotationId() {
		return annotationId;
	}

	/**
	 * @param annotationId the annotationId to set
	 */
	@JsonProperty("AnnotationId")
	public void setAnnotationId(String annotationId) {
		this.annotationId = annotationId;
	}

}
