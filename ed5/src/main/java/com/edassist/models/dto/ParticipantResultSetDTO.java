package com.edassist.models.dto;

import java.util.List;

public class ParticipantResultSetDTO {

	private List<ParticipantDTO> participants;
	private PaginationDTO pagination;

	public List<ParticipantDTO> getParticipants() {
		return participants;
	}

	public void setParticipants(List<ParticipantDTO> participants) {
		this.participants = participants;
	}

	public PaginationDTO getPagination() {
		return pagination;
	}

	public void setPagination(PaginationDTO pagination) {
		this.pagination = pagination;
	}

}
