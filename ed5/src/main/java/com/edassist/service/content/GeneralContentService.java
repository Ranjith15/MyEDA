package com.edassist.service.content;

import java.util.Date;
import java.util.List;

import com.edassist.models.contracts.content.GeneralContent;
import com.edassist.models.contracts.content.ProviderDocument;

public interface GeneralContentService {

	List<GeneralContent> findByComponent(String component, String client, String program, boolean textOnly, Date signedDate);

	List<GeneralContent> findByName(String component, String name, String client, String program, boolean textOnly, boolean cascade);

	List<ProviderDocument> findProviderDocuments(String providerType, Long providerId);

	GeneralContent insertContent(GeneralContent content);

	void updateContent(GeneralContent content);

	void deleteById(String id);

	List<GeneralContent> getByClient(String client);

	List<GeneralContent> getByProgram(String client, String program, boolean textOnly);

}
