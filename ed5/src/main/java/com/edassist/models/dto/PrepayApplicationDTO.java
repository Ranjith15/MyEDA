package com.edassist.models.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PrepayApplicationDTO {

	private Long applicationId;
	private Long applicationNumber;
	private String studentId;
	private BenefitPeriodDTO benefitPeriod;
	private ParticipantDTO participant;
	private EducationalProviderDTO educationalProvider;
	private boolean degreeCompleteYN;
	private String studentStatus;
	private String academicTerm;
	private Long sessionCreditHours;
	private BigDecimal tuitionAmount;
	private FinancialAidSourceDTO financialAidSource;
	private ApplicationSourceDTO applicationSource;
	private String otherFinancialAid;
	private BigDecimal financialAidAmount;
	private PrepayTuitionAppDTO prepayTuitionApp;
	private DegreeObjectivesDTO degreeObjective;
	private CourseOfStudyDTO courseOfStudy;
	private ThinBookApplicationDTO bookApplication;
	private String clientFax;
	private ApplicationStatusDTO applicationStatus;
	private Date dateModified;
	private Date agreementsDate;
	private Long unreadMessages;

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public BenefitPeriodDTO getBenefitPeriod() {
		return benefitPeriod;
	}

	public void setBenefitPeriod(BenefitPeriodDTO benefitPeriod) {
		this.benefitPeriod = benefitPeriod;
	}

	public ParticipantDTO getParticipant() {
		return participant;
	}

	public void setParticipant(ParticipantDTO participant) {
		this.participant = participant;
	}

	public EducationalProviderDTO getEducationalProvider() {
		return educationalProvider;
	}

	public void setEducationalProvider(EducationalProviderDTO educationalProvider) {
		this.educationalProvider = educationalProvider;
	}

	public boolean isDegreeCompleteYN() {
		return degreeCompleteYN;
	}

	public void setDegreeCompleteYN(boolean degreeCompleteYN) {
		this.degreeCompleteYN = degreeCompleteYN;
	}

	public String getStudentStatus() {
		return studentStatus;
	}

	public void setStudentStatus(String studentStatus) {
		this.studentStatus = studentStatus;
	}

	public String getAcademicTerm() {
		return academicTerm;
	}

	public void setAcademicTerm(String academicTerm) {
		this.academicTerm = academicTerm;
	}

	public Long getSessionCreditHours() {
		return sessionCreditHours;
	}

	public void setSessionCreditHours(Long sessionCreditHours) {
		this.sessionCreditHours = sessionCreditHours;
	}

	public BigDecimal getTuitionAmount() {
		return tuitionAmount;
	}

	public void setTuitionAmount(BigDecimal tuitionAmount) {
		this.tuitionAmount = tuitionAmount;
	}

	public FinancialAidSourceDTO getFinancialAidSource() {
		return financialAidSource;
	}

	public void setFinancialAidSource(FinancialAidSourceDTO financialAidSource) {
		this.financialAidSource = financialAidSource;
	}

	public ApplicationSourceDTO getApplicationSource() {
		return applicationSource;
	}

	public void setApplicationSource(ApplicationSourceDTO applicationSource) {
		this.applicationSource = applicationSource;
	}

	public String getOtherFinancialAid() {
		return otherFinancialAid;
	}

	public void setOtherFinancialAid(String otherFinancialAid) {
		this.otherFinancialAid = otherFinancialAid;
	}

	public BigDecimal getFinancialAidAmount() {
		return financialAidAmount;
	}

	public void setFinancialAidAmount(BigDecimal financialAidAmount) {
		this.financialAidAmount = financialAidAmount;
	}

	public PrepayTuitionAppDTO getPrepayTuitionApp() {
		return prepayTuitionApp;
	}

	public void setPrepayTuitionApp(PrepayTuitionAppDTO prepayTuitionApp) {
		this.prepayTuitionApp = prepayTuitionApp;
	}

	public DegreeObjectivesDTO getDegreeObjective() {
		return degreeObjective;
	}

	public void setDegreeObjective(DegreeObjectivesDTO degreeObjective) {
		this.degreeObjective = degreeObjective;
	}

	public CourseOfStudyDTO getCourseOfStudy() {
		return courseOfStudy;
	}

	public void setCourseOfStudy(CourseOfStudyDTO courseOfStudy) {
		this.courseOfStudy = courseOfStudy;
	}

	public ThinBookApplicationDTO getBookApplication() {
		return bookApplication;
	}

	public void setBookApplication(ThinBookApplicationDTO bookApplication) {
		this.bookApplication = bookApplication;
	}

	public String getClientFax() {
		return clientFax;
	}

	public void setClientFax(String clientFax) {
		this.clientFax = clientFax;
	}

	public ApplicationStatusDTO getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatusDTO applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Date getAgreementsDate() {
		return agreementsDate;
	}

	public void setAgreementsDate(Date agreementsDate) {
		this.agreementsDate = agreementsDate;
	}

	public Long getUnreadMessages() {
		return unreadMessages;
	}

	public void setUnreadMessages(Long unreadMessages) {
		this.unreadMessages = unreadMessages;
	}
}
