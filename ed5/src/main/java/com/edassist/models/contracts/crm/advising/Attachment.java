package com.edassist.models.contracts.crm.advising;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Attachment {
	@JsonProperty("AttachmentData")
	private String content;
	@JsonProperty("FileName")
	private String fileName;
	@JsonProperty("AnnotationId")
	private String annotationId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAnnotationId() {
		return annotationId;
	}

	public void setAnnotationId(String annotationId) {
		this.annotationId = annotationId;
	}

}
