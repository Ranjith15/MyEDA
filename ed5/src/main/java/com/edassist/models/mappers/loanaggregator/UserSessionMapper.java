package com.edassist.models.mappers.loanaggregator;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.contracts.loanaggregator.UserSession;
import com.edassist.models.dto.loanaggregator.UserSessionDTO;

@Mapper
public interface UserSessionMapper {
	UserSessionDTO toDTO(UserSession userSession);

	List<UserSessionDTO> toDTOList(List<UserSession> userSession);

}
