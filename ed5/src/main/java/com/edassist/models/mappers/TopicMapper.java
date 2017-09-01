package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.edassist.models.domain.crm.Topic;
import com.edassist.models.dto.TopicDTO;

@Mapper
public interface TopicMapper {
	TopicDTO toDTO(Topic topic);

	Topic toDomain(TopicDTO dto, @MappingTarget Topic topic);

	List<TopicDTO> toDTOList(List<Topic> topicList);
}
