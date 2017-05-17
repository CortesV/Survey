package com.softbistro.survey.imports.system.components.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.mysql.cj.api.jdbc.Statement;
import com.softbistro.survey.imports.system.components.entities.GroupQuestions;
import com.softbistro.survey.imports.system.components.entities.Question;
import com.softbistro.survey.imports.system.components.entities.Survey;
import com.softbistro.survey.imports.system.components.interfaces.ISurvey;
import com.softbistro.survey.response.Response;

@Repository
public class SurveyDAO implements ISurvey {

	private static final String INSERT_SURVEY = " INSERT INTO survey (client_id, name) VALUES ( ?, ?); ";
	private static final String INSERT_GROUP = " INSERT INTO question_sections (section_name) VALUES ( ? ) ";
	private static final String INSERT_CONNECT_SECTION_QUESTION_SURVEY = " INSERT INTO connect_question_section_survey (question_section_id, survey_id) VALUES ( ?, ? ) ";
	private static final String INSERT_QUESTION = " INSERT INTO questions (survey_id, question, question_section_id, answer_type, question_choices, required, required_comment) VALUES (:survey_id, :question, :question_section_id, :answer_type, :question_choices, :required, :required_comment) ";

	private static Logger log = Logger.getLogger(SurveyDAO.class);

	@Autowired
	private JdbcTemplate jdbc;

	@Override
	/**
	 * Save survey into db
	 * 
	 * @param Survey
	 *            survey
	 * @return Response
	 */
	public Response saveSurvey(Survey survey) {

		Connection connection = null;
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;

		try {
			connection = jdbc.getDataSource().getConnection();
			namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbc.getDataSource());
			connection.setAutoCommit(false);

			// Save survey to db and set generate id for survey
			survey.setId(executeStatement(INSERT_SURVEY, new Object[] { survey.getClienId(), survey.getTitle() },
					connection));

			for (GroupQuestions group : survey.getGroupQuestions()) {

				// Save group to db and set generate id for current group.
				group.setId(executeStatement(INSERT_GROUP, new Object[] { group.getTitle() }, connection));

				// Save connection between survey and current group.
				jdbc.update(INSERT_CONNECT_SECTION_QUESTION_SURVEY, new Object[] { group.getId(), survey.getId() });

				// Begin saving question of current group to db.
				List<Question> batchQuestions = new ArrayList<>();
				for (Question question : group.getQuestions()) {

					question.setSurvey_id(survey.getId());
					question.setQuestion_section_id(group.getId());
					batchQuestions.add(question);
				}

				SqlParameterSource[] sqlBatchQuestions = SqlParameterSourceUtils.createBatch(batchQuestions.toArray());
				namedParameterJdbcTemplate.batchUpdate(INSERT_QUESTION, sqlBatchQuestions);
				// End saving question.
			}

			connection.commit();
			connection.close();

			return new Response(survey.getTitle(), HttpStatus.CREATED, "Survey is saved");

		} catch (SQLException e) {

			log.error(e.getMessage());

			return new Response(null, HttpStatus.FAILED_DEPENDENCY, "Survey don't save");

		} finally {

			try {

				if (connection != null && !connection.isClosed())
					connection.close();
			} catch (SQLException e) {

				log.error(e.getMessage());
			}
		}

	}

	/**
	 * Execute statement and return generated Id.
	 * 
	 * @param preparedStatement
	 * @return Long
	 */
	public Long executeStatement(String query, Object[] attributes, Connection connection) throws SQLException {

		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		for (Integer numberAttribute = 0; numberAttribute < attributes.length; numberAttribute++) {

			Object object = attributes[numberAttribute];

			if (object.getClass().equals(String.class)) {
				preparedStatement.setString(numberAttribute + 1, String.valueOf(object));
				continue;
			}

			if (object.getClass().equals(Long.class)) {
				preparedStatement.setLong(numberAttribute + 1, (Long) object);
				continue;
			}

			if (object.getClass().equals(Integer.class)) {
				preparedStatement.setInt(numberAttribute + 1, (Integer) object);
				continue;
			}

			if (object.getClass().equals(Boolean.class)) {
				preparedStatement.setBoolean(numberAttribute + 1, (Boolean) object);
				continue;
			}

		}

		preparedStatement.executeUpdate();
		ResultSet keys = preparedStatement.getGeneratedKeys();
		Long generateId = null;

		if (keys.next()) {
			generateId = keys.getLong(1);
		}

		preparedStatement.close();
		return generateId;

	}

}

/*
 * preparedStatement = connection.prepareStatement(INSERT_SURVEY,
 * Statement.RETURN_GENERATED_KEYS); preparedStatement.setLong(1,
 * survey.getClienId()); preparedStatement.setString(2, survey.getTitle());
 * survey.setId(executeStatement(preparedStatement));
 */
/*
 * preparedStatement = connection.prepareStatement(INSERT_GROUP,
 * Statement.RETURN_GENERATED_KEYS); preparedStatement.setString(1,
 * group.getTitle()); group.setId(executeStatement(preparedStatement));
 */

/*
 * preparedStatement = connection.prepareStatement(
 * INSERT_CONNECT_SECTION_QUESTION_SURVEY, Statement.RETURN_GENERATED_KEYS);
 * preparedStatement.setLong(1, group.getId()); preparedStatement.setLong(2,
 * survey.getId()); preparedStatement.executeUpdate();
 */