package com.edassist.models.mappers.content;

import java.util.List;

import org.mapstruct.Mapper;

import com.edassist.models.contracts.content.EmailContent;
import com.edassist.models.dto.content.EmailContentDTO;

@Mapper
public interface EmailContentMapper {

	EmailContentDTO toDTO(EmailContent email);

	List<EmailContentDTO> toDTOList(List<EmailContent> emailList);

	EmailContent toDomain(EmailContentDTO emailDTO);

}
