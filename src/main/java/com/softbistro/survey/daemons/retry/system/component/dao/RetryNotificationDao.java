package com.softbistro.survey.daemons.retry.system.component.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.daemons.retry.system.component.entity.RetryNotification;
import com.softbistro.survey.daemons.retry.system.component.interfaces.IRetryNotification;

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

	private static final String SQL_GET_EMAIL_NEED_TO_RESEND = "SELECT n.id, `from`, `cc`, `to`, `header`, `body`, retry_count"
			+ " FROM notification as n JOIN notification_failure AS notification_fail"
			+ " ON notification_fail.notification_id = n.id"
			+ " WHERE n.status = 'ERROR' AND notification_fail.status = 'ERROR' AND n.id = ?;";

	private static final String SQL_INSERT_RETRY_NOTIFICATION = "INSERT INTO notification_failure(`status`,`notification_id`,`exception`, `retry_count`) VALUES ('ERROR', ?, ?, 0)";

	private static final String SQL_UPDATE_RESEND_EMAIL_TO_TRY = "UPDATE notification_failure SET status = 'TRY' WHERE status = ? AND notification_id = ?";
	private static final String SQL_UPDATE_RESEND_EMAIL_TO_SENT = "UPDATE notification_failure SET status='SENT' WHERE status = ? AND notification_id = ?";
	private static final String SQL_UPDATE_RESEND_EMAIL_TO_ERROR = "UPDATE notification_failure SET status='ERROR' WHERE status = ? AND notification_id = ?";
	private static final String SQL_UPDATE_INCREASE_RETRY_COUNT_FOR_RESEND_EMAIL = "UPDATE notification_failure SET retry_count=retry_count+1 WHERE status = ? AND notification_id = ?";
	private static final String SQL_UPDATE_RESEND_EMAIL_TO_VERIFIED_ERROR = "UPDATE notification_failure SET status='VERIFIED_ERROR' WHERE status = ? AND notification_id = ?";

	@Autowired
	@Qualifier("jdbcNotificationSystem")
	private JdbcTemplate jdbcTemplateNotification;

	/**
	 * Need for getting e-mail that need try to resend in string format from
	 * database
	 * 
	 */
	public static class ConnectToDBForRetryNotification implements RowMapper<RetryNotification> {

		@Override
		public RetryNotification mapRow(ResultSet rs, int rowNum) throws SQLException {
			RetryNotification message = new RetryNotification();

			message.setId(rs.getInt(1));
			message.setSenderEmail(rs.getString(2));
			message.setReceiverCCEmail(rs.getString(3));
			message.setReceiverEmail(rs.getString(4));
			message.setHeader(rs.getString(5));
			message.setBody(rs.getString(6));
			message.setRetryCount(rs.getInt(7));

			return message;
		}
	}

	/**
	 * Get an e-mail that need to resend
	 * 
	 * @author yagi
	 * @param id
	 * @return email
	 */
	@Override
	public RetryNotification getEmailForResending(int id) {
		return Optional.ofNullable(jdbcTemplateNotification.queryForObject(SQL_GET_EMAIL_NEED_TO_RESEND,
				new ConnectToDBForRetryNotification(), id)).map(message -> message).orElse(null);
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
	 * Update status on email that try to resend to "TRY"
	 * 
	 * @author yagi
	 * @param id
	 */
	@Override
	public void updateStatusRetryMessageToTry(int id) {
		jdbcTemplateNotification.update(SQL_UPDATE_RESEND_EMAIL_TO_TRY, "ERROR", id);
	};

	/**
	 * Update status on email that try to resend and end succesfull to "SENT"
	 * 
	 * @author yagi
	 * @param id
	 */
	@Override
	public void updateStatusRetryMessageToSent(int id) {
		jdbcTemplateNotification.update(SQL_UPDATE_RESEND_EMAIL_TO_SENT, "TRY", id);
	};

	/**
	 * Update status on email that has errors to "ERROR"
	 * 
	 * @author yagi
	 */
	@Override
	public void updateStatusRetryMessageToError(int id) {
		jdbcTemplateNotification.update(SQL_UPDATE_RESEND_EMAIL_TO_ERROR, "TRY", id);
	};

	/**
	 * Update count of try on email that has errors to "ERROR" (add +1)
	 * 
	 * @author yagi
	 */
	@Override
	public void updateIncreaseRetryCountForMessageToResend(int id) {
		jdbcTemplateNotification.update(SQL_UPDATE_INCREASE_RETRY_COUNT_FOR_RESEND_EMAIL, "ERROR", id);
	}

	/**
	 * Update status on email that try to resend and had exception 3 try to
	 * "VERIFIED_ERROR"
	 * 
	 * @author yagi
	 * @param id
	 */
	@Override
	public void updateStatusRetryMessageToVerifiedError(int id) {
		jdbcTemplateNotification.update(SQL_UPDATE_RESEND_EMAIL_TO_VERIFIED_ERROR, "ERROR", id);
	};

}
