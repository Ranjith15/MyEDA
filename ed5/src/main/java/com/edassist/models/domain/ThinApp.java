package com.edassist.models.domain;

import com.edassist.constants.ApplicationConstants;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Application")
public class ThinApp implements Comparable<ThinApp> {

	@Id
	@Column(name = "ApplicationID")
	private Long applicationID;

	@Column(name = "ApplicationNumber")
	private Long applicationNumber;

	@JoinColumn(name = "ParticipantID", referencedColumnName = "ParticipantID")
	@ManyToOne
	private Participant participantID;

	@JoinColumn(name = "EducationalProviderID", referencedColumnName = "EducationalProviderID")
	@ManyToOne
	private EducationalProviders educationalProvider;

	@ManyToOne
	@JoinColumn(name = "BenefitPeriodID", referencedColumnName = "BenefitPeriodID")
	private BenefitPeriod benefitPeriodID;

	@JoinColumn(name = "ApplicationStatusID", referencedColumnName = "ApplicationStatusID")
	@ManyToOne
	private ApplicationStatus applicationStatusID;

	@Column(name = "DateModified")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateModified;

	@JoinColumn(name = "ApplicationTypeID", referencedColumnName = "ApplicationTypeID")
	@ManyToOne(cascade = CascadeType.REFRESH)
	private ApplicationType applicationTypeID;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION_ID)
	private ThinPrepayTuitionApp thinPrepayTuitionApp;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION_ID)
	private ThinReimburseTuitionApp thinReimburseTuitionApp;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION_ID)
	private ThinReimburseBookApp thinReimburseBookApp;

	@OneToMany(mappedBy = "applicationID", fetch = FetchType.LAZY)
	private List<ApplicationComment> applicationComments;

	@Column(name = "SubmittedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date submittedDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION_ID)
	@Where(clause = "ExpenseTypeID is null")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ThinCourse> coursesCollection;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION_ID, fetch = FetchType.EAGER)
	@Where(clause = "ExpenseTypeID is not null AND ExpenseTypeID != 3 AND ExpenseTypeID != 8 AND FeesAmount is not null")
	private List<ThinExpense> nonCourseRelatedExpenses;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = ApplicationConstants.APPLICATION_ID)
	private Payments payment;

	@Transient
	private boolean reviewableComment;

	@Transient
	private String applicationCourseNumber;

	@Transient
	private Date courseStartDate;

	@Transient
	private Date courseEndDate;

	@Transient
	private String courseOfStudy;

	@Transient
	private String degreeObjective;

	@Transient
	private Date paymentDate;

	@Transient
	private List<ThinCourse> courses;

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

	public Participant getParticipantID() {
		return participantID;
	}

	public void setParticipantID(Participant participantID) {
		this.participantID = participantID;
	}

	public EducationalProviders getEducationalProvider() {
		return educationalProvider;
	}

	public void setEducationalProvider(EducationalProviders educationalProvider) {
		this.educationalProvider = educationalProvider;
	}

	public BenefitPeriod getBenefitPeriodID() {
		return benefitPeriodID;
	}

	public void setBenefitPeriodID(BenefitPeriod benefitPeriodID) {
		this.benefitPeriodID = benefitPeriodID;
	}

	public ApplicationStatus getApplicationStatusID() {
		return applicationStatusID;
	}

	public void setApplicationStatusID(ApplicationStatus applicationStatusID) {
		this.applicationStatusID = applicationStatusID;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public ApplicationType getApplicationTypeID() {
		return applicationTypeID;
	}

	public void setApplicationTypeID(ApplicationType applicationTypeID) {
		this.applicationTypeID = applicationTypeID;
	}

	public ThinPrepayTuitionApp getThinPrepayTuitionApp() {
		return thinPrepayTuitionApp;
	}

	public void setThinPrepayTuitionApp(ThinPrepayTuitionApp thinPrepayTuitionApp) {
		this.thinPrepayTuitionApp = thinPrepayTuitionApp;
	}

	public ThinReimburseTuitionApp getThinReimburseTuitionApp() {
		return thinReimburseTuitionApp;
	}

	public void setThinReimburseTuitionApp(ThinReimburseTuitionApp thinReimburseTuitionApp) {
		this.thinReimburseTuitionApp = thinReimburseTuitionApp;
	}

	public ThinReimburseBookApp getThinReimburseBookApp() {
		return thinReimburseBookApp;
	}

	public void setThinReimburseBookApp(ThinReimburseBookApp thinReimburseBookApp) {
		this.thinReimburseBookApp = thinReimburseBookApp;
	}

	public List<ApplicationComment> getApplicationComments() {
		return applicationComments;
	}

	public void setApplicationComments(List<ApplicationComment> applicationComments) {
		this.applicationComments = applicationComments;
	}

	public boolean isReviewableComment() {
		return reviewableComment;
	}

	public void setReviewableComment(boolean reviewableComment) {
		this.reviewableComment = reviewableComment;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public List<ThinExpense> getNonCourseRelatedExpenses() {
		return nonCourseRelatedExpenses;
	}

	public void setNonCourseRelatedExpenses(List<ThinExpense> nonCourseRelatedExpenses) {
		this.nonCourseRelatedExpenses = nonCourseRelatedExpenses;
	}

	public Payments getPayment() {
		return payment;
	}

	public void setPayment(Payments payment) {
		this.payment = payment;
	}

	public List<ThinCourse> getCoursesCollection() {
		return coursesCollection;
	}

	public void setCoursesCollection(List<ThinCourse> coursesCollection) {
		this.coursesCollection = coursesCollection;
	}

	public String getApplicationCourseNumber() {
		if (getCourses() != null && getCourses().size() > 0) {
			return getCourses().get(0).getNumber();
		}
		return applicationCourseNumber;
	}

	public void setApplicationCourseNumber(String applicationCourseNumber) {
		this.applicationCourseNumber = applicationCourseNumber;
	}

	public List<ThinCourse> getCourses() {
		if (coursesCollection != null && coursesCollection.size() > 0) {
			coursesCollection.removeIf(course -> course.getName() != null && course.getName().startsWith("DELETED-"));
		}
		return coursesCollection;
	}

	public void setCourses(List<ThinCourse> courses) {
		this.courses = courses;
	}

	public Date getCourseStartDate() {
		if (thinPrepayTuitionApp != null) {
			return getThinPrepayTuitionApp().getCourseStartDate();
		} else if (thinReimburseTuitionApp != null) {
			return getThinReimburseTuitionApp().getCourseStartDate();
		} else if (thinReimburseBookApp != null) {
			return getThinReimburseBookApp().getCourseStartDate();
		}
		return courseStartDate;
	}

	public void setCourseStartDate(Date courseStartDate) {
		this.courseStartDate = courseStartDate;
	}

	public Date getCourseEndDate() {
		if (thinPrepayTuitionApp != null) {
			return getThinPrepayTuitionApp().getCourseEndDate();
		} else if (thinReimburseTuitionApp != null) {
			return getThinReimburseTuitionApp().getCourseEndDate();
		} else if (thinReimburseBookApp != null) {
			return getThinReimburseBookApp().getCourseEndDate();
		}
		return courseEndDate;
	}

	public void setCourseEndDate(Date courseEndDate) {
		this.courseEndDate = courseEndDate;
	}

	public String getCourseOfStudy() {
		if (thinPrepayTuitionApp != null && this.getThinPrepayTuitionApp().getCourseOfStudy() != null) {
			return getThinPrepayTuitionApp().getCourseOfStudy().getCourseOfStudy();
		} else if (thinReimburseTuitionApp != null && this.getThinReimburseTuitionApp().getCourseOfStudy() != null) {
			return getThinReimburseTuitionApp().getCourseOfStudy().getCourseOfStudy();
		} else if (thinReimburseBookApp != null && this.getThinReimburseBookApp().getCourseOfStudy() != null) {
			return this.getThinReimburseBookApp().getCourseOfStudy().getCourseOfStudy();
		}
		return courseOfStudy;
	}

	public void setCourseOfStudy(String courseOfStudy) {
		this.courseOfStudy = courseOfStudy;
	}

	public String getDegreeObjective() {
		if (thinPrepayTuitionApp != null && getThinPrepayTuitionApp().getDegreeObjectiveID() != null) {
			return getThinPrepayTuitionApp().getDegreeObjectiveID().getDegree();
		} else if (thinReimburseTuitionApp != null && getThinReimburseTuitionApp().getDegreeObjectiveID() != null) {
			return getThinReimburseTuitionApp().getDegreeObjectiveID().getDegree();
		} else if (thinReimburseBookApp != null && getThinReimburseBookApp().getDegreeObjectiveID() != null) {
			return this.getThinReimburseBookApp().getDegreeObjectiveID().getDegree();
		}
		return degreeObjective;
	}

	public void setDegreeObjective(String degreeObjective) {
		this.degreeObjective = degreeObjective;
	}

	public Date getPaymentDate() {
		if (payment != null) {
			payment.getFormatTransactionDate();
		}
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Override
	public int compareTo(ThinApp argApp) {
		if (this.getDateModified() != null && argApp.getDateModified() != null) {
			return this.getDateModified().compareTo(argApp.getDateModified());
		} else if (this.getDateModified() != null) {
			return 1;
		} else {
			return -1;
		}
	}

}
