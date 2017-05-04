package com.softbistro.survey.daemons.notification.system.component.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.component.entity.NotificationRowMapper;
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
	
	
	
	@Autowired
	@Qualifier("jdbcNotificationSystem")
	private JdbcTemplate jdbcTemplateNotification;
	
	/**
	 * Get an e-mails that need to send
	 * 
	 * @author yagi
	 * @return List<Notification>
	 */
	@Override
	public List<Notification> getEmailsForSending() {
		return jdbcTemplateNotification.query(SQL_GET_LIST_EMAIL_NEED_TO_SEND, new NotificationRowMapper(),countOfRecords);
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
				new Object[] {notification.getSenderEmail(), notification.getReceiverCCEmail(),
						notification.getReceiverEmail(), notification.getHeader(), notification.getBody() });
	}

}
