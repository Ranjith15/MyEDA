package com.edassist.models.mappers.decorators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edassist.models.domain.Address;
import com.edassist.models.domain.Participant;
import com.edassist.models.dto.AddressDTO;
import com.edassist.models.dto.ApproverDTO;
import com.edassist.models.mappers.AddressMapper;
import com.edassist.models.mappers.ApproverMapper;

public abstract class ApproverMapperDecorator implements ApproverMapper {

	@Autowired
	@Qualifier("delegate")
	private ApproverMapper delegate;

	@Autowired
	private AddressMapper addressMapper;

	@Override
	public ApproverDTO toDTO(Participant participant) {
		ApproverDTO approverDTO = delegate.toDTO(participant);
		if (participant != null) {
			Address preferredAddress = null;
			if (participant.getHomeAddress() != null && participant.getPreferredAddress().equals(participant.getHomeAddress().getAddressId())) {
				preferredAddress = participant.getHomeAddress();
			} else if (participant.getWorkAddress() != null && participant.getPreferredAddress().equals(participant.getWorkAddress().getAddressId())) {
				preferredAddress = participant.getWorkAddress();
			}
			if (preferredAddress != null) {
				AddressDTO preferredAddressDTO = addressMapper.toDTO(preferredAddress);
				approverDTO.setAddress(preferredAddressDTO);
			}

		}

		return approverDTO;
	}

}
