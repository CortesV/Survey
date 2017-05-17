package com.softbistro.survey.creating.survey.component.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mysql.cj.api.jdbc.Statement;
import com.softbistro.survey.creating.survey.component.entity.Group;
import com.softbistro.survey.creating.survey.component.entity.Survey;
import com.softbistro.survey.creating.survey.component.interfacee.ISurveyDao;

@Repository
public class SurveyDao implements ISurveyDao {

	private static final String SQL_INSERT_INFORMATION_ABOUT_SURVEY = "INSERT INTO survey (client_id, name, theme,start_time, finish_time) VALUES(?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE_NAME_OF_SURVEY = "UPDATE survey "
			+ "SET survey.client_id=?, survey.name =?, survey.theme=?, "
			+ "survey.start_time=?, survey.finish_time=? WHERE survey.id = ?";
	private static final String SQL_GET_LIST_OF_SURVEYS = "SELECT * FROM survey WHERE client_id = ? AND 'delete' = 0";
	private static final String SQL_GET_LIST_OF_GROUPS_CLIENT = "SELECT * FROM `group` WHERE client_id = ? AND `delete`=0";
	private static final String SQL_ADD_GROUP_TO_SURVEY = "INSERT INTO connect_group_survey (survey_id, group_id) VALUES(?,?) ";
	private static final String SQL_GET_LIST_OF_GROUPS_SURVEY = "SELECT group.id, group.client_id, group.group_name FROM `group` INNER JOIN connect_group_survey AS cgs "
			+ "ON cgs.group_id = group.id INNER JOIN survey ON survey.id= cgs.survey_id WHERE survey.id=? AND survey.delete = 0";
	private static final String SQL_DELETE_SURVEY = "UPDATE `survey` "
			+ "LEFT JOIN sending_survey AS ss ON ss.survey_id = survey.id "
			+ "SET ss.answer_status= 'STOPPED', `delete`=1  WHERE survey.id = ? ";

	private static final String SQL_PREPARE_START_TIME_OF_SURVEY = "UPDATE survey SET start_time = ? WHERE survey.id = ? && ISNULL(`start_time`)";
	private static final String SQL_UPDATE_STATUS_OF_STARTED_SURVEY = "UPDATE survey SET `status`= 'NEW' WHERE survey.id = ?";

	private static final String SQL_UPDATE_STATUS_OF_STOPPED_SURVEY = "UPDATE sending_survey SET answer_status = 'STOPPED' WHERE survey_id = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Printing information in stack trace
	 */
	private static Logger log = Logger.getLogger(SurveyDao.class.getName());

	private class ListOfGroups implements RowMapper<Group> {

		@Override
		public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
			Group group = new Group();
			group.setId(rs.getInt(1));
			group.setClientId(rs.getInt(2));
			group.setGroupName(rs.getString(3));

			return group;
		}

	}

	/**
	 * Writing new survey into database.
	 * 
	 * @param survey
	 *            - parsed JSON with information about survey.
	 * @return ResponseEntity
	 */
	@Override
	public Integer create(Survey survey) {
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INFORMATION_ABOUT_SURVEY,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, survey.getClientId());
			preparedStatement.setString(2, survey.getSurveyName());

			preparedStatement.setString(3, survey.getSurveyTheme());
			preparedStatement.setDate(4, survey.getStartTime());
			preparedStatement.setDate(5, survey.getFinishTime());

			preparedStatement.executeUpdate();
			ResultSet keys = preparedStatement.getGeneratedKeys();

			Integer generatedId = 0;
			if (keys.next()) {
				generatedId = keys.getInt(1);
			}

			return generatedId;
		} catch (SQLException e) {
			log.error(e.getMessage());
			return 0;
		}

	}

	/**
	 * Update name of survey in database
	 * 
	 * @param surveyId
	 *            - id of survey that will be changed
	 */
	@Override
	public void update(Survey survey) {
		jdbcTemplate.update(SQL_UPDATE_NAME_OF_SURVEY, survey.getClientId(), survey.getSurveyName(),
				survey.getSurveyTheme(), survey.getStartTime(), survey.getFinishTime(), survey.getId());
	}

	/**
	 * Get all surveys from one client
	 * 
	 * @param clientId
	 * @return
	 */
	@Override
	public List<Survey> getAllSurveysByClient(Integer clientId) {
		return jdbcTemplate.query(SQL_GET_LIST_OF_SURVEYS, new BeanPropertyRowMapper<>(Survey.class), clientId);
	}

	/**
	 * Get all groups that has client
	 * 
	 * @param clientId
	 * @return
	 */
	@Override
	public List<Group> getGroupsClient(Integer clientId) {
		return jdbcTemplate.query(SQL_GET_LIST_OF_GROUPS_CLIENT, new BeanPropertyRowMapper<>(Group.class), clientId);
	}

	/**
	 * Adding groups that will be in survey
	 * 
	 * @param groups
	 * @return
	 */
	@Override
	public void addGroups(List<Group> groups) {
		jdbcTemplate.batchUpdate(SQL_ADD_GROUP_TO_SURVEY, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Group group = groups.get(i);
				ps.setInt(1, group.getSurveyId());
				ps.setInt(2, group.getId());
			}

			@Override
			public int getBatchSize() {
				return groups.size();
			}
		});

	}

	/**
	 * Get all groups that has survey
	 * 
	 * @param surveyId
	 */
	@Override
	public List<Group> getGroupsSurvey(Integer surveyId) {
		return jdbcTemplate.query(SQL_GET_LIST_OF_GROUPS_SURVEY, new ListOfGroups(), surveyId);
	}

	/**
	 * 
	 * Delete survey from database
	 * 
	 * @param surveyId
	 */
	@Override
	public void delete(Integer surveyId) {
		jdbcTemplate.update(SQL_DELETE_SURVEY, surveyId);
	}

	/**
	 * Start survey
	 * 
	 * @param surveyId
	 * @return
	 */
	@Override
	public void start(Integer surveyId) {
		LocalDate date = LocalDate.now();
		jdbcTemplate.update(SQL_PREPARE_START_TIME_OF_SURVEY, date, surveyId);
		jdbcTemplate.update(SQL_UPDATE_STATUS_OF_STARTED_SURVEY, surveyId);
	}

	/**
	 * Stop survey
	 * 
	 * @param surveyId
	 * @return
	 */
	@Override
	public void stop(Integer surveyId) {
		jdbcTemplate.update(SQL_UPDATE_STATUS_OF_STOPPED_SURVEY, surveyId);
	}

}
