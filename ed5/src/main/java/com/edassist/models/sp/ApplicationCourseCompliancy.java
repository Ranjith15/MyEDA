package com.edassist.models.sp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQuery(
		name = "callGetApplicationCourseCompliancy",
		procedureName = "getApplicationCourseCompliancy",
		parameters = {
				@StoredProcedureParameter(type= Long.class, name = "applicationCoursesId")
		},
		resultClasses = {
				ApplicationCourseCompliancy.class
		}
)
public class ApplicationCourseCompliancy {

	@Id
	private String Compliancy;

	public String getCompliancy() {
		return Compliancy;
	}

	public void setCompliancy(String compliancy) {
		Compliancy = compliancy;
	}

}
