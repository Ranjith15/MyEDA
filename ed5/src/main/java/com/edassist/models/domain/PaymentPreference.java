package com.edassist.models.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PaymentPreference")
public class PaymentPreference {

	@Id
	@Column(name = "PaymentPreferenceID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentPreferenceID;

	@JoinColumn(name = "ParticipantID", referencedColumnName = "ParticipantID")
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	private Participant participantID;

	@Column(name = "cxcID")
	private String cxcID;

	@Column(name = "PaymentPreference")
	private String paymentPreference;

	@ManyToOne(optional = false)
	@JoinColumn(name = "CreatedBy")
	private User createdBy;

	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ModifiedBy")
	private User modifiedBy;

	public PaymentPreference() {
		super();
	}

	public Long getPaymentPreferenceID() {
		return paymentPreferenceID;
	}

	public void setPaymentPreferenceID(Long paymentPreferenceID) {
		this.paymentPreferenceID = paymentPreferenceID;
	}

	public Participant getParticipantID() {
		return participantID;
	}

	public void setParticipantID(Participant participantId) {
		this.participantID = participantId;
	}

	public String getCXCID() {
		return cxcID;
	}

	public void setCXCID(String cxcID) {
		this.cxcID = cxcID;
	}

	public String getPaymentPreference() {
		return paymentPreference;
	}

	public void setPaymentPreference(String paymentPreference) {
		this.paymentPreference = paymentPreference;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
