package com.softbistro.survey.creating.survey.component.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mysql.cj.api.jdbc.Statement;
import com.softbistro.survey.creating.survey.component.entity.Group;
import com.softbistro.survey.creating.survey.component.entity.Survey;
import com.softbistro.survey.creating.survey.component.interfacee.ISurvey;

@Repository
public class SurveyDao implements ISurvey {

	private static final String SQL_INSERT_INFORMATION_ABOUT_SURVEY = "INSERT INTO survey (client_id, name, theme,start_time, finish_time) VALUES(?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE_NAME_OF_SURVEY = "UPDATE survey "
			+ "SET survey.client_id=?, survey.name =?, survey.theme=?, "
			+ "survey.start_time=?, survey.finish_time=? WHERE survey.id = ?";
	private static final String SQL_GET_LIST_OF_SURVEYS = "SELECT * FROM survey WHERE client_id = ? AND survey.delete = 0";
	private static final String SQL_GET_LIST_OF_GROUPS_CLIENT = "SELECT * FROM `group` WHERE client_id = ? AND `delete`=0";
	private static final String SQL_ADD_GROUP_TO_SURVEY = "INSERT INTO connect_group_survey (survey_id, group_id) VALUES(?,?) ";
	private static final String SQL_GET_LIST_OF_GROUPS_SURVEY = "SELECT g.id, g.client_id, g.group_name FROM `group` AS g INNER JOIN connect_group_survey AS c "
			+ "ON c.group_id = g.id INNER JOIN survey AS s ON s.id= c.survey_id WHERE s.id=? AND s.delete = 0";
	private static final String SQL_DELETE_SURVEY = "UPDATE sending_survey ,`survey` AS s "
			+ "LEFT JOIN connect_group_survey AS c ON c.survey_id = s.id "
			+ "LEFT JOIN questions AS quest ON quest.survey_id = s.id "
			+ "SET s.`delete` = 1, c.`delete`= 1, quest.`delete`= 1, "
			+ "sending_survey.answer_status= 'STOPPED'  WHERE s.id = ? AND sending_survey.survey_id = ?";

	private static final String SQL_UPDATE_STATUS_OF_STARTED_SURVEY = "UPDATE survey SET `status`= 'NEW', start_time = ? WHERE survey.id = ?";

	private static final String SQL_UPDATE_STATUS_OF_STOPPED_SURVEY = "UPDATE sending_survey SET answer_status = 'STOPPED' WHERE survey_id = ?";
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private class ListOfSurveys implements RowMapper<Survey> {

		@Override
		public Survey mapRow(ResultSet rs, int rowNum) throws SQLException {
			Survey survey = new Survey();
			survey.setId(rs.getInt(1));
			survey.setClientId(rs.getInt(2));
			survey.setSurveyName(rs.getString(3));
			survey.setSurveyTheme(rs.getString(4));
			survey.setStartTime(rs.getDate(5));
			survey.setFinishTime(rs.getDate(6));

			return survey;
		}

	}

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
	public ResponseEntity<Integer> createSurvey(Survey survey) {
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
			return new ResponseEntity<Integer>(generatedId, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Update name of survey in database
	 * 
	 * @param surveyId
	 *            - id of survey that will be changed
	 */
	@Override
	public ResponseEntity<Object> updateOfSurvey(Survey survey) {
		try {
			jdbcTemplate.update(SQL_UPDATE_NAME_OF_SURVEY, survey.getClientId(), survey.getSurveyName(),
					survey.getSurveyTheme(), survey.getStartTime(), survey.getFinishTime(), survey.getId());
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get all surveys from one client
	 * 
	 * @param clientId
	 * @return
	 */
	@Override
	public ResponseEntity<List<Survey>> getAllSurveysOfClient(Integer clientId) {
		try {
			List<Survey> surveys = jdbcTemplate.query(SQL_GET_LIST_OF_SURVEYS, new ListOfSurveys(), clientId);
			return new ResponseEntity<List<Survey>>(surveys, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Survey>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get all groups that has client
	 * 
	 * @param clientId
	 * @return
	 */
	@Override
	public ResponseEntity<List<Group>> getGroupsClient(Integer clientId) {
		try {
			List<Group> groups = jdbcTemplate.query(SQL_GET_LIST_OF_GROUPS_CLIENT,
					new BeanPropertyRowMapper<>(Group.class), clientId);
			return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Group>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Adding groups that will be in survey
	 * 
	 * @param groups
	 * @return
	 */
	@Override
	public ResponseEntity<Object> addGroupsToSurvey(List<Group> groups) {
		try {
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
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get all groups that has survey
	 * 
	 * @param surveyId
	 */
	@Override
	public ResponseEntity<List<Group>> getGroupsSurvey(Integer surveyId) {
		try {
			List<Group> groups = jdbcTemplate.query(SQL_GET_LIST_OF_GROUPS_SURVEY, new ListOfGroups(), surveyId);

			return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Group>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 
	 * Delete survey from database
	 * 
	 * @param surveyId
	 */
	@Override
	public ResponseEntity<Object> deleteSurvey(Integer surveyId) {
		try {
			jdbcTemplate.update(SQL_DELETE_SURVEY, surveyId, surveyId);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Start survey
	 * 
	 * @param surveyId
	 * @return
	 */
	@Override
	public ResponseEntity<Object> startSurvey(Integer surveyId) {
		try {
			Calendar cal = Calendar.getInstance();
			Date date = new Date(cal.getTimeInMillis());
			jdbcTemplate.update(SQL_UPDATE_STATUS_OF_STARTED_SURVEY, date, surveyId);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.OK);
		}
	}

	/**
	 * Stop survey
	 * 
	 * @param surveyId
	 * @return
	 */
	@Override
	public ResponseEntity<Object> stopSurvey(Integer surveyId) {
		try {

			jdbcTemplate.update(SQL_UPDATE_STATUS_OF_STOPPED_SURVEY, surveyId);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.OK);
		}
	}

}
