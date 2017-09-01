package com.edassist.dao.impl;

import com.edassist.dao.ApprovalHistoryDao;
import com.edassist.models.domain.ApprovalHistory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ApprovalHistoryDaoImpl extends GenericDaoImpl<ApprovalHistory> implements ApprovalHistoryDao {

	static Logger logger = Logger.getLogger(ApprovalHistoryDaoImpl.class);



}
