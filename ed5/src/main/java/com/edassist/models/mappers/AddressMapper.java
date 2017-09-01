package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.edassist.models.domain.Address;
import com.edassist.models.dto.AddressDTO;

@Mapper(uses = { StateMapper.class })
public interface AddressMapper {

	AddressDTO toDTO(Address address);

	Address toDomain(AddressDTO addressDTO, @MappingTarget Address address);

}
