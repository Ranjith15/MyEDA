package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ApplicationIssues")
public class ApplicationComment {

	@Id
	@Column(name = "ApplicationIssuesID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long applicationCommentId;

	@Column(name = "ApplicationID")
	private Long applicationID;

	@JoinColumn(name = "ApplicationStatusID", referencedColumnName = "ApplicationStatusID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private ApplicationStatus applicationStatusID;

	@Column(name = "Comment")
	private String comment;

	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@ManyToOne(optional = false)
	@JoinColumn(name = "CreatedBy")
	private User createdBy;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@ManyToOne
	@JoinColumn(name = "ModifiedBy")
	private User modifiedBy;

	@Column(name = "ReviewedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date reviewedDate;

	@ManyToOne
	@JoinColumn(name = "ReviewedBy")
	private User reviewedBy;

	@Column(name = "Reviewed")
	private Boolean reviewed;

	@Column(name = "ViewableToParticipant")
	private Boolean viewableToParticipant;

	@Column(name = "EmailSent")
	private Boolean emailSent;

	@Column(name = "defaultDocumentsID")
	private Long defaultDocumentsID;

	public Long getDefaultDocumentsID() {
		return defaultDocumentsID;
	}

	public Long getApplicationCommentId() {
		return applicationCommentId;
	}

	public Long getApplicationID() {
		return applicationID;
	}

	public ApplicationStatus getApplicationStatusID() {
		return applicationStatusID;
	}

	public String getComment() {
		return comment;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public Date getReviewedDate() {
		return reviewedDate;
	}

	public User getReviewedBy() {
		return reviewedBy;
	}

	public Boolean getReviewed() {
		return reviewed;
	}

	public Boolean getEmailSent() {
		return emailSent;
	}

	public void setDefaultDocumentsID(Long defaultDocumentsID) {
		this.defaultDocumentsID = defaultDocumentsID;
	}

	public void setApplicationCommentId(Long applicationCommentId) {
		this.applicationCommentId = applicationCommentId;
	}

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public void setApplicationStatusID(ApplicationStatus applicationStatusID) {
		this.applicationStatusID = applicationStatusID;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setReviewedDate(Date reviewedDate) {
		this.reviewedDate = reviewedDate;
	}

	public void setReviewedBy(User reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	public void setReviewed(Boolean reviewed) {
		this.reviewed = reviewed;
	}

	public void setEmailSent(Boolean emailSent) {
		this.emailSent = emailSent;
	}

	public void setViewableToParticipant(Boolean viewableToParticipant) {
		this.viewableToParticipant = viewableToParticipant;
	}

	public Boolean getViewableToParticipant() {
		return viewableToParticipant;
	}
}