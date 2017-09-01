/**
 * 
 */
package com.edassist.security;

import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;

import com.edassist.models.domain.User;

/**
 */
public class UserDetail implements UserDetails {

	private static final long serialVersionUID = 4567822654173505334L;

	private User user;

	private transient Collection authorities;

	private transient String password;

	private transient String username;

	private Long clientId;

	@Override
	public Collection getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public Long getClientId() {
		return clientId;
	}

	public User getUser() {
		return user;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public void setAuthorities(Collection authorities) {
		this.authorities = authorities;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

}
