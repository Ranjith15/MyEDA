package com.edassist.job;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.edassist.exception.LoanAggregatorException;
import com.edassist.models.domain.StudentLoan;
import com.edassist.service.loanaggregator.LoanAggregatorService;
import com.edassist.service.loanaggregator.LoanAggregatorUserService;
import com.edassist.utils.CommonUtil;
import com.edassist.utils.LoanAggregatorUtil;

@Service
@EnableScheduling
public class LoanAggregatorScheduleJob {

	private static Logger log = Logger.getLogger(LoanAggregatorScheduleJob.class);

	@Autowired
	private LoanAggregatorService loanAggregatorService;

	@Autowired
	private LoanAggregatorUserService loanAggregatorUserService;

	@Scheduled(fixedRate = 95 * 60 * 1000, initialDelay = 10 * 1000)
	public void refreshCobSessionTask() {
		if (CommonUtil.getHotProperty(LoanAggregatorUtil.JOB_SCHEDULER).equals("enabled")) {
			try {
				loanAggregatorService.refreshLoanAggregatorAuthTokens();
			} catch (Exception e) {
				log.error("Exception while refreshing COB Session." + e.getMessage());
				e.printStackTrace();
			}

			log.debug("refreshCobSessionTask executed on -" + new Date());
		}
	}

	@Scheduled(cron = "0 0 22 * * ?", zone = "CST")
	public void refreshUserAccountDetailsDailyTask() throws LoanAggregatorException, Exception {
		List<StudentLoan> studentLoanList = null;
		if (CommonUtil.getHotProperty(LoanAggregatorUtil.JOB_SCHEDULER).equals("enabled")) {
			try {
				log.debug("** Start Loan Aggregator Refresh Job **");
				studentLoanList = loanAggregatorUserService.fetchUserAcctNumbersWithActiveApps();
				if (studentLoanList != null && !studentLoanList.isEmpty()) {
					for (StudentLoan studentLoan : studentLoanList) {
						log.debug("** StudentLoan Id: "+studentLoan.getStudentLoanID());
						loanAggregatorUserService.refreshUserAccountDetailsFromJob(Long.valueOf(studentLoan.getParticipantID()));
					}
				} else {
					log.debug("** No Loan Aggregator Loans to Refresh **");
				}
				log.debug("** Start Loan Aggregator Refresh Job **");
			} catch (Exception e) {
				log.error("Exception while refreshing User Accounts daily." + e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
