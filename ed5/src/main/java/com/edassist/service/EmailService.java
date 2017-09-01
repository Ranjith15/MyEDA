package com.edassist.service;

public interface EmailService {

	void sendApplicationEventNotificationEmail(Long applicationId, Long emailContentId, String comment);

	void sendPasswordResetEmail(Long userId, String newPassword, String email);
}