package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.domain.Refunds;
import com.edassist.models.dto.RefundDTO;

@Mapper(uses = { RefundMethodMapper.class })
public interface RefundsMapper {

	RefundDTO toDTO(Refunds refund);

	List<RefundDTO> toDTOList(List<Refunds> refunds);

}