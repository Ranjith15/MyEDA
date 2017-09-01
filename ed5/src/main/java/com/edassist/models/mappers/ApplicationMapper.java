package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.domain.App;
import com.edassist.models.domain.ThinApp;
import com.edassist.models.dto.AppDTO;
import com.edassist.models.dto.ThinAppDTO;
import com.edassist.models.mappers.decorators.ApplicationMapperDecorator;

@Mapper(uses = { ApplicationStatusMapper.class, ApplicationTypeMapper.class, ApplicationCoursesMapper.class, ThinBenefitPeriodMapper.class, CourseMapper.class, ExpenseMapper.class,
		ParticipantMapper.class, ApplicationStatusMapper.class, ApplicationCommentsMapper.class, FinancialAidSourceMapper.class, PaymentMapper.class, RefundsMapper.class,
		EducationalProviderMapper.class, ApplicationDocumentsMapper.class, BenefitPeriodMapper.class })
@DecoratedWith(ApplicationMapperDecorator.class)
public interface ApplicationMapper {

	@Mappings({ @Mapping(source = "applicationStatusID", target = "applicationStatus"), @Mapping(source = "applicationTypeID", target = "applicationType"),
			@Mapping(source = "application.educationalProvider.providerName", target = "providerName"), @Mapping(source = "application.benefitPeriodID.programID.programID", target = "programId"),
			@Mapping(source = "application.benefitPeriodID.programID.programName", target = "programName"), @Mapping(source = "application.courseStartDate", target = "sessionStartDate"),
			@Mapping(source = "application.courseEndDate", target = "sessionEndDate"), @Mapping(source = "application.participantID.participantId", target = "participantId"),
			@Mapping(source = "application.participantID.user.firstName", target = "firstName"), @Mapping(source = "application.participantID.user.middleInitial", target = "mI"),
			@Mapping(source = "application.participantID.user.lastName", target = "lastName"), @Mapping(source = "application.benefitPeriodID", target = "thinBenefitPeriodDTO"),
			@Mapping(source = "application.payment.transactionDate", target = "paymentDate") })
	ThinAppDTO toDTO(ThinApp application);

	List<ThinAppDTO> toDTOList(List<ThinApp> applications);

	AppDTO toAppDTO(App app);

}
