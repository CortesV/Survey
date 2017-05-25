package com.softbistro.survey.daemons.notification.system.component.interfaces;

import java.util.List;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;

/**
 * Methods for working with notification
 * 
 * @author alex_alokhin
 *
 */
public interface ISendingMessage {
	/**
	 * Get records from DB with e-mails of users, header and body of message for
	 * sending general information about survey as email
	 */
	public List<Notification> getEmailsForSending();

	/**
	 * Update status on emails that need to sending to "IN_PROCESS"
	 * 
	 * @author yagi
	 */
	public void updateStatusMessagesToInProcess();

	/**
	 * Update status on emails that sent to "PROCESSED" from "IN_PROCESS"
	 * 
	 * @author yagi
	 */
	public void updateStatusMessagesFromInProcessToProcessed(int id);

	/**
	 * Update status on emails that sent to "PROCESSED" from "ERROR"
	 * 
	 * @author yagi
	 */
	public void updateStatusMessagesFromErrorToProcessed(int id);

	/**
	 * Update status on emails that has errors to "ERROR"
	 * 
	 * @author yagi
	 */
	public void updateStatusMessagesToError(int id);

	/**
	 * Insert notification into table
	 * 
	 * @param notification
	 *            - new record
	 * 
	 * @author alex_alokhin
	 */
	public void insertIntoNotification(Notification notification);

}
