package com.survey.softbistro.creating.survey.component.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.survey.softbistro.creating.survey.component.entity.Group;
import com.survey.softbistro.creating.survey.component.entity.Survey;
import com.survey.softbistro.creating.survey.component.interfacee.ISurvey;
import com.survey.softbistro.response.Response;
import com.survey.softbistro.response.Status;

@Repository
public class SurveyDao implements ISurvey {

	private static final String SQL_INSERT_INFORMATION_ABOUT_SURVEY = "INSERT INTO survey.survey (client_id, name, theme,start_time, finish_time) VALUES(?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE_NAME_OF_SURVEY = "UPDATE survey SET survey.name = ? WHERE survey.id = ?";
	private static final String SQL_GET_LIST_OF_SURVEYS = "SELECT * FROM survey WHERE client_id = ?";
	private static final String SQL_GET_LIST_OF_GROUPS = "SELECT * FROM survey.group WHERE client_id = ?";
	private static final String SQL_ADD_GROUP_TO_SURVEY = "INSERT INTO survey.connect_group_survey (survey_id, group_id) VALUES(?,?) ";
	@Autowired
	JdbcTemplate jdbcTemplate;

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
	 * @return Response
	 */
	@Override
	public Response createSurvey(Survey survey) {
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INFORMATION_ABOUT_SURVEY,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, survey.getClientId());
			preparedStatement.setString(2, survey.getSurveyName());
			preparedStatement.setString(3, survey.getSurveyTheme());
			preparedStatement.setDate(4, survey.getStartTime());
			preparedStatement.setDate(5, survey.getFinishTime());

			preparedStatement.execute();

			return new Response(preparedStatement.getGeneratedKeys().getInt(1), Status.DONE);
		} catch (SQLException e) {
			e.printStackTrace();
			return new Response(0, Status.ERROR);
		}

	}

	/**
	 * Update name of survey in database
	 * 
	 * @param surveyId
	 *            - id of survey that will be changed
	 */
	@Override
	public Response updateNameOfSurvey(String newNameOfSurvey, Integer surveyId) {
		jdbcTemplate.update(SQL_UPDATE_NAME_OF_SURVEY, newNameOfSurvey, surveyId);
		return new Response(surveyId, Status.DONE);
	}

	/**
	 * Get all surveys from one client
	 * 
	 * @param clientId
	 * @return
	 */
	@Override
	public List<Survey> getAllSurveysOfClient(Integer clientId) {
		List<Survey> surveys = jdbcTemplate.query(SQL_GET_LIST_OF_SURVEYS, new ListOfSurveys(), clientId);
		return surveys;
	}

	/**
	 * Get all groups that has client
	 * 
	 * @param clientId
	 * @return
	 */
	@Override
	public List<Group> getGroups(Integer clientId) {
		List<Group> groups = jdbcTemplate.query(SQL_GET_LIST_OF_GROUPS, new ListOfGroups(), clientId);
		return groups;
	}

	/**
	 * Adding groups that will be in survey
	 * 
	 * @param groups
	 * @return
	 */
	@Override
	public Status addGroupsToSurvey(List<Group> groups) {
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
		return Status.DONE;
	}

}
