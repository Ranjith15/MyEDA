package com.edassist.models.dto;

public class ParticipantSupervisorDTO {

	private String firstName;
	private String lastName;
	private String employeeId;
	private String approvalLevel;
	private String status;
	private String role;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(String approvalLevel) {
		String approvalText = "";
		if (approvalLevel.equals("1")) {
			approvalText = "Primary";
		} else if (approvalLevel.equals("2")) {
			approvalText = "Secondary";
		}
		this.approvalLevel = approvalText;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		String statusText = "";
		if (status.equals("Y") || status.equals("S")) {
			statusText = "Active";
		} else {
			statusText = "Inactive";
		}
		this.status = statusText;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}