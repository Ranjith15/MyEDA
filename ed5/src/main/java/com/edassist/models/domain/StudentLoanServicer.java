/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.edassist.models.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "StudentLoanServicer")

public class StudentLoanServicer implements Serializable {

	private static final long serialVersionUID = -7421449241939389843L;

	public StudentLoanServicer() {
	}

	private Long studentLoanServicerID;
	private String name;
	private String phone;
	private String fax;
	private String address1;
	private String address2;
	private String city;
	private State state;
	private String zip;
	private String webSite;
	private String email;
	private boolean active;
	private Long loanAggregatorProviderId;

	/**************************************************************************
	 *
	 * GETTERS
	 *
	 *************************************************************************/

	@Id
	@Basic(optional = false)
	@Column(name = "StudentLoanServicerID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getStudentLoanServicerID() {
		return studentLoanServicerID;
	}

	@Basic(optional = false)
	@Column(name = "Name")
	public String getName() {
		return name;
	}

	@Basic(optional = false)
	@Column(name = "Phone")
	public String getPhone() {
		return phone;
	}

	@Column(name = "Fax")
	public String getFax() {
		return fax;
	}

	@Basic(optional = false)
	@Column(name = "Address1")
	public String getAddress1() {
		return address1;
	}

	@Column(name = "Address2")
	public String getAddress2() {
		return address2;
	}

	@Basic(optional = false)
	@Column(name = "City")
	public String getCity() {
		return city;
	}

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "State")
	public State getState() {
		return state;
	}

	@Basic(optional = false)
	@Column(name = "Zip")
	public String getZip() {
		return zip;
	}

	@Column(name = "WebSite")
	public String getWebSite() {
		return webSite;
	}

	@Basic(optional = false)
	@Column(name = "Email")
	public String getEmail() {
		return email;
	}

	@Basic(optional = false)
	@Column(name = "Active")
	public boolean isActive() {
		return active;
	}

	@Column(name = "LoanAggregatorProviderId")
	public Long getLoanAggregatorProviderId() {
		return loanAggregatorProviderId;
	}


	/**************************************************************************
	 *
	 * SETTERS
	 *
	 *************************************************************************/

	public void setStudentLoanServicerID(Long studentLoanServicerID) {
		this.studentLoanServicerID = studentLoanServicerID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setLoanAggregatorProviderId(Long loanAggregatorProviderId) {
		this.loanAggregatorProviderId = loanAggregatorProviderId;
	}


}
