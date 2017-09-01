package com.edassist.controller.loanaggregator;

import com.edassist.models.contracts.loanaggregator.FastLinkUser;
import com.edassist.models.contracts.loanaggregator.LoanAggregatorUserResponse;
import com.edassist.models.contracts.loanaggregator.UserAccountDetails;
import com.edassist.models.domain.type.StudentLoanList;
import com.edassist.models.dto.loanaggregator.FastLinkUserDTO;
import com.edassist.models.dto.loanaggregator.LoanAggregatorUserResponseDTO;
import com.edassist.models.dto.loanaggregator.UserAccountDetailsDTO;
import com.edassist.models.mappers.loanaggregator.FastLinkUserMapper;
import com.edassist.models.mappers.loanaggregator.LoanAggregatorUserResponseMapper;
import com.edassist.models.mappers.loanaggregator.UserAccountDetailsMapper;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.SessionService;
import com.edassist.service.loanaggregator.LoanAggregatorUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoanAggregatorUserController {

	private final SessionService sessionService;
	private final LoanAggregatorUserService loanAggregatorUserService;
	private final UserAccountDetailsMapper userAccountDetailsMapper;
	private final FastLinkUserMapper fastLinkUserMapper;
	private final LoanAggregatorUserResponseMapper loanAggregatorUserResponseMapper;

	@Autowired
	public LoanAggregatorUserController(SessionService sessionService, LoanAggregatorUserService loanAggregatorUserService, UserAccountDetailsMapper userAccountDetailsMapper,
			FastLinkUserMapper fastLinkUserMapper, LoanAggregatorUserResponseMapper loanAggregatorUserResponseMapper) {
		this.sessionService = sessionService;
		this.loanAggregatorUserService = loanAggregatorUserService;
		this.userAccountDetailsMapper = userAccountDetailsMapper;
		this.fastLinkUserMapper = fastLinkUserMapper;
		this.loanAggregatorUserResponseMapper = loanAggregatorUserResponseMapper;
	}

	@RequestMapping(value = "/v1/loan-aggregator/user/login", method = RequestMethod.POST)
	public ResponseEntity<LoanAggregatorUserResponseDTO> loginUser() throws Exception {
		Long participantId = sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID);
		LoanAggregatorUserResponse loanAggregatorUserResponse = loanAggregatorUserService.authenticateUser(participantId);
		LoanAggregatorUserResponseDTO loanAggregatorUserResponseDto = loanAggregatorUserResponseMapper.toDTO(loanAggregatorUserResponse);
		return new ResponseEntity<>(loanAggregatorUserResponseDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/loan-aggregator/users/accounts", method = RequestMethod.POST)
	public ResponseEntity<List<UserAccountDetailsDTO>> fetchUserAccounts() throws Exception {
		String userSession = sessionService.getServiceUserSessionHeader();
		List<UserAccountDetails> userAccounts = loanAggregatorUserService.getUserAccounts(userSession);
		List<UserAccountDetailsDTO> userAccountDetailsDto = userAccountDetailsMapper.toDTOList(userAccounts);
		return new ResponseEntity<>(userAccountDetailsDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/loan-aggregator/user/accessTokens", method = RequestMethod.POST)
	public ResponseEntity<FastLinkUserDTO> getUserAccessTokens() throws Exception {
		String userSession = sessionService.getServiceUserSessionHeader();
		FastLinkUser fastLinkUser = loanAggregatorUserService.getUserAccessTokens(userSession);
		FastLinkUserDTO fastLinkUserDto = fastLinkUserMapper.toDTO(fastLinkUser);
		return new ResponseEntity<>(fastLinkUserDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/loan-aggregator/user/refreshAccounts", method = RequestMethod.POST)
	public ResponseEntity<LoanAggregatorUserResponseDTO> refreshUserAccountDetails(@RequestBody StudentLoanList studentLoanList) throws Exception {
		LoanAggregatorUserResponse loanAggregatorUserResponse = loanAggregatorUserService.refreshUserAccountDetailsFromLogin(studentLoanList.getStudentLoanList());
		LoanAggregatorUserResponseDTO loanAggregatorUserResponseDto = loanAggregatorUserResponseMapper.toDTO(loanAggregatorUserResponse);
		return new ResponseEntity<>(loanAggregatorUserResponseDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/loan-aggregator/user/logout", method = RequestMethod.POST)
	public ResponseEntity<Boolean> logoutUser() {
		String userSession = sessionService.getServiceUserSessionHeader();
		Boolean userLogoutStatus = Boolean.valueOf(loanAggregatorUserService.logoutUser(userSession));
		return new ResponseEntity<>(userLogoutStatus, HttpStatus.OK);
	}

}
