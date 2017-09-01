package com.edassist.models.domain;

import javax.persistence.*;

@Entity
@Table(name = "ProgramEligibleDocumentType")
public class ProgramEligibleDocumentType {

	@Id
	@Column(name = "ProgramEligibleDocumentTypeID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long programEligibleDocumentTypeID;

	@JoinColumn(name = "ProgramID", referencedColumnName = "ProgramID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private Program programID;

	@JoinColumn(name = "DefaultDocumentsID", referencedColumnName = "DefaultDocumentsID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private DefaultDocuments defaultDocumentsID;

	public ProgramEligibleDocumentType() {
		super();
	}

	public Long getProgramEligibleDocumentTypeID() {
		return programEligibleDocumentTypeID;
	}

	public void setProgramEligibleDocumentTypeID(Long programEligibleDocumentTypeID) {
		this.programEligibleDocumentTypeID = programEligibleDocumentTypeID;
	}

	public Program getProgramID() {
		return programID;
	}

	public void setProgramID(Program programID) {
		this.programID = programID;
	}

	public DefaultDocuments getDefaultDocumentsID() {
		return defaultDocumentsID;
	}

	public void setDefaultDocumentsID(DefaultDocuments defaultDocumentsID) {
		this.defaultDocumentsID = defaultDocumentsID;
	}

}
