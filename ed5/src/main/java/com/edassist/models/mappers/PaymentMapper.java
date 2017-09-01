package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.domain.Payments;
import com.edassist.models.dto.PaymentDTO;

@Mapper
public interface PaymentMapper {

	PaymentDTO toDTO(Payments payment);

	List<PaymentDTO> toDTOList(List<Payments> payments);

}