package com.survey.softbistro.sending.gmail.component.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.survey.softbistro.sending.gmail.component.entity.SurveyMessage;
import com.survey.softbistro.sending.gmail.component.interfacee.ISurveyMessage;

@Repository
public class EmailDao implements ISurveyMessage {
	int countOfRecords = 10;

	private static final String SQL_GET_LIST_ID_NEW_SURVEYS = "SELECT id FROM survey.survey WHERE status = 'NEW' LIMIT ? OFFSET ?";

	private static final String SQL_UPDATE_LIST_ID_NEW_SURVEYS = "UPDATE `survey`.`survey` SET `status`= ? WHERE status = ? LIMIT ?";

	private static final String SQL_GET_LIST_EMAIL_OF_USERS_IN_SURVEY = "SELECT p.email , survey.name, c.client_name, p.id AS participant_id, survey.id AS survey_id "
			+ "FROM participant AS p "
			+ "INNER JOIN connect_group_participant AS connect ON p.id = connect.participant_id "
			+ "INNER JOIN `group` AS g ON connect.group_id = g.id "
			+ "INNER JOIN connect_group_survey ON connect_group_survey.group_id = g.id "
			+ "INNER JOIN survey AS participantSurvey ON  connect_group_survey.survey_id = participantSurvey.id, "
			+ "survey.survey, survey.clients AS c "
			+ "INNER JOIN survey AS surveyClient ON surveyClient.client_id = c.id "
			+ "WHERE  participantSurvey.id = ? AND survey.id=? AND surveyClient.id=? GROUP BY email";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Need for geting emails in string format from database
	 * 
	 * @author zviproject
	 *
	 */
	public static class ConnectToDB implements RowMapper<SurveyMessage> {

		@Override
		public SurveyMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
			SurveyMessage message = new SurveyMessage();

			message.setParticipantEmail(rs.getString(1));
			message.setSurveyName(rs.getString(2));
			message.setClientName(rs.getString(3));
			message.setParticipantId(rs.getInt(4));
			message.setSurveyId(5);

			return message;
		}
	}

	/**
	 * Need for geting emails in string format from database
	 * 
	 * @author zviproject
	 *
	 */
	public class GetId implements RowMapper<Integer> {

		@Override
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Integer id = rs.getInt(1);
			return id;
		}
	}

	/**
	 * Get records from DB with emails of users for sending messages about
	 * survey
	 */
	@Override
	public List<SurveyMessage> getEmailsForSending(Integer page) {

		List<SurveyMessage> allEmailsOfUsers = new ArrayList<>();

		List<SurveyMessage> emailsOfUsers = new ArrayList<>();

		// jdbcTemplate.batchUpdate(SQL_GET_LIST_EMAIL_OF_USERS_IN_SURVEY, new
		// BatchPreparedStatementSetter() {
		//
		// @Override
		// public void setValues(PreparedStatement ps, int i) throws
		// SQLException {
		// Integer surveyId = newSurveysId.get(i);
		// ps.setInt(1, surveyId);
		// ps.setInt(2, surveyId);
		// ps.setInt(3, surveyId);
		// }
		//
		// @Override
		// public int getBatchSize() {
		//
		// return newSurveysId.size();
		// }
		// });

		for (int surveyId : getSurveysId(page)) {

			emailsOfUsers = jdbcTemplate.query(SQL_GET_LIST_EMAIL_OF_USERS_IN_SURVEY, new ConnectToDB(), surveyId,
					surveyId, surveyId);

			allEmailsOfUsers.addAll(emailsOfUsers);
		}
		return allEmailsOfUsers;
	}

	/**
	 * Get id surveys from DB where status = "NEW"
	 * 
	 * @return
	 */
	private List<Integer> getSurveysId(int page) {
		List<Integer> surveysId = new ArrayList<>();
		page *= countOfRecords;
		surveysId = jdbcTemplate.query(SQL_GET_LIST_ID_NEW_SURVEYS, new GetId(), countOfRecords, page);

		String statusForChange = "NEW";
		String statusWillBeChaged = "IN_PROGRESS";

		jdbcTemplate.update(SQL_UPDATE_LIST_ID_NEW_SURVEYS, statusForChange, statusWillBeChaged, countOfRecords);

		return surveysId;
	}

}
