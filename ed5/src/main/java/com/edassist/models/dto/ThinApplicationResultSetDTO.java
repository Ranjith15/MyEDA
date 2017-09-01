package com.edassist.models.dto;

import java.util.List;

public class ThinApplicationResultSetDTO {
	private List<ThinAppDTO> applications;
	private PaginationDTO pagination;

	public List<ThinAppDTO> getApplications() {
		return applications;
	}

	public void setApplications(List<ThinAppDTO> applications) {
		this.applications = applications;
	}

	public PaginationDTO getPagination() {
		return pagination;
	}

	public void setPagination(PaginationDTO pagination) {
		this.pagination = pagination;
	}

}
