package com.survey.softbistro.sending.gmail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.survey.softbistro.connect.mysql.ConnectToMySQL;
import com.survey.softbistro.sending.gmail.interfacee.IEmail;

@Repository
@Component
public class EmailDao extends ConnectToMySQL implements IEmail {

	private static final String SQL_GET_LIST_EMAIL_OF_USERS = "SELECT email FROM clients";

	JdbcTemplate jdbcTemplate = new JdbcTemplate(connectToDB());

	/**
	 * Need for geting emails in string format from database
	 * 
	 * @author zviproject
	 *
	 */
	public static class ConnectToDB implements RowMapper<String> {

		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String userEmail = rs.getString(1);
			return userEmail;
		}
	}

	/**
	 * Get records from DB with emails of users for sending messages about
	 * survey
	 */
	public List<String> getEmailsForSending() {
		List<String> emailsOfUsers = new ArrayList<>();
		emailsOfUsers = jdbcTemplate.query(SQL_GET_LIST_EMAIL_OF_USERS, new ConnectToDB());
		return emailsOfUsers;
	}
}
