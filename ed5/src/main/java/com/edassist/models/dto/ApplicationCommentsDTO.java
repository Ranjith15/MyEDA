package com.edassist.models.dto;

import java.util.Date;

public class ApplicationCommentsDTO {

	private Long applicationCommentId;
	private Long applicationID;
	private String applicationStatus;
	private String comment;
	private Date dateCreated;
	private Date reviewedDate;
	private UserDTO createdBy;
	private UserDTO reviewedBy;
	private boolean reviewed;
	private boolean isEdassistComment;
	private boolean emailSent;

	public Long getApplicationCommentId() {
		return applicationCommentId;
	}

	public void setApplicationCommentId(Long applicationCommentId) {
		this.applicationCommentId = applicationCommentId;
	}

	public Long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getReviewedDate() {
		return reviewedDate;
	}

	public void setReviewedDate(Date reviewedDate) {
		this.reviewedDate = reviewedDate;
	}

	public UserDTO getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserDTO createdBy) {
		this.createdBy = createdBy;
	}

	public UserDTO getReviewedBy() {
		return reviewedBy;
	}

	public void setReviewedBy(UserDTO reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	public boolean isReviewed() {
		return reviewed;
	}

	public void setReviewed(boolean reviewed) {
		this.reviewed = reviewed;
	}

	public boolean isEdassistComment() {
		return isEdassistComment;
	}

	public void setEdassistComment(boolean isEdassistComment) {
		this.isEdassistComment = isEdassistComment;
	}

	public boolean isEmailSent() {
		return emailSent;
	}

	public void setEmailSent(boolean emailSent) {
		this.emailSent = emailSent;
	}
}
