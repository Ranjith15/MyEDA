package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.edassist.models.domain.Application;
import com.edassist.models.dto.TuitionApplicationDTO;

@Mapper(uses = { BenefitPeriodMapper.class, ParticipantMapper.class, EducationalProviderMapper.class, ReimburseTuitionAppMapper.class, FinancialAidSourceMapper.class, DegreeObjectivesMapper.class,
		CourseOfStudyMapper.class, ApplicationStatusMapper.class, ApplicationSourceMapper.class })

public interface TuitionApplicationMapper {

	@Mappings({ @Mapping(target = "bookApplication", ignore = true), @Mapping(source = "applicationStatusID", target = "applicationStatus"),
			@Mapping(source = "tuitionApplication.benefitPeriodID.programID.clientID.fax", target = "clientFax") })
	TuitionApplicationDTO toDTO(Application tuitionApplication);

	@Mappings({ @Mapping(target = "studentID", defaultValue = "Unknown") })
	Application toDomain(TuitionApplicationDTO tuitionApplicationDTO, @MappingTarget Application application);
}
