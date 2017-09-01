package com.edassist.models.mappers.decorators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edassist.models.domain.Application;
import com.edassist.models.dto.ApplicationSessionInfoDTO;
import com.edassist.models.mappers.ApplicationSessionInfoMapper;

public abstract class ApplicationSessionInfoMapperDecorator implements ApplicationSessionInfoMapper {

	@Autowired
	@Qualifier("delegate")
	private ApplicationSessionInfoMapper delegate;

	// manual mapping of expGraduationDt
	@Override
	public Application toDomain(ApplicationSessionInfoDTO applicationSessionInfoDTO, Application application) {

		if (applicationSessionInfoDTO == null || application == null) {
			return null;
		}

		Application updatedApplication = delegate.toDomain(applicationSessionInfoDTO, application);

		updatedApplication.getParticipantID().getCurrentEducationProfile().setExpGraduationDt(applicationSessionInfoDTO.getExpGraduationDt());

		return updatedApplication;
	}

}
