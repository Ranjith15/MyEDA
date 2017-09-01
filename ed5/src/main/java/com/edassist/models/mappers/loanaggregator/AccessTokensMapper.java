package com.edassist.models.mappers.loanaggregator;

import org.mapstruct.Mapper;

import com.edassist.models.contracts.loanaggregator.AccessTokens;
import com.edassist.models.dto.loanaggregator.AccessTokensDTO;

@Mapper
public interface AccessTokensMapper {

	AccessTokensDTO toDTO(AccessTokens accessToken);

	AccessTokens toDomain(AccessTokensDTO dto);
	
}
