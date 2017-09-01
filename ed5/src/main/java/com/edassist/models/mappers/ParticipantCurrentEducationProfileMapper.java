package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.edassist.models.domain.ParticipantCurrentEducationProfile;
import com.edassist.models.dto.ParticipantCurrentEducationProfileDTO;

@Mapper(uses = { DegreeObjectivesMapper.class, CourseOfStudyMapper.class, ProgramMapper.class, EducationalProviderMapper.class })
public interface ParticipantCurrentEducationProfileMapper {

	ParticipantCurrentEducationProfileDTO toDTO(ParticipantCurrentEducationProfile participantCurrentEducationProfile);

	ParticipantCurrentEducationProfile toDomain(ParticipantCurrentEducationProfileDTO participantCurrentEducationProfileDTO,
			@MappingTarget ParticipantCurrentEducationProfile participantCurrentEducationProfile);

}
