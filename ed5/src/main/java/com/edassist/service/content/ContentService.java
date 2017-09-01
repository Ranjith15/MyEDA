package com.edassist.service.content;

import java.util.List;

public interface ContentService {

	List<String> getComponentKeys(String type);

	List<String> getNamesKeys(String type, String component);

	List<String> getCollectionKeys(String type);

	void migrateClient(String clientId);

	void propagateClient(String clientId);

	Long needsPropagatedContentSize(String clientId);
}
