package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.edassist.models.domain.StudentLoan;
import com.edassist.models.dto.StudentLoanDTO;

@Mapper(uses = { StudentLoanServicerMapper.class })
public interface StudentLoanMapper {

	@Mappings({ @Mapping(source = "loanServicerID", target = "studentLoanServicer") })

	StudentLoanDTO toDTO(StudentLoan studentLoan);

	List<StudentLoanDTO> toDTOList(List<StudentLoan> studentLoans);

	StudentLoan toDomain(StudentLoanDTO studentLoanDTO, @MappingTarget StudentLoan StudentLoan);

}
