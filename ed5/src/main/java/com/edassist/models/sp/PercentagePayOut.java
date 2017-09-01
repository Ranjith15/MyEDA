package com.edassist.models.sp;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@NamedStoredProcedureQuery(
		name = "findPercentagePayoutForAppCourse",
		procedureName = "findPercentagePayoutForAppCourse",
		parameters = {
				@StoredProcedureParameter(type= Long.class, name = "applicationCoursesID")
		},
		resultClasses = {
				PercentagePayOut.class
		})
public class PercentagePayOut {

	@Id
	private BigDecimal Value;

	@Column
	private String PercentagePayout;

	public BigDecimal getValue() {
		return Value;
	}

	public void setValue(BigDecimal value) {
		Value = value;
	}

	public String getPercentagePayout() {
		return PercentagePayout;
	}

	public void setPercentagePayout(String percentagePayout) {
		PercentagePayout = percentagePayout;
	}
}
