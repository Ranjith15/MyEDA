package com.edassist.models.sp;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@NamedStoredProcedureQuery(
		name = "CallgetAccountSnapshotForParticipant",
		procedureName = "getAccountSnapshotForParticipant",
		parameters = {
				@StoredProcedureParameter(type= Long.class, name = "participantID")
		},
		resultClasses = {
				AccountSnapshotForParticipant.class
		}
)
public class AccountSnapshotForParticipant {

	@Id
	private String category;

	@Column
	private Double amt = 0.0;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public static Map<Long, AccountSnapshotForParticipant> getKeyValue(List<AccountSnapshotForParticipant> list) {
		return null;
	}

}
