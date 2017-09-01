package com.edassist.models.mappers.crm.advising;

import org.mapstruct.Mapper;

import com.edassist.models.contracts.crm.advising.Attachment;
import com.edassist.models.dto.AttachmentDTO;

@Mapper
public interface AttachmentMapper {

	AttachmentDTO toDTO(Attachment attachment);

	Attachment toDomain(AttachmentDTO dto);

}
