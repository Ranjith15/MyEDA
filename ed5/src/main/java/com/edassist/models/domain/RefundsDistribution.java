package com.edassist.models.domain;

import javax.persistence.*;

@Entity
@Table(name = "RefundDistribution")
public class RefundsDistribution {

	@Id
	@Column(name = "RefundDistributionID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long refundDistributionID;

	@JoinColumn(name = "ApplicationID", referencedColumnName = "ApplicationID")
	@ManyToOne(optional = false)
	private Application applicationID;

	@JoinColumn(name = "ApplicationCoursesID", referencedColumnName = "ApplicationCoursesID")
	@ManyToOne(optional = false)
	private ApplicationCourses applicationCoursesID;

	public Long getRefundsDistributionID() {
		return refundDistributionID;
	}

	public void setRefundsDistributionID(Long refundDistributionID) {
		this.refundDistributionID = refundDistributionID;
	}

	public Application getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Application applicationID) {
		this.applicationID = applicationID;
	}

	public ApplicationCourses getApplicationCoursesID() {
		return applicationCoursesID;
	}

	public void setApplicationCoursesID(ApplicationCourses applicationCoursesID) {
		this.applicationCoursesID = applicationCoursesID;
	}
}
