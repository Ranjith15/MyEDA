package com.edassist.models.mappers;

import com.edassist.models.domain.Application;
import com.edassist.models.dto.PrepayApplicationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(uses = { BenefitPeriodMapper.class, ParticipantMapper.class, EducationalProviderMapper.class, PrepayTuitionAppMapper.class, FinancialAidSourceMapper.class, DegreeObjectivesMapper.class,
		CourseOfStudyMapper.class, ApplicationStatusMapper.class, ApplicationSourceMapper.class })
public interface PrepayApplicationMapper {

	@Mappings({ @Mapping(target = "bookApplication", ignore = true), @Mapping(source = "applicationStatusID", target = "applicationStatus"),
			@Mapping(source = "applicationID", target = "applicationId"), @Mapping(source = "benefitPeriodID", target = "benefitPeriod"), @Mapping(source = "participantID", target = "participant"),
			@Mapping(source = "studentID", target = "studentId"), @Mapping(source = "financialAidSourceId", target = "financialAidSource"),
			@Mapping(source = "applicationSourceID", target = "applicationSource"), @Mapping(source = "degreeObjectiveID", target = "degreeObjective"),
			@Mapping(source = "courseOfStudyID", target = "courseOfStudy"), @Mapping(source = "application.benefitPeriodID.programID.clientID.fax", target = "clientFax") })
	PrepayApplicationDTO toDTO(Application application);

	@Mappings({ @Mapping(source = "studentId", target = "studentID", defaultValue = "Unknown") })
	Application toDomain(PrepayApplicationDTO prepayApplicationDTO, @MappingTarget Application application);
}
