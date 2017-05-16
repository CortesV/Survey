package com.softbistro.survey.daemons.notification.system.component.dao;

import java.sql.Date;
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

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.component.entity.NotificationClientSending;
import com.softbistro.survey.daemons.notification.system.component.entity.NotificationSurveySending;
import com.softbistro.survey.daemons.notification.system.component.interfaces.ISendingMessage;

/**
 * Class for working with notification
 * 
 * @author yagi, alex_alokhin, zviproject
 *
 */
@Repository
@Scope("prototype")
public class MessageDao implements ISendingMessage {

	@Value("${count.of.records}")
	private int countOfRecords;

	private static final String SQL_GET_LIST_EMAIL_NEED_TO_SEND = "SELECT `from`, `password`, description, `cc`, `to`, `header`, `body` "
			+ "FROM notification AS n JOIN sender AS s ON n.from=s.sender_email "
			+ "WHERE (status='NEW' and `to` !='')  LIMIT ? ";

	private static final String SQL_UPDATE_LIST_EMAIL_NEED_RESENDING_TO_NEW = "UPDATE notification SET status='NEW' WHERE status = ? LIMIT ?";

	private static final String SQL_UPDATE_LIST_EMAIL_TO_IN_PROCESS = "UPDATE notification SET status='IN_PROCESS' WHERE status = ? LIMIT ?";

	private static final String SQL_UPDATE_LIST_EMAIL_TO_PROCESSED = "UPDATE notification SET status='PROCESSED' WHERE status = ? LIMIT ?";

	private static final String SQL_UPDATE_LIST_EMAIL_TO_ERROR = "UPDATE notification SET status='ERROR' WHERE status = ? LIMIT ?";

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

			message.setSenderEmail(rs.getString(1));
			message.setSenderPassword(rs.getString(2));
			message.setSenderDescription(rs.getString(3));
			message.setReceiverCCEmail(rs.getString(4));
			message.setReceiverEmail(rs.getString(5));
			message.setHeader(rs.getString(6));
			message.setBody(rs.getString(7));

			return message;
		}
	}

	/**
	 * Get an e-mails that need to send
	 * 
	 * @author yagi
	 * @return List<Notification>
	 */
	@Override
	public List<Notification> getEmailsForSending() {
		return jdbcTemplateNotification.query(SQL_GET_LIST_EMAIL_NEED_TO_SEND, new ConnectToDBForNotification(),
				countOfRecords);
	}

	/**
	 * Update status on emails that need to resending to "NEW"
	 * 
	 * @author yagi
	 */
	@Override
	public void updateStatusMessagesToNew() {
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_EMAIL_NEED_RESENDING_TO_NEW, "IN_PROCESS", countOfRecords);
	}

	/**
	 * Update status on emails that need to send to "IN_PROCESS"
	 * 
	 * @author yagi
	 */
	@Override
	public void updateStatusMessagesToInProcess() {
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_EMAIL_TO_IN_PROCESS, "NEW", countOfRecords);
	}

	/**
	 * Update status on emails that sent to "PROCESSED"
	 * 
	 * @author yagi
	 */
	@Override
	public void updateStatusMessagesToProcessed() {
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_EMAIL_TO_PROCESSED, "IN_PROCESS", 1);
	}

	/**
	 * Update status on emails that has errors to "ERROR"
	 * 
	 * @author yagi
	 */
	@Override
	public void updateStatusMessagesToError() {
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_EMAIL_TO_ERROR, "IN_PROCESS", 1);
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
		jdbcTemplateNotification.update(SQL_INSERT_NOTIFICATION,
				 notification.getSenderEmail(), notification.getReceiverCCEmail(),
					notification.getReceiverEmail(), notification.getHeader(), notification.getBody());
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
		jdbcTemplateSending.update(SQL_INSERT_SENDING_CLIENT, notification.getUrl(), notification.getClientId(), notification.getDate());
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
		jdbcTemplateSending.update(SQL_INSERT_SENDING_PASSWORD,notification.getUrl(), notification.getClientId(),notification.getDate());
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
		jdbcTemplateSending.update(SQL_INSERT_SENDING_SURVEY, notification.getUrl(),
				notification.getParticipantId(), notification.getSurveyId(), notification.getDate());
	}

}
