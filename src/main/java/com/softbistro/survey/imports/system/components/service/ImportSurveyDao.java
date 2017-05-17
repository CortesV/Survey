package com.softbistro.survey.imports.system.components.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.cj.api.jdbc.Statement;
import com.softbistro.survey.imports.system.components.entities.ImportSurvey;
import com.softbistro.survey.imports.system.components.interfaces.IImportSurveyDAO;
import com.softbistro.survey.question.components.entity.Question;

/**
 * Save survey into db
 * 
 * @author olegnovatskiy
 */
@Repository
public class ImportSurveyDao implements IImportSurveyDAO {

	private static final String SQL_INSERT_SURVEY = "INSERT INTO survey (client_id, name, start_time, finish_time) VALUES (?,?,?,?)";

	private static final String SQL_INSERT_GROUP = "INSERT INTO question_sections (section_name) VALUES (?);";

	private static final String SQL_CONNECT_GROUP_OF_QUESTIONS_TO_SURVEY = "INSERT INTO connect_question_section_survey (question_section_id, survey_id) VALUES (?,?);";

	private static final String SQL_INSERT_QUESTION = "INSERT INTO questions (survey_id, question, question_section_id, answer_type, question_choices, required, required_comment) VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final Logger LOGGER = Logger.getLogger(ImportSurveyDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Integer generatedSurveyId = 0;

	/**
	 * Save survey into db
	 * 
	 * @param Survey
	 *            survey
	 * @return Response
	 */
	@Override
	@Transactional
	public Integer saveSurvey(ImportSurvey importSurvey) {
		Connection connection = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			insertSurvey(connection, importSurvey);
			insertGroups(connection, importSurvey);
			insertQuestions(importSurvey);

			LOGGER.info("INSERT OF SURVEY DONE");
		} catch (SQLException e) {

			LOGGER.error(e.getMessage());

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}

		return generatedSurveyId;

	}

	private Integer insertSurvey(Connection connection, ImportSurvey importSurvey) {
		try {

			PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_SURVEY,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, importSurvey.getClienId());
			preparedStatement.setString(2, importSurvey.getTitle());
			preparedStatement.setDate(3, new Date(importSurvey.getStartTime().getTime()));
			preparedStatement.setDate(4, new Date(importSurvey.getFinishTime().getTime()));
			preparedStatement.executeUpdate();

			ResultSet keys = preparedStatement.getGeneratedKeys();

			if (keys.next()) {
				generatedSurveyId = keys.getInt(1);
			}

			importSurvey.setId(generatedSurveyId);

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return generatedSurveyId;

	}	

	private void insertGroups(Connection connection, ImportSurvey importSurvey) {
		try {
			Map<String, Integer> groups = importSurvey.getGroups();

			Set<String> groupsName = importSurvey.getGroups().keySet();

			groupsName.stream().forEach(groupName -> connectGroupQuestionAndSurvey(connection, importSurvey, groupName, groups));

			importSurvey.setGroups(groups);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

	}
	
	private void connectGroupQuestionAndSurvey(Connection connection, ImportSurvey importSurvey, String groupName, Map<String, Integer> groups){
		
		try{
			
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_GROUP,
					Statement.RETURN_GENERATED_KEYS);
			ResultSet keys;
			
			preparedStatement.setString(1, groupName);
			preparedStatement.executeUpdate();
			keys = preparedStatement.getGeneratedKeys();

			Integer generatedId = 0;
			if (keys.next()) {
				generatedId = keys.getInt(1);
			}

			groups.put(groupName, generatedId);

			// Connect group to survey
			jdbcTemplate.update(SQL_CONNECT_GROUP_OF_QUESTIONS_TO_SURVEY, groups.get(groupName),
					importSurvey.getId());
			
		}catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	private void insertQuestions(ImportSurvey importSurvey) {
		jdbcTemplate.batchUpdate(SQL_INSERT_QUESTION, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
				Question question = importSurvey.getQuestions().get(i);
				preparedStatement.setInt(1, importSurvey.getId());
				preparedStatement.setString(2, question.getQuestion());
				preparedStatement.setInt(3, importSurvey.getGroups().get(question.getGroupName()));
				preparedStatement.setString(4, question.getAnswerType());
				preparedStatement.setString(5, question.getQuestionChoices());
				preparedStatement.setBoolean(6, question.isRequired());
				preparedStatement.setBoolean(7, question.isRequiredComment());
			}

			@Override
			public int getBatchSize() {
				return importSurvey.getQuestions().size();
			}
		});

	}

}