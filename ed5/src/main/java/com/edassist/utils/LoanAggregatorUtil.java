package com.edassist.utils;

import com.edassist.exception.BadRequestException;

public class LoanAggregatorUtil {

	// yodlee hosts
	public static final String YODLEE_SERVICE_HOST = "services.hosts.yodlee";
	public static final String COBRAND_LOGIN_ID = "loanServicer.yodlee.cobrandLogin";
	public static final String COBRAND_LOGIN_PASSWORD = "loanServicer.yodlee.cobrandPassword";
	public static final String JOB_SCHEDULER = "enable.scheduled.jobs";

	// REST URLS
	public static final String COBRAND_LOGIN = "/cobrand/login";
	public static final String USER_REGISTER = "/user/register";
	public static final String USER_LOGIN = "/user/login";
	public static final String USER_LOGOUT = "/user/logout";
	public static final String USER_ACCESS_TOKEN = "/user/accessTokens";
	public static final String ACCOUNTS = "/accounts";
	public static final String TRANSACTIONS = "/transactions";

	// yodlee params
	public static final String CONTAINER = "loan";
	public static final String ACCOUNT_TYPE = "STUDENT_LOAN";

	public static String getYodleeServiceHost(String host) throws BadRequestException {
		String hostFromProperty = CommonUtil.getHotProperty(host);
		if (hostFromProperty == null || hostFromProperty.isEmpty()) {
			throw new BadRequestException();
		}
		return hostFromProperty;
	}
}