package com.survey.softbistro.notification.system.component.interfacee;

import java.util.List;

import com.survey.softbistro.notification.system.component.entity.RegistrationMessage;
import com.survey.softbistro.notification.system.component.entity.SurveyMessage;

public interface ISendingMessage {
	/**
	 * Get records from DB with emails of users for sending messages about
	 * survey
	 */
	public List<SurveyMessage> getEmailsForSending(Integer page);

	/**
	 * Get emails new clients for confirm registration new account
	 * 
	 * @param page
	 * @return
	 */
	public List<RegistrationMessage> getEmailOfNewClients(Integer page);

	/**
	 * Get emails of clients that changed password for confirm it.
	 * 
	 * @param page
	 * @return
	 */
	public List<RegistrationMessage> getEmailOfNewPassword(Integer page);
}
