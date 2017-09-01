package com.edassist.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edassist.constants.RestConstants;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.exception.ForbiddenException;
import com.edassist.exception.UnprocessableEntityException;
import com.edassist.models.domain.Application;
import com.edassist.models.domain.ApplicationCourses;
import com.edassist.models.domain.ProgramExpenseType;
import com.edassist.models.dto.ApplicationExpensesDTO;
import com.edassist.models.dto.ExpenseTypeDTO;
import com.edassist.models.mappers.ApplicationExpensesMapper;
import com.edassist.models.mappers.ExpenseTypeMapper;
import com.edassist.service.AccessService;
import com.edassist.service.ApplicationCoursesService;
import com.edassist.service.ApplicationService;
import com.edassist.service.ProgramExpenseTypeService;
import com.edassist.utils.CommonUtil;

@RestController
public class ExpensesController {

	private final ApplicationExpensesMapper applicationExpensesMapper;
	private final ExpenseTypeMapper expenseTypeMapper;
	private final AccessService accessService;
	private final ApplicationService applicationService;
	private final ApplicationCoursesService applicationCoursesService;
	private final ProgramExpenseTypeService programExpenseTypeService;

	@Autowired
	public ExpensesController(ApplicationExpensesMapper applicationExpensesMapper, ExpenseTypeMapper expenseTypeMapper, AccessService accessService, ApplicationService applicationService,
			ApplicationCoursesService applicationCoursesService, ProgramExpenseTypeService programExpenseTypeService) {
		this.applicationExpensesMapper = applicationExpensesMapper;
		this.expenseTypeMapper = expenseTypeMapper;
		this.accessService = accessService;
		this.applicationService = applicationService;
		this.applicationCoursesService = applicationCoursesService;
		this.programExpenseTypeService = programExpenseTypeService;
	}

	@RequestMapping(value = "/v1/applications/{applicationID}/expenses/expenseTypes", method = RequestMethod.GET)
	public ResponseEntity<List<ExpenseTypeDTO>> getExpenseTypes(@PathVariable("applicationID") Long applicationID) {
		List<ExpenseTypeDTO> expenseTypeDTOs = new ArrayList<>();

		Application application = applicationService.findById(applicationID);

		accessService.verifyParticipantOrHigherAccess(application);
		if (application == null) {
			throw new BadRequestException("Application must not be null.  ApplicationId: [" + applicationID + "]");
		}
		List<ProgramExpenseType> programExpenseTypes = programExpenseTypeService.findByParam("programID", application.getBenefitPeriodID().getProgramID());
		for (ProgramExpenseType programExpenseType : programExpenseTypes) {
			expenseTypeDTOs.add(expenseTypeMapper.toDTO(programExpenseType.getExpenseTypeID()));
		}

		return new ResponseEntity<>(expenseTypeDTOs, HttpStatus.OK);
	}

