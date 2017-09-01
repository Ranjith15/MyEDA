package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.edassist.models.domain.ReimburseStudentLoanApp;
import com.edassist.models.dto.ReimburseStudentLoanAppDTO;

@Mapper(uses = { DegreeObjectivesMapper.class, CourseOfStudyMapper.class, StudentLoanPaymentRequestMapper.class })
public interface ReimburseStudentLoanAppMapper {

	@Mappings({ @Mapping(source = "degreeObjectiveID", target = "degreeObjectives"), @Mapping(source = "courseOfStudyID", target = "courseOfStudy") })
	ReimburseStudentLoanAppDTO toDTO(ReimburseStudentLoanApp reimburseStudentLoanApp);

	ReimburseStudentLoanApp toDomain(ReimburseStudentLoanAppDTO reimburseStudentLoanAppDTO, @MappingTarget ReimburseStudentLoanApp reimburseStudentLoanApp);
}
