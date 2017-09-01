package com.edassist.models.mappers.decorators;

import com.edassist.models.domain.CourseHistory;
import com.edassist.models.domain.Expense;
import com.edassist.models.domain.ThinExpense;
import com.edassist.models.dto.ExpenseDTO;
import com.edassist.models.mappers.ExpenseMapper;
import com.edassist.service.ApplicationCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExpenseMapperDecorator implements ExpenseMapper {

	@Autowired
	@Qualifier("delegate")
	private ExpenseMapper delegate;

	@Autowired
	private ApplicationCoursesService applicationCoursesService;

	@Override
	public ExpenseDTO toDTO(ThinExpense expense) {
		return delegate.toDTO(expense);
	}

	@Override
	public ExpenseDTO toDTO(Expense expense) {
		ExpenseDTO expenseDto = delegate.toDTO(expense);
		final Long applicationStatusId = expense.getApplication().getApplicationStatus().getApplicationStatusID();
		final List<CourseHistory> historyList = expense.getHistoryList();
		final BigDecimal requestedFee = expense.getFeesAmount();
		expenseDto.setApprovedAmount(applicationCoursesService.getApprovedAmount(requestedFee, applicationStatusId, historyList, false));
		expenseDto.setPaidAmount(applicationCoursesService.getPaidAmount(requestedFee, applicationStatusId, historyList, false));
		return expenseDto;
	}

	@Override
	public List<ExpenseDTO> toDTOListFromThin(List<ThinExpense> expenses) {
		List<ExpenseDTO> expenseDtoList = new ArrayList<>();
		for (ThinExpense expense : expenses) {
			expenseDtoList.add(toDTO(expense));
		}
		return expenseDtoList;
	}

	@Override
	public List<ExpenseDTO> toDTOList(List<Expense> expenses) {
		List<ExpenseDTO> expenseDtoList = new ArrayList<>();
		for (Expense expense : expenses) {
			expenseDtoList.add(toDTO(expense));
		}
		return expenseDtoList;
	}

	@Override
	public ThinExpense toThin(Expense expense) {
		return delegate.toThin(expense);
	}

	@Override
	public List<ThinExpense> toThinList(List<Expense> expenses) {
		return delegate.toThinList(expenses);
	}
}
