package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.edassist.models.domain.Application;
import com.edassist.models.domain.ApplicationComment;
import com.edassist.models.dto.ApplicationCommentsDTO;
import com.edassist.models.mappers.decorators.ApplicationCommentsMapperDecorator;

@Mapper(uses = { ApplicationStatusMapper.class, UserMapper.class })
@DecoratedWith(ApplicationCommentsMapperDecorator.class)
public interface ApplicationCommentsMapper {

	@Mappings({ @Mapping(source = "applicationComments.applicationStatusID.applicationStatus", target = "applicationStatus") })
	ApplicationCommentsDTO toDTO(ApplicationComment applicationComments);

	List<ApplicationCommentsDTO> toDTOList(List<ApplicationComment> applicationComments);

	@Mappings({ @Mapping(target = "createdBy", ignore = true), @Mapping(target = "modifiedBy", ignore = true), @Mapping(target = "reviewedBy", ignore = true),
			@Mapping(target = "reviewedDate", ignore = true), @Mapping(target = "dateCreated", ignore = true), @Mapping(source = "application.applicationID", target = "applicationID"),
			@Mapping(source = "application.applicationStatusID", target = "applicationStatusID") })
	ApplicationComment toDomain(ApplicationCommentsDTO applicationCommentsDto, @MappingTarget ApplicationComment originalApplicationComment, Application application);

}
