package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.domain.ThinAppActivityForMyTeam;
import com.edassist.models.dto.ThinReviewApplicationDTO;

@Mapper
public interface ThinReviewApplicationMapper {

	@Mappings({ @Mapping(source = "applicationID.applicationID", target = "applicationID"), @Mapping(source = "applicationID.applicationNumber", target = "applicationNumber"),
			@Mapping(source = "applicationID.applicationStatusID.applicationStatusID", target = "applicationStatusID"),
			@Mapping(source = "applicationID.applicationStatusID.applicationStatus", target = "applicationStatus"),
			@Mapping(source = "applicationID.participantID.participantId", target = "participantId"), @Mapping(source = "applicationID.participantID.user.firstName", target = "firstName"),
			@Mapping(source = "applicationID.participantID.user.middleInitial", target = "mI"), @Mapping(source = "applicationID.participantID.user.lastName", target = "lastName"),
			@Mapping(source = "applicationID.applicationTypeID.applicationTypeName", target = "applicationTypeCode"), @Mapping(source = "applicationID.courseStartDate", target = "sessionStartDate"),
			@Mapping(source = "applicationID.benefitPeriodID.programID.programName", target = "programName"), @Mapping(source = "applicationID.dateModified", target = "dateModified") })

	ThinReviewApplicationDTO toDTO(ThinAppActivityForMyTeam thinAppActivityForMyTeam);

	List<ThinReviewApplicationDTO> toDTOList(List<ThinAppActivityForMyTeam> thinAppActivityForMyTeams);

}
