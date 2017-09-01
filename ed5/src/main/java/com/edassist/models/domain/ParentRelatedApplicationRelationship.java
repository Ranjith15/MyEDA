package com.edassist.models.domain;

import javax.persistence.*;

@Entity
@Table(name = "ParentRelatedApplicationRelationship")
public class ParentRelatedApplicationRelationship {

	@Id
	@Column(name = "ParentRelatedApplicationID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long parentRelatedApplicationID;

	@Column(name = "ParentApplicationID")
	private Long parentApplicationID;

	@JoinColumn(name = "RelatedApplicationID", referencedColumnName = "ApplicationID")
	@OneToOne(optional = false)
	private Application relatedApplicationID;

	public Long getParentRelatedApplicationID() {
		return parentRelatedApplicationID;
	}

	public Long getParentApplicationID() {
		return parentApplicationID;
	}

	public Application getRelatedApplicationID() {
		return relatedApplicationID;
	}

	public void setParentRelatedApplicationID(Long parentRelatedApplicationID) {
		this.parentRelatedApplicationID = parentRelatedApplicationID;
	}

	public void setParentApplicationID(Long parentApplicationID) {
		this.parentApplicationID = parentApplicationID;
	}

	public void setRelatedApplicationID(Application relatedApplicationID) {
		this.relatedApplicationID = relatedApplicationID;
	}

}
