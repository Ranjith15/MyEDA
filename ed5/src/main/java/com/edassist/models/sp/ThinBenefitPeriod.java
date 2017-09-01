package com.edassist.models.sp;

import javax.persistence.*;

@Entity
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(
				name = "getBPList3",
				procedureName = "getBPList3",
				parameters = {
						@StoredProcedureParameter(type = Long.class, name = "participantID") },
				resultClasses = ThinBenefitPeriod.class),
		@NamedStoredProcedureQuery(name = "getBPListByClient",
				procedureName = "getBPListByClient",
				parameters = {
						@StoredProcedureParameter(type = Long.class, name = "clientID") },
				resultClasses = ThinBenefitPeriod.class)
})
public class ThinBenefitPeriod {

	@Id
	@Column(name = "BenefitPeriodID")
	private Long benefitPeriodID;

	@Column(name = "BenefitPeriodName")
	private String benefitPeriodName;

	@Column
	private String dateRange;

	public ThinBenefitPeriod() {
	}

	public Long getBenefitPeriodID() {
		return benefitPeriodID;
	}

	public void setBenefitPeriodID(Long benefitPeriodID) {
		this.benefitPeriodID = benefitPeriodID;
	}

	public String getBenefitPeriodName() {
		return benefitPeriodName;
	}

	public void setBenefitPeriodName(String benefitPeriodName) {
		this.benefitPeriodName = benefitPeriodName;
	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
}