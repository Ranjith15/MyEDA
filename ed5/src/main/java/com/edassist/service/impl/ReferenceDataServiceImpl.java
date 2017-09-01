package com.edassist.service.impl;

import com.edassist.exception.CrmException;
import com.edassist.exception.CrmTokenExpiredException;
import com.edassist.models.domain.crm.*;
import com.edassist.service.CRMService;
import com.edassist.service.ReferenceDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class ReferenceDataServiceImpl implements ReferenceDataService {

	private static Logger log = Logger.getLogger(ReferenceDataServiceImpl.class);

	private ReferenceData refData = null;
	private Map<String, Topic> topicsMap;
	private List<TicketStatus> statusList;
	private Map<String, List<Code>> advisingRefDataMap = new HashMap<String, List<Code>>();

	private Map<Integer, String> statusMap = new HashMap<Integer, String>();
	private Map<String, Topic> helpDeskTopicMap;
	private static final String refDataErrMsg = "No Reference Data found for the categoty %s";
	private static final String refDataDescErrMsg = "No description found for the categoty %s and id %s";

	@Autowired
	private CRMService crmService;

	/*
	 * (non-Javadoc)
	 * @see com.edassist.service.ReferenceDataService#getStatuses()
	 */
	@Override
	public List<TicketStatus> getStatuses() {
		if (statusList == null) {
			this.loadReferenceData();
		}
		return statusList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.edassist.service.ReferenceDataService#getSubTopics()
	 */
	@Override
	public List<Topic> getSubTopics(String topicId) {
		if (topicsMap == null) {
			this.loadReferenceData();
		}
		Topic topic = topicsMap.get(topicId);
		return new ArrayList<Topic>(Arrays.asList(topic.getSubTopics()));
	}

	/*
	 * (non-Javadoc)
	 * @see com.edassist.service.ReferenceDataService#getTopics()
	 */
	@Override
	public List<Topic> getTopics() {
		if (topicsMap == null) {
			this.loadReferenceData();
		}
		return new ArrayList<Topic>(topicsMap.values());
	}

	/*
	 * (non-Javadoc)
	 * @see com.edassist.service.ReferenceDataService#loadReferenceData()
	 */
	@Override
	@PostConstruct
	public void loadReferenceData() {
		try {
			try {
				refData = crmService.getReferenceData();

				advisingRefDataMap.put(ADVTOP, crmService.getReferenceData(ADVTOP));
				// advisingRefDataMap.put(ANTSTDT, getCrmService().getReferenceData(ANTSTDT));
				// advisingRefDataMap.put(ANTSTYR, getCrmService().getReferenceData(ANTSTYR));
				advisingRefDataMap.put(HLOEE, crmService.getReferenceData(HLOEE));// replaced HIEDCOMP //ED-1308
				// advisingRefDataMap.put(RESNADV, getCrmService().getReferenceData(RESNADV));
				// advisingRefDataMap.put(SCHTP, getCrmService().getReferenceData(SCHTP));
				advisingRefDataMap.put(TIMEZONE, crmService.getReferenceData(TIMEZONE));
				advisingRefDataMap.put(LOEAYCS, crmService.getReferenceData(LOEAYCS));// replaced TPDGSEEK //ED-1308

				advisingRefDataMap.put(RFSAOFS, crmService.getReferenceData(RFSAOFS));// replaced ITRFTAS //ED-1308
				advisingRefDataMap.put(IWAYITP, crmService.getReferenceData(IWAYITP));
				advisingRefDataMap.put(NOYICP, crmService.getReferenceData(NOYICP));
				// advisingRefDataMap.put(TNOYAC, getCrmService().getReferenceData(TNOYAC));
				// advisingRefDataMap.put(DYCHSL, getCrmService().getReferenceData(DYCHSL));
				// Loan Repay Advising Topics
				advisingRefDataMap.put(REGS, crmService.getReferenceData(REGS));
				advisingRefDataMap.put(CLRS, crmService.getReferenceData(CLRS));
				advisingRefDataMap.put(WTSL, crmService.getReferenceData(WTSL));
				// ED-1308
				advisingRefDataMap.put(YWCE, crmService.getReferenceData(YWCE));
				advisingRefDataMap.put(YOPE, crmService.getReferenceData(YOPE));
				advisingRefDataMap.put(CES, crmService.getReferenceData(CES));

			} catch (CrmTokenExpiredException ex) {
				refData = crmService.getReferenceData();
			}
		} catch (CrmException ex) {
			log.error("Error while loading reference data from CRM", ex);
		} catch (Exception ex) {
			log.error("Unexpected Error while loading reference data from CRM", ex);
		}
		if (refData != null) {
			List<Topic> topicList = refData.getTopics();
			if (topicList != null) {
				HashMap<String, Topic> map = new HashMap<String, Topic>();
				for (Topic topic : topicList) {
					map.put(topic.getTopicId(), topic);
				}
				topicsMap = map;
			}
			List<TicketState> stateList = refData.getStates();
			if (stateList != null) {
				ArrayList<TicketStatus> list = new ArrayList<TicketStatus>();
				for (TicketState state : stateList) {
					list.addAll(Arrays.asList(state.getStatuses()));
				}
				statusList = list;
			}
			List<Topic> helpDeskTopics = refData.getHelpDeskTopics();
			if (helpDeskTopics != null) {
				helpDeskTopicMap = new HashMap<String, Topic>();
				for (Topic topic : helpDeskTopics) {
					helpDeskTopicMap.put(topic.getTopicId(), topic);
				}
			}
			// Added to display the status description. Will be replaced with reference data from CRM
			statusMap.put(0, "Open");
			statusMap.put(1, "Closed");
			statusMap.put(2, "Cancelled");
			statusMap.put(3, "Scheduled");
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.edassist.service.ReferenceDataService#lookupSubTopic(java.lang .String)
	 */
	@Override
	public Topic lookupSubTopic(String subTopicId) {
		if (topicsMap == null) {
			this.loadReferenceData();
		}
		if (topicsMap != null) {
			for (Topic topic : topicsMap.values()) {
				for (Topic subTopic : topic.getSubTopics()) {
					if (subTopic.getTopicId().equals(subTopicId)) {
						return subTopic;
					}
				}
			}

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.edassist.service.ReferenceDataService#lookupTopicWithSubTopic (java.lang.String)
	 */
	@Override
	public Topic lookupTopicWithSubTopic(String subTopicId) {
		if (topicsMap == null) {
			this.loadReferenceData();
		}
		if (topicsMap != null) {
			for (Topic topic : topicsMap.values()) {
				for (Topic subTopic : topic.getSubTopics()) {
					if (subTopic.getTopicId().equals(subTopicId)) {
						return topic;
					}
				}
			}

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.edassist.service.ReferenceDataService#getReferenceData(java.lang.String)
	 */
	@Override
	public List<Code> getReferenceData(String category) throws CrmException {
		List<Code> codes = advisingRefDataMap.get(category);
		if (codes == null) {
			throw new CrmException(String.format(refDataErrMsg, category));
		}
		return codes;
	}
}
