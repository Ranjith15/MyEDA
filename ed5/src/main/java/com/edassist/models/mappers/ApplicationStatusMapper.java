package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.domain.ApplicationStatus;
import com.edassist.models.dto.ApplicationStatusDTO;

@Mapper
public interface ApplicationStatusMapper {

	ApplicationStatusDTO toDTO(ApplicationStatus applicationStatus);

	List<ApplicationStatusDTO> toDTOList(List<ApplicationStatus> applicationStatus);

}
