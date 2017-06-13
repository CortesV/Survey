package com.softbistro.survey.daemons.notification.system.retry.system.component.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.daemons.notification.system.retry.system.component.entity.RetryNotification;
import com.softbistro.survey.daemons.notification.system.retry.system.component.interfaces.IRetryNotification;

/**
 * Class for working with notification system that retry send messages with
 * status "ERROR"
 * 
 * @author yagi
 *
 */
@Repository
@Scope("prototype")
public class RetryNotificationDao implements IRetryNotification {

	@Value("${max.count.of.retry.resend.message}")
	private int maxCountOfRetryResendMessage;

	private static final String SQL_GET_ALL_ERROR_EMAILS_NEED_TO_RESEND = "SELECT n.id, `from`, `password`, description, `cc`, `to`, `header`, `body`, retry_count"
			+ " FROM notification as n JOIN notification_failure AS notification_fail"
			+ " ON n.id=notification_fail.notification_id JOIN sender ON n.from=sender.sender_email"
			+ " WHERE n.status = 'ERROR' AND retry_count<?;";

	private static final String SQL_INSERT_RETRY_NOTIFICATION = "INSERT INTO notification_failure(`notification_id`,`exception`, `retry_count`) VALUES (?, ?, 0) ON DUPLICATE KEY UPDATE notification_id=notification_id";

	private static final String SQL_UPDATE_INCREASE_RETRY_COUNT_FOR_RESEND_EMAIL = "UPDATE notification_failure AS notification_fail"
			+ " JOIN notification AS n ON notification_fail.notification_id = n.id SET retry_count=retry_count+1 WHERE n.status='ERROR' AND retry_count<?";

	private static final String SQL_UPDATE_LIST_RESEND_EMAIL_FROM_ERROR_TO_IN_PROCESS = "UPDATE notification SET status='IN_PROCESS' WHERE status = 'ERROR'";
	private static final String SQL_UPDATE_LIST_RESEND_EMAIL_FROM_IN_PROCESS_TO_PROCESSED = "UPDATE notification SET status='PROCESSED' WHERE status = 'IN_PROCESS' AND id = ?";
	private static final String SQL_UPDATE_LIST_RESEND_EMAIL_FROM_IN_PROCESS_TO_ERROR = "UPDATE notification SET status='ERROR' WHERE status = 'IN_PROCESS' AND id = ?";

	@Autowired
	@Qualifier("jdbcNotificationSystem")
	private JdbcTemplate jdbcTemplateNotification;

	/**
	 * Need for getting e-mail with error that need try to resend in string
	 * format from database
	 * 
	 */
	public static class ConnectToDBForRetryNotification implements RowMapper<RetryNotification> {

		@Override
		public RetryNotification mapRow(ResultSet rs, int rowNum) throws SQLException {
			RetryNotification message = new RetryNotification();

			message.setId(rs.getInt(1));
			message.setSenderEmail(rs.getString(2));
			message.setSenderPassword(rs.getString(3));
			message.setSenderDescription(rs.getString(4));
			message.setReceiverCCEmail(rs.getString(5));
			message.setReceiverEmail(rs.getString(6));
			message.setHeader(rs.getString(7));
			message.setBody(rs.getString(8));
			message.setRetryCount(rs.getInt(9));

			return message;
		}
	}

	/**
	 * Get all records from DB with e-mails of users, header and body of message
	 * for sending general information as email (Select record which has status
	 * "ERROR" and count of retry
	 * < @Value("${max.count.of.retry.resend.message}")
	 * 
	 * @return List<RetryNotification>
	 */
	@Override
	public List<RetryNotification> getAllErrorEmailsToResending() {
		return jdbcTemplateNotification.query(SQL_GET_ALL_ERROR_EMAILS_NEED_TO_RESEND,
				new ConnectToDBForRetryNotification(), maxCountOfRetryResendMessage);
	}

	/**
	 * Insert notification_failure into table
	 * 
	 * @param notification_failure
	 *            - new record
	 * 
	 * @author yagi
	 */
	@Override
	public void insertRetryNotification(RetryNotification retryNotification) {
		jdbcTemplateNotification.update(SQL_INSERT_RETRY_NOTIFICATION, retryNotification.getId(),
				retryNotification.getTextException());
	}

	/**
	 * Update list of messages that has errors to "ERROR" (increase count of
	 * retry (retry_count))
	 * 
	 * @author yagi
	 */
	@Override
	public void updateIncreaseRetryCountForMessageToResend() {
		jdbcTemplateNotification.update(SQL_UPDATE_INCREASE_RETRY_COUNT_FOR_RESEND_EMAIL, maxCountOfRetryResendMessage);
	}

	/**
	 * Update status on emails that need to resending from "ERROR" to
	 * "IN_PROCESS"
	 * 
	 * @author yagi
	 */
	@Override
	public void updateStatusMessagesToInProcess() {
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_RESEND_EMAIL_FROM_ERROR_TO_IN_PROCESS);
	}

	/**
	 * Update status on emails that sent to "PROCESSED" from "IN_PROCESS"
	 * 
	 * @author yagi
	 */
	@Override
	public void updateStatusMessagesFromInProcessToProcessed(int id) {
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_RESEND_EMAIL_FROM_IN_PROCESS_TO_PROCESSED, id);
	}

	/**
	 * Update status on email that has errors from "IN_PROCESS" to "ERROR"
	 * 
	 * @author yagi
	 */
	@Override
	public void updateStatusMessagesToError(int id) {
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_RESEND_EMAIL_FROM_IN_PROCESS_TO_ERROR, id);
	}

}
