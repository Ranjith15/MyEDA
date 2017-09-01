package com.edassist.models.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.edassist.models.domain.Application;
import com.edassist.models.dto.ApplicationSessionInfoDTO;
import com.edassist.models.mappers.decorators.ApplicationSessionInfoMapperDecorator;

@Mapper
@DecoratedWith(ApplicationSessionInfoMapperDecorator.class)
public interface ApplicationSessionInfoMapper {

	@Mappings({ @Mapping(source = "participantID.currentEducationProfile.expGraduationDt", target = "expGraduationDt") })
	ApplicationSessionInfoDTO toDTO(Application application);

	Application toDomain(ApplicationSessionInfoDTO applicationSessionInfoDTO, @MappingTarget Application application);

}
