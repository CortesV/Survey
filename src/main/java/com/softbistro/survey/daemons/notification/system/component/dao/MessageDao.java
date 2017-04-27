package com.softbistro.survey.daemons.notification.system.component.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
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

	private static final String SQL_GET_EMAIL_UPDATE_PASSWORD = "SELECT clients.email FROM clients "
			+ "WHERE clients.status='VERIFY_PASSWORD'  LIMIT ? ";

	private static final String SQL_GET_EMAIL_OF_USERS_IN_SURVEY = "SELECT p.email FROM participant AS p "
			+ "INNER JOIN survey AS s ON p.client_id = s.client_id " + "WHERE  s.id = ? GROUP BY email";

	private static final String SQL_GET_ID_NEW_SURVEYS = "SELECT id FROM survey WHERE status = 'NEW' LIMIT ?";

	private static final String SQL_UPDATE_LIST_ID_NEW_SURVEYS = "UPDATE `survey` SET `status`= 'DONE' WHERE status = 'NEW' LIMIT ?";

	private static final String SQL_GET_EMAIL_OF_NEW_CLIENTS = "SELECT email FROM clients "
			+ "WHERE clients.status='NEW'  LIMIT ? ";

	private static final String SQL_INSERT_NOTIFICATION = "INSERT INTO notification(`from`,`cc`, `to`, `header`, `body`) VALUES (?,?,?,?,?)";

	private static final String SQL_UPDATE_NEW_CLIENTS = "UPDATE clients SET status='DONE' WHERE status = ? LIMIT ?";

	private static final String SQL_UPDATE_LIST_NEW_NOTIFICATION = "UPDATE notification SET status='NEW' WHERE NOT status ='NEW'  LIMIT ?";

	@Autowired
	@Qualifier("jdbcNotificationSystem")
	private JdbcTemplate jdbcTemplateNotification;

	@Autowired
	@Qualifier("jdbcSurvey")
	private JdbcTemplate jdbcTemplateClient;

	/**
	 * Need for getting e-mails in string format from database
	 * 
	 */
	public static class ConnectToDB implements RowMapper<Notification> {

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
		List<Notification> emailsForSending = new ArrayList<>();
		emailsForSending = jdbcTemplateNotification.query(SQL_GET_LIST_EMAIL_NEED_TO_SEND, new ConnectToDB(),
				countOfRecords);

		return emailsForSending;
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
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_EMAIL_TO_PROCESSED, "IN_PROCESS", countOfRecords);
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
	 * For getting mails of users that were registered or changed password
	 * 
	 * @author alex_alokhin
	 *
	 */
	public class ConnectToDBforRegistrationAndPass implements RowMapper<String> {

		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString(1);
		}
	}

	/**
	 * Get mails of clients that change password
	 * 
	 * @return - list of mails
	 * 
	 * @author alex_alokhin
	 */
	@Override
	public ArrayList<String> getEmailOfNewPassword() {
		ArrayList<String> clientsEmails = new ArrayList<>();
		clientsEmails = (ArrayList<String>) jdbcTemplateClient.query(SQL_GET_EMAIL_UPDATE_PASSWORD,
				new ConnectToDBforRegistrationAndPass(), countOfRecords);
		jdbcTemplateClient.update(SQL_UPDATE_NEW_CLIENTS, "VERIFY_PASSWORD", countOfRecords);

		return clientsEmails;

	}

	/**
	 * Get mails of clients that have registration process
	 * 
	 * @return - list of mails
	 * 
	 * @author alex_alokhin
	 */
	@Override
	public ArrayList<String> getEmailOfNewClients() {
		ArrayList<String> clientsEmails = new ArrayList<>();
		clientsEmails = (ArrayList<String>) jdbcTemplateClient.query(SQL_GET_EMAIL_OF_NEW_CLIENTS,
				new ConnectToDBforRegistrationAndPass(), countOfRecords);
		jdbcTemplateClient.update(SQL_UPDATE_NEW_CLIENTS, "NEW", countOfRecords);

		return clientsEmails;
	}

	/**
	 * Get mails of clients that started the survey
	 * 
	 * @return - list of mails
	 * 
	 * @author alex_alokhin
	 */
	@Override
	public ArrayList<String> getEmailsForSendingSurvey() {
		ArrayList<String> emailsOfUsers = new ArrayList<>();
		for (int surveyId : getSurveysId()) {
			emailsOfUsers.addAll(jdbcTemplateClient.query(SQL_GET_EMAIL_OF_USERS_IN_SURVEY,
					new ConnectToDBforRegistrationAndPass(), surveyId));
		}

		return emailsOfUsers;
	}

	private List<Integer> getSurveysId() {
		List<Integer> surveysId = new ArrayList<>();
		surveysId = jdbcTemplateClient.queryForList(SQL_GET_ID_NEW_SURVEYS, Integer.class, countOfRecords);
		jdbcTemplateClient.update(SQL_UPDATE_LIST_ID_NEW_SURVEYS, countOfRecords);

		return surveysId;
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
				new Object[] { notification.getSenderEmail(), notification.getReceiverCCEmail(),
						notification.getReceiverEmail(), notification.getHeader(), notification.getBody() });
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_NEW_NOTIFICATION, countOfRecords);
	}

}
