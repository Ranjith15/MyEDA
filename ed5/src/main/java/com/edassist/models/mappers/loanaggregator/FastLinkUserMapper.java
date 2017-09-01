package com.edassist.models.mappers.loanaggregator;

import org.mapstruct.Mapper;

import com.edassist.models.contracts.loanaggregator.FastLinkUser;
import com.edassist.models.dto.loanaggregator.FastLinkUserDTO;

@Mapper(uses = UserAccessTokensMapper.class)
public interface FastLinkUserMapper {

	FastLinkUserDTO toDTO(FastLinkUser user);

	FastLinkUser toDomain(FastLinkUserDTO dto);

}
