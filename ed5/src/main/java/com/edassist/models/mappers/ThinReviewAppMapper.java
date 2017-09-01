package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.domain.ThinApp;
import com.edassist.models.dto.ThinReviewApplicationDTO;

@Mapper
public interface ThinReviewAppMapper {
	@Mappings({ @Mapping(source = "applicationStatusID.applicationStatusID", target = "applicationStatusID"), @Mapping(source = "applicationStatusID.applicationStatus", target = "applicationStatus"),
			@Mapping(source = "participantID.participantId", target = "participantId"), @Mapping(source = "participantID.user.firstName", target = "firstName"),
			@Mapping(source = "participantID.user.middleInitial", target = "mI"), @Mapping(source = "participantID.user.lastName", target = "lastName"),
			@Mapping(source = "applicationTypeID.applicationTypeName", target = "applicationTypeCode"), @Mapping(source = "courseStartDate", target = "sessionStartDate") })

	ThinReviewApplicationDTO toDTO(ThinApp thinApp);

	List<ThinReviewApplicationDTO> toDTOList(List<ThinApp> thinApps);
}
