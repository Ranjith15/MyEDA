package com.edassist.models.mappers;

import com.edassist.models.domain.PrepayTuitionApp;
import com.edassist.models.dto.PrepayTuitionAppDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface PrepayTuitionAppMapper {

	PrepayTuitionAppDTO toDTO(PrepayTuitionApp prepayTuitionApp);

	PrepayTuitionApp toDomain(PrepayTuitionAppDTO prepayTuitionAppDTO, @MappingTarget PrepayTuitionApp prepayTuitionApp);
}
