package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.edassist.models.domain.Application;
import com.edassist.models.dto.LoanApplicationDTO;

@Mapper(uses = { ApplicationStatusMapper.class, EducationalProviderMapper.class, ReimburseStudentLoanAppMapper.class
		// ,ProgramMapper.class
})
public interface LoanApplicationMapper {

	@Mappings({ @Mapping(source = "applicationStatusID", target = "applicationStatus") })

	LoanApplicationDTO toDTO(Application application);

	Application toDomain(LoanApplicationDTO loanApplicationDTO, @MappingTarget Application application);
}
