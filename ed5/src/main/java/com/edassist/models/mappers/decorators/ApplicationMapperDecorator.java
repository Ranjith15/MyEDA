package com.edassist.models.mappers.decorators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edassist.models.domain.*;
import com.edassist.models.dto.AppDTO;
import com.edassist.models.dto.EligibilityEventHistoryDTO;
import com.edassist.models.dto.ThinAppDTO;
import com.edassist.models.mappers.ApplicationMapper;
import com.edassist.models.mappers.CourseMapper;
import com.edassist.models.mappers.EligibilityEventHistoryMapper;
import com.edassist.models.mappers.ExpenseMapper;
import com.edassist.service.ApplicationService;
import com.edassist.utils.CommonUtil;

public abstract class ApplicationMapperDecorator implements ApplicationMapper {

	@Autowired
	@Qualifier("delegate")
	private ApplicationMapper delegate;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private ExpenseMapper expenseMapper;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private EligibilityEventHistoryMapper eligibilityEventHistoryMapper;

	@Override
	public ThinAppDTO toDTO(ThinApp application) {
		ThinAppDTO applicationDTO = delegate.toDTO(application);
		List<ThinCourse> courses = application.getCourses();
		List<ThinExpense> nonCourseExpenses = application.getNonCourseRelatedExpenses();
		applicationDTO.setTotalRequestedAmount(applicationService.getTotalRequestedAmount(courses, nonCourseExpenses));
		applicationDTO.setTotalRefunds(applicationService.getTotalRefunds(courses, nonCourseExpenses));
		return applicationDTO;
	}

	@Override
	public AppDTO toAppDTO(App application) {
		AppDTO appDTO = delegate.toAppDTO(application);
		List<Course> courses = application.getCourses();
		List<Expense> nonCourseExpenses = application.getNonCourseRelatedExpenses();
		appDTO.setTotalRequestedAmount(applicationService.getTotalRequestedAmount(courseMapper.toThinList(courses), expenseMapper.toThinList(nonCourseExpenses)));
		appDTO.setTotalRefunds(applicationService.getTotalRefunds(courseMapper.toThinList(courses), expenseMapper.toThinList(nonCourseExpenses)));

		List<EligibilityEventHistoryDTO> statusHistoryDtoList = eligibilityEventHistoryMapper.toDTOListFromRuleMessages(application.getRuleMessages());
		statusHistoryDtoList.addAll(eligibilityEventHistoryMapper.toDTOListFromEventComments(application.getEligibilityEventComments()));
		statusHistoryDtoList.addAll(eligibilityEventHistoryMapper.toDTOListFromAppStatusChange(application.getAppStatusChangeCollection()));
		statusHistoryDtoList.addAll(eligibilityEventHistoryMapper.toDTOListFromAppStatusChangeLive(application.getAppStatusChangeLiveCollection()));
		statusHistoryDtoList.addAll(eligibilityEventHistoryMapper.toDTOListFromApprovalHistory(application.getApprovalHistorys()));
		List<EligibilityEventHistoryDTO> statusHistoryfromRefundList = eligibilityEventHistoryMapper.toDTOListFromRefunds(application.getRefunds());
		for (EligibilityEventHistoryDTO eligibilityHistoryDto : statusHistoryfromRefundList) {
			eligibilityHistoryDto.setStatusId(CommonUtil.getMatchingApplicationStatus(eligibilityHistoryDto.getDateCreated(), application.getAppStatusChangeCollection()));
		}
		statusHistoryDtoList.addAll(statusHistoryfromRefundList);
		appDTO.setStatusHistory(statusHistoryDtoList);

		return appDTO;
	}

	@Override
	public List<ThinAppDTO> toDTOList(List<ThinApp> applications) {
		List<ThinAppDTO> thinAppDTOList = new ArrayList<>();
		for (ThinApp application : applications) {
			ThinAppDTO thinAppDTO = this.toDTO(application);
			thinAppDTOList.add(thinAppDTO);
		}
		return thinAppDTOList;
	}

}
