package com.edassist.models.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ProgramCourseOfStudy")
public class ProgramCourseOfStudy implements Serializable {

	@Id
	@Column(name = "ProgramCOSID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long programCOSID;

	@JoinColumn(name = "CourseOfStudyID", referencedColumnName = "CourseOfStudyID")
	@ManyToOne(optional = false)
	private CourseOfStudy courseOfStudyID;

	@JoinColumn(name = "ProgramID", referencedColumnName = "ProgramID")
	@ManyToOne(optional = false)
	private Program programID;

	public ProgramCourseOfStudy() {
	}

	public Long getProgramCOSID() {
		return programCOSID;
	}

	public void setProgramCOSID(Long programCOSID) {
		this.programCOSID = programCOSID;
	}

	public CourseOfStudy getCourseOfStudyID() {
		return courseOfStudyID;
	}

	public void setCourseOfStudyID(CourseOfStudy courseOfStudyID) {
		this.courseOfStudyID = courseOfStudyID;
	}

	public Program getProgramID() {
		return programID;
	}

	public void setProgramID(Program programID) {
		this.programID = programID;
	}

	@Override
	public String toString() {
		return "bean.ProgramCourseOfStudy[programCOSID=" + programCOSID + "]";
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (programCOSID != null ? programCOSID.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof ProgramCourseOfStudy)) {
			return false;
		}
		ProgramCourseOfStudy other = (ProgramCourseOfStudy) object;
		if ((this.programCOSID == null && other.programCOSID != null) || (this.programCOSID != null && !this.programCOSID.equals(other.programCOSID))) {
			return false;
		}
		return true;
	}

}
