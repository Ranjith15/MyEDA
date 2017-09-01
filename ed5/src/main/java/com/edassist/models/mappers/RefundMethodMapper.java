package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.domain.RefundMethod;
import com.edassist.models.dto.RefundMethodDTO;

@Mapper
public interface RefundMethodMapper {

	RefundMethodDTO toDTO(RefundMethod refundMethod);

	List<RefundMethodDTO> toDTOList(List<RefundMethod> refundMethods);

}