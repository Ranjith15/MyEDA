package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.domain.User;
import com.edassist.models.dto.UserDetailsDTO;

@Mapper
public interface UserDetailsMapper {

	@Mappings({ @Mapping(source = "user.participantID.participantId", target = "participantId"),  @Mapping(source = "user.userType.id", target = "userTypeID") })

	UserDetailsDTO toDTO(User user);

}
