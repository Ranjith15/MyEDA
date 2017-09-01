package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.edassist.models.domain.StudentLoanPaymentRequest;
import com.edassist.models.dto.StudentLoanPaymentRequestDTO;

@Mapper(uses = { StudentLoanMapper.class })
public interface StudentLoanPaymentRequestMapper {

	@Mappings({ @Mapping(source = "studentLoanID", target = "studentLoan") })

	StudentLoanPaymentRequestDTO toDTO(StudentLoanPaymentRequest studentLoanPaymentRequest);

	StudentLoanPaymentRequest toDomain(StudentLoanPaymentRequestDTO studentLoanPaymentRequestDTO, @MappingTarget StudentLoanPaymentRequest studentLoanPaymentRequest);
}
