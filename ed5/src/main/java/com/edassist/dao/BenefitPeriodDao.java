package com.edassist.dao;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.BenefitPeriod;
import com.edassist.models.sp.CapInfo;
import com.edassist.models.sp.ThinBenefitPeriod;

import java.util.List;

public interface BenefitPeriodDao extends GenericDao<BenefitPeriod> {

	List<BenefitPeriod> search(String programId) throws ExceededMaxResultsException;

	List<ThinBenefitPeriod> callBPList3(Long participantId) throws ExceededMaxResultsException;

	List<String> getBPListByClient(Long clientId) throws ExceededMaxResultsException;

	CapInfo getParticipantCapInfo(Long participantId, Long programId, Long degreeObjectiveId, String benefitPeriod);
}
