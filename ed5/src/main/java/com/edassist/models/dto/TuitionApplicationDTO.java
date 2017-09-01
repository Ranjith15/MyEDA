package com.edassist.models.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TuitionApplicationDTO {

	private String studentID;
	private BenefitPeriodDTO benefitPeriodID;
	private ParticipantDTO participantID;
	private EducationalProviderDTO educationalProvider;
	private boolean degreeCompleteYN;
	private String studentStatus;
	private String academicTerm;
	private Long sessionCreditHours;
	private BigDecimal tuitionAmount;
	private FinancialAidSourceDTO financialAidSourceId;
	private ApplicationSourceDTO applicationSourceID;
	private String otherFinancialAid;
	private BigDecimal financialAidAmount;
	private Long applicationID;
	private Long applicationNumber;
	private ReimburseTuitionAppDTO reimburseTuitionApp;
	private DegreeObjectivesDTO degreeObjectiveID;
	private CourseOfStudyDTO courseOfStudyID;
	private ThinBookApplicationDTO bookApplication;
	private String clientFax;
	private ApplicationStatusDTO applicationStatus;
	private Date dateModified;
	private Date agreementsDate;
	private Long unreadMessages;

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public BenefitPeriodDTO getBenefitPeriodID() {
		return benefitPeriodID;
	}

	public void setBenefitPeriodID(BenefitPeriodDTO benefitPeriodID) {
		this.benefitPeriodID = benefitPeriodID;
	}

	public ParticipantDTO getParticipantID() {
		return participantID;
	}

	public void setParticipantID(ParticipantDTO participantID) {
		this.participantID = participantID;
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

	public FinancialAidSourceDTO getFinancialAidSourceId() {
		return financialAidSourceId;
	}

	public void setFinancialAidSourceId(FinancialAidSourceDTO financialAidSourceId) {
		this.financialAidSourceId = financialAidSourceId;
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

	public Long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Long applicationID) {
		this.applicationID = applicationID;
	}

	public Long getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public ReimburseTuitionAppDTO getReimburseTuitionApp() {
		return reimburseTuitionApp;
	}

	public void setReimburseTuitionApp(ReimburseTuitionAppDTO reimburseTuitionApp) {
		this.reimburseTuitionApp = reimburseTuitionApp;
	}

	public DegreeObjectivesDTO getDegreeObjectiveID() {
		return degreeObjectiveID;
	}

	public void setDegreeObjectiveID(DegreeObjectivesDTO degreeObjectiveID) {
		this.degreeObjectiveID = degreeObjectiveID;
	}

	public CourseOfStudyDTO getCourseOfStudyID() {
		return courseOfStudyID;
	}

	public void setCourseOfStudyID(CourseOfStudyDTO courseOfStudyID) {
		this.courseOfStudyID = courseOfStudyID;
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

	public ApplicationSourceDTO getApplicationSourceID() {
		return applicationSourceID;
	}

	public void setApplicationSourceID(ApplicationSourceDTO applicationSourceID) {
		this.applicationSourceID = applicationSourceID;
	}

	public Long getUnreadMessages() {
		return unreadMessages;
	}

	public void setUnreadMessages(Long unreadMessages) {
		this.unreadMessages = unreadMessages;
	}

}
