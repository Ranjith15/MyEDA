package com.edassist.models.mappers.loanaggregator;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.contracts.loanaggregator.Amount;
import com.edassist.models.dto.loanaggregator.AmountDTO;

@Mapper
public interface AmountMapper {

	AmountDTO toDTO(Amount amount);

	List<AmountDTO> toDTOList(List<Amount> amounts);

}
