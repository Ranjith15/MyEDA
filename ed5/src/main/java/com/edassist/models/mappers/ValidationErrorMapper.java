package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.validation.ObjectError;

import com.edassist.models.dto.Errors;

@Mapper
public interface ValidationErrorMapper {

	Errors toError(ObjectError objectError);

	List<Errors> toErrorList(List<ObjectError> objectError);

}
