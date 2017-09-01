package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.edassist.models.domain.StudentLoanServicer;
import com.edassist.models.dto.StudentLoanServicerDTO;

@Mapper
public interface StudentLoanServicerMapper {

	StudentLoanServicerMapper INSTANCE = Mappers.getMapper(StudentLoanServicerMapper.class);

	StudentLoanServicerDTO toDTO(StudentLoanServicer studentLoanServicer);

}
