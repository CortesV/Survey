package com.softbistro.survey.daemons.notification.system.component.dao;

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

	private static final String SQL_GET_LIST_EMAIL_NEED_TO_SEND = "SELECT n.id, `from`, `password`, description, `cc`, `to`, `header`, `body` "
			+ "FROM notification AS n JOIN sender AS s ON n.from=s.sender_email "
			+ "WHERE (status='NEW' and `to` !='')  LIMIT ? ";

	private static final String SQL_UPDATE_LIST_EMAIL_TO_IN_PROCESS = "UPDATE notification SET status='IN_PROCESS' WHERE status = ? LIMIT ?";

	private static final String SQL_UPDATE_LIST_EMAIL_TO_PROCESSED = "UPDATE notification SET status='PROCESSED' WHERE status = ? AND id = ?";

	private static final String SQL_UPDATE_LIST_EMAIL_TO_ERROR = "UPDATE notification SET status='ERROR' WHERE status = ? AND id = ?";

	private static final String SQL_INSERT_NOTIFICATION = "INSERT INTO notification(`from`,`cc`, `to`, `header`, `body`,`status`) VALUES (?,?,?,?,?,'NEW')";

	@Autowired
	@Qualifier("jdbcNotificationSystem")
	private JdbcTemplate jdbcTemplateNotification;

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

	@Override
	public List<Notification> getEmailsForSending() {
		return jdbcTemplateNotification.query(SQL_GET_LIST_EMAIL_NEED_TO_SEND, new ConnectToDBForNotification(),
				countOfRecords);
	}

	@Override
	public void updateStatusMessagesToInProcess() {
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_EMAIL_TO_IN_PROCESS, "NEW", countOfRecords);
	}

	@Override
	public void updateStatusMessagesFromInProcessToProcessed(int id) {
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_EMAIL_TO_PROCESSED, "IN_PROCESS", id);
	}

	@Override
	public void updateStatusMessagesFromErrorToProcessed(int id) {
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_EMAIL_TO_PROCESSED, "ERROR", id);
	}

	@Override
	public void updateStatusMessagesToError(int id) {
		jdbcTemplateNotification.update(SQL_UPDATE_LIST_EMAIL_TO_ERROR, "IN_PROCESS", id);
	}

	@Override
	public void insertIntoNotification(Notification notification) {
		jdbcTemplateNotification.update(SQL_INSERT_NOTIFICATION, notification.getSenderEmail(),
				notification.getReceiverCCEmail(), notification.getReceiverEmail(), notification.getHeader(),
				notification.getBody());
	}

}
