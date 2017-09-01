package com.edassist.models.sp;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NamedStoredProcedureQuery(
		name = "getCapLimitForParticipant",
		procedureName = "getCapLimitForParticipant",
		parameters = {
				@StoredProcedureParameter(type= Long.class, name = "participantId"),
				@StoredProcedureParameter(type= Long.class, name = "programId"),
				@StoredProcedureParameter(type= Long.class, name = "degreeObjectiveId"),
				@StoredProcedureParameter(type= String.class, name = "BenefitPeriodName")
		},
		resultClasses = {
				CapInfo.class
		}
)
public class CapInfo {

	@Id
	private BigDecimal inProgressAmount;

	@Column
	private BigDecimal paidAmount;

	@Column
	private BigDecimal cap;

	@Column
	private BigDecimal remainingCapAmount;

	@Column
	private Integer appCount;

	public BigDecimal getInProgressAmount() {
		return inProgressAmount;
	}

	public void setInProgressAmount(BigDecimal inProgressAmount) {
		this.inProgressAmount = inProgressAmount;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getCap() {
		return cap;
	}

	public void setCap(BigDecimal cap) {
		this.cap = cap;
	}

	public BigDecimal getRemainingCapAmount() {
		return remainingCapAmount;
	}

	public void setRemainingCapAmount(BigDecimal remainingCapAmount) {
		this.remainingCapAmount = remainingCapAmount;
	}

	public Integer getAppCount() {
		return appCount;
	}

	public void setAppCount(Integer appCount) {
		this.appCount = appCount;
	}
}
