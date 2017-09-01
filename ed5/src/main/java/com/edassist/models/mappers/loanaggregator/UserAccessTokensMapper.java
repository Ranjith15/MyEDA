package com.edassist.models.mappers.loanaggregator;

import org.mapstruct.Mapper;

import com.edassist.models.contracts.loanaggregator.UserAccessTokens;
import com.edassist.models.dto.loanaggregator.UserAccessTokensDTO;

@Mapper(uses = AccessTokensMapper.class)
public interface UserAccessTokensMapper {

	UserAccessTokensDTO toDTO(UserAccessTokens userAccessTokens);

	UserAccessTokens toDomain(UserAccessTokensDTO dto);

}
