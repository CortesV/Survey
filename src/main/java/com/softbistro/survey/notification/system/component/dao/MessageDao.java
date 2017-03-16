package com.softbistro.survey.notification.system.component.dao;

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

import com.softbistro.survey.notification.system.component.entity.RegistrationMessage;
import com.softbistro.survey.notification.system.component.entity.SurveyMessage;
import com.softbistro.survey.notification.system.component.interfacee.ISendingMessage;

@Repository
@Scope("prototype")
public class MessageDao implements ISendingMessage {

	@Value("${count.of.records}")
	private int countOfRecords;

	private static final String SQL_GET_LIST_ID_NEW_SURVEYS = "SELECT id FROM survey.survey WHERE status = 'NEW' LIMIT ?";

	private static final String SQL_UPDATE_LIST_ID_NEW_SURVEYS = "UPDATE `survey`.`survey` SET `status`= 'DONE' WHERE status = 'NEW' LIMIT ?";

	private static final String SQL_GET_LIST_EMAIL_OF_USERS_IN_SURVEY = "SELECT p.email , survey.name, c.client_name, p.id AS participant_id, survey.id AS survey_id, survey.start_time, survey.finish_time "
			+ "FROM participant AS p "
			+ "INNER JOIN connect_group_participant AS connect ON p.id = connect.participant_id "
			+ "INNER JOIN `group` AS g ON connect.group_id = g.id "
			+ "INNER JOIN connect_group_survey ON connect_group_survey.group_id = g.id "
			+ "INNER JOIN survey AS participantSurvey ON  connect_group_survey.survey_id = participantSurvey.id, "
			+ "survey.survey, survey.clients AS c "
			+ "INNER JOIN survey AS surveyClient ON surveyClient.client_id = c.id "
			+ "WHERE  participantSurvey.id = ? AND survey.id=? AND surveyClient.id=? GROUP BY email";

	private static final String SQL_GET_LIST_EMAIL_NEW_CLIENTS = "SELECT clients.client_name, clients.email FROM clients "
			+ "WHERE clients.status='NEW'  LIMIT ? ";

	private static final String SQL_GET_LIST_EMAIL_UPDATE_PASSWORD = "SELECT clients.client_name, clients.email FROM clients "
			+ "WHERE clients.status='VERIFY_PASSWORD'  LIMIT ? ";

	private static final String SQL_UPDATE_LIST_NEW_CLIENTS = "UPDATE clients SET status='IN_PROGRESS' WHERE status = ? LIMIT ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Need for geting emails in string format from database
	 * 
	 * @author zviproject
	 *
	 */
	public static class ConnectToDBforSurvey implements RowMapper<SurveyMessage> {

		@Override
		public SurveyMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
			SurveyMessage message = new SurveyMessage();

			message.setParticipantEmail(rs.getString(1));
			message.setSurveyName(rs.getString(2));
			message.setClientName(rs.getString(3));
			message.setParticipantId(rs.getInt(4));
			message.setSurveyId(rs.getInt(5));
			message.setSurveyStartTime(rs.getDate(6));
			message.setSurveyFinashTime(rs.getDate(7));

			return message;
		}
	}

	/**
	 * Need for geting information for message about new registration client and
	 * password
	 * 
	 * @author zviproject
	 *
	 */
	public class ConnectToDBforRegistration implements RowMapper<RegistrationMessage> {

		@Override
		public RegistrationMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
			RegistrationMessage message = new RegistrationMessage();

			message.setClientName(rs.getString(1));
			message.setClientEmail(rs.getString(2));

			return message;
		}
	}

	/**
	 * Get emails new clients for confirm registration new account
	 * 
	 * @param page
	 * @return
	 */
	@Override
	public List<RegistrationMessage> getEmailOfNewClients() {
		List<RegistrationMessage> clientsEmails = new ArrayList<>();
		clientsEmails = jdbcTemplate.query(SQL_GET_LIST_EMAIL_NEW_CLIENTS, new ConnectToDBforRegistration(),
				countOfRecords);

		jdbcTemplate.update(SQL_UPDATE_LIST_NEW_CLIENTS, "NEW", countOfRecords);
		return clientsEmails;

	}

	/**
	 * Get emails of clients that changed password for confirm it.
	 * 
	 * @param page
	 * @return
	 */
	@Override
	public List<RegistrationMessage> getEmailOfNewPassword() {
		List<RegistrationMessage> clientsEmails = new ArrayList<>();
		clientsEmails = jdbcTemplate.query(SQL_GET_LIST_EMAIL_UPDATE_PASSWORD, new ConnectToDBforRegistration(),
				countOfRecords);

		jdbcTemplate.update(SQL_UPDATE_LIST_NEW_CLIENTS, "VERIFY_PASSWORD", countOfRecords);
		return clientsEmails;

	}

	/**
	 * Get records from DB with emails of users for sending messages about <br>
	 * survey
	 */
	@Override
	public List<SurveyMessage> getEmailsForSending() {

		List<SurveyMessage> allEmailsOfUsers = new ArrayList<>();

		List<SurveyMessage> emailsOfUsers = new ArrayList<>();

		for (int surveyId : getSurveysId()) {

			emailsOfUsers = jdbcTemplate.query(SQL_GET_LIST_EMAIL_OF_USERS_IN_SURVEY, new ConnectToDBforSurvey(),
					surveyId, surveyId, surveyId);

			allEmailsOfUsers.addAll(emailsOfUsers);
		}
		return allEmailsOfUsers;
	}

	private List<Integer> getSurveysId() {
		List<Integer> surveysId = new ArrayList<>();
		surveysId = jdbcTemplate.queryForList(SQL_GET_LIST_ID_NEW_SURVEYS, Integer.class, countOfRecords);

		jdbcTemplate.update(SQL_UPDATE_LIST_ID_NEW_SURVEYS, countOfRecords);

		return surveysId;
	}

}