package com.softbistro.survey.daemons.notification.system.component.interfaces;

import java.util.List;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.component.entity.NotificationClientSending;
import com.softbistro.survey.daemons.notification.system.component.entity.NotificationSurveySending;

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
	 * Update status on emails that need to resending to "NEW"
	 */
	public void updateStatusMessagesToNew();

	/**
	 * Update status on emails that need to resending to "NEW"
	 * 
	 * @author yagi
	 */
	public void updateStatusMessagesToInProcess();

	/**
	 * Update status on emails that need to send to "IN_PROCESS"
	 * 
	 * @author yagi
	 */
	public void updateStatusMessagesToProcessed();

	/**
	 * Update status on emails that has errors to "ERROR"
	 * 
	 * @author yagi
	 */
	public void updateStatusMessagesToError();

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
	 * Insert info about client
	 * 
	 * @param notification
	 *            - new record
	 * 
	 * @author alex_alokhin
	 */
	public void insertIntoSendingClient(NotificationClientSending notification);

	/**
	 * Insert info about client
	 * 
	 * @param notification
	 *            - new record
	 * 
	 * @author alex_alokhin
	 */
	public void insertIntoSendingPassword(NotificationClientSending notification);

	/**
	 * Insert info about client
	 * 
	 * @param notification
	 *            - new record
	 * 
	 * @author alex_alokhin
	 */
	public void insertIntoSendingSurvey(NotificationSurveySending notification);

}
