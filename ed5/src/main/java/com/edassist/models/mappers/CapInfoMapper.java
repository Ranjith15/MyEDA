package com.edassist.models.mappers;

import com.edassist.models.sp.CapInfo;
import com.edassist.models.dto.CapInfoDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CapInfoMapper {

	CapInfoDTO toDTO(CapInfo capInfo);
}
