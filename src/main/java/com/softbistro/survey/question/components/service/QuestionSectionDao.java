package com.softbistro.survey.question.components.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.cj.api.jdbc.Statement;
import com.softbistro.survey.question.components.entity.QuestionSection;
import com.softbistro.survey.question.components.interfaces.IQuestionSection;

/**
 * Data access object for question section entity
 * 
 * @author af150416
 *
 */
@Repository
public class QuestionSectionDao implements IQuestionSection {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SQL_FOR_SETTING_QUESTION_SECTION = "INSERT INTO question_sections (question_sections.client_id, "
			+ "question_sections.section_name, question_sections.description_short, question_sections.description_long) VALUES (?, ?, ?, ?)";
	private static final String SQL_FOR_UPDATING_QUESTION_SECTION = "UPDATE question_sections As q SET q.client_id=?, "
			+ "q.section_name=?, q.description_short=?, q.description_long=? WHERE q.id=?";
	private static final String SQL_FOR_DELETING_QUESTION_SECTION = "UPDATE question_sections AS qs SET qs.delete=1 WHERE qs.id=?";
	private static final String SQL_FOR_GETTING_QUESTION_SECTION_BY_ID = "SELECT * FROM question_sections AS q WHERE q.id=? "
			+ "AND q.delete !=1";
	private static final String SQL_FOR_GETTING_QUESTION_SECTION_BY_CLIENT_ID = "SELECT * FROM question_sections AS q "
			+ "WHERE q.client_id=? AND q.delete !=1";
	private static final String SQL_FOR_GETTING_QUESTION_SECTION_BY_SURVEY_ID = "SELECT * FROM question_sections AS q "
			+ "LEFT JOIN connect_question_section_survey AS c ON c.question_section_id=q.id WHERE c.survey_id=? AND q.delete !=1 AND c.`delete`!=1";
	private static final String SQL_FOR_ADDING_QUESTION_SECTION_TO_SURVEY = "INSERT INTO connect_question_section_survey "
			+ "(connect_question_section_survey.question_section_id, connect_question_section_survey.survey_id) VALUES (?, ?)";
	private static final String SQL_FOR_DELETING_QUESTION_SECTION_FROM_SURVEY = "UPDATE connect_question_section_survey AS c "
			+ "SET c.delete = 1 WHERE c.question_section_id = ? AND c.survey_id = ?";

	/**
	 * Method for creating QuestionSection
	 * 
	 * @param questionSection
	 * @return ResponseEntity
	 */
	@Override
	public Integer setQuestionSection(QuestionSection questionSection) {

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_FOR_SETTING_QUESTION_SECTION,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, questionSection.getClientId());
				preparedStatement.setString(2, questionSection.getSectionName());
				preparedStatement.setString(3, questionSection.getDescriptionShort());
				preparedStatement.setString(4, questionSection.getDescriptionLong());
				return preparedStatement;
			}
		}, holder);

		return holder.getKey().intValue();

	}

	/**
	 * getJdbcTemplate Method for updating QuestionSection
	 * 
	 * @param questionSection,
	 *            id
	 * @return ResponseEntity
	 */
	@Override
	public void updateQuestionSection(QuestionSection questionSection, Integer questionSectionId) {

		jdbcTemplate.update(SQL_FOR_UPDATING_QUESTION_SECTION, questionSection.getClientId(),
				questionSection.getSectionName(), questionSection.getDescriptionShort(),
				questionSection.getDescriptionLong(), questionSectionId);
	}

	/**
	 * Method for deleting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return ResponseEntity
	 */
	@Override
	public void deleteQuestionSection(Integer questionSectionId) {

		jdbcTemplate.update(SQL_FOR_DELETING_QUESTION_SECTION, questionSectionId);
	}

	/**
	 * Method to getting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return ResponseEntity
	 */
	@Override
	public QuestionSection getQuestionSectionById(Integer questionSectionId) {

		return Optional
				.ofNullable(jdbcTemplate.query(SQL_FOR_GETTING_QUESTION_SECTION_BY_ID,
						new BeanPropertyRowMapper<>(QuestionSection.class), questionSectionId))
				.get().stream().findFirst().orElse(null);
	}

	/**
	 * Method to getting QuestionSection from db by surveyId
	 * 
	 * @param surveyId
	 * @return ResponseEntity
	 */
	@Override
	public List<QuestionSection> getQuestionSectionByClientId(Integer clientId) {

		return jdbcTemplate.query(SQL_FOR_GETTING_QUESTION_SECTION_BY_CLIENT_ID,
				new BeanPropertyRowMapper<>(QuestionSection.class), clientId);
	}

	/**
	 * Method to getting QuestionSection from db by surveyId
	 * 
	 * @param surveyId
	 * @return ResponseEntity
	 */
	@Override
	public List<QuestionSection> getQuestionSectionBySurveyId(Integer surveyId) {

		return jdbcTemplate.query(SQL_FOR_GETTING_QUESTION_SECTION_BY_SURVEY_ID,
				new BeanPropertyRowMapper<>(QuestionSection.class), surveyId);
	}

	/**
	 * Method for adding QuestionSection to survey
	 * 
	 * @param questionSection
	 *            id, survey id
	 * @return ResponseEntity
	 */
	@Override
	public void addQuestionSectionToSurvey(Integer questionSectionId, Integer surveyId) {

		jdbcTemplate.update(SQL_FOR_ADDING_QUESTION_SECTION_TO_SURVEY, questionSectionId, surveyId);
	}

	/**
	 * Method for deleting QuestionSection from survey
	 * 
	 * @param questionSectionId,
	 *            survey id
	 * @return ResponseEntity
	 */
	@Override
	public void deleteQuestionSectionFromSurvey(Integer questionSectionId, Integer surveyId) {

		jdbcTemplate.update(SQL_FOR_DELETING_QUESTION_SECTION_FROM_SURVEY, questionSectionId, surveyId);
	}
}
