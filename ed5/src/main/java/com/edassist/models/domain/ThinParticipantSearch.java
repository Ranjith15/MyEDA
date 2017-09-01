package com.edassist.models.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Participant")
public class ThinParticipantSearch {

	@Id
	@Column(name = "ParticipantID")
	private Long participantId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "UserID")
	private ThinUser user;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "participantID")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ThinApp> applicationSet;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ClientID")
	private Client client;

	@Column(name = "EmployeeId")
	private String employeeId;

	@Column(name = "UniqueId")
	private String uniqueId;

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public ThinUser getUser() {
		return user;
	}

	public void setUser(ThinUser user) {
		this.user = user;
	}

	public List<ThinApp> getApplicationSet() {
		return applicationSet;
	}

	public void setApplicationSet(List<ThinApp> applicationSet) {
		this.applicationSet = applicationSet;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
}
