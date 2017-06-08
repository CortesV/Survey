package com.softbistro.survey.daemons.notification.system.retry.system.component.interfaces;

import java.util.List;

import com.softbistro.survey.daemons.notification.system.retry.system.component.entity.RetryNotification;

/**
 * Get an e-mail that need to resend
 * 
 * @author yagi
 * @param id
 * @return email
 */
public interface IRetryNotification {

	/**
	 * Get all records from DB with e-mails of users, header and body of message
	 * for sending general information as email
	 * 
	 * @return List<RetryNotification>
	 */
	public List<RetryNotification> getAllErrorEmailsToResending();

	/**
	 * Insert notification_failure into table
	 * 
	 * @param notification_failure
	 *            - new record
	 * 
	 * @author yagi
	 */
	public void insertRetryNotification(RetryNotification retryNotification);

	/**
	 * Update list of messages that has errors to "ERROR" (increase count of
	 * retry (retry_count))
	 * 
	 * @author yagi
	 */
	public void updateIncreaseRetryCountForMessageToResend();

	/**
	 * Update status on emails that need to resending to "IN_PROCESS"
	 * 
	 * @author yagi
	 */
	public void updateStatusMessagesToInProcess();

	/**
	 * Update status on email that sent to "PROCESSED" from "IN_PROCESS"
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

}
