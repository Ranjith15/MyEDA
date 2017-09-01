package com.edassist.service.content;

import java.util.List;

import com.edassist.models.contracts.content.EmailContent;

public interface EmailContentService {

	List<EmailContent> findByComponent(String component, String client, String program);

	EmailContent findByName(String component, String name, String client, String program, boolean cascade);

	EmailContent addEmail(EmailContent email);

	void updateEmail(EmailContent email);

	void deleteById(String id);

	List<EmailContent> getByClient(String client);

	List<EmailContent> getByProgram(String client, String program);
}
