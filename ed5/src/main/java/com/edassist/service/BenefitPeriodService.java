package com.edassist.service;

import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.Application;
import com.edassist.models.domain.BenefitPeriod;
import com.edassist.models.sp.CapInfo;
import com.edassist.models.sp.ThinBenefitPeriod;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.List;

public interface BenefitPeriodService extends GenericService<BenefitPeriod> {

	List<BenefitPeriod> search(String programId) throws ExceededMaxResultsException;

	List<ThinBenefitPeriod> callBPList3(Long participantId) throws ExceededMaxResultsException;

	List<String> getBPListByClient(Long clientId) throws ExceededMaxResultsException;

	BigDecimal fetchDiscountAmount(Application application);

	BigDecimal calculateDiscountAmount(BigDecimal tuitionAmount, BigDecimal discountPercent);

	void adjustBenefitPeriod(Application application);

	List<BenefitPeriod> fetchBenefitPeriodList(Application application) throws DataAccessException, ExceededMaxResultsException;

	CapInfo getParticipantCapInfo(Long participantId, Long programId, Long degreeObjectiveId, String benefitPeriod);
}
