package com.softbistro.survey.daemons.notification.system.component.interfaces;

import java.util.List;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.notification.db.entity.NotificationClientSending;
import com.softbistro.survey.notification.db.entity.NotificationSurveySending;

/**
 * Methods for working with notification
 * 
 * @author alex_alokhin
 *
 */
public interface INotification {

	/**
	 * Get all records from DB with e-mails of users, header and body of message
	 * for sending general information about survey as email
	 */
	public List<Notification> getAllEmailsToSending();

	/**
	 * Get records from DB with e-mails of users, header and body of message for
	 * sending general information about survey as email get messages with
	 * status "NEW" and with status "IN_PROCESS" where (current time - last
	 * modified date (time)) > = 5 minutes
	 * 
	 * @return List<Notification>
	 */
	public List<Notification> getEmailsToSendingForThread();

	/**
	 * Update status on emails that need to sending to "IN_PROCESS"
	 * 
	 * @author yagi
	 */
	public void updateStatusMessagesToInProcess();

	/**
	 * Update status on email that sent to "PROCESSED" from "IN_PROCESS"
	 * 
	 * @author yagi
	 */
	public void updateStatusMessagesFromInProcessToProcessed(int id);

	/**
	 * Update status on email that sent to "PROCESSED" from "ERROR"
	 * 
	 * @author yagi
	 */
	public void updateStatusMessagesFromErrorToProcessed(int id);

	/**
	 * Update status on email that has errors to "ERROR"
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

	/**
	 * Insert info about notification
	 * 
	 * @param notification
	 *            - new record
	 * 
	 * @author alex_alokhin
	 */
	public void insertIntoSendingClient(NotificationClientSending notification);

	/**
	 * Insert info about notification
	 * 
	 * @param notification
	 *            - new record
	 * 
	 * @author alex_alokhin
	 */
	public void insertIntoSendingPassword(NotificationClientSending notification);

	/**
	 * Insert info about notification
	 * 
	 * @param notification
	 *            - new record
	 * 
	 * @author alex_alokhin
	 */
	public void insertIntoSendingSurvey(NotificationSurveySending notification);

}