	// TODO Add application compliancy
	@RequestMapping(value = "/v1/applications/{applicationId}/expenses", method = RequestMethod.GET)
	public ResponseEntity<List<ApplicationExpensesDTO>> getApplicationExpenses(@PathVariable("applicationId") Long applicationId) {
		Application application = applicationService.findById(applicationId);
		accessService.compareParticipantOrClientAdminToSession(application.getParticipantID().getParticipantId());
		List<ApplicationExpensesDTO> applicationExpensesDTOList = getListOfExpenses(application);

		return new ResponseEntity<>(applicationExpensesDTOList, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/expenses", method = RequestMethod.POST)
	public ResponseEntity<List<ApplicationExpensesDTO>> createApplicationCourse(@PathVariable("applicationId") Long applicationId, @RequestBody List<ApplicationExpensesDTO> applicationExpensesDTOList)
			throws Exception {

		Long relatedCourseId = null;
		Application application = applicationService.findById(applicationId);

		if (application == null) {
			throw new BadRequestException("Application not found");
		}

		accessService.compareParticipantToSession(application.getParticipantID().getParticipantId());

		if (!applicationService.isApplicationEditable(application)) {
			throw new ForbiddenException();
		}

		List<ApplicationCourses> createdExpenses = new ArrayList<>();

		for (ApplicationExpensesDTO applicationExpensesDTO : applicationExpensesDTOList) {

			if (applicationExpensesDTO.getRelatedCourseID() != null) {
				relatedCourseId = applicationExpensesDTO.getRelatedCourseID().getApplicationCoursesID();
			}

			if (applicationId != null && applicationExpensesDTO.getExpenseType() != null && applicationExpensesDTO.getFeesAmount() != null) {
				createdExpenses.add(programExpenseTypeService.createExpense(applicationId, applicationExpensesDTO.getExpenseType().getExpenseTypeID(), relatedCourseId,
						applicationExpensesDTO.getFeesAmount(), applicationExpensesDTO.getNumberOfBooks(), applicationExpensesDTO.getMaintainSkillsYN(), applicationExpensesDTO.getMeetMinimumQualsYN(),
						applicationExpensesDTO.getNewCareerFieldYN()));
			} else {
				throw new UnprocessableEntityException(RestConstants.REST_MISSING_REQUIRED_FIELD);
			}
		}

		List<ApplicationExpensesDTO> createdExpensesDTO = applicationExpensesMapper.toDTOList(createdExpenses);

		return new ResponseEntity<>(createdExpensesDTO, HttpStatus.OK);
	}

	private List<ApplicationExpensesDTO> getListOfExpenses(Application application) throws ExceededMaxResultsException {
		List<ApplicationExpensesDTO> applicationExpensesDTOList;

		String[] paramNames = { "applicationID", "expenseType", "feesAmount" };
		Object[] paramValues = { application, CommonUtil.RESTRICTION_NOT_NULL, CommonUtil.RESTRICTION_NOT_NULL };

		List<ApplicationCourses> ApplicationExpensesList = applicationCoursesService.findByParams(paramNames, paramValues, null, null);
		applicationExpensesDTOList = applicationExpensesMapper.toDTOList(ApplicationExpensesList);
		return applicationExpensesDTOList;
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/expenses/{applicationExpenseId}", method = RequestMethod.PUT)
	public ResponseEntity<ApplicationExpensesDTO> updateApplicationExpenses(@PathVariable("applicationId") Long applicationId, @PathVariable("applicationExpenseId") Long applicationExpenseId,
			@RequestBody ApplicationExpensesDTO applicationExpenseDTO) {

		Application application = applicationService.findById(applicationId);
		ApplicationCourses updatedExpense;

		if (application == null) {
			throw new BadRequestException("Application not found");
		}

		if (!accessService.verifyCourseAndExpenseInApplication(application, applicationExpenseId)) {
			throw new BadRequestException();
		}

		accessService.verifyUserToSession(application);

		if (!applicationService.isApplicationEditable(application)) {
			throw new ForbiddenException();
		}

		if (applicationExpenseDTO == null || !applicationExpenseDTO.getApplicationExpensesID().equals(applicationExpenseId)) {
			throw new BadRequestException();
		} else {
			ApplicationCourses applicationExpense = applicationCoursesService.findById(applicationExpenseDTO.getApplicationExpensesID());
			updatedExpense = applicationExpensesMapper.toDomain(applicationExpenseDTO, applicationExpense);
		}

		if (updatedExpense != null) {
			applicationCoursesService.persistApplicationCourseOrExpense(updatedExpense);
		}

		ApplicationExpensesDTO updatedExpenseDTO = applicationExpensesMapper.toDTO(updatedExpense);

		return new ResponseEntity<>(updatedExpenseDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/applications/{applicationId}/expenses/{applicationExpenseId}", method = RequestMethod.DELETE)
	public ResponseEntity<ApplicationExpensesDTO> deleteApplicationExpenses(@PathVariable("applicationId") Long applicationId, @PathVariable("applicationExpenseId") Long applicationExpenseId) {

		ApplicationCourses applicationExpense = applicationCoursesService.findById(applicationExpenseId);
		ApplicationExpensesDTO applicationExpenseDTO = applicationExpensesMapper.toDTO(applicationExpense);
		if (applicationExpense != null) {
			applicationCoursesService.remove(applicationExpense);
		}
		return new ResponseEntity<>(applicationExpenseDTO, HttpStatus.OK);
	}
}
