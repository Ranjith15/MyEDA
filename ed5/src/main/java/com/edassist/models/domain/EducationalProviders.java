package com.edassist.models.domain;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.edassist.constants.ApplicationConstants;
import com.edassist.utils.CommonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 */
@Entity
@Table(name = "EducationalProviders")
public class EducationalProviders implements Serializable {

	private static final long serialVersionUID = 2788644349279945684L;

	public static enum ACTIVE_TYPE {
		ACTIVE(Byte.valueOf("1"), "Active"), INACTIVE(Byte.valueOf("2"), "Inactive");

		private final Byte value;
		private final String displayText;

		ACTIVE_TYPE(Byte val, String display) {
			this.value = val;
			this.displayText = display;
		}

		public Byte getValue() {
			return value;
		}

		public String getDisplayText() {
			return displayText;
		}
	};

	public void setAccreditation1(String accreditation1) {
		this.accreditation1 = accreditation1;
	}

	public void setAccreditation2(String accreditation2) {
		this.accreditation2 = accreditation2;
	}

	public void setAccreditation3(String accreditation3) {
		this.accreditation3 = accreditation3;
	}

	public void setAccreditation4(String accreditation4) {
		this.accreditation4 = accreditation4;
	}

	public void setAccreditation5(String accreditation5) {
		this.accreditation5 = accreditation5;
	}

	public String getAccreditation5() {
		return accreditation5;
	}

	public void setHasMoreAccreditations(boolean hasMoreAccreditations) {
		this.moreAccreditations = hasMoreAccreditations;
	}

	@JsonIgnore
	public boolean isMoreAccreditations() {

		this.moreAccreditations = false;

		try {
			if (this.getProviderAccreditationCollection().size() > ApplicationConstants.DISPLAYED_IN_PROVIDER_SEARCH) {
				this.moreAccreditations = true;
			}
		} catch (Exception e) {
			System.out.println("Line 97: EducationalProviders.java");
			System.out.println(e.getLocalizedMessage());
			;
		}

		return moreAccreditations;
	}

	private String formattedAccreditation(ProviderAccreditation pa) {

		if (pa == null) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String expirationDate = formatter.format(pa.getEffectiveEndDate());

		String retVal = pa.getAccreditingBodyID().getAccreditingBodyName() + "(" + pa.getAccreditingBodyID().getAccreditingTypeDisplayName() + ") Expires " + expirationDate;

		return retVal;
	}

