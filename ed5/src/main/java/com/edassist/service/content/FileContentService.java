package com.edassist.service.content;

import com.edassist.models.contracts.content.FileContent;
import org.springframework.core.io.ByteArrayResource;

public interface FileContentService {

	ByteArrayResource retrieveFileById(String id);

	Boolean saveOrUpdateFileById(FileContent fileContent);

}
