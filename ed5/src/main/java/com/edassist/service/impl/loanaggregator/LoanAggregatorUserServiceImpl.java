package com.edassist.service.impl.loanaggregator;

import com.edassist.dao.GenericDao;
import com.edassist.dao.StudentLoanDao;
import com.edassist.exception.LoanAggregatorException;
import com.edassist.models.contracts.loanaggregator.*;
import com.edassist.models.domain.*;
import com.edassist.models.mappers.LoanAggregatorUserDomainMapper;
import com.edassist.models.mappers.loanaggregator.LoanTransactionMapper;
import com.edassist.service.GenericService;
import com.edassist.service.ParticipantService;
import com.edassist.service.impl.GenericServiceImpl;
import com.edassist.service.loanaggregator.LoanAggregatorService;
import com.edassist.service.loanaggregator.LoanAggregatorUserService;
import com.edassist.service.loanaggregator.StudentLoanPaymentHistoryService;
import com.edassist.utils.CommonUtil;
import com.edassist.utils.LoanAggregatorUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class LoanAggregatorUserServiceImpl extends GenericServiceImpl<LoanAggregatorUserDomain> implements LoanAggregatorUserService {

	private static Logger log = Logger.getLogger(LoanAggregatorUserServiceImpl.class);

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private LoanAggregatorService loanAggregatorService;

	@Autowired
	private LoanAggregatorUserDomainMapper loanAggregatorUserDomainMapper;

	@Autowired
	private StudentLoanDao studentLoanDao;

	private GenericDao<LoanAggregatorUserDomain> loanAggregatorUserDao;

	@Autowired
	private StudentLoanPaymentHistoryService studentLoanPaymentHistoryService;

	@Autowired
	private LoanTransactionMapper loanTransactionMapper;

	@Autowired
	private GenericService<Property> propertyService;

	@Autowired
	private GenericService<StudentLoan> studentLoanService;

	@Autowired
	public LoanAggregatorUserServiceImpl(@Qualifier("loanAggregatorUserDao") GenericDao<LoanAggregatorUserDomain> genericDao) {
		super(genericDao);
		this.loanAggregatorUserDao = genericDao;
	}

	@Autowired
	@Qualifier("loanAggregatorRestTemplate")
	private RestTemplate restTemplate;

	@Override
	public LoanAggregatorUserResponse authenticateUser(Long participantId) throws Exception {

		LoanAggregatorUserResponse userInDb = verifyLoanServicerUserinDB(participantId);
		LoanAggregatorUserResponse newUser = new LoanAggregatorUserResponse();

		if (userInDb.getUser() == null) {
			Participant participant = participantService.findById(participantId);
			String preferredEmail = participant.getPreferEmail();
			LoanAggregatorUser loanAggregatorUserDto = new LoanAggregatorUser();
			loanAggregatorUserDto.setLoginName(this.getUsername());
			loanAggregatorUserDto.setPassword(this.getPassword());
			loanAggregatorUserDto.setEmail(preferredEmail);
			userInDb.setUser(loanAggregatorUserDto);
			newUser = registerUser(userInDb);
			storeLoanServicerUsertoDB(userInDb, participantId);
			return newUser;
		} else {
			return loginUser(userInDb);
		}
	}

	@Override
	public List<UserAccountDetails> getUserAccounts(String userSession) throws LoanAggregatorException, Exception {
		String url = LoanAggregatorUtil.getYodleeServiceHost(LoanAggregatorUtil.YODLEE_SERVICE_HOST) + LoanAggregatorUtil.ACCOUNTS;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("container", LoanAggregatorUtil.CONTAINER).queryParam("accountType", LoanAggregatorUtil.ACCOUNT_TYPE);
		HttpEntity<String> requestEntity = new HttpEntity<String>(loanAggregatorService.getHeaders(userSession));

		ResponseEntity<UserAccounts> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, requestEntity, UserAccounts.class);
		if (response != null) {
			return response.getBody().getAccount();
		}
		return null;
	}

	@Override
	public List<UserAccountDetails> refreshUserAccount(Long id, String userSession) throws LoanAggregatorException, Exception {
		String url = LoanAggregatorUtil.getYodleeServiceHost(LoanAggregatorUtil.YODLEE_SERVICE_HOST) + LoanAggregatorUtil.ACCOUNTS + "/"+id.toString();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("container", LoanAggregatorUtil.CONTAINER).queryParam("accountType", LoanAggregatorUtil.ACCOUNT_TYPE);
		HttpEntity<String> requestEntity = new HttpEntity<String>(loanAggregatorService.getHeaders(userSession));

		ResponseEntity<UserAccounts> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, requestEntity, UserAccounts.class);
		if (response != null) {
			return response.getBody().getAccount();
		}
		return null;
	}

	private LoanAggregatorUserResponse registerUser(LoanAggregatorUserResponse newUser) throws Exception {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(LoanAggregatorUtil.getYodleeServiceHost(LoanAggregatorUtil.YODLEE_SERVICE_HOST) + LoanAggregatorUtil.USER_REGISTER);
		HttpEntity<LoanAggregatorUserResponse> requestEntity = new HttpEntity<LoanAggregatorUserResponse>(newUser, loanAggregatorService.getHeaders());
		return restTemplate.postForEntity(builder.build().encode().toUri(), requestEntity, LoanAggregatorUserResponse.class).getBody();
	}

	private LoanAggregatorUserResponse loginUser(LoanAggregatorUserResponse userInDb) throws Exception {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(LoanAggregatorUtil.getYodleeServiceHost(LoanAggregatorUtil.YODLEE_SERVICE_HOST) + LoanAggregatorUtil.USER_LOGIN);
		HttpEntity<LoanAggregatorUserResponse> requestEntity = new HttpEntity<LoanAggregatorUserResponse>(userInDb, loanAggregatorService.getHeaders());
		return restTemplate.postForEntity(builder.build().encode().toUri(), requestEntity, LoanAggregatorUserResponse.class).getBody();
	}

	private String getUsername() {
		return this.generateRandomString(3, 3, 0, 0, 0, false) + this.generateRandomString(4, 4, 0, 4, 0, false);
	}

	private String getPassword() {
		return this.generateRandomString(8, 16, 1, 1, 1, true);
	}

	private void storeLoanServicerUsertoDB(LoanAggregatorUserResponse newUser, Long participantId) {

		LoanAggregatorUserDomain loanAggregatorUserDomain = new LoanAggregatorUserDomain();
		loanAggregatorUserDomain = loanAggregatorUserDomainMapper.toDomain(newUser.getUser(), loanAggregatorUserDomain);
		loanAggregatorUserDomain.setParticipantId(participantId);
		loanAggregatorUserDomain.setDateCreated(new Date());

		loanAggregatorUserDao.saveOrUpdate(loanAggregatorUserDomain);
	}

	private LoanAggregatorUserResponse verifyLoanServicerUserinDB(Long participantId) {
		LoanAggregatorUserResponse userFromDb = new LoanAggregatorUserResponse();
		LoanAggregatorUserDomain loanAggregatorUserDomain = loanAggregatorUserDao.findByUniqueParam("participantId", participantId);
		if (loanAggregatorUserDomain != null) {
			userFromDb.setUser(loanAggregatorUserDomainMapper.toDTO(loanAggregatorUserDomain));
		}
		return userFromDb;
	}

	public Participant fetchParticipantById(Long participantId) {
		return participantService.findById(participantId);
	}

	private String generateRandomString(int minLen, int maxLen, int noOfCAPSAlpha, int noOfDigits, int noOfSplChars, boolean passwordFlag) {
		if (minLen > maxLen) {
			throw new IllegalArgumentException("Min. Length > Max. Length!");
		}
		if ((noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen) {
			throw new IllegalArgumentException("Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
		}
		Random rnd = new Random();
		int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
		char[] randomString = new char[len];
		int index = 0;
		for (int i = 0; i < noOfCAPSAlpha; i++) {
			index = getNextIndex(rnd, len, randomString);
			randomString[index] = CommonUtil.ALPHA_CAPS.charAt(rnd.nextInt(CommonUtil.ALPHA_CAPS.length()));
		}
		for (int i = 0; i < noOfDigits; i++) {
			index = getNextIndex(rnd, len, randomString);
			randomString[index] = CommonUtil.NUM.charAt(rnd.nextInt(CommonUtil.NUM.length()));
		}
		if (passwordFlag) {
			for (int i = 0; i < noOfSplChars; i++) {
				index = getNextIndex(rnd, len, randomString);
				randomString[index] = CommonUtil.SPL_CHARS.charAt(rnd.nextInt(CommonUtil.SPL_CHARS.length()));
			}
		}
		for (int i = 0; i < len; i++) {
			if (randomString[i] == 0) {
				randomString[i] = CommonUtil.ALPHA.charAt(rnd.nextInt(CommonUtil.ALPHA.length()));
			}
		}
		if (passwordFlag) {
			return removeContinuousRecurrentCharacters(String.valueOf(randomString));
		}
		return String.valueOf(randomString);
	}

	private String removeContinuousRecurrentCharacters(String input) {
		String outStr = "";
		boolean addFlag = true;
		for (int i = 0; i < input.length(); i++) {
			addFlag = true;
			if (i >= 2 && input.charAt(i - 2) == input.charAt(i) && input.charAt(i - 1) == input.charAt(i)) {
				addFlag = false;
			}
			if (addFlag) {
				outStr += input.charAt(i);
			}
		}
		return outStr;
	}

	private int getNextIndex(Random rnd, int len, char[] randomString) {
		int index = rnd.nextInt(len);
		while (randomString[index = rnd.nextInt(len)] != 0)
			;
		return index;
	}

	@Override
	public FastLinkUser getUserAccessTokens(String userSession) throws LoanAggregatorException, Exception {
		String url = LoanAggregatorUtil.getYodleeServiceHost(LoanAggregatorUtil.YODLEE_SERVICE_HOST) + LoanAggregatorUtil.USER_ACCESS_TOKEN;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("appIds", getFastLinkAppID());
		HttpEntity<String> requestEntity = new HttpEntity<String>(loanAggregatorService.getHeaders(userSession));
		ResponseEntity<FastLinkUser> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, requestEntity, FastLinkUser.class);
		return response != null ? response.getBody() : null;
	}

	@Override
	public String getFastLinkAppID() throws DataAccessException, Exception {
		String fastLinkAppId = null;
		Property property = null;
		List<Property> propertyList = propertyService.findByParam("propertyKey", "YODLEE_FASTLINK_APP_ID");
		if (propertyList != null && propertyList.size() > 0) {
			property = propertyList.get(0);
			fastLinkAppId = property.getPropertyValue();
		}
		return fastLinkAppId;
	}

	@Override
	public List<StudentLoan> fetchLoanAggregatorUserAccountNumbers(Long participantId) throws LoanAggregatorException, Exception {
		return studentLoanDao.fetchLoanAggregatorUserAccountNumbers(participantId);
	}

	@Override
	public List<StudentLoan> fetchUserAcctNumbersWithActiveApps() throws LoanAggregatorException, Exception {
		return studentLoanDao.fetchUserAcctNumbersWithActiveApps();

	}

	@Override
	public LoanAggregatorUserResponse refreshUserAccountDetailsFromLogin(List<StudentLoan> studentLoans) throws Exception {
		LoanAggregatorUserResponse loanAggregatorUserResponse = this.authenticateUser(Long.valueOf(studentLoans.get(0).getParticipantID()));
		String userSession = (loanAggregatorUserResponse != null) ? loanAggregatorUserResponse.getUser().getSession().getUserSession() : null;
		List<UserAccountDetails> userAccountDetailsList = this.getUserAccounts(userSession);

		// Refresh Account Details
		if (userAccountDetailsList != null && !userAccountDetailsList.isEmpty()) {
			for (UserAccountDetails userAccountDetails : userAccountDetailsList) {
				// Map details to be refreshed into domain object and SaveOrUpdate in DB
				List<StudentLoan> filteredStudentLoans = studentLoans.stream().filter(item -> item.getLoanAggregatorUserAccountId().equals(userAccountDetails.getId().intValue()))
						.collect(Collectors.toList());
				for (StudentLoan studentLoan : filteredStudentLoans) {
					if (studentLoan != null && userAccountDetails != null && studentLoan.getLoanAggregatorUserAccountId() == userAccountDetails.getId().intValue()) {
						studentLoan = studentLoanService.findById(studentLoan.getStudentLoanID());
						// Map details to be refreshed into domain object and SaveOrUpdate in DB
						this.mapAndSaveOrUpdate(studentLoan, userAccountDetails);
					}
				}
			}
		}

		// Fetch transactions over last 90 days & persist in DB
		List<LoanTransaction> transactionList = loanAggregatorService.getUserTransactions(userSession, 0);
		saveLoanTransactions(transactionList, null);
		return loanAggregatorUserResponse;
	}

	@Override
	public void refreshUserAccountDetailsFromJob(Long participantId) throws Exception {

		List<UserAccountDetails> userAccountDetailsList = null;
		List<LoanTransaction> transactionList = null;
		LoanAggregatorUserResponse loanAggregatorUserResponse = this.authenticateUser(Long.valueOf(participantId));
		String userSession = (loanAggregatorUserResponse != null) ? loanAggregatorUserResponse.getUser().getSession().getUserSession() : null;

		List<StudentLoan> studentLoanList = this.fetchLoanAggregatorUserAccountNumbers(participantId);
		for (StudentLoan studentLoan : studentLoanList) {
			log.debug("ParticipantId: " + participantId + " - AccountId: " + studentLoan.getLoanAggregatorUserAccountId());
			userAccountDetailsList = this.refreshUserAccount(Long.valueOf(studentLoan.getLoanAggregatorUserAccountId()), userSession);
			// Refresh Account Details
			if (userAccountDetailsList != null && !userAccountDetailsList.isEmpty()) {
				for (UserAccountDetails userAccountDetails : userAccountDetailsList) {
					if (studentLoan != null && userAccountDetails != null) {
						// Map details to be refreshed into domain object and SaveOrUpdate in DB
						this.mapAndSaveOrUpdate(studentLoan, userAccountDetails);
					}
				}
			}

			// Fetch transactions over last 90 days & persist in DB
			transactionList = loanAggregatorService.getUserTransactions(userSession, studentLoan.getLoanAggregatorUserAccountId());
			saveLoanTransactions(transactionList, studentLoan);
		}
		if (userSession != null) {
			logoutUser(userSession);
		}
	}

	@Override
	public boolean logoutUser(String userSession) {
		String url = LoanAggregatorUtil.getYodleeServiceHost(LoanAggregatorUtil.YODLEE_SERVICE_HOST) + LoanAggregatorUtil.USER_LOGOUT;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		HttpEntity<String> requestEntity = new HttpEntity<String>(loanAggregatorService.getHeaders(userSession));
		ResponseEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, requestEntity, String.class);
		return response != null && HttpStatus.NO_CONTENT == response.getStatusCode();
	}

	private void saveLoanTransactions(List<LoanTransaction> transactionList, StudentLoan studentLoan) throws DataAccessException, Exception {
		if (null != transactionList && !transactionList.isEmpty()) {
			List<StudentLoanPaymentHistory> studentLoanPaymentHistories = new ArrayList<StudentLoanPaymentHistory>();
			StudentLoanPaymentHistory studentLoanPaymentHistory = null;
			for (LoanTransaction loanTransaction : transactionList) {
				log.debug("Account Id: "+loanTransaction.getAccountId() + " Amount:" + loanTransaction.getAmount().getAmount() + " Transaction Date: " + loanTransaction.getDate());
				if (studentLoan == null) {
					StudentLoan studentLoanForTransaction = studentLoanService.findByUniqueParam("loanAggregatorUserAccountId", loanTransaction.getAccountId().intValue());
					studentLoanPaymentHistory = loanTransactionMapper.toDomain(loanTransaction, studentLoanForTransaction);
				} else {
					studentLoanPaymentHistory = loanTransactionMapper.toDomain(loanTransaction, studentLoan);
				}
				studentLoanPaymentHistories.add(studentLoanPaymentHistory);
			}
			studentLoanPaymentHistoryService.saveLoanPaymentHistory(studentLoanPaymentHistories);
		} else {
			log.debug("Participant have no LoanTransactions");
		}
	}

	private void mapAndSaveOrUpdate(StudentLoan studentLoan, UserAccountDetails userAccountDetails) {
		studentLoan.setModifiedBy(11312960); // SYSTEM PROCESS USER ID
		studentLoan.setModifiedDate(new Date());
		studentLoan.setLoanBalance(userAccountDetails.getBalance() != null ? userAccountDetails.getBalance().getAmount() : studentLoan.getLoanBalance());
		studentLoan.setAmountDue(userAccountDetails.getAmountDue() != null ? userAccountDetails.getAmountDue().getAmount() : studentLoan.getAmountDue());
		studentLoan.setPaymentDueDate(userAccountDetails.getDueDate() != null ? userAccountDetails.getDueDate() : studentLoan.getPaymentDueDate());
		studentLoan.setLastRefreshedDate(userAccountDetails.getRefreshInfo().getLastRefreshed() != null ? userAccountDetails.getRefreshInfo().getLastRefreshed() : studentLoan.getLastRefreshedDate());
		studentLoanDao.saveOrUpdate(studentLoan);
	}

}
