package com.edassist.models.mappers.crm.advising;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.contracts.crm.advising.AvailableDate;
import com.edassist.models.dto.crm.advising.AvailableDateDTO;

@Mapper
public interface AvailableDateMapper {
	AvailableDateDTO toDTO(AvailableDate availableDate);

	AvailableDate toDomain(AvailableDateDTO dto);

	List<AvailableDateDTO> toDTOList(List<AvailableDate> availableDateList);
}
