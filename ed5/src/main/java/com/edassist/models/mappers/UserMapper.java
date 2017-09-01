package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.edassist.models.domain.ParticipantSupervisor;
import com.edassist.models.domain.ThinUser;
import com.edassist.models.domain.User;
import com.edassist.models.dto.UserDTO;

@Mapper(uses = { UserTypeMapper.class })
public interface UserMapper {

	UserDTO toDTO(User user);

	@Mappings({ @Mapping(source = "participantSupervisor.participantID.user.firstName", target = "firstName"),
			@Mapping(source = "participantSupervisor.participantID.user.lastName", target = "lastName") })
	UserDTO toDTO(ParticipantSupervisor participantSupervisor);

	List<UserDTO> toDTOList(List<ParticipantSupervisor> participantSupervisors);

	@Mappings({ @Mapping(source = "id", target = "userId") })
	UserDTO toDTO(ThinUser user);

	List<UserDTO> toUserDTOList(List<ThinUser> users);

	User toDomain(UserDTO userDTO, @MappingTarget User user);

}
