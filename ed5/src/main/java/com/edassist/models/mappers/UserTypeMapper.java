package com.edassist.models.mappers;

import com.edassist.models.domain.UserType;
import com.edassist.models.dto.UserTypeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserTypeMapper {

	UserTypeDTO toDTO(UserType userType);

	UserType toDomain(UserTypeDTO userTypeDTO);

	List<UserTypeDTO> toDTOList(List<UserType> userTypeList);
}
