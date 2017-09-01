package com.edassist.models.domain;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@Column(name = "UserID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;

	@Basic(optional = false)
	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "MI")
	private String middleInitial;

	@Basic(optional = false)
	@Column(name = "LastName")
	private String lastName;

	@Basic(optional = false)
	@Column(name = "UserName")
	private String userName;

	@Basic(optional = false)
	@Column(name = "Password")
	private String password;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "UserTypeID")
	private UserType userType;

	@Column(name = "ResetPassword")
	private Integer resetPassword;

	@Column(name = "FailedLoginAttempts")
	private Integer failedLoginAttempts;

	@Column(name = "UserLocked")
	private boolean userLocked;

	@Column(name = "UserLockedTS")
	private Date userLockedTimeStamp;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	private Date dateCreated;

	@Basic(optional = false)
	@Column(name = "CreatedBy")
	private Long createdBy;

	@Column(name = "ModifiedBy")
	private Long modifiedBy;

	@Basic(optional = false)
	@Column(name = "MostRecentLogin")
	private Date mostRecentLogin;

	@OneToOne(mappedBy = "user")
	private Participant participantID;

	@Transient
	private String role = "Undetermined";

	public User() {
		super();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Integer getResetPassword() {
		return resetPassword;
	}

	public void setResetPassword(Integer resetPassword) {
		this.resetPassword = resetPassword;
	}

	public Integer getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(Integer failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public boolean isUserLocked() {
		return userLocked;
	}

	public void setUserLocked(boolean userLocked) {
		this.userLocked = userLocked;
	}

	public Date getUserLockedTimeStamp() {
		return userLockedTimeStamp;
	}

	public void setUserLockedTimeStamp(Date userLockedTimeStamp) {
		this.userLockedTimeStamp = userLockedTimeStamp;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getMostRecentLogin() {
		return mostRecentLogin;
	}

	public void setMostRecentLogin(Date mostRecentLogin) {
		this.mostRecentLogin = mostRecentLogin;
	}

	public Participant getParticipantID() {
		return participantID;
	}

	public void setParticipantID(Participant participantID) {
		this.participantID = participantID;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Transient
	public String getFullNameFirstThenLast() {
		StringBuilder fullName = new StringBuilder();
		boolean isBlank = true;
		if (!StringUtils.isEmpty(firstName)) {
			fullName.append(firstName.trim());
			isBlank = false;
		}

		if (!StringUtils.isEmpty(lastName)) {
			if (!isBlank) {
				fullName.append(" ");
			}
			fullName.append(lastName.trim());
			isBlank = false;
		}
		return fullName.toString();
	}
}
