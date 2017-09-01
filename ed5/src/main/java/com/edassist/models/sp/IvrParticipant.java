package com.edassist.models.sp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQuery(
		name = "findParticipantByPhone",
		procedureName = "findParticipantByPhone",
		parameters = {
				@StoredProcedureParameter(type= String.class, name = "phonenumber")
		},
		resultClasses = {
				IvrParticipant.class
		}
)
public class IvrParticipant {

	@Id
	private Long participantID;

	public Long getParticipantID() {
		return participantID;
	}

	public void setParticipantID(Long participantID) {
		this.participantID = participantID;
	}
}
