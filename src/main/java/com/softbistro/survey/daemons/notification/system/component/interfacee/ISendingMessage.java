package com.softbistro.survey.notification.system.component.interfacee;

import java.util.List;

import com.softbistro.survey.notification.system.component.entity.RegistrationMessage;
import com.softbistro.survey.notification.system.component.entity.SurveyMessage;

public interface ISendingMessage {
	/**
	 * Get records from DB with emails of users for sending messages about
	 * survey
	 */
	public List<SurveyMessage> getEmailsForSending();

	/**
	 * Get emails new clients for confirm registration new account
	 * 
	 * @param page
	 * @return
	 */
	public List<RegistrationMessage> getEmailOfNewClients();

	/**
	 * Get emails of clients that changed password for confirm it.
	 * 
	 * @param page
	 * @return
	 */
	public List<RegistrationMessage> getEmailOfNewPassword();

	/**
	 * Insert information for confirm change password
	 * 
	 * @param uuid
	 * @param clientd
	 */
	public void insertForConfirmPassword(String uuid, Integer clientd);

	/**
	 * Insert information for confirm email new user
	 * 
	 * @param uuid
	 * @param clientd
	 */
	public void insertForConfirmEmail(String uuid, Integer clientId);

	/**
	 * Insert information for confirm vote in survey
	 * 
	 * @param uuid
	 * @param surveyId
	 */
	public void insertForConfirmVote(String uuid, Integer participantId, Integer surveyId);
}
