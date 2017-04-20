package com.softbistro.survey.imports.system.components.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.mysql.cj.api.jdbc.Statement;
import com.softbistro.survey.imports.system.components.entities.GroupQuestions;
import com.softbistro.survey.imports.system.components.entities.Question;
import com.softbistro.survey.imports.system.components.entities.Survey;
import com.softbistro.survey.imports.system.components.interfaces.ISurveyDAO;

/**
 * Save survey into db
 * 
 * @author olegnovatskiy
 */
@Repository
public class ImportSurveyDao implements ISurveyDAO {

	private static final String INSERT_SURVEY = "INSERT INTO survey (client_id, name) VALUES (?,?);";

	private static final String INSERT_GROUP = "INSERT INTO question_sections (section_name) VALUES (?);";

	private static final String INSERT_CONNECT_SECTION_QUESTION_SURVEY = "INSERT INTO connect_question_section_survey (question_section_id, survey_id) VALUES (?,?);";

	private static final String INSERT_QUESTION = "INSERT INTO questions (survey_id, question, question_section_id, answer_type, question_choices, required, required_comment) VALUES (:surveyId, :text, :questionSectionId, :answerType, :questionChoices, :required, :requiredComment);";

	private static Logger log = Logger.getLogger(ImportSurveyDao.class);

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
	public void saveSurvey(Survey savedSurvey) {

		Connection connection = null;
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;

		try {
			connection = jdbc.getDataSource().getConnection();
			namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbc.getDataSource());
			connection.setAutoCommit(false);

			savedSurvey.setId(executeStatement(INSERT_SURVEY,
					new Object[] { savedSurvey.getClienId(), savedSurvey.getTitle() }, connection));

			saveQuestions(savedSurvey, namedParameterJdbcTemplate, connection);

			connection.commit();
			connection.close();

		} catch (SQLException e) {

			log.error(e.getMessage());

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
	 * Save batch of questions into db.
	 * 
	 * @param savedSurvey
	 * @param namedParameterJdbcTemplate
	 * @param connection
	 * @throws SQLException
	 */
	private void saveQuestions(Survey savedSurvey, NamedParameterJdbcTemplate namedParameterJdbcTemplate,
			Connection connection) throws SQLException {

		for (GroupQuestions group : savedSurvey.getGroupQuestions()) {

			group.setId(executeStatement(INSERT_GROUP, new Object[] { group.getTitle() }, connection));

			jdbc.update(INSERT_CONNECT_SECTION_QUESTION_SURVEY, new Object[] { group.getId(), savedSurvey.getId() });

			List<Question> batchQuestions = new ArrayList<>();
			for (Question question : group.getQuestions()) {

				question.setSurveyId(savedSurvey.getId());
				question.setQuestionSectionId(group.getId());
				batchQuestions.add(question);
			}

			SqlParameterSource[] sqlBatchQuestions = SqlParameterSourceUtils.createBatch(batchQuestions.toArray());
			namedParameterJdbcTemplate.batchUpdate(INSERT_QUESTION, sqlBatchQuestions);
		}

	}

	/**
	 * Execute statement and return generated Id.
	 * 
	 * @param preparedStatement
	 * @return Long
	 */
	private Long executeStatement(String query, Object[] attributes, Connection connection) throws SQLException {

		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		for (Integer numberAttribute = 0; numberAttribute < attributes.length; numberAttribute++) {

			Object object = attributes[numberAttribute];

			if (object instanceof String) {
				preparedStatement.setString(numberAttribute + 1, String.valueOf(object));
				continue;
			}

			if (object instanceof Long) {
				preparedStatement.setLong(numberAttribute + 1, (Long) object);
				continue;
			}

			if (object instanceof Integer) {
				preparedStatement.setInt(numberAttribute + 1, (Integer) object);
				continue;
			}

			if (object instanceof Boolean) {
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