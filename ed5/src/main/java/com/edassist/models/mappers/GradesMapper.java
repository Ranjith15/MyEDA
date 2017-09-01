package com.edassist.models.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.edassist.models.domain.Grades;
import com.edassist.models.dto.GradesDTO;

@Mapper
public interface GradesMapper {

	@Mappings({ @Mapping(source = "gradeDesc", target = "gradeName") })

	GradesDTO toDTO(Grades grades);

	List<GradesDTO> toDTOList(List<Grades> gradesList);

	Grades toDomain(GradesDTO gradesDTO, @MappingTarget Grades grades);
}
