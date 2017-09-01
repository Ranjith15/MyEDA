package com.edassist.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.edassist.models.domain.PaymentPreference;
import com.edassist.models.dto.PaymentPreferenceDTO;

@Mapper
public interface PaymentPreferenceMapper {

	PaymentPreferenceDTO toDTO(PaymentPreference paymentPreference);

	PaymentPreference toDomain(PaymentPreferenceDTO paymentPreferenceDTO, @MappingTarget PaymentPreference paymentPreference);

}
