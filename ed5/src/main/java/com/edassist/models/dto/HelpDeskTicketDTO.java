package com.edassist.models.dto;

import org.springframework.web.multipart.MultipartFile;

public class HelpDeskTicketDTO {

	private String subTopicId;
	private String commentText;
	private String status;
	private MultipartFile file;

	public String getSubTopicId() {
		return subTopicId;
	}

	public void setSubTopicId(String subTopicId) {
		this.subTopicId = subTopicId;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
