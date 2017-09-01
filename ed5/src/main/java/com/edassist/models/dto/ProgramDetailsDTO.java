package com.edassist.models.dto;

import com.edassist.models.dto.content.GeneralContentDTO;

import java.util.List;

public class ProgramDetailsDTO implements Comparable<ProgramDetailsDTO> {

	private Long programId;
	private String programName;
	private GeneralContentDTO programDescription;
	private List<GeneralContentDTO> policyDocuments;

	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public GeneralContentDTO getProgramDescription() {
		return programDescription;
	}

	public void setProgramDescription(GeneralContentDTO programDescription) {
		this.programDescription = programDescription;
	}

	public List<GeneralContentDTO> getPolicyDocuments() {
		return policyDocuments;
	}

	public void setPolicyDocuments(List<GeneralContentDTO> policyDocuments) {
		this.policyDocuments = policyDocuments;
	}

	@Override
	public int compareTo(ProgramDetailsDTO programDetailsDTO) {
		if (programDetailsDTO != null) {
			if (this.getProgramName() != null && programDetailsDTO.getProgramName() != null) {
				return this.getProgramName().compareTo(programDetailsDTO.getProgramName());
			} else if (this.getProgramName() != null) {
				return 1;
			} else {
				return -1;
			}
		}
		return 1;
	}

}
