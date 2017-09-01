package com.edassist.models.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "AppStatusChangeLive")

public class AppStatusChangeLive implements Serializable {

	@Id
	@Column(name = "ChangeId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "ApplicationID", referencedColumnName = "ApplicationID")
	@ManyToOne(optional = false)
	private App application;

	@JoinColumn(name = "ApplicationStatusID", referencedColumnName = "ApplicationStatusID")
	@ManyToOne
	private ApplicationStatus applicationStatus;

	@JoinColumn(name = "ModifiedBy", referencedColumnName = "UserID")
	@ManyToOne(optional = false)
	private ThinUser modifiedBy;

	@Basic(optional = false)
	@Column(name = "DateModified")
	private Date dateModified;

	public AppStatusChangeLive() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public App getApplication() {
		return application;
	}

	public void setApplication(App application) {
		this.application = application;
	}

	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public ThinUser getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(ThinUser modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
}
