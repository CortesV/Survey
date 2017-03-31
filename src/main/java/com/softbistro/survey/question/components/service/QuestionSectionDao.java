package com.softbistro.survey.question.components.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

	private final static String SQL_FOR_SETTING_QUESTION_SECTION = "INSERT INTO survey.question_sections "
			+ "(survey.question_sections.survey_id, survey.question_sections.section_name, "
			+ "survey.question_sections.description_short, survey.question_sections.description_long) VALUES (?, ?, ?, ?)";
	private final static String SQL_FOR_UPDATING_QUESTION_SECTION = "UPDATE survey.question_sections As q SET q.survey_id=?, "
			+ "q.section_name=?, q.description_short=?, q.description_long=? WHERE q.id=?";
	private final static String SQL_FOR_DELETING_QUESTION_SECTION = "UPDATE survey.question_sections AS qs SET qs.delete=1 WHERE qs.id=?";
	private final static String SQL_FOR_GETTING_QUESTION_SECTION_BY_ID = "SELECT * FROM survey.question_sections AS q WHERE q.id=? "
			+ "AND q.delete !=1";
	private final static String SQL_FOR_GETTING_QUESTION_SECTION_BY_SURVEY_ID = "SELECT * FROM survey.question_sections AS q "
			+ "WHERE q.survey_id=? AND q.delete !=1";

	/**
	 * Method for creating QuestionSection
	 * 
	 * @param questionSection
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<Object> setQuestionSection(QuestionSection questionSection) {
		try {
			jdbcTemplate.update(SQL_FOR_SETTING_QUESTION_SECTION, questionSection.getSurveyId(),
					questionSection.getSectionName(), questionSection.getDescriptionShort(),
					questionSection.getDescriptionLong());
			return new ResponseEntity<Object>(HttpStatus.CREATED);
		}

		catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for updating QuestionSection
	 * 
	 * @param questionSection,
	 *            id
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<Object> updateQuestionSection(QuestionSection questionSection, Integer questionSectionId) {
		try {
			jdbcTemplate.update(SQL_FOR_UPDATING_QUESTION_SECTION, questionSection.getSurveyId(),
					questionSection.getSectionName(), questionSection.getDescriptionShort(),
					questionSection.getDescriptionLong(), questionSectionId);
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for deleting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<Object> deleteQuestionSection(Integer questionSectionId) {
		try {
			jdbcTemplate.update(SQL_FOR_DELETING_QUESTION_SECTION, questionSectionId);
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to getting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<QuestionSection> getQuestionSectionById(Integer questionSectionId) {
		try {
			return new ResponseEntity<QuestionSection>(
					jdbcTemplate.queryForObject(SQL_FOR_GETTING_QUESTION_SECTION_BY_ID,
							new BeanPropertyRowMapper<>(QuestionSection.class), questionSectionId),
					HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<QuestionSection>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to getting QuestionSection from db by surveyId
	 * 
	 * @param surveyId
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<List<QuestionSection>> getQuestionSectionBySurveyId(Integer surveyId) {
		try {
			return new ResponseEntity<List<QuestionSection>>(
					(List<QuestionSection>) jdbcTemplate.query(SQL_FOR_GETTING_QUESTION_SECTION_BY_SURVEY_ID,
							new BeanPropertyRowMapper<>(QuestionSection.class), surveyId),
					HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<List<QuestionSection>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
