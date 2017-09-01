package com.edassist.models.mappers;

import com.edassist.models.domain.Expense;
import com.edassist.models.domain.ThinExpense;
import com.edassist.models.dto.ExpenseDTO;
import com.edassist.models.mappers.decorators.ExpenseMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(uses = { ExpenseTypeMapper.class })
@DecoratedWith(ExpenseMapperDecorator.class)
public interface ExpenseMapper {
	@Mappings({ @Mapping(source = "expense.expenseType.description", target = "name") })
	ExpenseDTO toDTO(ThinExpense expense);

	@Mappings({ @Mapping(source = "expense.expenseType.description", target = "name") })
	ExpenseDTO toDTO(Expense expense);

	List<ExpenseDTO> toDTOListFromThin(List<ThinExpense> expenses);
	List<ExpenseDTO> toDTOList(List<Expense> expenses);

	ThinExpense toThin(Expense expense);
	List<ThinExpense> toThinList(List<Expense> expenses);

}
