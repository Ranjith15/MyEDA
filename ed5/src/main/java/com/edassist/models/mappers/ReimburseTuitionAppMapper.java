package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.edassist.models.domain.ReimburseTuitionApp;
import com.edassist.models.dto.ReimburseTuitionAppDTO;

@Mapper
public interface ReimburseTuitionAppMapper {

	ReimburseTuitionAppDTO toDTO(ReimburseTuitionApp reimburseTuitionApp);

	ReimburseTuitionApp toDomain(ReimburseTuitionAppDTO reimburseTuitionAppDTO, @MappingTarget ReimburseTuitionApp reimburseTuitionApp);

}
