package com.edassist.models.sp;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedStoredProcedureQuery(
		name = "getAccountSnapshot",
		procedureName = "getAccountSnapshot",
		parameters = {
				@StoredProcedureParameter(type= Long.class, name = "participantID"),
				@StoredProcedureParameter(type= String.class, name = "benefitPeriodName"),
				@StoredProcedureParameter(type= Long.class, name = "currApplicationID")
		},
		resultClasses = {
				AccountSnapshot.class
		}
)
public class AccountSnapshot implements Serializable {

	private static final long serialVersionUID = 1909618132736345661L;

	@Id
	private Long sortKey;

	@Column
	private Double amt = 0.0;

	@Column
	private String category;

	public Long getSortKey() {
		return sortKey;
	}

	public void setSortKey(Long sortKey) {
		this.sortKey = sortKey;
	}

	public Double getAmt() {
		if (amt != null) {
			return amt;
		}
		return 0.0;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
