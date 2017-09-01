package com.edassist.service.content;

import com.edassist.models.contracts.content.EmailContent;
import com.edassist.models.contracts.content.FileContent;
import com.edassist.models.contracts.content.GeneralContent;

public interface PropagationService {

	void propagateGeneralContent(GeneralContent generalContent);

	void propagateEmailContent(EmailContent emailContent);

	void propagateFileContent(FileContent fileContent);
}