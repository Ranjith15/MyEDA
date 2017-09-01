package com.edassist.models.domain;

import com.edassist.constants.ExpenseTypeConstants;
import com.edassist.constants.RestConstants;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ExpenseType")
public class ExpenseType {

	@Id
	@Column(name = "ExpenseTypeID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long expenseTypeID;

	@Column(name = "Code")
	private String code;

	@Column(name = "Description")
	private String description;

	@Basic(optional = false)
	@Column(name = "DateCreated")
	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Column(name = "ModifiedDate")
	@Temporal(TemporalType.DATE)
	private Date modifiedDate;

	@Column(name = "CreatedBy")
	private Long createdBy;

	@Column(name = "ModifiedBy")
	private Long modifiedBy;

	public Long getExpenseTypeID() {
		return expenseTypeID;
	}

	public void setExpenseTypeID(Long expenseTypeID) {
		this.expenseTypeID = expenseTypeID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	@Transient
	public String getExpenseRelation() {
		String relation = RestConstants.EXPENSETYPE_NON_COURSE;
		if (getExpenseTypeID() == 3 || getExpenseTypeID() == 8) {
			relation = RestConstants.EXPENSETYPE_COURSE;
		}
		return relation;
	}

	@Transient
	public boolean isBookExpense() {
		return (this.getCode() != null && this.getCode().equals(ExpenseTypeConstants.BOOKS.getCode()));
	}

	@Transient
	public Boolean isCourseRelatedFee() { return (this.getCode() != null && !this.getCode().equals(ExpenseTypeConstants.BOOKS.getCode()) && !this.getExpenseTypeID().equals(ExpenseTypeConstants.NON_COURSE_RELATED_FEE.getId())); }

}
