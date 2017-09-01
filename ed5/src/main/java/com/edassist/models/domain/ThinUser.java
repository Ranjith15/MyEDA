
package com.edassist.models.domain;

import javax.persistence.*;

@Entity
@Table(name = "Users")
public class ThinUser {

	@Id
	@Column(name = "userID")
	private Long id;

	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "MI")
	private String mI;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "UserTypeID")
	private UserType userType;

	@Column(name = "UserName")
	private String userName;

	public ThinUser() {}

	public ThinUser(Long id, String firstName, String lastName, String mI) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mI = mI;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getmI() {
		return mI;
	}

	public void setmI(String mI) {
		this.mI = mI;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
