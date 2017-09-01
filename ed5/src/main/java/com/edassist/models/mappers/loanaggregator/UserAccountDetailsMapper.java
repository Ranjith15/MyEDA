package com.edassist.models.mappers.loanaggregator;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.edassist.models.contracts.loanaggregator.UserAccountDetails;
import com.edassist.models.domain.StudentLoan;
import com.edassist.models.dto.loanaggregator.UserAccountDetailsDTO;

@Mapper(uses = { AmountMapper.class, RefreshInfoMapper.class })
public interface UserAccountDetailsMapper {

	@Mappings({ @Mapping(source = "providerName", target = "servicerName") })
	UserAccountDetailsDTO toDTO(UserAccountDetails userAccountDetails);

	List<UserAccountDetailsDTO> toDTOList(List<UserAccountDetails> userAccountDetails);

	@Mappings({ @Mapping(source = "accountNumber", target = "accountNumber"), @Mapping(source = "balance.amount", target = "loanBalance"), @Mapping(source = "amountDue.amount", target = "amountDue"),
			@Mapping(source = "accountStatus", target = "accountStatus") })
	StudentLoan toStudentLoan(UserAccountDetails userAccountDetails);
}
