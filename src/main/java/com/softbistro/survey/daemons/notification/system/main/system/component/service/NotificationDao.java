package com.softbistro.survey.daemons.notification.system.main.system.component.service;

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

import com.softbistro.survey.daemons.notification.system.main.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.main.system.component.interfaces.INotification;
import com.softbistro.survey.notification.db.entity.NotificationClientSending;
import com.softbistro.survey.notification.db.entity.NotificationSurveySending;

/**
 * Class for working with notification
 * 
 * @author yagi, alex_alokhin
 *
 */
@Repository
@Scope("prototype")
public class NotificationDao implements INotification {

	@Value("${count.of.records}")
	private int countOfRecords;

	@Value("${count.of.minutes.then.thread.considered.stopped}")
	private int countOfMinutesThenThreadConsideredStopped;

	private static final String SQL_GET_LIST_ALL_EMAILS_NEED_TO_SEND = "SELECT n.id, `from`, `password`, description, `cc`, `to`, `header`, `body`"
			+ " FROM notification AS n JOIN sender AS s ON n.from=s.sender_email WHERE status='NEW'"
			+ " OR (status='IN_PROCESS' AND TIMESTAMPDIFF(MINUTE, n.modified_date, NOW())>=?)";

	private static final String SQL_GET_LIST_EMAILS_FOR_THREAD_NEED_TO_SEND = "SELECT n.id, `from`, `password`, description, `cc`, `to`, `header`, `body`"
			+ " FROM notification AS n JOIN sender AS s ON n.from=s.sender_email WHERE status='NEW'"
			+ " OR (status='IN_PROCESS' AND TIMESTAMPDIFF(MINUTE, n.modified_date, NOW())>=?) LIMIT ?";

	private static final String SQL_UPDATE_LIST_EMAIL_TO_IN_PROCESS = "UPDATE notification SET status='IN_PROCESS' WHERE status = ?";
	private static final String SQL_UPDATE_LIST_EMAIL_TO_PROCESSED = "UPDATE notification SET status='PROCESSED' WHERE status = ? AND id = ?";
	private static final String SQL_UPDATE_LIST_EMAIL_TO_ERROR = "UPDATE notification SET status='ERROR' WHERE status = ? AND id = ?";

	private static final String SQL_INSERT_NOTIFICATION = "INSERT INTO notification(`from`,`cc`, `to`, `header`, `body`,`status`) VALUES (?,?,?,?,?,'NEW')";
	private static final String SQL_INSERT_SENDING_CLIENT = "INSERT INTO sending_client (`url`,`client_id`, `working_time`) VALUES(?,?,?)";
	private static final String SQL_INSERT_SENDING_PASSWORD = "INSERT INTO sending_password (`url`,`client_id`,`working_time`) VALUES(?,?,?)";
	private static final String SQL_INSERT_SENDING_SURVEY = "INSERT INTO sending_survey (`url`,`participant_id`,`survey_id`,`working_time`) VALUES(?,?,?,?)";

	@Autowired
	@Qualifier("jdbcNotificationSystem")
	private JdbcTemplate jdbcTemplateNotification;

	@Autowired
	@Qualifier("jdbcSurvey")
	private JdbcTemplate jdbcTemplateSending;

	/**
	 * Need for getting e-mails in string format from database
	 * 
	 */
	public static class ConnectToDBForNotification implements RowMapper<Notification> {

		@Override
		public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
			Notification message = new Notification();

			message.setId(rs.getInt(1));
			message.setSenderEmail(rs.getString(2));
			message.setSenderPassword(rs.getString(3));
			message.setSenderDescription(rs.getString(4));
			message.setReceiverCCEmail(rs.getString(5));
			message.setReceiverEmail(rs.getString(6));
			message.setHeader(rs.getString(7));
			message.setBody(rs.getString(8));

			return message;
		}
	}

	/**
	 * Get all records from DB with e-mails of users, header and body of message
	 * for sending general information about survey as email
	 * 
	 * @return List<Notification>
	 */
	@Override
	public List<Notification> getAllEmailsToSending() {
		return jdbcTemplateNotification.query(SQL_GET_LIST_ALL_EMAILS_NEED_TO_SEND, new ConnectToDBForNotification(),
				countOfMinutesThenThreadConsideredStopped);
	}

	/**
	 * Get records from DB with e-mails of users, header and body of message for
	 * sending general information about survey as email get messages with
	 * status "NEW" and with status "IN_PROCESS" where (current time - last
	 * modified date (time)) > = 5 minutes
	 * 
	 * @return List<Notification>
	 */
	@Override
	public List<Notification> getEmailsToSendingForThread() {
		return jdbcTemplateNotification.query(SQL_GET_LIST_EMAILS_FOR_THREAD_NEED_TO_SEND,
				new ConnectToDBForNotification(), countOfMinutesThenThreadConsideredStopped, countOfRecords);
	}

	/**
	 * Update status on emails that need to sending to "IN_PROCESS"
	 * 
	 * @author yagi
	 */
	@Override
	public void updateStatusMessagesToInProcess() {
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_EMAIL_TO_IN_PROCESS, "NEW");
	}

	/**
	 * Update status on email that sent to "PROCESSED" from "IN_PROCESS"
	 * 
	 * @author yagi
	 */
	@Override
	public void updateStatusMessagesFromInProcessToProcessed(int id) {
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_EMAIL_TO_PROCESSED, "IN_PROCESS", id);
	}

	/**
	 * Update status on email that sent to "PROCESSED" from "ERROR"
	 * 
	 * @author yagi
	 */
	@Override
	public void updateStatusMessagesFromErrorToProcessed(int id) {
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_EMAIL_TO_PROCESSED, "ERROR", id);
	}

	/**
	 * Update status on email that has errors to "ERROR"
	 * 
	 * @author yagi
	 */
	@Override
	public void updateStatusMessagesToError(int id) {
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_EMAIL_TO_ERROR, "IN_PROCESS", id);
	}

	/**
	 * Insert notification into table
	 * 
	 * @param notification
	 *            - new record
	 * 
	 * @author alex_alokhin
	 */
	@Override
	public void insertIntoNotification(Notification notification) {
		jdbcTemplateNotification.update(SQL_INSERT_NOTIFICATION, notification.getSenderEmail(),
				notification.getReceiverCCEmail(), notification.getReceiverEmail(), notification.getHeader(),
				notification.getBody());
	}

	/**
	 * Insert info about client
	 * 
	 * @param notification
	 *            - new record
	 * 
	 * @author alex_alokhin
	 */
	@Override
	public void insertIntoSendingClient(NotificationClientSending notification) {
		jdbcTemplateSending.update(SQL_INSERT_SENDING_CLIENT, notification.getUrl(), notification.getClientId(),
				notification.getDate());
	}

	/**
	 * Insert info about client
	 * 
	 * @param notification
	 *            - new record
	 * 
	 * @author alex_alokhin
	 */
	@Override
	public void insertIntoSendingPassword(NotificationClientSending notification) {
		jdbcTemplateSending.update(SQL_INSERT_SENDING_PASSWORD, notification.getUrl(), notification.getClientId(),
				notification.getDate());
	}

	/**
	 * Insert info about client
	 * 
	 * @param notification
	 *            - new record
	 * 
	 * @author alex_alokhin
	 */
	@Override
	public void insertIntoSendingSurvey(NotificationSurveySending notification) {
		jdbcTemplateSending.update(SQL_INSERT_SENDING_SURVEY, notification.getUrl(), notification.getParticipantId(),
				notification.getSurveyId(), notification.getDate());
	}

}
