package com.softbistro.survey.daemons.notification.system.component.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.component.interfacee.ISendingMessage;

@Repository
@Scope("prototype")
public class MessageDao implements ISendingMessage {
	@Value("${count.of.records}")
	private int countOfRecords;

	private static final String SQL_GET_LIST_EMAIL_NEW_CLIENTS = "SELECT `from`,`cc`, `to`, `header`, `body` FROM notification "
			+ "WHERE (status='NEW' and `to` !='')  LIMIT ? ";

	private static final String SQL_UPDATE_LIST_NEW_CLIENTS = "UPDATE notification SET status='IN_PROCESS' WHERE status = ? LIMIT ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Need for getting e-mails in string format from database
	 * 
	 */
	public static class ConnectToDB implements RowMapper<Notification> {

		@Override
		public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
			Notification message = new Notification();

			message.setSenderEmail(rs.getString(1));
			message.setReceiverCCEmail(rs.getString(2));
			message.setReceiverEmail(rs.getString(3));
			message.setHeader(rs.getString(4));
			message.setBody(rs.getString(5));

			return message;
		}
	}

	/**
	 * Get an e-mails that need send
	 * 
	 * @param page
	 * @return
	 */
	@Override
	public List<Notification> getEmailsForSending() {
		List<Notification> emailsForSending = new ArrayList<>();
		emailsForSending = jdbcTemplate.query(SQL_GET_LIST_EMAIL_NEW_CLIENTS, new ConnectToDB(),
				countOfRecords);

		jdbcTemplate.update(SQL_UPDATE_LIST_NEW_CLIENTS, "NEW", countOfRecords);
		return emailsForSending;

	}
}
