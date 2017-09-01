package com.edassist.models.mappers;

import org.mapstruct.Mapper;

import com.edassist.models.sp.AccountSnapshot;
import com.edassist.models.sp.AccountSnapshotForParticipant;
import com.edassist.models.dto.AccountSnapshotDTO;

@Mapper
public interface AccountSnapshotMapper {

	AccountSnapshotDTO toDTO(AccountSnapshot accountSnapshot);

	AccountSnapshotDTO toDTO(AccountSnapshotForParticipant accountSnapshotForParticipant);

}
