package com.edassist.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.edassist.constants.RestConstants;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.CrmException;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.exception.ForbiddenException;
import com.edassist.exception.InternalServerException;
import com.edassist.exception.LoanAggregatorException;
import com.edassist.exception.NotFoundException;
import com.edassist.exception.UnauthorizedException;
import com.edassist.exception.UnprocessableEntityException;
import com.edassist.models.dto.Errors;
import com.edassist.models.dto.RestErrors;

@ControllerAdvice
public class RestControllerAdvice {

	private static Logger log = Logger.getLogger(RestControllerAdvice.class);

	@Autowired
	private MessageSource messageSource;

	private ResponseEntity<RestErrors> handleException(Exception ex, HttpStatus httpStatus) {
		log.error(ex.getClass() + ":" + ex.getMessage(), ex.getCause());
		return new ResponseEntity<>(new RestErrors(ex.getMessage()), httpStatus);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<RestErrors> handleNotFoundException(NotFoundException ex) {
		return handleException(ex, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<RestErrors> handleBadRequestException(BadRequestException ex) {
		return handleException(ex, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ForbiddenException.class)
	protected ResponseEntity<RestErrors> handleForbiddenException(ForbiddenException ex) {
		return handleException(ex, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(UnauthorizedException.class)
	protected ResponseEntity<RestErrors> handleUnauthorizedException(UnauthorizedException ex) {
		return handleException(ex, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UnprocessableEntityException.class)
	protected ResponseEntity<RestErrors> handleUnprocessableEntityException(UnprocessableEntityException ex) {
		return handleException(ex, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(DataAccessException.class)
	protected ResponseEntity<RestErrors> handleDataAccessException(DataAccessException ex) {
		log.error(ex.getClass() + ":" + ex.getMessage(), ex.getCause());
		return new ResponseEntity<>(new RestErrors(RestConstants.REST_UNEXPECTED_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InternalServerException.class)
	protected ResponseEntity<RestErrors> handleInternalServerException(InternalServerException ex) {
		return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(LockedException.class)
	protected ResponseEntity<RestErrors> handleLockedException(LockedException ex) {
		return handleException(ex, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<RestErrors> handleBadCredentialsException(BadCredentialsException ex) {
		return handleException(ex, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<RestErrors> handleValidationException(MethodArgumentNotValidException ex) {
		List<Errors> errors = new ArrayList<>();
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			Errors error = new Errors();
			error.setDefaultMessage(messageSource.getMessage(fieldError, null));
			error.setCode(fieldError.getCode());
			errors.add(error);
		}
		RestErrors restErrors = new RestErrors("Validation Failed", errors);
		return new ResponseEntity<>(restErrors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<RestErrors> handleGeneralException(Exception ex) {
		log.error(ex.getClass() + ":" + ex.getMessage(), ex.getCause());
		return new ResponseEntity<>(new RestErrors(RestConstants.REST_UNEXPECTED_ERROR), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExceededMaxResultsException.class)
	protected ResponseEntity<RestErrors> handleExceededMaxResultsException(ExceededMaxResultsException ex) {
		log.error(ex.getClass() + ":" + ex.getMessage(), ex.getCause());
		return new ResponseEntity<>(new RestErrors(RestConstants.REST_EXCEEDED_MAX_RESULT), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CrmException.class)
	protected ResponseEntity<RestErrors> handleCrmException(CrmException ex) {
		return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(LoanAggregatorException.class)
	protected ResponseEntity<RestErrors> handleLoanAggregatorException(LoanAggregatorException ex) {
		log.error(ex.getClass() + ":" + ex.getErrorResponse());
		return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}