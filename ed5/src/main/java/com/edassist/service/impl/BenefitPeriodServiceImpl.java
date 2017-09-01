package com.edassist.service.impl;

import com.edassist.dao.BenefitPeriodDao;
import com.edassist.dao.GenericDao;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.*;
import com.edassist.models.sp.CapInfo;
import com.edassist.models.sp.ThinBenefitPeriod;
import com.edassist.service.BenefitPeriodService;
import com.edassist.service.GenericService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.List;

@Service
public class BenefitPeriodServiceImpl extends GenericServiceImpl<BenefitPeriod> implements BenefitPeriodService {

	private static Logger log = Logger.getLogger(BenefitPeriodServiceImpl.class);

	@Autowired
	private GenericService<ProgramProviders> programProviderService;

	private BenefitPeriodDao benefitPeriodDao;

	public BenefitPeriodServiceImpl() {
	}

	@Autowired
	public BenefitPeriodServiceImpl(@Qualifier("benefitPeriodDaoImpl") GenericDao<BenefitPeriod> genericDao) {
		super(genericDao);
		this.benefitPeriodDao = (BenefitPeriodDao) genericDao;
	}

	@Override
	public List<BenefitPeriod> search(String programId) throws ExceededMaxResultsException {
		return benefitPeriodDao.search(programId);
	}

	@Override
	public List<ThinBenefitPeriod> callBPList3(Long participantID) throws ExceededMaxResultsException {
		return benefitPeriodDao.callBPList3(participantID);
	}

	@Override
	public List<String> getBPListByClient(Long clientId) throws ExceededMaxResultsException {
		return benefitPeriodDao.getBPListByClient(clientId);
	}

	@Override
	public BigDecimal fetchDiscountAmount(Application application) {

		if (application == null) {
			throw new BadRequestException("Application must not be null.");
		}

		if (application.getBenefitPeriodID() == null) {
			throw new BadRequestException("BenefitPeriod for this application must not be null.");
		}

		if (application.getBenefitPeriodID().getProgramID() == null) {
			throw new BadRequestException("Program for this application must not be null.");
		}

		if (application.getEducationalProvider() == null) {
			throw new BadRequestException("Education provider for this application  must not be null.");
		}

		if (application.getEducationalProvider().isFeaturedProvider()) {
			// provider is in FPN
			// lookup program discount
			String[] paramNames = { "programID", "educationalProviderID" };
			Object[] paramValues = { application.getBenefitPeriodID().getProgramID(), application.getEducationalProvider() };
			List<ProgramProviders> progProviderList;
			try {
				progProviderList = programProviderService.findByParams(paramNames, paramValues, null, null);
			} catch (ExceededMaxResultsException e) {
				throw new BadRequestException("More than one programProvider record was returned, expected only 1.  ProgramId: [" + application.getBenefitPeriodID().getProgramID().getProgramID()
						+ "] ProviderId: [" + application.getEducationalProvider().getEducationalProviderId() + "]");
			}

			if (CollectionUtils.isEmpty(progProviderList)) {
				return BigDecimal.ZERO;
			} else if (progProviderList.size() > 1) {
				throw new BadRequestException("More than one programProvider record was returned, expected only 1.  ProgramId: [" + application.getBenefitPeriodID().getProgramID().getProgramID()
						+ "] ProviderId: [" + application.getEducationalProvider().getEducationalProviderId() + "]");
			} else {
				return progProviderList.get(0).getProgramDiscountPercent();
			}
		} else {
			// if the provider is not FPN then return zero
			return BigDecimal.ZERO;
		}
	}

	@Override
	public BigDecimal calculateDiscountAmount(BigDecimal tuitionAmount, BigDecimal discountPercent) {
		if (tuitionAmount == null || discountPercent == null || BigDecimal.ZERO.equals(tuitionAmount) || BigDecimal.ZERO.equals(discountPercent)) {
			return BigDecimal.ZERO;
		}
		return tuitionAmount.multiply((discountPercent.divide(BigDecimal.valueOf(100L))), MathContext.DECIMAL32);
	}

