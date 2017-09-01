package com.edassist.service;

import java.util.List;

import com.edassist.exception.CrmException;
import com.edassist.models.domain.crm.Code;
import com.edassist.models.domain.crm.TicketStatus;
import com.edassist.models.domain.crm.Topic;

public interface ReferenceDataService {

	List<Topic> getTopics();

	List<Topic> getSubTopics(String topicId);

	Topic lookupTopicWithSubTopic(String subTopicId);

	Topic lookupSubTopic(String subTopicId);

	List<TicketStatus> getStatuses();

	void loadReferenceData() throws CrmException;

	List<Code> getReferenceData(String category) throws CrmException;

	String ADVTOP = "ADVTOP";
	String TIMEZONE = "TIMEZONE";
	String IWAYITP = "IWAYITP";
	String NOYICP = "NOYICP";
	String REGS = "REGS";
	String CLRS = "CLRS";
	String WTSL = "WTSL";
	String YWCE = "YWCE";
	String YOPE = "YOPE";
	String CES = "CES";
	String HLOEE = "HLOEE";
	String LOEAYCS = "LOEAYCS";
	String RFSAOFS = "RFSAOFS";

}
