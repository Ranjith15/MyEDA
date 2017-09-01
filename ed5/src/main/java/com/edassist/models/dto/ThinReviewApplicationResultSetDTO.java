package com.edassist.models.dto;

import java.util.List;

public class ThinReviewApplicationResultSetDTO {
	private List<ThinReviewApplicationDTO> applications;

	public List<ThinReviewApplicationDTO> getApplications() {
		return applications;
	}

	public void setApplications(List<ThinReviewApplicationDTO> applications) {
		this.applications = applications;
	}

	public PaginationDTO getPagination() {
		return pagination;
	}

	public void setPagination(PaginationDTO pagination) {
		this.pagination = pagination;
	}

	private PaginationDTO pagination;

}
