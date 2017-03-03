package com.survey.softbistro.sending.gmail.component.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.survey.softbistro.sending.gmail.component.interfacee.IEmail;

@Repository
public class EmailDao implements IEmail {
	int countOfRecords = 10;

	private static final String SQL_GET_LIST_ID_NEW_SURVEYS = "SELECT id FROM survey.survey WHERE status = \"NEW\" LIMIT ? OFFSET ?";

	private static final String SQL_UPDATE_LIST_ID_NEW_SURVEYS = "UPDATE `survey`.`survey` SET `status`= ? WHERE status = ? LIMIT ?";

	// private static final String SQL_GET_LIST_EMAILS_FOR_REGISTRATION=;
	//
	// private static final String SQL_GET_LIST_EMAILS_FOR_VERIFY_PASSWORD=;

	private static final String SQL_GET_LIST_EMAIL_OF_USERS_IN_SURVEY = "SELECT p.email FROM participant AS p "
			+ "INNER JOIN connect_group_participant AS connect "
			+ "ON p.id = connect.participant_id "
			+ "INNER JOIN `group` AS g "
			+ "ON connect.group_id = g.id "
			+ "INNER JOIN connect_group_survey "
			+ "ON connect_group_survey.group_id "
			+ "INNER JOIN survey "
			+ "ON  connect_group_survey.survey_id = survey.id "
			+ "WHERE survey.id = ? GROUP BY email";

	private static final String SQL_GET_CLIENT_NAME_OF_SURVEY = "SELECT c.client_name " + "FROM clients AS c "
			+ "INNER JOIN survey " + "ON c.id = survey.client_id " + "WHERE survey.name = ?";

	private static final String SQL_GET_THEME_OF_SURVEY = "SELECT survey.theme FROM survey WHERE survey.name = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

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
	 * Need for geting emails in string format from database
	 * 
	 * @author zviproject
	 *
	 */
	public static class GetId implements RowMapper<Integer> {

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
	public List<String> getEmailsForSending(Integer page) {

		List<String> allEmailsOfUsers = new ArrayList<>();

		List<String> emailsOfUsers = new ArrayList<>();

		for (int surveyId : getSurveysId(page)) {

			emailsOfUsers = jdbcTemplate.query(SQL_GET_LIST_EMAIL_OF_USERS_IN_SURVEY, new ConnectToDB(), surveyId);

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

	// /**
	// * Getting information about name survey
	// */
	// public String getSurveyName(String surveyName) {
	// String name = jdbcTemplate.//----(SQL_GET_THEME_OF_SURVEY, new
	// ConnectToDB(), surveyName);
	// return name;
	// }
}
