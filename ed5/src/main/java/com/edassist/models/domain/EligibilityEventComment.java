package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ApplicationComments")
public class EligibilityEventComment {

	@Id
	@Column(name = "ApplicationCommentsID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long applicationCommentsID;

	@Basic(optional = false)
	@Column(name = "CommentTypeID")
	private Long commentTypeID;

	@Lob
	@Column(name = "Comment")
	private String comment;

	@Column(name = "ViewableByParticipant")
	private boolean viewableByParticipant;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.DATE)
	private Date modifiedDate;

	@Column(name = "ApplicationID")
	private Long applicationID;

	@ManyToOne(optional = false)
	@JoinColumn(name = "CreatedBy")
	private ThinUser createdBy;

	@ManyToOne()
	@JoinColumn(name = "ModifiedBy")
	private ThinUser modifiedBy;

	@JoinColumn(name = "ApplicationStatusID", referencedColumnName = "ApplicationStatusID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private ApplicationStatus applicationStatus;

	@Column(name = "AppStatusChange")
	private boolean appStatusChange;

	public EligibilityEventComment() {

	}

	public Long getApplicationCommentsID() {
		return applicationCommentsID;
	}

	public void setApplicationCommentsID(Long applicationCommentsID) {
		this.applicationCommentsID = applicationCommentsID;
	}

	public Long getCommentTypeID() {
		return commentTypeID;
	}

	public void setCommentTypeID(Long commentTypeID) {
		this.commentTypeID = commentTypeID;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isViewableByParticipant() {
		return viewableByParticipant;
	}

	public void setViewableByParticipant(boolean viewableByParticipant) {
		this.viewableByParticipant = viewableByParticipant;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public ThinUser getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(ThinUser createdBy) {
		this.createdBy = createdBy;
	}

	public ThinUser getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(ThinUser modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public boolean isAppStatusChange() {
		return appStatusChange;
	}

	public void setAppStatusChange(boolean appStatusChange) {
		this.appStatusChange = appStatusChange;
	}
}
