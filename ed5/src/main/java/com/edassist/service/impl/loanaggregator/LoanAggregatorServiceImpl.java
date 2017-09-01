package com.edassist.service.impl.loanaggregator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.edassist.constants.LoanAggregatorConstants;
import com.edassist.dao.GenericDao;
import com.edassist.exception.ForbiddenException;
import com.edassist.exception.LoanAggregatorException;
import com.edassist.models.contracts.loanaggregator.LoanTransaction;
import com.edassist.models.contracts.loanaggregator.UserLoanTransactions;
import com.edassist.models.domain.AuthenticationTokens;
import com.edassist.models.dto.loanaggregator.AuthenticationDTO;
import com.edassist.service.impl.GenericServiceImpl;
import com.edassist.service.loanaggregator.LoanAggregatorService;
import com.edassist.utils.CommonUtil;
import com.edassist.utils.LoanAggregatorUtil;

@Service
public class LoanAggregatorServiceImpl extends GenericServiceImpl<AuthenticationTokens> implements LoanAggregatorService {

	private static Logger log = Logger.getLogger(LoanAggregatorServiceImpl.class);

	@Autowired
	@Qualifier("loanAggregatorRestTemplate")
	private RestTemplate restTemplate;

	private GenericDao<AuthenticationTokens> authenticationTokensDao;

	@Autowired
	public LoanAggregatorServiceImpl(@Qualifier("authenticationTokensDao") GenericDao<AuthenticationTokens> genericDao) {
		super(genericDao);
		this.authenticationTokensDao = genericDao;
	}

	@Override
	public String getLoanAggregatorAuthTokens() throws ForbiddenException, LoanAggregatorException {

		AuthenticationTokens authenticationTokens = authenticationTokensDao.findById(LoanAggregatorConstants.LA_AUTHENTICATION_TOKEN_ID);
		if (authenticationTokens == null) {
			throw new LoanAggregatorException(LoanAggregatorConstants.LOANAGGREGATOR_TOKEN_FAILED);
		}
		return authenticationTokens.getLoginToken();
	}

	@Override
	public void refreshLoanAggregatorAuthTokens() throws LoanAggregatorException {

		Date currTime = new Date();
		AuthenticationTokens authTokens = authenticationTokensDao.findById(LoanAggregatorConstants.LA_AUTHENTICATION_TOKEN_ID);
		if (authTokens != null) {
			String url = LoanAggregatorUtil.getYodleeServiceHost(LoanAggregatorUtil.YODLEE_SERVICE_HOST) + LoanAggregatorUtil.COBRAND_LOGIN;
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("cobrandLogin", authTokens.getLoginName()).queryParam("cobrandPassword", authTokens.getPassword());
			ResponseEntity<AuthenticationDTO> response = restTemplate.postForEntity(builder.build().encode().toUri(), null, AuthenticationDTO.class);
			authTokens.setLoginToken(response.getBody().getSession().getCobSession());
			authTokens.setDateModified(currTime);
			authenticationTokensDao.saveOrUpdate(authTokens);
		}

	}

	@Override
	public HttpHeaders getHeaders(String userSession) throws ForbiddenException, LoanAggregatorException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		StringBuilder header = new StringBuilder("cobSession=").append(this.getLoanAggregatorAuthTokens());
		header.append(",userSession=").append(userSession);
		headers.add("Authorization", header.toString());
		return headers;
	}

	@Override
	public HttpHeaders getHeaders() throws ForbiddenException, LoanAggregatorException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		StringBuilder header = new StringBuilder("cobSession=").append(this.getLoanAggregatorAuthTokens());
		headers.add("Authorization", header.toString());
		return headers;
	}

	@Override
	public List<LoanTransaction> getUserTransactions(String userSession, int accountId) throws LoanAggregatorException {

		String url = LoanAggregatorUtil.getYodleeServiceHost(LoanAggregatorUtil.YODLEE_SERVICE_HOST) + LoanAggregatorUtil.TRANSACTIONS;
		Date nintyDaysAgoDate = CommonUtil.addDays(new Date(), -90);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("container", "loan").queryParam("fromDate", CommonUtil.formatDate(nintyDaysAgoDate, "yyyy-MM-dd"));
		if (accountId != 0) {
			builder.queryParam("accountId", accountId);
		}
		HttpEntity<?> requestEntity = new HttpEntity<>(getHeaders(userSession));

		List<LoanTransaction> transactions = new ArrayList<LoanTransaction>();
		ResponseEntity<UserLoanTransactions> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, requestEntity, UserLoanTransactions.class);
		if (response != null) {
			transactions = response.getBody().getTransaction();
		}
		return transactions;
	}

}