	@Override
	public void adjustBenefitPeriod(Application application) {
		if (application == null) {
			throw new BadRequestException("application must not be null.");
		}

		if (application.getBenefitPeriodID() == null) {
			throw new BadRequestException("Application's benefitPeriod must not be null.");
		}

		if (application.getBenefitPeriodID().getProgramID() == null) {
			throw new BadRequestException("Application's program must not be null.");
		}

		if (application.getBenefitPeriodID().getProgramID().getDeterminationDate() == null) {
			throw new BadRequestException("Program's DeterminationDate must not be null.");
		}

		Program selectProgram = application.getBenefitPeriodID().getProgramID();

		// check the client configuration to determine if you use course start date or course end date or the Payment Date
		Date targetDate = null;

		if (Program.DETERMINATION_TYPE.CourseStartDate.equals(selectProgram.getDeterminationDate())) {
			targetDate = application.getCourseStartDate();
		} else if (Program.DETERMINATION_TYPE.CourseEndDate.equals(selectProgram.getDeterminationDate())) {
			targetDate = application.getCourseEndDate();
		} else if (Program.DETERMINATION_TYPE.PaymentDate.equals(selectProgram.getDeterminationDate()) && application.getReadyForPaymentDate() != null) {
			log.debug("(adjust) Potential BP change owing to Payment Date Determination (Ready for Payment Date) - application number " + application.getApplicationNumber());
			targetDate = application.getReadyForPaymentDate();
		} else if (Program.DETERMINATION_TYPE.PaymentDate.equals(selectProgram.getDeterminationDate()) && application.getReadyForPaymentDate() == null) {
			if ("SLR".equals(application.getBenefitPeriodID().getProgramID().getProgramTypeID().getProgramTypeCode())
					|| "SLD".equals(application.getBenefitPeriodID().getProgramID().getProgramTypeID().getProgramTypeCode())) {
				log.debug("(adjust) Potential BP change owing to Payment Date Determination (Billing Period End Date) - application number " + application.getApplicationNumber());
				targetDate = application.getReimburseStudentLoanApp().getLoanPaymentPeriodEndDate(); // will eventually be adjusted (when application transitions to 425/500 status)
			} else {
				log.debug("(adjust) Potential BP change owing to Payment Date Determination (Course End Date) - application number " + application.getApplicationNumber());
				targetDate = application.getCourseEndDate(); // will eventually be adjusted (when application transitions to 425/500 status)
			}
		} else if (Program.DETERMINATION_TYPE.BillingPeriodStartDate.equals(selectProgram.getDeterminationDate())) {
			targetDate = application.getReimburseStudentLoanApp().getLoanPaymentPeriodStartDate();
		} else if (Program.DETERMINATION_TYPE.BillingPeriodEndDate.equals(selectProgram.getDeterminationDate())) {
			targetDate = application.getReimburseStudentLoanApp().getLoanPaymentPeriodEndDate();
		} else {
			throw new BadRequestException("Program's determination date [" + selectProgram.getDeterminationDate() + "] did not match a known value [" + Program.DETERMINATION_TYPE.CourseStartDate
					+ ", " + Program.DETERMINATION_TYPE.CourseEndDate + "]");
		}

		if (targetDate == null) {
			throw new BadRequestException("TargetDate must not be null");
		}

		// iterate thru list of benefit periods find the one that encompasses the date
		try {
			List<BenefitPeriod> benefitPeriodList = this.fetchBenefitPeriodList(application);
			if (!CollectionUtils.isEmpty(benefitPeriodList)) {
				boolean foundBenefitPeriodMatch = false;
				for (BenefitPeriod currBenefitPeriod : benefitPeriodList) {

					if ((currBenefitPeriod.getStartDate().compareTo(targetDate) < 0 || currBenefitPeriod.getStartDate().compareTo(targetDate) == 0)
							&& (currBenefitPeriod.getEndDate().compareTo(targetDate) > 0 || currBenefitPeriod.getEndDate().compareTo(targetDate) == 0)) {
						currBenefitPeriod.setProgramID(selectProgram);
						log.debug("Adjusting application benefit period. Application " + application.getApplicationNumber() + " - changing from BPID "
								+ application.getBenefitPeriodID().getBenefitPeriodID() + " to BPID " + currBenefitPeriod.getBenefitPeriodID());
						application.setBenefitPeriodID(currBenefitPeriod);
						foundBenefitPeriodMatch = true;
					}
				}
				if (!foundBenefitPeriodMatch) {
					log.error("Benefit period does not exist for target date [" + targetDate + "]. ");
				}
			}
		} catch (ExceededMaxResultsException e) {
			log.error(e.getMessage());
			throw new BadRequestException();
		}
	}

	@Override
	public List<BenefitPeriod> fetchBenefitPeriodList(Application application) throws DataAccessException, ExceededMaxResultsException {
		if (application != null && application.getBenefitPeriodID() != null && application.getBenefitPeriodID().getProgramID() != null) {
			// TODO PERFORMANCE: should be able to use domain object mapping, but was getting lazy init error
			return benefitPeriodDao.findByParam("programID", application.getBenefitPeriodID().getProgramID());
		}
		return null;
	}

	@Override
	public CapInfo getParticipantCapInfo(Long participantId, Long programId, Long degreeObjectiveId, String benefitPeriod) {
		return benefitPeriodDao.getParticipantCapInfo(participantId, programId, degreeObjectiveId, benefitPeriod);
	}

}
