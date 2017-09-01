package com.edassist.models.mappers.crm.advising;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.contracts.crm.advising.AvailableSlot;
import com.edassist.models.dto.crm.advising.AvailableSlotDTO;

@Mapper
public interface AvailableSlotMapper {
	AvailableSlotDTO toDTO(AvailableSlot availableSlot);

	AvailableSlot toDomain(AvailableSlotDTO dto);

	List<AvailableSlotDTO> toDTOList(List<AvailableSlot> availableSlotList);
}
