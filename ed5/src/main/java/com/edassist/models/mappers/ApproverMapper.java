package com.edassist.models.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import com.edassist.models.domain.Participant;
import com.edassist.models.dto.ApproverDTO;
import com.edassist.models.mappers.decorators.ApproverMapperDecorator;

@Mapper(uses = { UserMapper.class })
@DecoratedWith(ApproverMapperDecorator.class)
public interface ApproverMapper {

	ApproverDTO toDTO(Participant participant);

}
