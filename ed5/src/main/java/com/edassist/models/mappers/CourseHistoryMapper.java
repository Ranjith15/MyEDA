package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.domain.CourseHistory;
import com.edassist.models.dto.CourseHistoryDTO;

@Mapper
public interface CourseHistoryMapper {
	CourseHistoryDTO toDTO(CourseHistory courseHistory);

	List<CourseHistoryDTO> toDTOList(List<CourseHistory> courseHistories);
}
