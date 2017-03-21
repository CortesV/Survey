package com.softbistro.survey.question.components.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.question.components.entity.QuestionSection;
import com.softbistro.survey.question.components.interfaces.IQuestionSection;
import com.softbistro.survey.response.Response;

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
	private final static String SQL_FOR_GETTING_QUESTION_SECTION_BY_SECTION_NAME = "SELECT * FROM survey.question_sections "
			+ "AS q WHERE q.section_name=? AND q.delete !=1";

	/**
	 * Method for creating QuestionSection
	 * 
	 * @param questionSection
	 * @return Response
	 */
	@Override
	public Response setQuestionSection(QuestionSection questionSection) {
		try {
			jdbcTemplate.update(SQL_FOR_SETTING_QUESTION_SECTION, questionSection.getSurveyId(),
					questionSection.getSectionName(), questionSection.getDescriptionShort(),
					questionSection.getDescriptionLong());
			return new Response(null, HttpStatus.CREATED, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method for updating QuestionSection
	 * 
	 * @param questionSection,
	 *            id
	 * @return Response
	 */
	@Override
	public Response updateQuestionSection(QuestionSection questionSection, Integer questionSectionId) {
		try {
			jdbcTemplate.update(SQL_FOR_UPDATING_QUESTION_SECTION, questionSection.getSurveyId(),
					questionSection.getSectionName(), questionSection.getDescriptionShort(),
					questionSection.getDescriptionLong(), questionSectionId);
			return new Response(null, HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method for deleting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return Response
	 */
	@Override
	public Response deleteQuestionSection(Integer questionSectionId) {
		try {
			jdbcTemplate.update(SQL_FOR_DELETING_QUESTION_SECTION, questionSectionId);
			return new Response(null, HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method to getting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return Response
	 */
	@Override
	public Response getQuestionSectionById(Integer questionSectionId) {
		try {
			return new Response(
					jdbcTemplate.queryForObject(SQL_FOR_GETTING_QUESTION_SECTION_BY_ID,
							new BeanPropertyRowMapper<>(QuestionSection.class), questionSectionId),
					HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method to getting QuestionSection from db by surveyId
	 * 
	 * @param surveyId
	 * @return Response
	 */
	@Override
	public Response getQuestionSectionBySurveyId(Integer surveyId) {
		try {
			return new Response(
					(List<QuestionSection>) jdbcTemplate.query(SQL_FOR_GETTING_QUESTION_SECTION_BY_SURVEY_ID,
							new BeanPropertyRowMapper<>(QuestionSection.class), surveyId),
					HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method to getting QuestionSection from db by section name
	 * 
	 * @param name
	 * @return Response
	 */
	@Override
	public Response getQuestionSectionByName(String name) {
		try {
			return new Response(jdbcTemplate.queryForObject(SQL_FOR_GETTING_QUESTION_SECTION_BY_SECTION_NAME,
					new BeanPropertyRowMapper<>(QuestionSection.class), name), HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