	private void populateAccreditations() {
		Collection<ProviderAccreditation> pas = this.getProviderAccreditationCollection();
		Iterator itr = pas.iterator();
		int x = 0;
		while (itr.hasNext()) {
			x++;
			ProviderAccreditation setMe = (ProviderAccreditation) itr.next();
			String formatted = formattedAccreditation(setMe);
			Method m;
			try {
				m = this.getClass().getMethod("setAccreditation" + x, new Class[] { String.class });
				m.invoke(this, formatted);
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
	}

	@JsonIgnore
	public String getAccreditation1() {
		if (StringUtils.isBlank(accreditation1)) {
			populateAccreditations();
		}
		return accreditation1;
	}

	@JsonIgnore
	public String getAccreditation2() {
		return accreditation2;
	}

	@JsonIgnore
	public String getAccreditation3() {
		return accreditation3;
	}

	@JsonIgnore
	public String getAccreditation4() {
		if (this.isMoreAccreditations()) {
			accreditation4 = accreditation4 + " AND MORE...";
		}
		return accreditation4;
	}

	@JsonIgnore
	public String getAccreditation6() {
		return accreditation6;
	}

	@JsonIgnore
	public void setAccreditation6(String accreditation6) {
		this.accreditation6 = accreditation6;
	}

	@JsonIgnore
	public String getAccreditation7() {
		return accreditation7;
	}

	public void setAccreditation7(String accreditation7) {
		this.accreditation7 = accreditation7;
	}

	@JsonIgnore
	public String getAccreditation8() {
		return accreditation8;
	}

	public void setAccreditation8(String accreditation8) {
		this.accreditation8 = accreditation8;
	}

	public String getAccreditation9() {
		return accreditation9;
	}

	public void setAccreditation9(String accreditation9) {
		this.accreditation9 = accreditation9;
	}

	@Transient
	@JsonIgnore
	public boolean isActive() {
		if (this.getProviderStatusID() == null || this.getProviderStatusID() == Byte.valueOf("2")) {
			return false;
		}
		return (ACTIVE_TYPE.ACTIVE.getValue().equals(this.getProviderStatusID()));

	}

	@Id
	@Column(name = "EducationalProviderID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long educationalProviderId;

	@Column(name = "ProviderName")
	private String providerName;

	@Column(name = "ProviderAddress1")
	private String providerAddress1;

	@Column(name = "ProviderAddress2")
	private String providerAddress2;

	@Column(name = "ProviderCountry")
	private String providerCountry;

	@Column(name = "ProviderState")
	private String providerState;

	@Column(name = "ProviderCity")
	private String providerCity;

	@Column(name = "ProviderZip")
	private String providerZip;

	@Column(name = "ProviderPhone")
	private String providerPhone;

	@Column(name = "ProviderFax")
	private String providerFax;

	@Column(name = "ProviderCode")
	private String providerCode;

	@Column(name = "ProviderURL")
	private String providerURL;

	@Column(name = "ParentCode")
	private String parentCode;

	@Column(name = "Comments")
	private String comments;

	@Column(name = "Active")
	private Byte providerStatusID;

	@Column(name = "CreatedBy")
	private Long createdBy;

	@Column(name = "ModifiedBy")
	private Long modifiedBy;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated = new Date();

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate = new Date();

	@Column(name = "ProviderContactName  ")
	private String providerContactName;

	@Column(name = "Email ")
	private String email;

	@Column(name = "PaymentName")
	private String paymentName;

	@Column(name = "PaymentAddress1")
	private String paymentAddress1;

	@Column(name = "PaymentAddress2")
	private String paymentAddress2;

	@Column(name = "PaymentCountry")
	private String paymentCountry;

	@Column(name = "PaymentState")
	private String paymentState;

	@Column(name = "PaymentCity")
	private String paymentCity;

	@Column(name = "PaymentZip ")
	private String paymentZip;

	@Column(name = "FpnContactName")
	private String fpnContactName;

	@Column(name = "FpnContactPhone")
	private String fpnContactPhone;

	@Column(name = "FpnContactFax")
	private String fpnContactFax;

	@Column(name = "FpnContactEmail")
	private String fpnContactEmail;

	@Column(name = "DeliveryOnline")
	private boolean deliveryOnline;

	@Column(name = "ContactName")
	private String contactName;

	@Column(name = "CountryID")
	private Long countryID;

	@Column(name = "featuredProvider")
	private boolean featuredProvider;

	@Column(name = "enhancedEdAssistNetwork")
	private boolean enhancedEdAssistNetwork;

	@Transient
	private Boolean isClientEEN;
	@Transient
	private Boolean isClientFPN;

	// TAM 3213
	@Column(name = "AcceptLOC")
	private boolean acceptLOC;

	@Transient
	private String proOtherState;

	// TAM-3018
	// This is a hacky fix. Should be refactored some time in the future.
	@Transient
	private String accreditation1;

	@Transient
	private String accreditation2;

	@Transient
	private String accreditation3;

	@Transient
	private String accreditation4;

	@Transient
	private String accreditation5;

	@Transient
	private String accreditation6;

	@Transient
	private String accreditation7;

	@Transient
	private String accreditation8;

	@Transient
	private String accreditation9;

	@Transient
	private boolean moreAccreditations;

	/*
	 * TAM-2936
	 */
	@Transient
	@JsonIgnore
	public String getExistenceOfPayAddress() {
		if (StringUtils.isNotBlank(this.getPaymentAddress1())) {
			return "true";
		}

		return "false";
	}

	/**
	 * TAM-2936
	 */
	@Transient
	@JsonIgnore
	public String getPayAddressLine1() {
		StringBuffer retVal = new StringBuffer("");

		if (StringUtils.isNotBlank(this.getPaymentName())) {
			retVal.append(this.getPaymentName());
		}
		return retVal.toString();
	}

	/**
	 * TAM-2936
	 */
	@Transient
	@JsonIgnore
	public String getPayAddressLine2() {
		StringBuffer retVal = new StringBuffer("");

		if (StringUtils.isNotBlank(this.getPaymentAddress1())) {
			retVal.append(this.getPaymentAddress1());
		}

		return retVal.toString();
	}

	/**
	 * TAM-2936
	 */
	@Transient
	@JsonIgnore
	public String getPayAddressLine3() {
		StringBuffer retVal = new StringBuffer("");

		if (StringUtils.isBlank(this.getPaymentAddress2())) {
			retVal.append(this.getPaymentCity());
			retVal.append(", ");
			retVal.append(this.getPaymentState());
			retVal.append(" ");
			retVal.append(this.getPaymentZip());
		} else {
			retVal.append(this.paymentAddress2);
		}

		return retVal.toString();
	}

	/**
	 * TAM-2936
	 */
	@Transient
	@JsonIgnore
	public String getPayAddressLine4() {
		StringBuffer retVal = new StringBuffer("");

		if (StringUtils.isNotBlank(this.getPaymentAddress2())) {
			retVal.append(this.getPaymentCity());
			retVal.append(", ");
			retVal.append(this.getPaymentState());
			retVal.append(" ");
			retVal.append(this.getPaymentZip());
		}

		return retVal.toString();
	}

	// @Transient
	// public String isPayAddress(){
	// if( StringUtils.isNotBlank(this.getPaymentAddress1()))
	// return "true";
	// else{
	// return "false";
	// }
	// }

	@Transient
	@JsonIgnore
	public String getFullAddress() {
		String fullAddress = null;
		if (providerAddress1 != null && !providerAddress1.trim().isEmpty()) {
			fullAddress = providerAddress1.trim();
		}

		if (providerAddress2 != null && !providerAddress2.trim().isEmpty()) {
			fullAddress = fullAddress + " " + providerAddress2.trim();
		}

		if (providerCity != null && !providerCity.trim().isEmpty()) {
			fullAddress = fullAddress + ", " + providerCity.trim();
		}

		if (providerState != null && !providerState.trim().isEmpty()) {
			fullAddress = fullAddress + ", " + providerState.trim();
		}

		if (providerCountry != null && !providerCountry.trim().isEmpty()) {
			fullAddress = fullAddress + ", " + providerCountry.trim();
		}

		return fullAddress;
	}

	@Transient
	private String payOtherState;

	@Transient
	private String proState;

	@Transient
	private String payState;

	@Transient
	private Set<Long> degreeObjectiveIds;

	@Transient
	private Set<Long> courseIds;

	@Transient
	private Set<Long> assessmentIds;

	@Transient
	private Set<Long> specialProgramIds;

	@Transient
	private Long accreditingBodyID;

	@Transient
	private String effectiveEndDate;

	@Transient
	private Long clientId;

	@Transient
	private Long programID;

	@JsonIgnore
	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getProgramID() {
		return programID;
	}

	public void setProgramID(Long programID) {
		this.programID = programID;
	}

	@JsonIgnore
	public Long getAccreditingBodyID() {
		return accreditingBodyID;
	}

	public void setAccreditingBodyID(Long accreditingBodyID) {
		this.accreditingBodyID = accreditingBodyID;
	}

	@JsonIgnore
	public String getEffectiveEndDate() {

		return effectiveEndDate;
	}

	public void setEffectiveEndDate(String effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	@JsonIgnore
	public Set<Long> getCourseIds() {
		return courseIds;
	}

	public void setCourseIds(Set<Long> courseIds) {
		this.courseIds = courseIds;
	}

	@JsonIgnore
	public Set<Long> getAssessmentIds() {
		return assessmentIds;
	}

	public void setAssessmentIds(Set<Long> assessmentIds) {
		this.assessmentIds = assessmentIds;
	}

	@JsonIgnore
	public Set<Long> getSpecialProgramIds() {
		return specialProgramIds;
	}

	public void setSpecialProgramIds(Set<Long> specialProgramIds) {
		this.specialProgramIds = specialProgramIds;
	}

	@JsonIgnore
	public Set<Long> getDegreeObjectiveIds() {
		return degreeObjectiveIds;
	}

	public void setDegreeObjectiveIds(Set<Long> degreeObjectiveIds) {
		this.degreeObjectiveIds = degreeObjectiveIds;
	}

	@JsonIgnore
	public String getPayState() {
		if (payState == null && "US".equalsIgnoreCase(paymentCountry)) {
			return paymentState;
		}
		return payState;

	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

	@JsonIgnore
	public String getPayOtherState() {
		if (payOtherState == null && !"US".equalsIgnoreCase(paymentCountry)) {
			return paymentState;
		}
		return payOtherState;
	}

	public void setPayOtherState(String payOtherState) {
		this.payOtherState = payOtherState;
	}

	@JsonIgnore
	public String getProOtherState() {
		if (proOtherState == null && !"US".equalsIgnoreCase(providerCountry)) {
			return providerState;
		}
		return proOtherState;
	}

	public void setProOtherState(String proOtherState) {
		this.proOtherState = proOtherState;
	}

	@JsonIgnore
	public String getProState() {
		if (proState == null && "US".equalsIgnoreCase(providerCountry)) {
			return providerState;
		}
		return proState;
	}

	public void setProState(String proState) {
		this.proState = proState;
	}

	@Transient
	private boolean restrictSearchToFeaturedProviders;

	@Transient
	private Set<Long> programDegreeObjectiveId;

	@Transient
	private BigDecimal programDiscountPercent;

	@JsonIgnore
	public BigDecimal getProgramDiscountPercent() {
		return programDiscountPercent;
	}

	public void setProgramDiscountPercent(BigDecimal programDiscountPercent) {
		this.programDiscountPercent = programDiscountPercent;
	}

	@JsonIgnore
	public Set<Long> getProgramDegreeObjectiveId() {
		return programDegreeObjectiveId;
	}

	public void setProgramDegreeObjectiveId(Set<Long> programDegreeObjectiveId) {
		this.programDegreeObjectiveId = programDegreeObjectiveId;
	}

	@JsonIgnore
	public boolean isRestrictSearchToFeaturedProviders() {
		return restrictSearchToFeaturedProviders;
	}

	public void setRestrictSearchToFeaturedProviders(boolean restrictSearchToFeaturedProviders) {
		this.restrictSearchToFeaturedProviders = restrictSearchToFeaturedProviders;
	}

	@JsonIgnore
	public String getDisplayedFeaturedProvider() {
		if (this.isClientEEN != null && this.isClientEEN && this.isEnhancedEdAssistNetwork()) {
			return "Yes";
		} else if (this.isClientFPN != null && this.isClientFPN && this.isFeaturedProvider()) {
			return "Yes";
		} else {
			return "No";
		}
	}

	public void setDisplayedFeaturedProvider(String displayedFeaturedProvider) {
		// shouldn't be called
	}

	@Transient
	private String submitAction;

	@JsonIgnore
	public String getSubmitAction() {
		return submitAction;
	}

	public void setSubmitAction(String submitAction) {
		this.submitAction = submitAction;
	}

	@JsonIgnore
	public boolean isFeaturedProvider() {
		return featuredProvider;
	}

	public void setFeaturedProvider(boolean featuredProvider) {
		this.featuredProvider = featuredProvider;
	}

	@JsonIgnore
	public boolean isEnhancedEdAssistNetwork() {
		return enhancedEdAssistNetwork;
	}

	public void setEnhancedEdAssistNetwork(boolean enhancedEdAssistNetwork) {
		this.enhancedEdAssistNetwork = enhancedEdAssistNetwork;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "educationalProviderID", fetch = FetchType.LAZY)
	private Collection<PriorLearningAssesments> priorLearningAssesmentsCollection;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "educationalProviderID", fetch = FetchType.LAZY)
	// @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Collection<ProviderAccreditation> providerAccreditationCollection;

	@OneToMany(mappedBy = "educationalProviderID", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	// @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Collection<ProgramProviders> programProvidersCollection;

	@JoinColumn(name = "sectorID", referencedColumnName = "sectorID")
	@ManyToOne
	private Sector sectorID;

	private String discountType;
	private Double discountPercentage;

	@Column(name = "DiscountType")
	@JsonIgnore
	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	@Column(name = "DiscountPercentage")
	@JsonIgnore
	public Double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public EducationalProviders() {
	}

	public EducationalProviders(Long educationalProviderID, String providerCode, Date dateCreated, Long createdBy) {
		this.educationalProviderId = educationalProviderID;
		this.providerCode = providerCode;
		this.dateCreated = dateCreated;
		this.createdBy = createdBy;
	}

	@JsonIgnore
	public Sector getSectorID() {
		return sectorID;
	}

	public void setSectorID(Sector sectorID) {
		this.sectorID = sectorID;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	@JsonIgnore
	public String getProviderAddress1() {
		return providerAddress1;
	}

	public void setProviderAddress1(String providerAddress1) {
		this.providerAddress1 = providerAddress1;
	}

	@JsonIgnore
	public String getProviderAddress2() {
		return providerAddress2;
	}

	public void setProviderAddress2(String providerAddress2) {
		this.providerAddress2 = providerAddress2;
	}

	@JsonIgnore
	public String getProviderCity() {
		return providerCity;
	}

	public void setProviderCity(String providerCity) {
		this.providerCity = providerCity;
	}

	@JsonIgnore
	public String getProviderState() {
		if ("US".equalsIgnoreCase(providerCountry) && proState != null && !proState.isEmpty()) {
			return proState;
		}
		if (proOtherState != null && !proOtherState.isEmpty()) {
			return proOtherState;
		}

		return providerState;
	}

	public void setProviderState(String providerState) {
		this.providerState = providerState;
	}

	@JsonIgnore
	public String getProviderZip() {
		return providerZip;
	}

	public void setProviderZip(String providerZip) {
		this.providerZip = providerZip;
	}

	@JsonIgnore
	public String getProviderPhone() {
		return CommonUtil.formatPhNumber(providerPhone);

	}

	public void setProviderPhone(String providerPhone) {
		this.providerPhone = providerPhone;
	}

	@JsonIgnore
	public String getProviderFax() {
		return CommonUtil.formatPhNumber(providerFax);
	}

	public void setProviderFax(String providerFax) {
		this.providerFax = providerFax;
	}

	@JsonIgnore
	public String getProviderCountry() {
		return providerCountry;
	}

	public void setProviderCountry(String providerCountry) {
		this.providerCountry = providerCountry;
	}

	@JsonIgnore
	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	@JsonIgnore
	public String getProviderURL() {
		return providerURL;
	}

	public void setProviderURL(String providerURL) {
		this.providerURL = providerURL;
	}

	@JsonIgnore
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Transient
	@JsonIgnore
	public String getFormatDateCreated() {
		return CommonUtil.formatDate(dateCreated, CommonUtil.MMDDYYY);
	}

	@JsonIgnore
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) throws ParseException {
		this.dateCreated = dateCreated;

	}

	@Transient
	@JsonIgnore
	public String getFormatModifiedDate() {
		return CommonUtil.formatDate(modifiedDate, CommonUtil.MMDDYYY);
	}

	@JsonIgnore
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@JsonIgnore
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@JsonIgnore
	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	@JsonIgnore
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@JsonIgnore
	public Long getCountryID() {
		return countryID;
	}

	public void setCountryID(Long countryID) {
		this.countryID = countryID;
	}

	@JsonIgnore
	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	@JsonIgnore
	public String getPaymentAddress1() {
		return paymentAddress1;
	}

	public void setPaymentAddress1(String paymentAddress1) {
		this.paymentAddress1 = paymentAddress1;
	}

	@JsonIgnore
	public String getPaymentAddress2() {
		return paymentAddress2;
	}

	public void setPaymentAddress2(String paymentAddress2) {
		this.paymentAddress2 = paymentAddress2;
	}

	@JsonIgnore
	public String getPaymentCountry() {
		return paymentCountry;
	}

	public void setPaymentCountry(String paymentCountry) {
		this.paymentCountry = paymentCountry;
	}

	@JsonIgnore
	public String getPaymentState() {
		if ("US".equalsIgnoreCase(paymentCountry) && payState != null && !payState.isEmpty()) {
			return payState;
		}
		if (payOtherState != null && !payOtherState.isEmpty()) {
			return payOtherState;
		}
		return paymentState;

	}

	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}

	@JsonIgnore
	public String getPaymentCity() {
		return paymentCity;
	}

	public void setPaymentCity(String paymentCity) {
		this.paymentCity = paymentCity;
	}

	@JsonIgnore
	public String getPaymentZip() {
		return paymentZip;
	}

	public void setPaymentZip(String paymentZip) {
		this.paymentZip = paymentZip;
	}

	@JsonIgnore
	public String getFpnContactName() {
		return fpnContactName;
	}

	public void setFpnContactName(String fpnContactName) {
		this.fpnContactName = fpnContactName;
	}

	@JsonIgnore
	public String getFpnContactPhone() {
		return CommonUtil.formatPhNumber(fpnContactPhone);
	}

	public void setFpnContactPhone(String fpnContactPhone) {
		this.fpnContactPhone = fpnContactPhone;
	}

	@JsonIgnore
	public String getFpnContactFax() {
		return CommonUtil.formatPhNumber(fpnContactFax);
	}

	public void setFpnContactFax(String fpnContactFax) {
		this.fpnContactFax = fpnContactFax;
	}

	@JsonIgnore
	public String getFpnContactEmail() {
		return fpnContactEmail;
	}

	public void setFpnContactEmail(String fpnContactEmail) {
		this.fpnContactEmail = fpnContactEmail;
	}

	public Long getEducationalProviderId() {
		return educationalProviderId;
	}

	public void setEducationalProviderId(Long educationalProviderId) {

		this.educationalProviderId = educationalProviderId;
	}

	public Byte getProviderStatusID() {
		return providerStatusID;
	}

	public void setProviderStatusID(Byte providerStatusID) {
		this.providerStatusID = providerStatusID;
	}

	@JsonIgnore
	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@JsonIgnore
	public String getProviderContactName() {
		return providerContactName;
	}

	public void setProviderContactName(String providerContactName) {
		this.providerContactName = providerContactName;
	}

	@JsonIgnore
	public Collection<ProviderAccreditation> getProviderAccreditationCollection() {
		return providerAccreditationCollection;
	}

	public void setProviderAccreditationCollection(Collection<ProviderAccreditation> providerAccreditationCollection) {
		this.providerAccreditationCollection = providerAccreditationCollection;
	}

	@Override
	public String toString() {
		return "bean.EducationalProviders[educationalProviderID=" + educationalProviderId + "]";
	}

	@JsonIgnore
	public boolean isDeliveryOnline() {
		return deliveryOnline;
	}

	public void setDeliveryOnline(boolean deliveryOnline) {
		this.deliveryOnline = deliveryOnline;
	}

	@JsonIgnore
	public Collection<ProgramProviders> getProgramProvidersCollection() {
		return programProvidersCollection;
	}

	public void setProgramProvidersCollection(Collection<ProgramProviders> programProvidersCollection) {
		this.programProvidersCollection = programProvidersCollection;
	}

	@Transient
	@JsonIgnore
	public List<ProgramProviders> getIneligibleProgramProvidersCollection() {
		List<ProgramProviders> list = new ArrayList<ProgramProviders>();
		if (programProvidersCollection != null) {
			for (ProgramProviders programProviders : programProvidersCollection) {
				if (programProviders.getProviderEligibility() == 0 // this controls step 1 functionality
						|| programProviders.getProviderStatusID() == 0) // this controls app submission functionality
				{
					list.add(programProviders);
				}
			}
			/*
			 * Iterator<ProgramProviders> it = programProvidersCollection.iterator(); ProgramProviders programProviders; while(it.hasNext()){ programProviders = it.next();
			 * if(!programProviders.isProviderEligibility()){ list.add(programProviders); } }
			 */
		}
		return list;

	}

	@Transient
	@JsonIgnore
	public List<ProgramProviders> getEligibleProgramProvidersCollection() {
		List<ProgramProviders> list = new ArrayList<ProgramProviders>();
		if (programProvidersCollection != null) {
			for (ProgramProviders programProviders : programProvidersCollection) {
				// updated to if there are non-zero discounts instead of just eligibility check
				BigDecimal discount = programProviders.getProgramDiscountPercent();

				if (programProviders.getProviderEligibility() != 0 || (discount != null && discount.compareTo(new BigDecimal(0)) != 0)) {
					list.add(programProviders);
				}
			}
			/*
			 * Iterator<ProgramProviders> it = programProvidersCollection.iterator(); ProgramProviders programProviders; while(it.hasNext()){ programProviders = it.next();
			 * if(programProviders.isProviderEligibility()){ list.add(programProviders); } }
			 */
		}
		return list;

	}

	/* TAM-2500 */
	@Transient
	private String accreditingBodyDisplayString = "";

	@Transient
	public void setAccreditingBodyDisplayString(Long clientId) {
		String accreditingBodyString = "";
		String eligibleClientSpecificCode = null;
		if (clientId != null) {
			eligibleClientSpecificCode = "C" + clientId.toString().trim();
		}

		Collection<ProviderAccreditation> accreds = this.getProviderAccreditationCollection();
		String comma = "";
		Iterator iterAccred = accreds.iterator();
		while (iterAccred.hasNext()) {
			ProviderAccreditation pa = (ProviderAccreditation) iterAccred.next();
			if (pa.getEffectiveEndDate() == null || (pa.getEffectiveEndDate().after(new Date()) /* &&pa.getEffectiveStartDate().before(new Date()) */)) {
				if (pa.getAccreditingBodyID().getAccreditingType().equals(eligibleClientSpecificCode)) {
					accreditingBodyString += comma + pa.getAccreditingBodyID().getAccreditingBodyName() + "(" + pa.getAccreditingBodyID().getAccreditingTypeDisplayName() + ")";
					comma = ",";
				} else {
					if (!pa.getAccreditingBodyID().getAccreditingType().startsWith("C")) {
						accreditingBodyString += comma + pa.getAccreditingBodyID().getAccreditingBodyName() + "(" + pa.getAccreditingBodyID().getAccreditingTypeDisplayName() + ")";
						comma = ",";
					}
				}
			}
		}
		this.accreditingBodyDisplayString = accreditingBodyString;
		return;
	}

	@Transient
	@JsonIgnore
	public String getAccreditingBodyDisplayString() {
		return this.accreditingBodyDisplayString;
	}

	@Transient
	@JsonIgnore
	public String getAccreditingBodyName() {

		String accreditingBodyName = null;

		if (CollectionUtils.isNotEmpty(providerAccreditationCollection)) {

			// The Provider Accreditation collection should really be a 1 item list if it's not empty
			ProviderAccreditation accreditation = (ProviderAccreditation) CollectionUtils.get(providerAccreditationCollection, 0);

			if (accreditation != null && accreditation.getAccreditingBodyID() != null) {

				accreditingBodyName = accreditation.getAccreditingBodyID().getAccreditingBodyDisplayName();
			}
		}

		return accreditingBodyName;

	}

	@JsonIgnore
	public Collection<PriorLearningAssesments> getPriorLearningAssesmentsCollection() {
		return priorLearningAssesmentsCollection;
	}

	public void setPriorLearningAssesmentsCollection(Collection<PriorLearningAssesments> priorLearningAssesmentsCollection) {
		this.priorLearningAssesmentsCollection = priorLearningAssesmentsCollection;
	}


	@Transient
	@JsonIgnore
	public String getAddressLineTwoDisplay() {
		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isEmpty(providerCity)) {
			sb.append(providerCity);
			if (!StringUtils.isEmpty(providerState)) {
				sb.append(", ");
				sb.append(providerState);
				if (!StringUtils.isEmpty(providerZip)) {
					sb.append(" ");
					sb.append(providerZip);
				}
			}
		} else if (!StringUtils.isEmpty(providerState)) {
			sb.append(providerState);
			if (!StringUtils.isEmpty(providerZip)) {
				sb.append(" ");
				sb.append(providerZip);
			}
		} else {
			sb.append(providerZip);
		}

		return sb.toString();
	}

	@Transient
	@JsonIgnore
	public String getUrlDisplay() {
		if (StringUtils.isEmpty(providerURL)) {
			return "";
		}
		if (providerURL.startsWith("http://")) {
			return providerURL;
		} else {
			return "http://" + providerURL;
		}
	}

	@Transient
	@JsonIgnore
	public String getDiscountPercentageDisplay() {
		if (discountPercentage == null || discountPercentage.doubleValue() == 0) {
			return "";
		}
		return String.valueOf(discountPercentage) + "%";

	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof EducationalProviders) {
			EducationalProviders objAsEducationalProvider = (EducationalProviders) obj;
			if (this.getEducationalProviderId() != null && objAsEducationalProvider.getEducationalProviderId() != null
					&& this.getEducationalProviderId().equals(objAsEducationalProvider.getEducationalProviderId())) {
				return true;
			}
		}

		return false;
	}

	@Transient
	@JsonIgnore
	public String getProviderCityStateZipDisplay() {
		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isEmpty(this.getProviderCity())) {
			sb.append(this.getProviderCity());
			if (!StringUtils.isEmpty(providerState)) {
				sb.append(", ");
				sb.append(providerState);
				if (!StringUtils.isEmpty(this.getProviderZip())) {
					sb.append(" ");
					sb.append(this.getProviderZip());
				}
			}
		} else if (!StringUtils.isEmpty(providerState)) {
			sb.append(providerState);
			if (!StringUtils.isEmpty(this.getProviderZip())) {
				sb.append(" ");
				sb.append(this.getProviderZip());
			}
		} else {
			if (!StringUtils.isEmpty(this.getProviderZip())) {
				sb.append(this.getProviderZip());
			}
		}

		return sb.toString();
	}

	// TAM 3213
	@JsonIgnore
	public boolean isAcceptLOC() {
		return acceptLOC;
	}

	public void setAcceptLOC(boolean acceptLOC) {
		this.acceptLOC = acceptLOC;
	}

	@JsonIgnore
	public Boolean getIsClientEEN() {
		return isClientEEN;
	}

	public void setIsClientEEN(Boolean isClientEEN) {
		this.isClientEEN = isClientEEN;
	}

	@JsonIgnore
	public Boolean getIsClientFPN() {
		return isClientFPN;
	}

	public void setIsClientFPN(Boolean isClientFPN) {
		this.isClientFPN = isClientFPN;
	}

	@Transient
	private String providerDocumentType;

	@JsonIgnore
	public String getProviderDocumentType() {
		return providerDocumentType;
	}

	public void setProviderDocumentType(String providerDocumentType) {
		this.providerDocumentType = providerDocumentType;
	}

	@Transient
	@JsonIgnore
	private String providerDocumentDisplayName;

	public String getProviderDocumentDisplayName() {
		return providerDocumentDisplayName;
	}

	public void setProviderDocumentDisplayName(String providerDocumentDisplayName) {
		this.providerDocumentDisplayName = providerDocumentDisplayName;
	}

}
