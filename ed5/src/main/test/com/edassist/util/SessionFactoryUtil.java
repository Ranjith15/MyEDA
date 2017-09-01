package com.edassist.util;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SessionFactoryUtil {
	private static ApplicationContext appContext;
	private static SessionFactory sessionFactory;
	private static ApplicationContext applicationPortletContext;
	private static ApplicationContext contentPortletContext;
	private static ApplicationContext clientPortletContext;
	private static ApplicationContext participantPortletContext;
	private static ApplicationContext programPortletContext;
	private static ApplicationContext providerPortletContext;
	private static ApplicationContext workflowDistributionPortletContext;
	private static ApplicationContext applicationWorkflowPortletContext;

	public static ApplicationContext getAppContext() {
		if (appContext == null) {
			String[] appContextPath = { "src/main/test/junittestapplicationContext.xml" };
			appContext = new FileSystemXmlApplicationContext(appContextPath);
		}
		return appContext;
	}

	public static ApplicationContext getApplicationPortletContext() {
		if (applicationPortletContext == null) {
			String[] portletContextPath = { "src/main/webapp/WEB-INF/context/portlet/ApplicationPortlet.xml" };
			applicationPortletContext = new FileSystemXmlApplicationContext(portletContextPath, getAppContext());
		}

		return applicationPortletContext;
	}

	public static ApplicationContext getContentPortletContext() {
		if (contentPortletContext == null) {
			String[] portletContextPath = { "src/main/webapp/WEB-INF/context/portlet/ContentPortlet.xml" };
			contentPortletContext = new FileSystemXmlApplicationContext(portletContextPath, getAppContext());
		}

		return contentPortletContext;
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = (SessionFactory) getAppContext().getBean("sessionFactory");
		}
		return sessionFactory;
	}

	public static ApplicationContext getClientPortletContext() {
		if (clientPortletContext == null) {
			String[] portletContextPath = { "src/main/webapp/WEB-INF/context/portlet/ClientPortlet.xml" };
			clientPortletContext = new FileSystemXmlApplicationContext(portletContextPath, getAppContext());
		}
		return clientPortletContext;
	}

	public static ApplicationContext getParticipantPortletContext() {
		if (participantPortletContext == null) {
			String[] portletContextPath = { "src/main/webapp/WEB-INF/context/portlet/ParticipantPortlet.xml" };
			participantPortletContext = new FileSystemXmlApplicationContext(portletContextPath, getAppContext());
		}
		return participantPortletContext;
	}

	public static ApplicationContext getProgramPortletContext() {
		if (programPortletContext == null) {
			String[] portletContextPath = { "src/main/webapp/WEB-INF/context/portlet/ProgramPortlet.xml" };
			programPortletContext = new FileSystemXmlApplicationContext(portletContextPath, getAppContext());
		}
		return programPortletContext;
	}

	public static ApplicationContext getProviderPortletContext() {
		if (providerPortletContext == null) {
			String[] portletContextPath = { "src/main/webapp/WEB-INF/context/portlet/ProviderPortlet.xml" };
			providerPortletContext = new FileSystemXmlApplicationContext(portletContextPath, getAppContext());
		}
		return providerPortletContext;
	}

	public static ApplicationContext getWorkflowDistributionPortletContext() {
		if (workflowDistributionPortletContext == null) {
			String[] portletContextPath = { "src/main/webapp/WEB-INF/context/portlet/WorkflowDistributionPortlet.xml" };
			workflowDistributionPortletContext = new FileSystemXmlApplicationContext(portletContextPath, getAppContext());
		}
		return workflowDistributionPortletContext;
	}

	public static ApplicationContext getApplicationWorkflowPortletContext() {
		if (applicationWorkflowPortletContext == null) {
			String[] portletContextPath = { "src/main/webapp/WEB-INF/context/portlet/ApplicationWorkflowPortlet.xml" };
			applicationWorkflowPortletContext = new FileSystemXmlApplicationContext(portletContextPath, getAppContext());
		}
		return applicationWorkflowPortletContext;
	}
}
