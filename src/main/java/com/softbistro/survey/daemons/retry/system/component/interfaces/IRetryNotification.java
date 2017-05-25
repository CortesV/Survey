package com.softbistro.survey.daemons.retry.system.component.interfaces;

import com.softbistro.survey.daemons.retry.system.component.entity.RetryNotification;

/**
 * Get an e-mail that need to resend
 * 
 * @author yagi
 * @param id
 * @return email
 */
public interface IRetryNotification {

	/**
	 * Get an e-mail that need to resend
	 * 
	 * @author yagi
	 * @param id
	 * @return email
	 */
	public RetryNotification getEmailForResending(int id);

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
	 * Update status on email that try to resend to "TRY"
	 * 
	 * @author yagi
	 * @param id
	 */
	public void updateStatusRetryMessageToTry(int id);

	/**
	 * Update status on email that try to resend and end succesfull to "SENT"
	 * 
	 * @author yagi
	 * @param id
	 */
	public void updateStatusRetryMessageToSent(int id);

	/**
	 * Update status on email that has errors to "ERROR"
	 * 
	 * @author yagi
	 */
	public void updateStatusRetryMessageToError(int id);

	/**
	 * Update count of try on email that has errors to "ERROR" (add +1)
	 * 
	 * @author yagi
	 */
	public void updateIncreaseRetryCountForMessageToResend(int id);

	/**
	 * Update status on email that try to resend and had exception 3 try to
	 * "VERIFIED_ERROR"
	 * 
	 * @author yagi
	 * @param id
	 */
	public void updateStatusRetryMessageToVerifiedError(int id);

}
