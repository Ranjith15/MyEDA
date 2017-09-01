package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.domain.ExpenseType;
import com.edassist.models.dto.ExpenseTypeDTO;

@Mapper
public interface ExpenseTypeMapper {

	ExpenseTypeDTO toDTO(ExpenseType expenseType);

	List<ExpenseTypeDTO> toDTOList(List<ExpenseType> expenseTypes);

	ExpenseType toDomain(ExpenseTypeDTO expenseTypeDTO);

}
