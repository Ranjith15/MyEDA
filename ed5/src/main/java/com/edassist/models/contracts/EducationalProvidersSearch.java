package com.edassist.models.contracts;

public class EducationalProvidersSearch {

	private String educationalProviderId;
	private String providerName;
	private String providerCity;
	private String providerState;
	private String providerZip;
	private String parentCode;
	private Boolean isFeaturedProvider;
	private Boolean isOnlineProvider;
	private String submitAction;
	private String accreditingBodyID;
	private String accreditingTypeName;
	private String degreeObjectiveID;
	private String courseOfStudyID;
	private Boolean isClientEEN;
	private Boolean isClientFPN;

	public String getAccreditingTypeName() {
		return accreditingTypeName;
	}

	public void setAccreditingTypeName(String accreditingTypeName) {
		this.accreditingTypeName = accreditingTypeName;
	}

	public String getAccreditingBodyID() {
		return accreditingBodyID;
	}

	public void setAccreditingBodyID(String accreditingBodyID) {
		this.accreditingBodyID = accreditingBodyID;
	}

	public String getDegreeObjectiveID() {
		return degreeObjectiveID;
	}

	public void setDegreeObjectiveID(String degreeObjectiveID) {
		this.degreeObjectiveID = degreeObjectiveID;
	}

	public String getCourseOfStudyID() {
		return courseOfStudyID;
	}

	public void setCourseOfStudyID(String courseOfStudyID) {
		this.courseOfStudyID = courseOfStudyID;
	}

	public EducationalProvidersSearch() {
		super();
	}

	public String getEducationalProviderId() {
		return educationalProviderId;
	}

	public String getProviderName() {
		return providerName;
	}

	public String getProviderCity() {
		return providerCity;
	}

	public String getProviderState() {
		return providerState;
	}

	public String getProviderZip() {
		return providerZip;
	}

	public String getParentCode() {
		return parentCode;
	}

	public Boolean isFeaturedProvider() {
		return isFeaturedProvider;
	}

	public void setFeaturedProvider(Boolean isFeaturedProvider) {
		this.isFeaturedProvider = isFeaturedProvider;
	}

	public Boolean getIsFeaturedProvider() {
		return isFeaturedProvider;
	}

	public Boolean isOnlineProvider() {
		return isOnlineProvider;
	}

	public void setOnlineProvider(Boolean isOnlineProvider) {
		this.isOnlineProvider = isOnlineProvider;
	}

	public Boolean getIsOnlineProvider() {
		return isOnlineProvider;
	}

	public String getSubmitAction() {
		return submitAction;
	}

	public void setEducationalProviderId(String educationalProviderId) {
		this.educationalProviderId = educationalProviderId;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public void setProviderCity(String providerCity) {
		this.providerCity = providerCity;
	}

	public void setProviderState(String providerState) {
		this.providerState = providerState;
	}

	public void setProviderZip(String providerZip) {
		this.providerZip = providerZip;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public void setIsFeaturedProvider(Boolean isFeaturedProvider) {
		this.isFeaturedProvider = isFeaturedProvider;
	}

	public void setIsOnlineProvider(Boolean isOnlineProvider) {
		this.isOnlineProvider = isOnlineProvider;
	}

	public void setSubmitAction(String submitAction) {
		this.submitAction = submitAction;
	}

	public Boolean isClientEEN() {
		return isClientEEN;
	}

	public void setClientEEN(Boolean isClientEEN) {
		this.isClientEEN = isClientEEN;
	}

	public Boolean isClientFPN() {
		return isClientFPN;
	}

	public void setClientFPN(Boolean isClientFPN) {
		this.isClientFPN = isClientFPN;
	}

}
