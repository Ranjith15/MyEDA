package com.edassist.security;

import com.edassist.models.domain.User;
import com.edassist.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class TamsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider implements AuthenticationProvider {

	private static Logger log = Logger.getLogger(TamsAuthenticationProvider.class);

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		try {
			authentication = doInternalAuthenticate(authentication);
		} catch (AuthenticationException e) {
			// TODO log warning message
			throw e;
		} catch (Exception e) {
			log.error("Unexpected exception in tams authentication provider: ", e);
			e.printStackTrace();
			// Catch-all for exceptions to prevent stack-track on login
			String message = "Unexpected exception in tams authentication provider";
			throw new AuthenticationServiceException(message, e);
		}

		if (!authentication.isAuthenticated()) {
			return authentication;
		}

		UsernamePasswordAuthenticationToken simpleUserAuthentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),
				authentication.getAuthorities());
		simpleUserAuthentication.setDetails(authentication.getDetails());

		return super.authenticate(simpleUserAuthentication);
	}

	private Authentication doInternalAuthenticate(Authentication authentication) {

		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		Long clientId = null;
		User user = null;
		Map userMap = null;
		/*
		 * Split username. Format is clientId:username
		 */
		if (StringUtils.isNotBlank(username)) {
			String[] userDetail = StringUtils.split(username, ":", 2);

			if (userDetail != null && userDetail.length == 2) {
				username = userDetail[1];
				clientId = new Long(userDetail[0]);
				System.out.println("doInternalAuthenticate==" + clientId + "," + username);
				userMap = userService.authenticate(username, password, clientId);
				if (userMap != null && userMap.containsKey("user")) {
					user = (User) userMap.get("user");
				} else {
					throw new BadCredentialsException("Invalid login credentials");
				}
			}
		}

		if (user != null) {
			// Retrieve roles
			List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, roles);
			token.setDetails(clientId);

			return token;
		} else {

			if (userMap != null && ((Boolean) userMap.get("isLocked") == Boolean.TRUE)) {
				throw new LockedException("Your account has been locked due to too many invalid attempts. Please contact your administrator.");
			} else {
				throw new BadCredentialsException("Invalid login credentials");
			}
		}
	}

	@Override
	public boolean supports(Class authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

	@Override
	public UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		User user = userService.retrieveUser(username, (Long) authentication.getDetails());
		UserDetail userDetail = new UserDetail();
		userDetail.setUsername(username);
		userDetail.setUser(user);
		userDetail.setClientId((Long) authentication.getDetails());

		Collection<GrantedAuthority> tweakedAuthorities = new ArrayList();

		userDetail.setAuthorities(tweakedAuthorities);
		return userDetail;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails arg0, UsernamePasswordAuthenticationToken arg1) throws AuthenticationException {
		// Not implemented
	}

}
