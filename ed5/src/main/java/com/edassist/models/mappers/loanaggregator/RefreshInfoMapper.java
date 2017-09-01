package com.edassist.models.mappers.loanaggregator;

import org.mapstruct.Mapper;

import com.edassist.models.contracts.loanaggregator.RefreshInfo;
import com.edassist.models.dto.loanaggregator.RefreshInfoDTO;

@Mapper
public interface RefreshInfoMapper {
	RefreshInfoDTO toDTO(RefreshInfo refreshInfo);
}
