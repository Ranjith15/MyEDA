package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Property")
@NamedQueries({ @NamedQuery(name = "Property", query = "SELECT p FROM Property p"),
		@NamedQuery(name = "Property.findByPropertyKey", query = "SELECT p FROM Property p WHERE p.propertyKey = :propertyKey") })
public class Property {

	@Id
	@Column(name = "PropertyId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long propertyId;

	@Column(name = "PropertyKey")
	private String propertyKey;

	@Column(name = "PropertyValue")
	private String propertyValue;

	@Column(name = "Description")
	private String description;

	@JoinColumn(name = "CreatedBy", referencedColumnName = "UserID")
	@ManyToOne
	private User createdBy;

	@JoinColumn(name = "ModifiedBy", referencedColumnName = "UserID")
	@ManyToOne
	private User modifiedBy;

	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	public Property() {
	}

	public Long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getmodifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

}
